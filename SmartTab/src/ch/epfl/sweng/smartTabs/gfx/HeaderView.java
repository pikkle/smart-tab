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
import android.util.AttributeSet;
import android.view.View;

/**
 * @author pikkle
 * 
 */
public class HeaderView extends View{
	private Resources res;
	private Paint paint;
	private String mTitle;
	private Metronome metronome;
	private double mPct;
	
	/**
	 * @param context
	 * @param attrs
	 */
	public HeaderView(Context context, String title) {
		super(context);
		paint = new Paint();
		res = context.getResources();
		mTitle = title;
		mPct = 1.0;
		metronome = new Metronome();
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
		
		
		//Drawing the Metronome
		int padding = 4;
		int radius = (int) (mPct*(canvas.getHeight() - 2*padding)/2);
		int posX = canvas.getWidth() - (canvas.getHeight() - 2*padding)/2 - padding/2;
		int posY = canvas.getHeight()/2;
		int alpha = (int) (mPct*255);
//		System.out.println("Radius : " + radius + " Alpha : " + alpha);
		metronome.draw(canvas, posX, posY, radius, alpha);
		
    }
	private void drawDebugBox(Canvas canvas) {
		paint.setColor(Color.RED);
		paint.setAlpha(100);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
	}
	
	public void incPct(){
		if(mPct <= 0.975){
			mPct += 0.025;			
		} else {
			mPct = 1;
		}
//		System.out.println("Incrementing : " + mPct);

	}
	
	public void decPct(){
		if(mPct >= 0.5){
			mPct -= 0.025;			
		} else {
			mPct = 0.5;
		}
//		System.out.println("Decrementing : " + mPct);
	}
	
	public void setPct(double pct){
		mPct = pct;
	}
}
