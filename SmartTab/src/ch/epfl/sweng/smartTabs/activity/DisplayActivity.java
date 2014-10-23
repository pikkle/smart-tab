package ch.epfl.sweng.smartTabs.activity;

import android.app.Activity;
<<<<<<< HEAD
import android.graphics.Point;
=======
import android.content.Intent;
>>>>>>> be1181eac68fcb7fa85d83b487d4d66cd02212f0
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.Display;
import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.gfx.GridViewDraw;
import ch.epfl.sweng.smartTabs.gfx.NoteView;
import ch.epfl.sweng.smartTabs.gfx.TabAnimationThread;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;

/**
 * @author Faton Ramadani 
 * The activity which displays the tabs and handle the animation
 */
public class DisplayActivity extends Activity {
	private static SoundPool pool;

	private static NoteView n;
	private static int a3;
	private static int b2;
	private static int b3;
	private static int e1;
	private static int g2;
	private static int g3;
	private static int stringIndex;
	private final int maxStreams = 6;
	private GridViewDraw mDrawable;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		Intent intent = getIntent();
		Tab tab = (Tab) intent.getExtras().getSerializable("tab");
		

		n = new NoteView(this, tab, Instrument.GUITAR);
		pool = new SoundPool(maxStreams, AudioManager.STREAM_MUSIC, 0);

		stringIndex = 1;
		
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		int width = size.x;
		int height = size.y;
		mDrawable = new GridViewDraw(width, height, "Jeux Interdits");
		n.setBackground(mDrawable);

		a3 = pool.load(this, R.raw.a3, 1);
		b2 = pool.load(this, R.raw.b2, 1);
		b3 = pool.load(this, R.raw.b3, 1);
		e1 = pool.load(this, R.raw.e1, 1);
		g2 = pool.load(this, R.raw.g2, 1);
		g3 = pool.load(this, R.raw.g3, 1);


		setContentView(n);

		TabAnimationThread thread = new TabAnimationThread(n);
		thread.start();
	}

	public static void playNote(Time time) {

		switch (stringIndex) {
		case 1:
			System.out.println("Playing string : " + stringIndex);
			pool.play(b3, 1, 1, 1, 0, 1);
			pool.play(e1, 1, 1, 1, 0, 1);

			break;
		case 2:
			System.out.println("Playing string : " + stringIndex);
			pool.play(b2, 1, 1, 1, 0, 1);
			break;
		case 3:
			System.out.println("Playing string : " + stringIndex);
			pool.play(g2, 1, 1, 1, 0, 1);

			break;
		case 4:
			System.out.println("Playing string : " + stringIndex);
			pool.play(b3, 1, 1, 1, 0, 1);

			break;
		case 5:
			System.out.println("Playing string : " + stringIndex);
			pool.play(b2, 1, 1, 1, 0, 1);

			break;
		case 6:
			System.out.println("Playing string : " + stringIndex);
			pool.play(g2, 1, 1, 1, 0, 1);
			break;

		case 7:
			System.out.println("Playing string : " + stringIndex);
			pool.play(b3, 1, 1, 1, 0, 1);

			break;
		case 8:
			System.out.println("Playing string : " + stringIndex);
			pool.play(b2, 1, 1, 1, 0, 1);

			break;
		case 9:
			System.out.println("Playing string : " + stringIndex);
			pool.play(g2, 1, 1, 1, 0, 1);

			break;
		case 10:
			System.out.println("Playing string : " + stringIndex);
			pool.play(b3, 1, 1, 1, 0, 1);
			pool.play(e1, 1, 1, 1, 0, 1);

			break;
		case 11:
			System.out.println("Playing string : " + stringIndex);
			pool.play(b2, 1, 1, 1, 0, 1);
			break;
		case 12:
			System.out.println("Playing string : " + stringIndex);
			pool.play(g2, 1, 1, 1, 0, 1);

			break;
		case 13:
			System.out.println("Playing string : " + stringIndex);
			pool.play(a3, 1, 1, 1, 0, 1);

			break;
		case 14:
			System.out.println("Playing string : " + stringIndex);
			pool.play(b2, 1, 1, 1, 0, 1);

			break;
		case 15:
			System.out.println("Playing string : " + stringIndex);
			pool.play(g2, 1, 1, 1, 0, 1);
			break;

		case 16:
			System.out.println("Playing string : " + stringIndex);
			pool.play(g3, 1, 1, 1, 0, 1);

			break;

		case 17:
			System.out.println("Playing string : " + stringIndex);
			pool.play(b2, 1, 1, 1, 0, 1);

			break;
		case 18:
			System.out.println("Playing string : " + stringIndex);
			pool.play(g2, 1, 1, 1, 0, 1);

			break;	
		default:
			break;
		}
		stringIndex++;
	}
}