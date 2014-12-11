package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import ch.epfl.sweng.smartTabs.R;


/**
 *	The footer has various options such as a slider to change the speed.
 */
public class FooterView extends View {
	private boolean displayed = true;
	private Paint paint;
	private Boolean running = false;
	private Context mContext;
	private FavoritesView favs;
	

	/**
	 * Draws the support
	 * @param context
	 * @param attrs
	 */
	public FooterView(Context context, boolean isFav) {
		super(context);
		mContext = context;
		paint = new Paint();
		this.setBackgroundColor(Color.WHITE);
		favs = new FavoritesView(context, isFav);
	}
	
	
	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		favs.draw(canvas, paint);
		if (running) {
		    drawPlay(canvas);
		}
		else {
		    drawPause(canvas);
		}
    }

	private void drawPause(Canvas canvas) {
	    paint.setColor(Color.BLACK);
	    Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_action_pause);
	    canvas.drawBitmap(bmp, 50, 0, paint);    
    }

    private void drawPlay(Canvas canvas) {
        paint.setColor(Color.BLACK);
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_action_play);
	    canvas.drawBitmap(bmp, 50, 0, paint);    
    }


    public boolean isDisplayed() {
		return displayed;
	}
    
    public int getFavPosX(){
    	return favs.getFavPositonX();
    }
    
    public FavoritesView getFav(){
    	return favs;
    }


    public void playPause() {
        running = !running;
        this.invalidate();
    }
}