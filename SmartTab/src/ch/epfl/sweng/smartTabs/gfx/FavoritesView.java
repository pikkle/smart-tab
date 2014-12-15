package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * View for the "Favorites" sliding menu
 *
 */
public class FavoritesView extends View {

    private final static int OFFSET = 50;
    private boolean mIsFavorite;
    private Context mContext;
    private int mPosX;
    private int mPosY;

    public FavoritesView(Context context, boolean favorite) {
        super(context);
        mContext = context;
        mIsFavorite = favorite;
    }

    public void draw(Canvas canvas, Paint paint) {
        Bitmap bmp;
        if (mIsFavorite) {
            bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.btn_star_big_on);
        } else {
            bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.btn_star_big_off);
        }
        mPosX = canvas.getWidth() - bmp.getWidth() - OFFSET;
        mPosY = 0;
        canvas.drawBitmap(bmp, mPosX, mPosY, paint);
    }

    public void setIsFav() {
        mIsFavorite = !mIsFavorite;
    }

    public boolean isFav() {
        return mIsFavorite;
    }

    public int getFavPositonX() {
        return mPosX;
    }

    public int getFavPositionY() {
        return mPosY;
    }
}
