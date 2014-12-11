package ch.epfl.sweng.smartTabs.activity;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
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


/**
 * @author fatonramadani
 *
 */
public class DisplayActivity extends Activity {
	private static final String PREFS_NAME = "MyPrefsFile";
	private HeaderView headerView;
	private FooterView footerView;
	private CursorView cursorView;

	private LinearLayout wrapper;
	private LinearLayout musicWrapper;
	private FrameLayout testWrapper;
	
	private boolean running;
	
    
    private SharedPreferences sharedPrefs;
    
    private Tab tab;


	private static final int PACE = 200;
	private static final double millisInMin = 60000.0; // number of millis in one min
	private static final int OFFSET = 50;


	private boolean backPressedOnce = false;
	private SoundPool pool = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
	private static final long SAMPLE_TIME = 1000;
	private static final int DELAY = 2000;
	
	private int playingPosition; // Position of the time to play (Intital value corresponds to the future cursor position)

	private double delay;

	private int speed = 1;
	private TablatureView tablatureView;
	private MusicSheetView musicSheetView;

	private float lastX;
	private float newX;
	private int tabPosX;
	private boolean scrolled = false;
	private SampleMap map;
	private final Note[] tuning = { new Note(Height.E, 3), new Note(Height.B, 2),
			new Note(Height.G, 2), new Note(Height.D, 2),
			new Note(Height.A, 1), new Note(Height.E, 1) };
			

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		checkDialog(this);
		
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
		this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Intent intent = getIntent();
		tab = (Tab) intent.getExtras().getSerializable("tab");

		delay = computeDelay(tab.getTempo(), PACE, speed, millisInMin);

		setContentView(R.layout.activity_display);
		
		sharedPrefs = getSharedPreferences(PREFS_NAME, 0);

		wrapper = (LinearLayout) (this.findViewById(R.id.wrapper));
		musicWrapper = new LinearLayout(getBaseContext());
		musicWrapper.setOrientation(LinearLayout.VERTICAL);
		testWrapper = new FrameLayout(getBaseContext());

		headerView 		= new HeaderView(getBaseContext(), tab.getTabName());
		footerView 		= new FooterView(getBaseContext(), sharedPrefs.contains(tab.getTabName()));
		tablatureView 	= new TablatureView(getBaseContext(), tab, Instrument.GUITAR, PACE);
		musicSheetView 	= new MusicSheetView(getBaseContext(), tab);
		cursorView 		= new CursorView(getBaseContext());
		
		
		playingPosition = cursorView.getPosX();
		
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
		
		
		map = new SampleMap(getApplicationContext(), pool, tuning);

		// Basic scrolling
		Thread t = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					if (running && !tablatureView.isTerminated()) {
						tablatureView.scrollBy(speed, 0);
						musicSheetView.scrollBy(speed, 0);
						
						playingPosition += speed; // increment the position at
													// which we want to look for
													// a time to play

						headerView.computeRatio(playingPosition, PACE);
						headerView.postInvalidate();
					
					}

					if (tab.timeMapContains(playingPosition)) {
						final Time t = tab.getTimeAt(playingPosition);
						if (t != null && running) {
							
							for (int i = 0; i < (Instrument.GUITAR).getNumOfStrings(); i++) {
								String fret = t.getNote(i);
								if (!fret.equals("")) {
									int fretNumber = Integer.parseInt(fret);
									final Note note = tuning[i].addHalfTones(fretNumber);
									final int sampleId = pool.play(map.getSampleId(note), 1, 1, 1, 0, 1);
									new Thread(new Runnable() {
										
										@Override
										public void run() {
											try {
												Thread.sleep(SAMPLE_TIME);
											} catch (InterruptedException e) {
												// TODO Auto-generated catch block
												e.printStackTrace();
											}
											pool.stop(sampleId);
										}
									}).start();
									
								}
							}
						}
					}

