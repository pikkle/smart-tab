package ch.epfl.sweng.smartTabs.gfx;



import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

public class NoteView extends View implements Runnable{


	private final int RADIUS = 20;
	int xPos = 0;
	int yPos = 0;
	int deltaX = 5;
	int deltaY = 5;
	private final Paint paint = new Paint();

	public NoteView(Context context) {
		super(context);
	}


	protected void onDraw(Canvas canvas) {		        
		super.onDraw(canvas);
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
		
		canvas.drawColor(Color.BLACK);
		canvas.drawCircle(xPos, yPos, RADIUS, paint);

	}

	@Override
	public void run() {
		xPos += deltaX;
			if(deltaX > 0){
				if(xPos >= getWidth() - RADIUS/2){
					deltaX *= -1;
				}
			}else{
				if(xPos <= 0){
					deltaX *= -1;
				}
			}

			yPos += deltaY;
			if(deltaY > 0){
				if(yPos >= getHeight() - RADIUS/2){
					deltaY *= -1;
				}
			}else{
				if(yPos <= 0){
					deltaY *= -1;
				}
			}
		
	}
}