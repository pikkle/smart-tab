/**
 * 
 */
package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;

/**
 * @author pikkle
 *
 */
public class TablatureView extends View{
	private static final float THIN_LINE_WIDTH = 2f;
	private Paint paint;
	private Resources res;
	private final Height[] stantardTuning = {Height.E, Height.B, Height.G, Height.D, Height.A, Height.E}; //TODO: add a parameter in tab that contains its tuning
	private Instrument instr;
	private Tab tab;
	private Context context;
	private int pace = 200;
	private int endOfTab;
	private final int startingPos = 100; //Display initially starts at 100px
	private final int firstNotePos = startingPos + 100; //First note's position
	
	/**
	 * @param context
	 * @param attrs
	 */
	public TablatureView(Context context, Tab tab, Instrument instr, int pace) {
		super(context);
		paint = new Paint();
		this.context = context;
		this.tab = tab;
		this.instr = instr;
		this.pace = pace;
		tab.initTimeMap(firstNotePos);
		this.setBackgroundColor(Color.WHITE);
		invalidate();
		endOfTab = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		float textSize = canvas.getHeight()*0.1f;
		int tabLineMargin = (int) (canvas.getHeight()/(instr.getNumOfStrings()+1));
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(THIN_LINE_WIDTH);
		paint.setTextSize(textSize);
		Rect textRect = new Rect();
		for (int i = 1; i <= instr.getNumOfStrings(); i++) { //Draws the grid
			paint.setStrokeWidth(i+1);
			canvas.drawLine(
					startingPos, 
					i * tabLineMargin, 
					endOfTab, //TODO: calculate size of tab
					i * tabLineMargin, 
					paint);
			paint.getTextBounds(stantardTuning[i-1].toString(), 0, stantardTuning[i-1].toString().length(), textRect);
			canvas.drawText(stantardTuning[i-1].toString(),
					startingPos - textSize, 
					i * tabLineMargin - textRect.centerY(), 
					paint);
		}
		
		//Adds notes to the grid
		int pos = firstNotePos;
		Rect noteRect = new Rect();
		for (int i = 0; i < tab.length(); i++) {
			double noteDuration = Duration.valueOf(tab.getTime(i).getDuration()).getDuration();
			pos += pace*noteDuration;
			if (pos-getScrollX() > 0 && pos-getScrollX() < getWidth()) { //Draws only what is necessary
				for (int j = 0; j < instr.getNumOfStrings(); j++) {
					paint.getTextBounds(tab.getTime(i).getNote(j), 0, tab.getTime(i).getNote(j).length(), noteRect);
					float textHeight = (j+1) * tabLineMargin + textSize/3;
					paint.setColor(Color.WHITE);
					canvas.drawRect(pos, textHeight-textSize, pos+noteRect.right, textHeight, paint);
					paint.setColor(Color.BLACK);
					canvas.drawText(tab.getTime(i).getNote(j), pos, textHeight, paint);
				}
			}
		}
		endOfTab = pos + 200;
    }
	
	public boolean isTerminated() {
		return getScrollX() > endOfTab-getWidth()*1/5;
	}
}