					try {
						Thread.sleep((int) delay, decimalPart(delay));
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
	 * This method computes the delay for which the thread has to sleep
	 * 
	 * @param tempo
	 * @param pace
	 * @param speed
	 * @param millisinmin
	 * @return the delay
	 */
	private double computeDelay(double tempo, double pace, double speed,
			double millisinmin) {
		return (speed*millisinmin/(tempo*pace));
	}

	private LinearLayout.LayoutParams weight(int i) {
		return new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, i);
	}
	
	/**
	 * This method computes the decimal part of the delay and returns it as an integer,
	 * which is the number of nanosecs the thread has to sleep for.
	 * @param delay
	 * @return the number of nanosecs
	 */
	private int decimalPart(double delay) {
		double decimal = delay % 1;
		while(decimal%1 != 0){
			decimal *= 10;
		}
		return (int) decimal;
	}
	
	

	@Override
	public boolean onTouchEvent(MotionEvent event) {

		final float x = event.getX();
		final float y = event.getY();
		
		if(x > footerView.getFavPosX() && y > footerView.getY() && MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP){
			footerView.getFav().setIsFav();
			footerView.invalidate();
			SharedPreferences.Editor editor = sharedPrefs.edit();
			if(!sharedPrefs.contains(tab.getTabName())){
				editor.putString(tab.getTabName(), serialize(tab));
				editor.commit();
				Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
			} else {
				editor.remove(tab.getTabName());
				editor.commit();
				Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
			}	
		} else {	
			if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
				tabPosX = tablatureView.getScrollX();
				this.lastX = x;
				scrolled = false;
			} 
			
			if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_MOVE) {
				this.newX = x;
				int delta = (int) (lastX - newX);
				
				if(Math.abs(delta) >= 30) {
					running = false;
					scrolled = true;
					int newPosX = tabPosX + delta;
					if(lastX != newX && newPosX >= 0 && newPosX <= tablatureView.getEndOfTab()){
						tablatureView.scrollTo(newPosX, 0);
						musicSheetView.scrollTo(newPosX, 0);
						playingPosition = (int) (tablatureView.getScrollX() + cursorView.getX() + 4*OFFSET);
					}
				}
			} 
			
			if(MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) {
				if(!scrolled){
					running = !running;
					footerView.playPause();
				}
			}
		}
		
		
		
		return true;
	}
	

	@Override
	public void onBackPressed() {
		if (backPressedOnce) {
			super.onBackPressed();
			pool.release();
			running = !running;
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

	/**
	 * Author: Raphael Khoury
	 * Initialize shared preferences and sync with preferences fragment
	 * Call to method that actually create dialog (createDialog(Context))
	 */

	private void checkDialog(Context cont) {
		SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(cont);
		Boolean showHelp = pref.getBoolean("pref_show_help", true);
		if (showHelp) {
			createDialog(this);
		}
		
	}
	
    public String serialize(Tab t){
   	 try {
   	     ByteArrayOutputStream bo = new ByteArrayOutputStream();
   	     ObjectOutputStream so = new ObjectOutputStream(bo);
   	     so.writeObject(t);
   	     so.flush();
   	     return new String(Base64.encode(bo.toByteArray(), Base64.DEFAULT));
   	 } catch (Exception e) {
   		 e.printStackTrace();
   		 return "Error";
   	 }
   }
 
/**
 * Author: 
 * @param Context
 * Create dialog box and display it
 */
	public void createDialog(final Context cont) {
		final CheckBox checkBox = new CheckBox(cont);
		checkBox.setText(R.string.show_help);
		checkBox.setEnabled(true);
		LinearLayout linLayout = new LinearLayout(cont);
		linLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
				LinearLayout.LayoutParams.MATCH_PARENT));
		linLayout.addView(checkBox);

		AlertDialog.Builder adBuilder = new AlertDialog.Builder(cont);
		adBuilder.setView(linLayout);
		adBuilder.setTitle(R.string.title_help);
		adBuilder.setMessage(R.string.help_content);
		adBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface arg0, int arg1) {
				SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(cont);
				SharedPreferences.Editor editor = pref.edit();
				editor.putBoolean("pref_show_help", checkBox.isChecked()).commit();
				Toast.makeText(cont, "Saved new preferences.", Toast.LENGTH_SHORT).show();
			}
		});
		adBuilder.create().show();
	}
}
