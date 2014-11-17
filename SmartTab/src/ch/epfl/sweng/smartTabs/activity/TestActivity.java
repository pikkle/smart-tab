package ch.epfl.sweng.smartTabs.activity;

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
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.RelativeLayout;
import android.widget.Toast;
import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.gfx.CursorView;
import ch.epfl.sweng.smartTabs.gfx.FooterView;
import ch.epfl.sweng.smartTabs.gfx.HeaderView;
import ch.epfl.sweng.smartTabs.gfx.MusicSheetView;
import ch.epfl.sweng.smartTabs.gfx.ScrollingView;
import ch.epfl.sweng.smartTabs.gfx.TablatureView;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.SampleMap;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;

public class TestActivity extends Activity {
	private HeaderView headerView;
	private FooterView footerView;
	private ScrollingView scrollingView;
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
	private SoundPool pool = new SoundPool(65, AudioManager.STREAM_MUSIC, 0);
	private static final int DELAY = 2000;
	
	private int playingPosition = 120; //Position of the time to play (Intital value corresponds to the future cursor position)
	private int delay = 5;
	private TablatureView tablatureView;
	private MusicSheetView musicSheetView;
	private HorizontalScrollView scroller;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		System.out.println("Initîalising TestActivity");
		
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
		//scroller = (HorizontalScrollView) (this.findViewById(R.id.scroller));
		
		headerView = new HeaderView(getBaseContext(), tab.getTabName());
		footerView = new FooterView(getBaseContext());
		tablatureView = new TablatureView(getBaseContext(), tab, Instrument.GUITAR, PACE);
		musicSheetView = new MusicSheetView(getBaseContext(), tab);
		
		cursorView = new CursorView(getBaseContext());
		
		musicWrapper.addView(musicSheetView, weight(4));
		musicWrapper.addView(tablatureView, weight(6));		
		//musicWrapper.addView(scrollingView, weight(1));
		bodyWrapper.addView(footerView, weight(1));
		contentWrapper.addView(headerView, 0, weight(1));
		wrapper.addView(cursorView);
		
		// Basic scrolling
		Thread t = new Thread(new Runnable() {
			
			Note[] tuning = {
					new Note(Height.E, 3), new Note(Height.B, 2),
					new Note(Height.G, 2), new Note(Height.D, 2), 
					new Note(Height.A, 1), new Note(Height.E, 1)
			};
			SampleMap map = new SampleMap(getApplicationContext(),pool, tuning);
			
			@Override
			public void run() {
				while(true) {
					if (running && !tablatureView.isTerminated()) {
						tablatureView.scrollBy(2, 0);
						playingPosition += 2;		//increment the position at which we want to look for a time to play
					}
					
					if (tab.timeMapContains(playingPosition)) {
						final Time t = tab.getTimeAt(playingPosition);
						if (t != null) {
						new Thread(new Runnable() {
							
							@Override
							public void run() {
								for (int i = 0; i < (Instrument.GUITAR).getNumOfStrings(); i++) {
									String fret = t.getNote(i);
									if (!fret.equals("")) {
										int fretNumber = Integer.parseInt(fret);
										final Note note = tuning[i].addHalfTones(fretNumber);
										pool.play(map.getSampleId(note), 1, 1, 1, 0, 1);
									}
								}
								
							}
						}).start();
						}
					}
					
					try {
						Thread.sleep(delay, 0);
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		t.start();
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
			Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();
			
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
