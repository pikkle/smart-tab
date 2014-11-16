/**
 * 
 */
package ch.epfl.sweng.smartTabs.gfx;

import android.R.color;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

/**
 * @author pikkle
 *
 */
public class CursorView extends View {
	private Paint paint;
	/**
	 * @param context
	 * @param attrs
	 */
	public CursorView(Context context) {
		super(context);
		paint = new Paint();
		
	}
	@Override
	public void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(color.holo_orange_dark);
		canvas.drawRect(100, 0, 150, canvas.getHeight(), paint);
	}

}
