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
	private Metronome metronome;
	private double mPct;

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
		metronome = new Metronome();
	}
	
	@Override
    protected void onDraw(Canvas canvas) {
		paint.setTextSize(canvas.getHeight()*0.5f);
		paint.setAlpha(255);
		paint.setColor(Color.GRAY);
		canvas.drawText(mTitle, canvas.getWidth()/16, canvas.getHeight()/2, paint);	
		
		
		//Drawing the Metronome
		int padding = 4;
		int radius = (int) (mPct*(canvas.getHeight() - 2*padding)/2);
		int posX = canvas.getWidth() - (canvas.getHeight() - 2*padding)/2 - padding/2;
		int posY = canvas.getHeight()/2;
		int alpha = (int) (mPct*255);
		metronome.draw(canvas, posX, posY, radius, alpha);
	}
	private void drawDebugBox(Canvas canvas) {
		paint.setColor(Color.RED);
		paint.setAlpha(100);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
	}
	
	public void incPct(){
		if(mPct <= 0.975){
			mPct += 0.025;			
		} else {
			mPct = 1;
		}
	}
	
	public void decPct(){
		if(mPct >= 0.5){
			mPct -= 0.025;			
		} else {
			mPct = 0.5;
		}
	}
	
	public void setPct(double pct){
		mPct = pct;
	}
}
