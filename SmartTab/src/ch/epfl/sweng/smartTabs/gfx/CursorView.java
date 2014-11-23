package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * @author pikkle
 * Draws the orange cursor, on top on the tablature view and the music sheet view.
 */
public class CursorView extends View {

	private Paint paint;
	private final int cursorSize = 5;
	private final int cursorColor = Color.rgb(255 , 165 , 0);
	private final int cursorPosition = 8;
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
		paint.setStrokeWidth(cursorSize);
		paint.setColor(cursorColor);
		canvas.drawLine(canvas.getWidth()/cursorPosition, 0,
				canvas.getWidth()/cursorPosition, canvas.getHeight(), paint);
    }
}