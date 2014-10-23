package ch.epfl.sweng.smartTabs.gfx;

import java.util.ArrayList;

import ch.epfl.sweng.smartTabs.activity.DisplayActivity;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
<<<<<<< HEAD
 * @author fatonramadani The note view draws the notes
 */
public class NoteView extends View {
=======
 * @author Faton Ramadani
 * The note view draws the notes
 */
public class NoteView extends View{
	
	// The tuning is hard coded for the moment, until we create the Note class
	private final Height[] stantardTuning = {Height.Mi, Height.Si, Height.Sol, Height.Ré, Height.La, Height.Mi };
>>>>>>> be1181eac68fcb7fa85d83b487d4d66cd02212f0

	private final int d = 120;
	private final Paint paint = new Paint();
<<<<<<< HEAD
	private ArrayList<Time> times = new ArrayList<Time>();
	private int[] posX = { 0, -d, -2 * d, -3 * d, -4 * d, -5 * d, -6 * d,
			-7 * d, -8 * d, -9 * d, -10 * d, -11 * d, -12 * d, -13 * d,
			-14 * d, -15 * d, -16 * d, -17 * d, -18 * d };
	private int ptr = 0;
	private final float ratio = 0.34375f;

	private String[] n1 = {"7", "", "", "", "", "0"};
	private String[] n2 = {"", "0", "", "", "", ""};
	private String[] n3 = {"", "", "0", "", "", ""};
	private String[] n4 = {"7", "", "", "", "", ""};
	private String[] n5 = {"", "0", "", "", "", ""};
	private String[] n6 = {"", "", "0", "", "", ""};
	private String[] n7 = {"7", "", "", "", "", ""};
	private String[] n8 = {"", "0", "", "", "", ""};
	private String[] n9 = {"", "", "0", "", "", ""};
	private String[] n10 = {"5", "", "", "", "", "0"};
	private String[] n11 = {"", "0", "", "", "", ""};
	private String[] n12 = {"", "", "0", "", "", ""};
	private String[] n13 = {"5", "", "", "", "", ""};
	private String[] n14 = {"", "0", "", "", "", ""};
	private String[] n15 = {"", "", "0", "", "", ""};
	private String[] n16 = {"5", "", "", "", "", ""};
	private String[] n17 = {"", "0", "", "", "", ""};
	private String[] n18 = {"", "", "0", "", "", ""};
	private Time t1;
	private Time t2;
	private Time t3;
	private Time t4;
	private Time t5;
	private Time t6;
	private Time t7;
	private Time t8;
	private Time t9;
	private Time t10;
	private Time t11;
	private Time t12;
	private Time t13;
	private Time t14;
	private Time t15;
	private Time t16;
	private Time t17;
	private Time t18;
=======
	private final ArrayList<Time> times = new ArrayList<Time>();
	private final int[] posX = {0, -d, -2*d, -3*d, -4*d, -5*d};
	private final float ratio = 0.34375f;
	
	
	private double dx = Duration.Ronde.getDuration();
	private int ptr = 0;
	private final Instrument myInstrument;

	private int h;
	private int w;
	private final Tab myTab;
	
	private final int numNotes = 6;
>>>>>>> be1181eac68fcb7fa85d83b487d4d66cd02212f0

