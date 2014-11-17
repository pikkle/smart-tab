package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * @author pikkle
 *
 */
public class CursorView extends View {

	private Paint paint;
	
	/**
	 * Draw the cursor
	 * @param context
	 * @param attrs
	 */
	public CursorView(Context context) {
		super(context);	
		paint = new Paint();		
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		paint.setStrokeWidth(5);
		paint.setColor(Color.rgb(255,165,0));
		canvas.drawLine(canvas.getWidth()/8, 0, canvas.getWidth()/8, canvas.getHeight(), paint);
    }
}