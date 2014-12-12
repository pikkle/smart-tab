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
    
    private boolean mDisplayed = true;
	private Paint mPaint;
	private Boolean mRunning = false;
	private Context mContext;
	private final static int LEFTPADDING = 50;
	private int speedUpPosX;
	private int speedDownPosX;
	private int speedIconWidth;
	private String speedFactor;
	private FavoritesView mFavs;
	

	/**
	 * Draws the support
	 * @param context
	 * @param attrs
	 */
	public FooterView(Context context, boolean isFav, int factor) {
		super(context);
		mContext = context;
		mPaint = new Paint();
		this.setBackgroundColor(Color.WHITE);

		speedFactor = ""+factor+"%";
		mFavs = new FavoritesView(context, isFav);
	}
	
	
	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mFavs.draw(canvas, mPaint);
		drawSpeedDown(canvas);
		drawSpeedUp(canvas);
		drawSpeed(canvas);
		if (mRunning) {
		    drawPlay(canvas); 
		} else {
		    drawPause(canvas);
		}
    }
	
	private void drawSpeedUp(Canvas canvas){
		mPaint.setColor(Color.BLACK);
	    Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_action_fast_forward);
	    setSpeedIconWidth(bmp.getWidth());
	    speedUpPosX = canvas.getWidth()/2+50;
	    canvas.drawBitmap(bmp, speedUpPosX, 0, mPaint);
	}
	
	private void drawSpeed(Canvas canvas) {
		mPaint.setColor(Color.BLACK);
		mPaint.setTextSize(32);
		canvas.drawText(speedFactor, canvas.getWidth()/2 -40, canvas.getHeight()
				/2, mPaint);
	}
	
	private void drawSpeedDown(Canvas canvas){
		mPaint.setColor(Color.BLACK);
	    Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_action_rewind);
	    speedDownPosX = canvas.getWidth()/2-(50+bmp.getWidth());
	    canvas.drawBitmap(bmp, speedDownPosX, 0, mPaint);
	}
	
	public void setSpeedFactor(int factor) {
		speedFactor = ""+factor+"%";
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

    public int getSpeedUpPosX() {
    	return speedUpPosX;
    }
    
    public int getSpeedDownPosX() {
    	return speedDownPosX;
    }
    
    public int getSpeedIconWidth() {
    	return speedIconWidth;
    }
    
    public void setSpeedIconWidth(int width) {
    	speedIconWidth = width;
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
    
    public void setRunning(boolean isRunning){
    	mRunning = isRunning;
    	this.invalidate();
    }

    public void playPause() {
        mRunning = !mRunning;
        this.invalidate();
    }
}