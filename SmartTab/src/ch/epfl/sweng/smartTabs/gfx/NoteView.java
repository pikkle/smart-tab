package ch.epfl.sweng.smartTabs.gfx;

import java.util.ArrayList;

import android.R.integer;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.activity.DisplayActivity;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;
import ch.epfl.sweng.smartTabs.music.TimesArrayGenerationThread;

/**
 * @author Faton Ramadani The note view draws the notes
 */
public class NoteView extends View {

	private final Paint paint = new Paint();
	private ArrayList<Time> times = new ArrayList<Time>();
	private int d = 120;
//	private final int[] posX = { 0, -d, -2 * d, -3 * d, -4 * d, -5 * d, -6 * d,
//			-780, -840, -900, -960, -1020, -1140, -1200, -1260, -1320, -1380,
//			-1440 };
	
	private ArrayList<Integer> posX = new ArrayList<Integer>();
	private TimesArrayGenerationThread timesGenThread;

	private static final float TAB_TEXT_SIZE = 48f;
	private int ptr = 0;
	private final Instrument myInstrument;

	private int w;
	private final Tab myTab;
	private Note[] tuning = {new Note(3, Height.E), new Note(2, Height.B), new Note(2, Height.G), new Note(2, Height.D), new Note(1, Height.A), new Note(1, Height.E)};
	private int maxNotes = 0;

	private GridViewDraw mGridView;

	public NoteView(Context context, Tab tab, Instrument instrument,
			GridViewDraw gridView) {
		super(context);
		myInstrument = instrument;
		myTab = tab;
		w = getWidth();
		timesGenThread = new TimesArrayGenerationThread(myTab, times, posX);
//		timesGenThread = new TimesArrayGenerationThread(myTab, times);
		timesGenThread.start();

		try {
			timesGenThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		maxNotes = posX.size();
		paint.setAntiAlias(true);

		mGridView = gridView;

		this.setBackground(mGridView);
	}

	protected void onDraw(Canvas canvas) {
		w = getWidth();
		super.onDraw(canvas);
		paint.setColor(Color.BLACK);
		// 6 notes
		if (mGridView.isDisplayTab()) {
			for (int i = ptr; i < maxNotes + ptr; i++) {
				drawTimes(times.get(i), canvas);
			}
		}
	}

	private void drawTimes(Time time, Canvas canvas) {
		Rect r = mGridView.getTabRect();
		float margin = mGridView.getTabLineMargin();
		paint.setTextSize(TAB_TEXT_SIZE);
		double pos = w - posX.get(time.getStep() % maxNotes);
		//Bitmap noteNoire = BitmapFactory.decodeResource(getResources(), R.raw.cledesol);
		//noteNoire = Bitmap.createScaledBitmap(noteNoire, ???standardWidth, ???standardHeight, false);
		//noteNoire = Bitmap.createBitmap(noteNoire);
		for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
			if (pos > mGridView.getTabLeftRect().left) {
				float textHeight = r.top + i*margin - (TAB_TEXT_SIZE/2);
				canvas.drawText(time.getNote(i), w - posX.get(time.getStep() % maxNotes), textHeight, paint);
				//canvas.drawBitmap(noteNoire, clefRect.left + 2 * HARD_LINE_WIDTH, clefRect.top, paint);
				//canvas.drawBitmap(noteNoire, w - posX[time.getStep() % 18], top, paint);
				if(pos < (w/4 - w/80)) {
					//paint.setAlpha((int) (pos - 150));
					paint.setAlpha(150);
					paint.setFakeBoldText(false);
				} else if (((w/4 - w/80) < pos) && (pos < (w/4 + w/80))) {
					paint.setColor(Color.RED);
					paint.setFakeBoldText(true);
				}else {					
					paint.setFakeBoldText(false);
					paint.setColor(Color.BLACK);
				}
			}
		}
	}

	protected void slideNotes(int speed) {
		for (int i = 0; i < maxNotes; i++) {
//			posX[i] += 4*speed;
			posX.set(i, posX.get(i)+4*speed);
			
			if ((w - posX.get(times.get(i).getStep() % maxNotes) == (w / 4))) {
				DisplayActivity.playNote(times.get(i), tuning);
			}
		}
	}

	public Tab getTab() {
		return myTab;
	}

	public ArrayList<Time> getTimes() {
		return times;
	}
	public Note[] getTuning(){
		return tuning;
	}

}