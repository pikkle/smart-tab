package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.music.Tab;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * @author fatonramadani
 * The view which draws the static parts of the tab, such as, the tuning and the name
 */
public class GridView extends View {
	
	private final Paint paint = new Paint();
	// The tuning is hard coded for the moment, until we create the Note class
	private final char[] stantardTuning = {'e', 'B', 'G', 'D', 'A', 'E'};
	private final float ratio = 0.34375f;
	

	public GridView(Context context) {
		super(context);
	}

	@Override
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		clearScreen(canvas);
		drawNameSong(canvas);
		drawStrings(canvas);
		drawCursor(canvas);
	}

	private void clearScreen(Canvas canvas){
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
	}

	private void drawNameSong(Canvas canvas){
		paint.setTextSize(48f);
		paint.setColor(Color.GRAY);
		canvas.drawText("Test", 50, 100, paint);
	}

	private void drawStrings(Canvas canvas){
		paint.setColor(Color.BLACK);
		paint.setTextSize(36f);
		paint.setStrokeWidth(2f);
		for (int i = 0; i < 6; i++) {
			canvas.drawLine(getWidth()/8, ratio*getHeight() + i*getHeight()/16, getWidth(), ratio*getHeight() + i*getHeight()/16, paint);
			canvas.drawText(stantardTuning[i]+"", getWidth()/16, ratio*getHeight() + i*getHeight()/16, paint);
		}
		canvas.drawLine(getWidth()/8, ratio*getHeight(), getWidth()/8, ratio*getHeight() + 5*getHeight()/16, paint);
	}

	private void drawCursor(Canvas canvas){
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10f);
		canvas.drawLine(getWidth()/3, getHeight()/4, getWidth()/3,3*getHeight()/4, paint);
		paint.setStrokeWidth(1f);
	}
}