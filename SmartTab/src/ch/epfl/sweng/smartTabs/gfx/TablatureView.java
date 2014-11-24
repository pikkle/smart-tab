package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;

/**
 * @author pikkle
 *
 */
public class TablatureView extends View{
	private final Paint paint = new Paint();
	private Instrument myInstr;
	private Tab myTab;
	private int myPace;
	private int endOfTab;
	private float padding;
	private int startingPos; //Display initially starts at 100px
	private int firstNotePos; //First note's position
	private Bitmap bmp;
	private boolean firstDraw =true;
	
	/**
	 * @param context
	 * @param attrs
	 */
	public TablatureView(Context context, Tab tab, Instrument instr, int pace) {
		super(context);
		
		this.myTab = tab;
		this.myInstr = instr;
		this.myPace = pace;
		this.setBackgroundColor(Color.WHITE);
		
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		padding = display.getHeight()/8;
		startingPos = display.getWidth()/8;
		firstNotePos = startingPos + 2*pace;
		
		
		tab.initTimeMap(firstNotePos);
		
		// TODO : Can be a field in the JSON
		endOfTab = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
		int tabLineMargin = (int) (6*canvas.getHeight()/8/(myInstr.getNumOfStrings()+1));
		float textSize = canvas.getHeight()*0.1f;
		
		if (firstDraw) {
			paint.setColor(Color.BLACK);
			paint.setStrokeWidth(1);
			paint.setTextSize(textSize);
			
			bmp = Bitmap.createBitmap((int) canvas.getWidth(),(int) canvas.getHeight(), Bitmap.Config.ARGB_8888);
			Canvas c = new Canvas(bmp);
			drawVerticalLineOnTab(c, startingPos, tabLineMargin);
			drawVerticalLineOnTab(c, endOfTab, tabLineMargin);
			//Draws the grid and the tuning
			drawGrid(c, tabLineMargin);
			// Draws the notes
			drawNotes(c);
			c.setBitmap(bmp);
			firstDraw = false;
			
			canvas.drawBitmap(bmp, 0, 0 ,null);
		}
		else {
			canvas.drawBitmap(bmp, 0 ,0, null);
		}
    }
		
	
	/**
	 * Draws the notes
	 * @param canvas
	 */
	private void drawNotes(Canvas canvas) {
		int pos = firstNotePos;
		Rect noteRect = new Rect();
		double temp = 0;
		int mes = 1;
		
		for (int i = 0; i < myTab.length(); i++) {
			double noteDuration = Duration.valueOf(myTab.getTime(i).getDuration()).getDuration();
			
			
			temp += noteDuration;
			pos += myPace*noteDuration;
			if(temp % 4d == 0d){
				temp = 0;
				paint.setTextSize(36);
				canvas.drawText(Integer.toString(mes++), pos + myPace/2,getHeight() - padding, paint);
				paint.setTextSize(canvas.getHeight()*0.08f);
				paint.setStrokeWidth(3);
				drawVerticalLineOnTab(canvas, pos + myPace/2, (int) (6*canvas.getHeight()/8/(myInstr.getNumOfStrings()+1)));
				paint.setStrokeWidth(1); 
			}
			
			paint.setTextSize(canvas.getHeight()*0.09f);
			
			if (pos-getScrollX() > 0 && pos-getScrollX() < getWidth()) { //Draws only what is necessary
				for (int j = 0; j < myInstr.getNumOfStrings(); j++) {
					paint.getTextBounds(myTab.getTime(i).getNote(j), 0, myTab.getTime(i).getNote(j).length(), noteRect);
					float textHeight = (j+1) * (int) (6*canvas.getHeight()/8/(myInstr.getNumOfStrings()+1)) + canvas.getHeight()*0.1f/3 +padding;
					
					// Draws the rectangle under the note
					paint.setColor(Color.WHITE);
					canvas.drawRect(pos, textHeight-canvas.getHeight()*0.1f , pos+noteRect.right, textHeight, paint);
					
					// Draws the note itself
					paint.setColor(Color.BLACK);
					canvas.drawText(myTab.getTime(i).getNote(j), pos, textHeight, paint);
				}
			}
		}
		endOfTab = pos + 200;
	}
	
	/**
	 * @param canvas
	 * @param y
	 */
	private void drawGrid(Canvas canvas, float y) {
		for (int i = 1; i <= myInstr.getNumOfStrings() ; i++) {	
			canvas.drawLine(startingPos, i * y  + padding, endOfTab, i * y + padding, paint);
		}	
	}

	/**
	 * Draws a thicker vertical line, starting from the top one to the last one.
	 * @param canvas
	 * @param x
	 * @param y
	 */
	private void drawVerticalLineOnTab(Canvas canvas, int x, int y) {
		paint.setStrokeWidth(2);
		canvas.drawLine(x, y  + padding, x, (float) myInstr.getNumOfStrings()*y  + padding, paint);
		paint.setStrokeWidth(1);
	}
	
	public boolean isTerminated() {
		return getScrollX() > endOfTab-135;
	}
}