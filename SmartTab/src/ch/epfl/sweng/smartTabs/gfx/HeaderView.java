package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * The top view containing the title and the metronome.
 */
public class HeaderView extends View{
	private Paint paint;
	private String mTitle;
	
	private final int titleAlpha = 255;
	private final float titleSizeRatio = 0.5f;

	/**
	 * Contains the title of the song.
	 * @param context
	 * @param title
	 */
	public HeaderView(Context context, String title) {
		super(context);	
		this.paint = new Paint();
		this.setBackgroundColor(Color.WHITE);
		this.mTitle = title;
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		paint.setTextSize(canvas.getHeight()*titleSizeRatio);
		paint.setAlpha(titleAlpha);
		paint.setColor(Color.GRAY);
		canvas.drawText(mTitle, canvas.getWidth()/16, canvas.getHeight()/2, paint);	
    }
}
