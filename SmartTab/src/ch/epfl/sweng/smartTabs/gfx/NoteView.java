package ch.epfl.sweng.smartTabs.gfx;

import java.util.ArrayList;

import ch.epfl.sweng.smartTabs.activity.DisplayActivity;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * @author Faton Ramadani
 * The note view draws the notes
 */
public class NoteView extends View{

	private final int d = 120;
	private final Paint paint = new Paint();
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

	public NoteView(Context context, Tab tab, Instrument instrument) {
		super(context);
		myInstrument = instrument;
		myTab = tab;
		h = getHeight();
		w = getWidth();
		
		paint.setAntiAlias(true);
		for (int i = 0; i < numNotes; i++) {
			times.add(myTab.getTime(i));
		}
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
		for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
			if (w - posX[time.getStep() % numNotes] > w/4) {
				paint.setColor(Color.BLACK);
				canvas.drawText(time.getNote(i), w - posX[time.getStep() % 6], ratio*h + i*h/16 + h/64, paint);
			}	
		}
	}


	protected void slideNotes(int speed) {
		for (int i = 0; i < numNotes; i++) {
			posX[i] += dx*speed;
			if ((w - posX[times.get(i).getStep() % 6] == (w / 4))){
				DisplayActivity.playNote(times.get(i));
				dx = 4/times.get(i).getDuration();
			}

		}
	}

}