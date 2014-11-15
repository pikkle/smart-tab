/**
 * 
 */
package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.R;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

/**
 * @author pikkle
 *
 */
public class FooterView extends View{
	private boolean displayed = true;
	private Paint paint;
	private Resources res;

	/**
	 * @param context
	 * @param attrs
	 */
	public FooterView(Context context, AttributeSet attrs) {
		super(context, attrs);
		paint = new Paint();
		res = context.getResources();
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.GREEN);
		paint.setAlpha(100);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
		float textSize = canvas.getHeight()*0.8f;
		paint.setTextSize(textSize);
		paint.setAlpha(255);
		paint.setColor(Color.GRAY);
		canvas.drawText("Footer !", 0, canvas.getHeight()/2+textSize/2, paint);
		
    }
	
	public void hide() {
		displayed = false;
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
		params.weight = 0;
		setLayoutParams(params);
	}
	
	public void show() {
		displayed = true;
		LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) getLayoutParams();
		params.weight = res.getInteger(R.integer.footerRatio);
		setLayoutParams(params);
	}

	public boolean isDisplayed() {
		return displayed;
	}
}
