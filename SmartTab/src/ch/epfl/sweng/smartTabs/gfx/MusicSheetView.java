/**
 * 
 */
package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Tab;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * @author pikkle
 *
 */
public class MusicSheetView extends View{
	private Paint paint;
	private Resources res;
	private Tab mTab;
	private int pace = 200;
	private int endOfTab;
	//Bitmap factories to create Bitmap
	private Bitmap noire = BitmapFactory.decodeResource(getResources(), R.raw.noire);
	//to do : update with right resources
	private Bitmap doubleCroche = BitmapFactory.decodeResource(getResources(), R.raw.noire);
	private Bitmap croche = BitmapFactory.decodeResource(getResources(), R.raw.noire);
	private Bitmap blanche = BitmapFactory.decodeResource(getResources(), R.raw.noire);
	private Bitmap ronde = BitmapFactory.decodeResource(getResources(), R.raw.noire);

	
	/**
	 * @param context
	 * @param attrs
	 */
	public MusicSheetView(Context context, Tab tab) {
		super(context);
		paint = new Paint();
		this.mTab = tab;
		endOfTab = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
	}

	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.CYAN);
		paint.setAlpha(100);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
		paint.setColor(Color.BLACK);
		//canvas.drawLine(0, 0, 100000, 6000, paint);
		int noteWidth = (int) (canvas.getWidth()/35);
		//create the bitmaps
		doubleCroche = Bitmap.createScaledBitmap(doubleCroche, noteWidth, noteHeight(doubleCroche, noteWidth), false);
		croche = Bitmap.createScaledBitmap(croche, noteWidth, noteHeight(croche, noteWidth), false);
		noire = Bitmap.createScaledBitmap(noire, noteWidth, noteHeight(noire, noteWidth), false);
		blanche = Bitmap.createScaledBitmap(blanche, noteWidth, noteHeight(blanche, noteWidth), false);
		ronde = Bitmap.createScaledBitmap(ronde, noteWidth, noteHeight(ronde, noteWidth), false);
		
		//to do : create the grid
		int startingPos = canvas.getWidth()*1/4;
		int lineMargin = (int) (canvas.getHeight()/(6));
		
		for (int i = 1; i <= 5; i++) { //Draws the grid
			paint.setStrokeWidth(1);
			canvas.drawLine(
					startingPos, 
					i * lineMargin, 
					endOfTab, //TODO: calculate size of tab
					i * lineMargin, 
					paint);
			
		}
		//adding notes to the grid
		int pos = startingPos + 100;
		Rect noteRect = new Rect();
		for (int i = 0; i < mTab.length(); i++) {
			double noteDuration = Duration.valueOf(mTab.getTime(i).getDuration()).getDuration();
			pos += pace*noteDuration;
			int noteHeightPos = lineMargin + lineMargin/2;
			if (pos-getScrollX() > 0 && pos-getScrollX() < getWidth()) { //Draws only what is necessary
				for (int j = 0; j < 5; j++) {
					if(mTab.getTime(i).getPartitionNote(j) != null) {
						System.out.println("note : " + mTab.getTime(i).getPartitionNote(j).getHeight().name());
						canvas.drawBitmap(noire, pos, noteHeightPos + j*lineMargin - noteHeight(noire, noteWidth), paint);
					}
				}
			}
		}
		endOfTab = pos + 100;
	}
	
	private int noteHeight(Bitmap note, int noteWidth) {
		return note.getHeight() * noteWidth / note.getWidth();
	}
	
	
}
