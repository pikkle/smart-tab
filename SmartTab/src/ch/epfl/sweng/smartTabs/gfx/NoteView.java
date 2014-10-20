package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class NoteView extends View{

	int xPos = 0;
	int deltaX = 5;
	private final Paint paint = new Paint();

	public NoteView(Context context) {
		super(context);
		paint.setAntiAlias(true);
	}


	protected void onDraw(Canvas canvas) {		        
		super.onDraw(canvas);
		paint.setColor(Color.WHITE);
		paint.setStyle(Paint.Style.FILL);
		canvas.drawPaint(paint);
		paint.setColor(Color.BLACK);
		paint.setTextSize(48f);
		canvas.drawText("7", getWidth() - xPos, getHeight()/2, paint);
	}

	protected void slideNotes(int speed) {
		xPos += deltaX*speed;
	}
}