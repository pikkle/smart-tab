package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author lourichard
 *
 */
public class GridViewDraw extends Drawable{
	private final Paint paint = new Paint();
	// The tuning is hard coded for the moment, until we create the Note class
	private final char[] stantardTuning = {'e', 'B', 'G', 'D', 'A', 'E'};
	private final float ratio = 0.34375f;
	private int mWidth;
	private int mHeight;
	private String nameSong;
	
	public GridViewDraw(int width, int height, String name) {
		super();
		mWidth = width;
		mHeight = height;
		nameSong = name;
		
	}

	@Override
	public void draw(Canvas canvas) {
		clearScreen(canvas);
		drawNameSong(canvas);
		drawStrings(canvas);
		drawCursor(canvas);
		
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
	
	private void clearScreen(Canvas canvas){
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
	}

	private void drawNameSong(Canvas canvas){
		paint.setTextSize(48f);
		paint.setColor(Color.GRAY);
		canvas.drawText(nameSong, 50, 100, paint);
	}

	private void drawStrings(Canvas canvas){
		paint.setColor(Color.BLACK);
		paint.setTextSize(36f);
		paint.setStrokeWidth(2f);
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(mWidth/8, ratio*mHeight + i*mHeight/16, mWidth, ratio*mHeight + i*mHeight/16, paint);
			canvas.drawText(stantardTuning[i]+"", mWidth/16, ratio*mHeight + i*mHeight/16, paint);
		}
		canvas.drawLine(mWidth/8, ratio*mHeight, mWidth/8, ratio*mHeight + 5*mHeight/16, paint);
	}

	private void drawCursor(Canvas canvas){
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10f);
		canvas.drawLine(mWidth/3, mHeight/4, mWidth/3,3*mHeight/4, paint);
		paint.setStrokeWidth(1f);
	}
}
