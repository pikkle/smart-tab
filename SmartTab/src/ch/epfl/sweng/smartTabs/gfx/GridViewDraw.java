package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author lourichard ( swag tmtc =D)
 *
 */
public class GridViewDraw extends Drawable{
	private final Paint paint = new Paint();
	private final Height[] stantardTuning = {Height.E, Height.B, Height.G, Height.D, Height.A, Height.E };
	private final float ratio = 0.34375f;
	private int mWidth;
	private int mHeight;
	private Instrument myInstrument;
	private Tab myTab;
	private boolean displayTab;
	private boolean displayPartition;
	
	public GridViewDraw(int width, int height, Instrument instru, Tab tab) {
		super();
		mWidth = width;
		mHeight = height;
		myInstrument = instru; // Swaggy Baby tu PESES dans le GAME
		myTab = tab;
		
	}

	@Override
	public void draw(Canvas canvas) {
		clearScreen(canvas);
		drawNameSong(canvas);
		
		drawStrings(canvas, false);
		drawStandardGrid(canvas, false);
		
		drawCursor(canvas);
		
		
	}

	private void drawStandardGrid(Canvas canvas, boolean centered) {
		paint.setColor(Color.BLACK);
		paint.setTextSize(36f);
		paint.setStrokeWidth(2f);
		if (centered){
			for (int i = 0; i < 5; i++) {
				canvas.drawLine(mWidth/10, ratio*mHeight + i*mHeight/20, mWidth, ratio*mHeight + i*mHeight/20, paint);
				canvas.drawText(stantardTuning[i]+"", mWidth/16, ratio*mHeight + i*mHeight/16, paint);
			}
			canvas.drawLine(mWidth/10, ratio*mHeight, mWidth/10, ratio*mHeight + 5*mHeight/16, paint);
		} else {
			float distributedHeight = mHeight/8;
			for (int i = 0; i < 5; i++) {
				canvas.drawLine(mWidth/10, distributedHeight + i*mHeight/20, mWidth, distributedHeight + i*mHeight/20, paint);
			}
			paint.setStrokeWidth(15f);
			canvas.drawLine(mWidth/10 + 2f, distributedHeight, mWidth/10 + 2f, distributedHeight + 4*mHeight/20, paint);
		}
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}
	
	private void clearScreen(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
	}
	
	private void drawNameSong(Canvas canvas) {
		paint.setTextSize(48f);
		paint.setColor(Color.GRAY);
		canvas.drawText(myTab.getTabName(), 50, 100, paint);
	}
	
	private void drawStrings(Canvas canvas, boolean centered) {
		paint.setColor(Color.BLACK);
		paint.setTextSize(36f);
		paint.setStrokeWidth(2f);
		if (centered){
			for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
				canvas.drawLine(mWidth/10, ratio*mHeight + i*mHeight/16, mWidth, ratio*mHeight + i*mHeight/16, paint);
				canvas.drawText(stantardTuning[i]+"", mWidth/16, ratio*mHeight + i*mHeight/16, paint);
			}
			canvas.drawLine(mWidth/10, ratio*mHeight, mWidth/10, ratio*mHeight + 5*mHeight/16, paint);
		} else {
			float distributedHeight = (mHeight-(mHeight/8))/2;
			for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
				canvas.drawLine(mWidth/10, distributedHeight + i*mHeight/16, mWidth, distributedHeight + i*mHeight/16, paint);
				canvas.drawText(stantardTuning[i]+"", mWidth/16, distributedHeight + i*mHeight/16, paint);
			}
			canvas.drawLine(mWidth/10, distributedHeight, mWidth/10, distributedHeight + 5*mHeight/16, paint);
		}
		
	}

	private void drawCursor(Canvas canvas) {
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10f);
		canvas.drawLine(mWidth/4, mHeight/8, mWidth/4, 7*mHeight/8, paint);
		paint.setStrokeWidth(1f);
	}
}
