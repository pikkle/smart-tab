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
    
    private final static int LEFTPADDING = 50;

    private boolean mDisplayed = true;
	private Paint mPaint;
	private Boolean mRunning = false;
	private Context mContext;
	private FavoritesView mFavs;
	

	/**
	 * Draws the support
	 * @param context
	 * @param attrs
	 */
	public FooterView(Context context, boolean isFav) {
		super(context);
		mContext = context;
		mPaint = new Paint();
		this.setBackgroundColor(Color.WHITE);
		mFavs = new FavoritesView(context, isFav);
	}
	
	
	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mFavs.draw(canvas, mPaint);
		if (mRunning) {
		    drawPlay(canvas); 
		} else {
		    drawPause(canvas);
		}
    }

	private void drawPause(Canvas canvas) {
	    mPaint.setColor(Color.BLACK);
	    Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_action_pause);
	    canvas.drawBitmap(bmp, LEFTPADDING, 0, mPaint);    
    }

    private void drawPlay(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_action_play);
	    canvas.drawBitmap(bmp, LEFTPADDING, 0, mPaint);    
    }

    public boolean isDisplayed() {
		return mDisplayed;
	}
    
    public int getFavPosX() {
    	return mFavs.getFavPositonX();
    }
    
    public FavoritesView getFav() {
    	return mFavs;
    }


    public void playPause() {
        mRunning = !mRunning;
        this.invalidate();
    }
}