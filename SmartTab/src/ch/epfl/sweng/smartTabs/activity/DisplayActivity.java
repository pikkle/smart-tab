package ch.epfl.sweng.smartTabs.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Toast;
import ch.epfl.sweng.smartTabs.gfx.GridViewDraw;
import ch.epfl.sweng.smartTabs.gfx.NoteView;
import ch.epfl.sweng.smartTabs.gfx.TabAnimationThread;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.NotePlaybackThread;
import ch.epfl.sweng.smartTabs.music.SampleMap;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;

/**
 * @author Faton Ramadani
 * The activity which displays the tabs and handle the animation
 */
public class DisplayActivity extends Activity {
	private final int maxStreams = 50;

	// TODO : Should be final
	private NoteView n;
	private static SoundPool pool;
	private static SampleMap map;
	private static NotePlaybackThread playbackThread;
	private static final int DELAY = 2000;

	private GridViewDraw mDrawable;
	private TabAnimationThread thread;

	private boolean backPressedOnce = false;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		requestWindowFeature(Window.FEATURE_NO_TITLE);
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);

		Intent intent = getIntent();
		Tab tab = (Tab) intent.getExtras().getSerializable("tab");

		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		
		
		mDrawable = new GridViewDraw(width, height, Instrument.GUITAR, tab, getResources());

		n = new NoteView(this, tab, Instrument.GUITAR, mDrawable);
		pool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);
		
		setContentView(n);

		// TODO : The new thread was useless
		map = new SampleMap(getApplicationContext(),
						pool, n.getTuning());


		thread = new TabAnimationThread(n);
		thread.start();
	}


	@Override
	public boolean onTouchEvent(MotionEvent event) {
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			thread.switchRunning();
		}
		return true;
	}

	@Override
	public void onBackPressed() {
		if (backPressedOnce) {
			super.onBackPressed();
			thread.stopPlaying();
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

	/**
	 * This method plays the notes contained in the Time object.
	 * @param time is the Time object containing the notes to play
	 */
	public void playNote(final Time time, Note[] tuning) {
		playbackThread = new NotePlaybackThread(pool, map, time, tuning);
		playbackThread.start();
	}
}