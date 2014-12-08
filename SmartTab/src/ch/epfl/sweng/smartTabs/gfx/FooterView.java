package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 *	The footer has various options such as a slider to change the speed.
 */
public class FooterView extends View {
	private boolean displayed = true;
	@SuppressWarnings("unused")
	private Paint paint;
	private Boolean running = false;

	/**
	 * Draws the support
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
		if (running) {
		    drawPlay(canvas);
		}
		else {
		    drawPause(canvas);
		}
    }

	private void drawPause(Canvas canvas) {
        // TODO Auto-generated method stub
        
    }


    private void drawPlay(Canvas canvas) {
        // TODO Auto-generated method stub
        
    }


    public boolean isDisplayed() {
		return displayed;
	}


    public void playPause() {
        running = !running;
    }
}