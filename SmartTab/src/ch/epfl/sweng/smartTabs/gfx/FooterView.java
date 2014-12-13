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
 * The footer has various options such as a slider to change the speed.
 */
public class FooterView extends View {

    private final static int LEFTPADDING = 50;
    private final static int TEXTSIZE = 32;
    private final static int HORIZONTALOFFSET = 50;
    private final static int TEXTOFFSET = 40;

    private Paint mPaint;
    private Context mContext;
    private int mSpeedUpPosX;
    private int mSpeedDownPosX;
    private int mSpeedIconWidth;
    private String mSpeedFactor;
    private FavoritesView mFavs;
    private boolean mDisplayed = true;
    private Boolean mRunning = false;

    /**
     * Draws the support
     * 
     * @param context
     * @param attrs
     */
    public FooterView(Context context, boolean isFav, int factor) {
        super(context);
        mContext = context;
        mPaint = new Paint();
        this.setBackgroundColor(Color.WHITE);

        mSpeedFactor = "" + factor + "%";
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

    private void drawSpeedUp(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_action_fast_forward);
        setSpeedIconWidth(bmp.getWidth());
        mSpeedUpPosX = canvas.getWidth() / 2 + HORIZONTALOFFSET;
        canvas.drawBitmap(bmp, mSpeedUpPosX, 0, mPaint);
    }

    private void drawSpeed(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        mPaint.setTextSize(TEXTSIZE);
        canvas.drawText(mSpeedFactor, canvas.getWidth() / 2 - TEXTOFFSET, canvas.getHeight() / 2, mPaint);
    }

    private void drawSpeedDown(Canvas canvas) {
        mPaint.setColor(Color.BLACK);
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.ic_action_rewind);
        mSpeedDownPosX = canvas.getWidth() / 2 - (HORIZONTALOFFSET + bmp.getWidth());
        canvas.drawBitmap(bmp, mSpeedDownPosX, 0, mPaint);
    }

    public void setSpeedFactor(int factor) {
        mSpeedFactor = "" + factor + "%";
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
        return mSpeedUpPosX;
    }

    public int getSpeedDownPosX() {
        return mSpeedDownPosX;
    }

    public int getSpeedIconWidth() {
        return mSpeedIconWidth;
    }

    public void setSpeedIconWidth(int width) {
        mSpeedIconWidth = width;
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

    public void setRunning(boolean isRunning) {
        mRunning = isRunning;
        this.invalidate();
    }

    public void playPause() {
        mRunning = !mRunning;
        this.invalidate();
    }
}