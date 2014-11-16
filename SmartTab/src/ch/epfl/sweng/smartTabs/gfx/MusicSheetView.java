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
		int noteWidth = (int) (canvas.getWidth()/100);
		//create the bitmaps
		doubleCroche = Bitmap.createScaledBitmap(doubleCroche, noteWidth, noteHeight(doubleCroche, noteWidth), false);
		croche = Bitmap.createScaledBitmap(croche, noteWidth, noteHeight(croche, noteWidth), false);
		noire = Bitmap.createScaledBitmap(noire, noteWidth, noteHeight(noire, noteWidth), false);
		blanche = Bitmap.createScaledBitmap(blanche, noteWidth, noteHeight(blanche, noteWidth), false);
		ronde = Bitmap.createScaledBitmap(ronde, noteWidth, noteHeight(ronde, noteWidth), false);
		
		//to do : create the grid
		int startingPos = canvas.getWidth()*1/4;
		int tabLineMargin = (int) (canvas.getHeight()/(6));
		
		for (int i = 1; i <= 5; i++) { //Draws the grid
			paint.setStrokeWidth(1);
			canvas.drawLine(
					startingPos, 
					i * tabLineMargin, 
					endOfTab, //TODO: calculate size of tab
					i * tabLineMargin, 
					paint);
			
		}
		//adding notes to the grid
		
	}
	
	private int noteHeight(Bitmap note, int noteWidth) {
		return note.getHeight() * noteWidth / note.getWidth();
	}
	
}
