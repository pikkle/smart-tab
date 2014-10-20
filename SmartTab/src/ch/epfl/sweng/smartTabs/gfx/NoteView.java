package ch.epfl.sweng.smartTabs.gfx;

import java.util.ArrayList;


import ch.epfl.sweng.smartTabs.activity.DisplayActivity;
import ch.epfl.sweng.smartTabs.music.Time;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * @author fatonramadani
 * The note view draws the notes
 */
public class NoteView extends View{

	// deltaX should differ according to tempo
	private final int deltaX = 5;
	private final int d = 200;
	private final Paint paint = new Paint();
	private ArrayList<Time> times = new ArrayList<Time>();
	private int[] posX = {0,-d,-2*d,-3*d,-4*d,-5*d,-6*d,-7*d,-8*d,-9*d,-10*d,-11*d,-12*d,-13*d,-14*d, -15*d,-16*d,-17*d,-18*d};
	private int ptr = 0;
	private final float ratio = 0.34375f;
	private String[] n1 = {"7","","","","","0"};
	private String[] n2 = {"","0","","","",""};
	private String[] n3 = {"","","0","","",""};
	private String[] n4 = {"7","","","","",""};
	private String[] n5 = {"","0","","","",""};
	private String[] n6 = {"","","0","","",""};
	private String[] n7 = {"7","","","","",""};
	private String[] n8 = {"","0","","","",""};
	private String[] n9 = {"","","0","","",""};
	private String[] n10 = {"5","","","","","0"};
	private String[] n11 = {"","0","","","",""};
	private String[] n12 = {"","","0","","",""};
	private String[] n13 = {"5","","","","",""};
	private String[] n14 = {"","0","","","",""};
	private String[] n15 = {"","","0","","",""};
	private String[] n16 = {"5","","","","",""};
	private String[] n17 = {"","0","","","",""};
	private String[] n18 = {"","","0","","",""};
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
	
	// The tuning is hard coded for the moment, until we create the Note class
	private final char[] stantardTuning = {'e', 'B', 'G', 'D', 'A', 'E'};

	

	public NoteView(Context context) {
		super(context);
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
		//for(int i = 0; i < 10; i++){
		//	times.add(tab.getTime(i));
		//}
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
		
	}


	protected void onDraw(Canvas canvas) {		        
		super.onDraw(canvas);
		clearScreen(canvas);
		drawNameSong(canvas);
		drawStrings(canvas);
		drawCursor(canvas);
		paint.setColor(Color.BLACK);
		

		paint.setTextSize(48f);
		for(int i = ptr; i < 18 + ptr; i++){
			drawTimes(times.get(i),canvas);
		}
	}

	private void drawTimes(Time time,Canvas canvas) {
		for (int i = 0; i < 6; i++) {
			if (getWidth() - posX[time.getStep() % 18] > getWidth()/3) {
				paint.setColor(Color.BLACK);
				canvas.drawText(time.getNote(i), getWidth() - posX[time.getStep() % 18], ratio*getHeight() + i*getHeight()/16, paint);
			}	
		}
	}


	protected void slideNotes(int speed) {
		for (int i = 0; i < 18; i++) {
			posX[i] += deltaX*speed;
			if ((getWidth() - posX[times.get(i).getStep() % 18] >= (getWidth() / 3 - 4))
					&& (getWidth() - posX[times.get(i).getStep() % 18] < (getWidth() / 3 + 1))) {
				DisplayActivity.playNote(times.get(i));
			}

		}
	}
	
	private void clearScreen(Canvas canvas){
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
	}

	private void drawNameSong(Canvas canvas){
		paint.setTextSize(48f);
		paint.setColor(Color.GRAY);
		canvas.drawText("Test", 50, 100, paint);
	}

	private void drawStrings(Canvas canvas){
		paint.setColor(Color.BLACK);
		paint.setTextSize(36f);
		paint.setStrokeWidth(2f);
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(getWidth()/8, ratio*getHeight() + i*getHeight()/16, getWidth(), ratio*getHeight() + i*getHeight()/16, paint);
			canvas.drawText(stantardTuning[i]+"", getWidth()/16, ratio*getHeight() + i*getHeight()/16, paint);
		}
		canvas.drawLine(getWidth()/8, ratio*getHeight(), getWidth()/8, ratio*getHeight() + 5*getHeight()/16, paint);
	}

	private void drawCursor(Canvas canvas){
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10f);
		canvas.drawLine(getWidth()/3, getHeight()/4, getWidth()/3,3*getHeight()/4, paint);
		paint.setStrokeWidth(1f);
	}
}