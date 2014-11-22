/**
 * 
 */
package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * @author pikkle
 *
 */
public class MusicSheetView extends View{
	private Paint paint;
	private Resources res;
	/**
	 * @param context
	 * @param attrs
	 */
	public MusicSheetView(Context context) {
		super(context);
		paint = new Paint();
	}

	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.CYAN);
		paint.setAlpha(100);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
		paint.setColor(Color.BLACK);
		canvas.drawLine(0, 0, 100000, 6000, paint);
    }
}
