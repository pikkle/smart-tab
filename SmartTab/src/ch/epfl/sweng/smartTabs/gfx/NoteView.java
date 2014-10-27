package ch.epfl.sweng.smartTabs.gfx;

import java.util.ArrayList;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import ch.epfl.sweng.smartTabs.activity.DisplayActivity;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;

/**
 * @author Faton Ramadani
 * The note view draws the notes
 */
public class NoteView extends View{

	private final int d = 120;
	private final Paint paint = new Paint();
	private ArrayList<Time> times = new ArrayList<Time>();
	private final int[] posX = {0, -d, -2*d, -3*d, -4*d, -5*d, -6*d, -780, -840, -900, -960, -1020, -1140,-1200, -1260, -1320, -1380,-1440};
	private final float ratio = 0.34375f;


	private double dx = Duration.Ronde.getDuration();
	private int ptr = 0;
	private final Instrument myInstrument;

	private int h;
	private int w;
	private final Tab myTab;

	private final int numNotes = 18;

	public NoteView(Context context, Tab tab, Instrument instrument) {
		super(context);
		myInstrument = instrument;
		myTab = tab;
		h = getHeight();
		w = getWidth();

		paint.setAntiAlias(true);
//		for (int i = 0; i < numNotes; i++) {
//			times.add(myTab.getTime(i));
//		}
	}


	protected void onDraw(Canvas canvas) {	
		h = getHeight();
		w = getWidth();
		super.onDraw(canvas);
		paint.setColor(Color.BLACK);

		paint.setTextSize(48f);
		// 6 notes
		for (int i = ptr; i < numNotes + ptr; i++) {
			drawTimes(times.get(i), canvas);
		}
	}

	private void drawTimes(Time time, Canvas canvas) {
		float delta = h*7/16 + h/8;
		for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
			if (w - posX[time.getStep() % numNotes] > w/4) {
				paint.setColor(Color.BLACK);
				
				canvas.drawText(time.getNote(i), w - posX[time.getStep() % 18], delta + i*h/16 + h/64, paint);
			}
		}
	}


	protected void slideNotes(int speed) {
		for (int i = 0; i < numNotes; i++) {
			posX[i] += dx*speed;
			if ((w - posX[times.get(i).getStep() % 18] == (w / 4))){
				DisplayActivity.playNote(times.get(i));
			}

		}
	}


	public Tab getTab() {
		return myTab;
	}


	public ArrayList<Time> getTimes() {
		return times;
	}

}