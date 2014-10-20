package ch.epfl.sweng.smartTabs.gfx;

import java.util.ArrayList;


import ch.epfl.sweng.smartTabs.activity.DisplayActivity;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class NoteView extends View{

	// deltaX should differ according to tempo
	int deltaX = 5;
	private final Paint paint = new Paint();
	private ArrayList<Time> times = new ArrayList<Time>();
	private int d = 200;
	private int[] posX = {0,-100,-150,-3*d,-4*d,-5*d,-6*d,0,0,0};
	private int ptr = 0;
	private final float RATIO = 0.34375f;
	private String[] n1 = {"7","","","","","0"};
	private String[] n2 = {"","0","","","",""};
	private String[] n3 = {"","","0","","",""};
	private String[] n4 = {"5","","","","","0"};
	private String[] n5 = {"","0","","","",""};
	private String[] n6 = {"","","0","","",""};
	private Time t1;
	private Time t2;
	private Time t3;
	private Time t4;
	private Time t5;
	private Time t6;
	
	// The tuning is harcoded for the moment, until we 
	private final char[] STANDARD_TUNNING = {'e','B','G','D','A','E'};

	

	public NoteView(Context context) {
		super(context);
		t1 = new Time(n1, 0, 0, false, 0);
		t2 = new Time(n2, 0, 0, false, 1);
		t3 = new Time(n3, 0, 0, false, 2);
		t4 = new Time(n4, 0, 0, false, 3);
		t5 = new Time(n5, 0, 0, false, 4);
		t6 = new Time(n6, 0, 0, false, 5);
		
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
	}


	protected void onDraw(Canvas canvas) {		        
		super.onDraw(canvas);
		clearScreen(canvas);
		drawNameSong(canvas);
		drawStrings(canvas);
		drawCursor(canvas);
		paint.setColor(Color.BLACK);
		

		paint.setTextSize(48f);
		for(int i = ptr; i < 6 + ptr; i++){
			drawTimes(times.get(i),canvas);
		}
	}

	private void drawTimes(Time time,Canvas canvas) {
		for (int i = 0; i < 6; i++) {
			if (getWidth() - posX[time.getStep() % 10] > getWidth()/3) {
				paint.setColor(Color.BLACK);
				canvas.drawText(time.getNote(i), getWidth() - posX[time.getStep() % 10], RATIO*getHeight() + i*getHeight()/16, paint);
			}	
		}
	}


	protected void slideNotes(int speed) {
		for (int i = 0; i < 6; i++) {
			posX[i] += deltaX*speed;
			if ((getWidth() - posX[times.get(i).getStep() % 10] >= (getWidth() / 3 - 4))
					&& (getWidth() - posX[times.get(i).getStep() % 10] < (getWidth() / 3 + 1))) {
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
			canvas.drawLine(getWidth()/8, RATIO*getHeight() + i*getHeight()/16, getWidth(), RATIO*getHeight() + i*getHeight()/16, paint);
			canvas.drawText(STANDARD_TUNNING[i]+"", getWidth()/16, RATIO*getHeight() + i*getHeight()/16, paint);
		}
		canvas.drawLine(getWidth()/8, RATIO*getHeight(), getWidth()/8, RATIO*getHeight() + 5*getHeight()/16, paint);
	}

	private void drawCursor(Canvas canvas){
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10f);
		canvas.drawLine(getWidth()/3, getHeight()/4, getWidth()/3,3*getHeight()/4, paint);
		paint.setStrokeWidth(1f);
	}
}