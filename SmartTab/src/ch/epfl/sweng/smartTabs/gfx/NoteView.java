package ch.epfl.sweng.smartTabs.gfx;

import java.util.ArrayList;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.view.View;
import ch.epfl.sweng.smartTabs.activity.DisplayActivity;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;

/**
 * @author Faton Ramadani The note view draws the notes
 */

public class NoteView extends View {

	private final float tabTextSize;
	private static final int DX = 4;
	private static final int PACE = 120;
	
	private final Paint paint = new Paint();
	private final Instrument myInstrument;
	private final ArrayList<Time> times = new ArrayList<Time>();
	private final ArrayList<Integer> posX = new ArrayList<Integer>();
	private final Tab myTab;
	private int w;
	
	private Note[] tuning = {
			new Note(Height.E, 3), new Note(Height.B, 2),
			new Note(Height.G, 2), new Note(Height.D, 2), 
			new Note(Height.A, 1), new Note(Height.E, 1)
	};
	
	private int maxNotes = 0;

	private GridViewDraw mGridView;
	
	private Typeface font;
	public NoteView(Context context, Tab tab, Instrument instrument,
			GridViewDraw gridView) {
		
		super(context);
		myInstrument = instrument;
		myTab = tab;
		w = getWidth();
		
		// Fill the position of the times according to the durations
		for (int i = 0; i < myTab.length(); i++) {
			times.add(myTab.getTime(i));
			if (i==0) {
				posX.add(0);
			} else {
				double noteDuration = Duration.valueOf(myTab.getTime(i).getDuration()).getDuration();
				posX.add((int) (posX.get(i-1) - PACE*noteDuration));				
			}
		}

		maxNotes = posX.size();
		mGridView = gridView;
		
		tabTextSize = mGridView.getTabLineMargin()/2;
		font = Typeface.createFromAsset(context.getAssets(), "fonts/DroidSansMono.ttf");
		paint.setAntiAlias(true);
		paint.setTypeface(font);
		this.setBackground(mGridView);
	}
	
	

	protected void onDraw(Canvas canvas) {
		w = getWidth();
		super.onDraw(canvas);
		paint.setColor(Color.BLACK);
		// 6 notes
		if (mGridView.isDisplayTab()) {
			for (int i = 0; i < maxNotes; i++) {
				drawTimes(times.get(i), canvas);
			}
		}
	}

	private void drawTimes(Time time, Canvas canvas) {
		Rect r = mGridView.getTabRect();
		float margin = mGridView.getTabLineMargin();
		paint.setTextSize(tabTextSize);
		float pos = w - posX.get(time.getStep());
		for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
			if (pos > mGridView.getTabLeftRect().left) {
				float textHeight = r.top + (i+1)*margin - (tabTextSize/1.5f);
				if (pos > mGridView.getTabLeftRect().left + time.getNote(i).length()*tabTextSize*0.6f) {
					paint.setColor(Color.WHITE);
					canvas.drawRect(pos-tabTextSize*0.1f,
							textHeight-tabTextSize*0.8f,
							pos+time.getNote(i).length()*tabTextSize*0.8f-tabTextSize*0.1f,
							textHeight, 
							paint);
				}
				paint.setColor(Color.BLACK);
				
				canvas.drawText(time.getNote(i), pos, textHeight, paint);
				
				/*
				if (pos < (w/4 - w/80)) {
					//paint.setAlpha((int) (pos - 150));
					paint.setAlpha(150);
					paint.setFakeBoldText(false);
				} else if (((w/4 - w/80) < pos) && (pos < (w/4 + w/80))) {
					paint.setColor(Color.RED);
					paint.setFakeBoldText(true);
				} else {					
					paint.setFakeBoldText(false);
					paint.setColor(Color.BLACK);
				}
				*/
			}
		}
	}

	protected void slideNotes(int speed) {
		for (int i = 0; i < maxNotes; i++) {
			posX.set(i, posX.get(i)+DX*speed);
			
			if ((w - posX.get(times.get(i).getStep() % maxNotes) <= mGridView.getTabRect().left+DX*speed) 
					&& (w - posX.get(times.get(i).getStep() % maxNotes) > mGridView.getTabRect().left)) {
				((DisplayActivity) this.getContext()).playNote(times.get(i), tuning);
				System.out.println(times.get(i).toString() + " : " +times.get(i).getStep());
			}
		}
	}

	public Tab getTab() {
		return myTab;
	}

	public ArrayList<Time> getTimes() {
		return times;
	}
	public Note[] getTuning() {
		return tuning;
	}
	
	public int getMaxNotes() {
		return maxNotes;
	}
}