	public NoteView(Context context, Tab tab, Instrument instrument){
		super(context);
<<<<<<< HEAD
		t1 = new Time(n1, 0, 0, false, 0);
		t2 = new Time(n2, 0, 0, false, 1);
		t3 = new Time(n3, 0, 0, false, 2);
		t4 = new Time(n4, 0, 0, false, 3);
		t5 = new Time(n5, 0, 0, false, 4);
		t6 = new Time(n6, 0, 0, false, 5);
		t7 = new Time(n7, 0, 0, false, 6);
		t8 = new Time(n8, 0, 0, false, 7);
		t9 = new Time(n9, 0, 0, false, 8);
		t10 = new Time(n10, 0, 0, false, 9);
		t11 = new Time(n11, 0, 0, false, 10);
		t12 = new Time(n12, 0, 0, false, 11);
		t13 = new Time(n13, 0, 0, false, 12);
		t14 = new Time(n14, 0, 0, false, 13);
		t15 = new Time(n15, 0, 0, false, 14);
		t16 = new Time(n16, 0, 0, false, 15);
		t17 = new Time(n17, 0, 0, false, 16);
		t18 = new Time(n18, 0, 0, false, 17);

		paint.setAntiAlias(true);
		// 10 should be the size
		// for(int i = 0; i < 10; i++){
		// times.add(tab.getTime(i));
		// }
		times.add(t1);
		times.add(t2);
		times.add(t3);
		times.add(t4);
		times.add(t5);
		times.add(t6);
		times.add(t7);
		times.add(t8);
		times.add(t9);
		times.add(t10);
		times.add(t11);
		times.add(t12);
		times.add(t13);
		times.add(t14);
		times.add(t15);
		times.add(t16);
		times.add(t17);
		times.add(t18);
=======
		myInstrument = instrument;
		myTab = tab;
		h = getHeight();
		w = getWidth();
		
		paint.setAntiAlias(true);
		for (int i = 0; i < numNotes; i++) {
			times.add(myTab.getTime(i));
		}
	}
>>>>>>> be1181eac68fcb7fa85d83b487d4d66cd02212f0

	}

<<<<<<< HEAD
	protected void onDraw(Canvas canvas) {
=======
	protected void onDraw(Canvas canvas) {	
		h = getHeight();
		w = getWidth();
>>>>>>> be1181eac68fcb7fa85d83b487d4d66cd02212f0
		super.onDraw(canvas);

		paint.setColor(Color.BLACK);

		paint.setTextSize(48f);
<<<<<<< HEAD
		for (int i = ptr; i < 18 + ptr; i++) {
=======
		// 6 notes
		for (int i = ptr; i < numNotes + ptr; i++) {
>>>>>>> be1181eac68fcb7fa85d83b487d4d66cd02212f0
			drawTimes(times.get(i), canvas);
		}
	}

	private void drawTimes(Time time, Canvas canvas) {
<<<<<<< HEAD
		for (int i = 0; i < 6; i++) {
			if (getWidth() - posX[time.getStep() % 18] > getWidth() / 3) {
				paint.setColor(Color.BLACK);
				canvas.drawText(time.getNote(i),
						getWidth() - posX[time.getStep() % 18], ratio
								* getHeight() + i * getHeight() / 16, paint);
			}
=======
		for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
			if (w - posX[time.getStep() % numNotes] > w/4 ) {
				paint.setColor(Color.BLACK);
				canvas.drawText(time.getNote(i), w - posX[time.getStep() % 6], ratio*h + i*h/16, paint);
			}	
>>>>>>> be1181eac68fcb7fa85d83b487d4d66cd02212f0
		}
	}

	protected void slideNotes(int speed) {
<<<<<<< HEAD
		for (int i = 0; i < 18; i++) {
			posX[i] += deltaX * speed;
			if ((getWidth() - posX[times.get(i).getStep() % 18] >= (getWidth() / 3 - 4))
					&& (getWidth() - posX[times.get(i).getStep() % 18] < (getWidth() / 3 + 1))) {
=======
		for (int i = 0; i < numNotes; i++) {
			posX[i] += dx*speed;
			if ((w - posX[times.get(i).getStep() % 6] == (w / 4))){
>>>>>>> be1181eac68fcb7fa85d83b487d4d66cd02212f0
				DisplayActivity.playNote(times.get(i));
				dx = 4/times.get(i).getDuration();
			}

		}
	}

<<<<<<< HEAD
=======
	private void drawNameSong(Canvas canvas){
		paint.setTextSize(48f);
		paint.setColor(Color.GRAY);
		canvas.drawText(myTab.getTabName(), 50, 100, paint);
	}

	private void drawStrings(Canvas canvas){
		paint.setColor(Color.BLACK);
		paint.setTextSize(36f);
		paint.setStrokeWidth(2f);
		for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
			canvas.drawLine(w/8, ratio*h + i*h/16, w, ratio*h + i*h/16, paint);
			canvas.drawText(stantardTuning[i]+"", w/16, ratio*h + i*h/16, paint);
		}
		canvas.drawLine(w/8, ratio*h, w/8, ratio*h + 5*h/16, paint);
	}

	private void drawCursor(Canvas canvas){
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10f);
		canvas.drawLine(w/4, h/4, w/4,3*h/4, paint);
		paint.setStrokeWidth(1f);
	}
>>>>>>> be1181eac68fcb7fa85d83b487d4d66cd02212f0
}