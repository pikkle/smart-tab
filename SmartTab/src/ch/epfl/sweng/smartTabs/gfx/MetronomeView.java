package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import ch.epfl.sweng.smartTabs.music.Tab;

public class MetronomeView extends View {
	private int mTempo;
	private Paint mPaint;
	private Context mContext;

	public MetronomeView(Context context, Tab tab) {
		super(context);
		mContext = context;
		mTempo = tab.getTempo();
	}
	
	protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		mPaint = new Paint();
		mPaint.setColor(Color.RED);
		
	}

	
}
