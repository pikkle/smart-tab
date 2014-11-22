/**
 * 
 */
package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

/**
 * @author pikkle
 * 
 */
public class HeaderView extends View{
	private Resources res;
	private Paint paint;
	private String mTitle;
	
	/**
	 * @param context
	 * @param attrs
	 */
	public HeaderView(Context context, String title) {
		super(context);
		paint = new Paint();
		res = context.getResources();
		mTitle = title;
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		drawDebugBox(canvas);
		float textSize = canvas.getHeight()*0.6f;
		paint.setTextSize(textSize);
		paint.setAlpha(255);
		paint.setColor(Color.GRAY);
		Rect textRect = new Rect();
		paint.getTextBounds(mTitle, 0, mTitle.length(), textRect);
		canvas.drawText(mTitle, 0, canvas.getHeight()/2-textRect.centerY(), paint);
		
    }
	private void drawDebugBox(Canvas canvas) {
		paint.setColor(Color.RED);
		paint.setAlpha(100);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
	}
}
