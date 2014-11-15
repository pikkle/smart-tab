/**
 * 
 */
package ch.epfl.sweng.smartTabs.gfx;

import java.util.ArrayList;

import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author pikkle
 *
 */
public class TablatureView extends View{
	private static final float THIN_LINE_WIDTH = 2f;
	private Paint paint;
	private Resources res;
	private Tab tab;
	private final Height[] stantardTuning = {Height.E, Height.B, Height.G, Height.D, Height.A, Height.E}; //TODO: add a parameter in tab that contains its tuning
	private Instrument instr;
	private final ArrayList<Time> times = new ArrayList<Time>();
	private final ArrayList<Integer> posX = new ArrayList<Integer>();
	private static final int DX = 4;
	private static final int PACE = 120;
	
	/**
	 * @param context
	 * @param attrs
	 */
	public TablatureView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
	}

	public void setTab(Tab tab) {
		this.tab = tab;
	}
	
	public void setInstrument(Instrument instr) {
		this.instr = instr;
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.BLUE);
		paint.setAlpha(100);
		//canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
		
		float textSize = canvas.getHeight()*0.1f;
		int tabLineMargin = (int) (canvas.getHeight()/(instr.getNumOfStrings()+1));
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(THIN_LINE_WIDTH);
		paint.setTextSize(textSize);
		Rect textRect = new Rect();
		for (int i = 1; i <= instr.getNumOfStrings(); i++) { //Draws the grid
			canvas.drawLine(canvas.getWidth()/8, 
					i * tabLineMargin, 
					10000, //TODO: calculate size of tab
					i * tabLineMargin, 
					paint);
			paint.getTextBounds(stantardTuning[i-1].toString(), 0, stantardTuning[i-1].toString().length(), textRect);
			canvas.drawText(stantardTuning[i-1].toString(),
					canvas.getWidth()/8 - textSize, 
					i * tabLineMargin - textRect.centerY(), 
					paint);
			
		}
		//Adds notes to the grid
		int pos = canvas.getWidth(); //starts at the end of the screen
		for (int i = 1; i < tab.length(); i++) {
			double noteDuration = Duration.valueOf(tab.getTime(i).getDuration()).getDuration();
			pos += PACE*noteDuration;
			
			for (int j = 0; j < instr.getNumOfStrings(); j++) {
				float textHeight = (j+1) * tabLineMargin;
				paint.setColor(Color.BLACK);
				canvas.drawText(tab.getTime(i).getNote(j), pos, textHeight, paint);
			}
		}
		
    }
}
