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
		paint.setTextSize(canvas.getHeight()*0.5f);
		paint.setAlpha(255);
		paint.setColor(Color.GRAY);
		canvas.drawText(mTitle, canvas.getWidth()/16, canvas.getHeight()/2, paint);	
    }
}