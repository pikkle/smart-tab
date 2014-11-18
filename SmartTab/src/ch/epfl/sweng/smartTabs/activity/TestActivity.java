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
import android.view.Window;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;

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
	
	private LinearLayout wrapper;
	private LinearLayout musicWrapper;
	private FrameLayout testWrapper;
	
	private Tab tab;
	
	private boolean running;

	private static final int PACE = 200;
	private static final float SMALLEST_DURATION = 0.25f; //double croche
	private static final double millisInMin = 60000.0;			//number of millis in one min
	
	private boolean backPressedOnce = false;
	private SoundPool pool = new SoundPool(65, AudioManager.STREAM_MUSIC, 0);
	private static final int DELAY = 2000;
	
	private int playingPosition = 136; //Position of the time to play (Intital value corresponds to the future cursor position)
	private int delay = 5;

	private int speed;
	private TablatureView tablatureView;
	private MusicSheetView musicSheetView;
	private HorizontalScrollView scroller;
	private int threshold = 200;
	private float lastX;
	private float lastY;
	private float newX;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		System.out.println("Initîalising TestActivity");
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
		
		Intent intent = getIntent();
		final Tab tab = (Tab) intent.getExtras().getSerializable("tab");

		speed = computeSpeed(tab.getTempo(), PACE, delay, millisInMin);
				
		setContentView(R.layout.activity_test);
		
		wrapper = (LinearLayout) (this.findViewById(R.id.wrapper));
		musicWrapper = new LinearLayout(getBaseContext());
		musicWrapper.setOrientation(LinearLayout.VERTICAL);
		testWrapper = new FrameLayout(getBaseContext());
		
		headerView 		= new HeaderView(getBaseContext(), tab.getTabName());
		footerView 		= new FooterView(getBaseContext());
		tablatureView 	= new TablatureView(getBaseContext(), tab, Instrument.GUITAR, PACE);
		musicSheetView 	= new MusicSheetView(getBaseContext(), tab);
		cursorView 		= new CursorView(getBaseContext());

		
		
		musicWrapper.addView(musicSheetView, weight(3));
		musicWrapper.addView(tablatureView, weight(7));
		
		testWrapper.addView(musicWrapper, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));	
		
		testWrapper.addView(cursorView, new FrameLayout.LayoutParams(
				FrameLayout.LayoutParams.MATCH_PARENT,
				FrameLayout.LayoutParams.MATCH_PARENT));
				
		
		wrapper.addView(headerView, weight(1));
		wrapper.addView(testWrapper, weight(8));	
		wrapper.addView(footerView, weight(1));
		
		// Basic scrolling
		Thread t = new Thread(new Runnable() {

			private int ptr = 0;

			
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
						musicSheetView.scrollBy(2, 0);
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
	
	/**
	 * This method computes the number of pixels to scroll by.
	 * @param tempo
	 * @param pace
	 * @param delay
	 * @param millisinmin
	 * @return the speed
	 */
	private int computeSpeed(double tempo, double pace, double delay, double millisinmin) {
		return (int) (tempo*PACE*delay/millisInMin);
	}

	private LinearLayout.LayoutParams weight(int i) {
		return new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, i);
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		
	    final float x = event.getX();
	    
		if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN){
			running = !running;
			this.lastX = x;
		}
		if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) {
			this.newX = x;
			tablatureView.scrollBy((int) (newX - lastX), 0);
			musicSheetView.scrollBy((int) (newX - lastX), 0);
			
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
