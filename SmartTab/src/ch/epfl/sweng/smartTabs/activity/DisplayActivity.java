package ch.epfl.sweng.smartTabs.activity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Display;
import android.view.MotionEvent;
import ch.epfl.sweng.smartTabs.gfx.GridViewDraw;
import ch.epfl.sweng.smartTabs.gfx.NoteView;
import ch.epfl.sweng.smartTabs.gfx.TabAnimationThread;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.NotePlaybackThread;
import ch.epfl.sweng.smartTabs.music.SampleMap;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;

/**
 * @author Faton Ramadani
 * The activity which displays the tabs and handle the animation
 */
public class DisplayActivity extends Activity {
	private final int maxStreams = 39;

	private static NoteView n;
	private static SoundPool pool;
	private static SampleMap map;
	private GridViewDraw mDrawable;
	private TabAnimationThread thread;
	private static NotePlaybackThread playbackThread;
	private Thread t;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Tab tab = (Tab) intent.getExtras().getSerializable("tab");


		n = new NoteView(this, tab, Instrument.GUITAR);
		pool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);



		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		mDrawable = new GridViewDraw(width, height, Instrument.GUITAR, tab, getResources());
		n.setBackground(mDrawable);

		setContentView(n);

		t = new Thread(new Runnable() {

			@Override
			public void run() {
				map = new SampleMap(getApplicationContext(),
						pool);
			}
		});
		t.start();
		try {
			t.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}


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
		thread.stopPlaying();
		pool.release();
		super.onBackPressed();
	}

	public static void playNote(final Time time) {
		playbackThread = new NotePlaybackThread(pool, map, time);
		playbackThread.start();
		try {
			playbackThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}