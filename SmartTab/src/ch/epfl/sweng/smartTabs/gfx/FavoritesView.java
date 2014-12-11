package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.R;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * View for the favorite tab
 *
 */
public class FavoritesView extends View{
	
	private boolean isFavorite;
	private Context mContext;
	private int posX;
	private int posY;
	
	public FavoritesView(Context context, boolean favorite){
		super(context);
		mContext = context;
		isFavorite=favorite;
	}
	
	public void draw(Canvas canvas, Paint paint){
		Bitmap bmp;
		if(isFavorite){
			bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.btn_star_big_on);
		} else {
			bmp = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.btn_star_big_off);
		}
		posX = canvas.getWidth() - bmp.getWidth() - 50;
		posY = 0;
		canvas.drawBitmap(bmp, posX, posY, paint);   
	}
	
	public void setIsFav() {
		isFavorite = !isFavorite;
	}
	
	public boolean isFav() {
		return isFavorite;
	}
	
	public int getFavPositonX(){
		return posX;
	}

	public int getFavPositionY(){
		return posY;
	}
}
