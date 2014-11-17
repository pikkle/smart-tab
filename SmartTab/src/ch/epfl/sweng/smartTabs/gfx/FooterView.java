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
public class FooterView extends View{
	private boolean displayed = true;
	private Paint paint;

	/**
	 * @param context
	 * @param attrs
	 */
	public FooterView(Context context) {
		super(context);
		paint = new Paint();
		this.setBackgroundColor(Color.WHITE);
	}
	
	
	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		
    }

	public boolean isDisplayed() {
		return displayed;
	}
}