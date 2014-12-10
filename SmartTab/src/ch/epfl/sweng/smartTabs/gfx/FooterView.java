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
	    paint.setColor(Color.BLACK);
	    drawEmptyRect(canvas,50, canvas.getHeight()/4, 70,  3*canvas.getHeight()/4);
	    drawEmptyRect(canvas,80, canvas.getHeight()/4, 100, 3*canvas.getHeight()/4);	    
    }


    private void drawEmptyRect(Canvas canvas, int left, int top, int right, int down) {
        canvas.drawLine(left, top, right, top, paint);
        canvas.drawLine(right, top, right, down, paint);
        canvas.drawLine(right, down, left, down, paint);
        canvas.drawLine(left, down, left, top, paint);
    }


    private void drawPlay(Canvas canvas) {
        paint.setColor(Color.BLACK);
        canvas.drawLine(50, canvas.getHeight()/4, 100, canvas.getHeight()/2, paint);
        canvas.drawLine(100, canvas.getHeight()/2,50, 3*canvas.getHeight()/4,  paint);
        canvas.drawLine(50, 3*canvas.getHeight()/4, 50, canvas.getHeight()/4, paint);
    }


    public boolean isDisplayed() {
		return displayed;
	}


    public void playPause() {
        running = !running;
        this.invalidate();
    }
}