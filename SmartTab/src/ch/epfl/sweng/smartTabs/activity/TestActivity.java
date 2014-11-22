package ch.epfl.sweng.smartTabs.activity;

import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.gfx.CursorView;
import ch.epfl.sweng.smartTabs.gfx.FooterView;
import ch.epfl.sweng.smartTabs.gfx.HeaderView;
import ch.epfl.sweng.smartTabs.gfx.MusicSheetView;
import ch.epfl.sweng.smartTabs.gfx.TablatureView;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.SampleMap;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;
import android.widget.Toast;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;

public class TestActivity extends Activity {
	private HeaderView headerView;
	private FooterView footerView;
	private MusicSheetView musicSheetView;
	private TablatureView tablatureView;
	private CursorView cursorView;
	
	private RelativeLayout wrapper;
	private LinearLayout contentWrapper;
	private LinearLayout bodyWrapper;
	private LinearLayout musicWrapper;
	
	private Tab tab;
	private final Height[] stantardTuning = {Height.E, Height.B, Height.G, Height.D, Height.A, Height.E};
	
	private boolean running;
	private static final int PACE = 200;
	private static final float SMALLEST_DURATION = 0.25f; //double croche
	
	private boolean backPressedOnce = false;
	private SoundPool pool = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
	private static final int DELAY = 2000;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Intent intent = getIntent();
		final Tab tab = (Tab) intent.getExtras().getSerializable("tab");

		setContentView(R.layout.activity_test);
		
		wrapper = (RelativeLayout) (this.findViewById(R.id.wrapper));
		contentWrapper = (LinearLayout) (this.findViewById(R.id.contentWrapper));
		bodyWrapper = (LinearLayout) (this.findViewById(R.id.bodyWrapper));
		musicWrapper = (LinearLayout) (this.findViewById(R.id.musicWrapper));
		
		headerView = new HeaderView(getBaseContext(), tab.getTabName());
		footerView = new FooterView(getBaseContext());
		musicSheetView = new MusicSheetView(getBaseContext());
		tablatureView = new TablatureView(getBaseContext(), tab, Instrument.GUITAR, PACE);
		cursorView = new CursorView(getBaseContext());
		
		musicWrapper.addView(musicSheetView, weight(4));
		musicWrapper.addView(tablatureView, weight(6));
		bodyWrapper.addView(footerView, weight(1));
		contentWrapper.addView(headerView, 0, weight(1));
		wrapper.addView(cursorView);
		
		// Basic scrolling
		Thread t = new Thread(new Runnable() {
			
			@Override
			public void run() {
				while(true) {
					if (running && !tablatureView.isTerminated()) {
						tablatureView.scrollBy(1, 0);
						
					}
					
					try {
						Thread.sleep(2, 0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
		
		Thread t2 = new Thread(new Runnable() {
			
			Note[] tuning = {
					new Note(3, Height.E), new Note(2, Height.B),
					new Note(2, Height.G), new Note(2, Height.D), 
					new Note(1, Height.A), new Note(1, Height.E)
			};
			SampleMap map = new SampleMap(getApplicationContext(),pool, tuning);
			@Override
			public void run() {
				while(true) {
					if (tablatureView.getScrollX() % (PACE*SMALLEST_DURATION)== 0) {
						Time t = tab.getTimeAt(tablatureView.getScrollX()/(PACE*SMALLEST_DURATION));
						if (t != null) {
							for (int i = 0; i < (Instrument.GUITAR).getNumOfStrings(); i++) {
								String fret = t.getNote(i);
								if (!fret.equals("")) {
									int fretNumber = Integer.parseInt(fret);
									final Note note = tuning[i].addHalfTones(fretNumber);
									new Thread(new Runnable() {
										
										@Override
										public void run() {
											pool.play(map.getSampleId(note), 1, 1, 1, 0, 1);
											try {
												Thread.sleep(2000);
												pool.resume(map.getSampleId(note));
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
										}
									}).start();
									
								}
							}
						}
					}
					try {
						Thread.sleep(2);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t2.start();
	}
	
	private LinearLayout.LayoutParams weight(int i) {
		return new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, i);
	}

	private void hide(View v) {
		v.setLayoutParams(weight(0));
	}
	
	private void show(View v, int weight) {
		v.setLayoutParams(weight(weight));
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
			running = !running;
		}
		return true;
	}
	
	@Override
	public void onBackPressed() {
		if (backPressedOnce) {
			super.onBackPressed();
			running = !running;
			pool.release();
		} else {
			backPressedOnce = true;
			Toast.makeText(this, "Please click BACK again to exit", Toast.LENGTH_SHORT).show();
			
			new Handler().postDelayed(new Runnable() {

				@Override
				public void run() {
					backPressedOnce = false;
				}
			}, DELAY);
		}
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.test, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
}
