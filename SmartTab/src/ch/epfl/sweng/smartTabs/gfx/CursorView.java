package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * @author pikkle
 *
 */
public class CursorView extends View {

	private Paint paint;
	private int posX;
	
	/**
	 * Draw the cursor
	 * @param context
	 * @param attrs
	 */
	public CursorView(Context context) {
		super(context);	
		paint = new Paint();
		WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
		Display display = wm.getDefaultDisplay();
		posX = display.getWidth()/8;
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		paint.setStrokeWidth(5);
		paint.setColor(Color.rgb(255,165,0));
		canvas.drawLine(posX, 0, canvas.getWidth()/8, canvas.getHeight(), paint);
    }
	
	public int getPosX(){
		return posX;
	}
}