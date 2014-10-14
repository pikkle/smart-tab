package ch.epfl.sweng.smartTabs.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;
import android.view.View;

@SuppressLint("DrawAllocation") 
public class DisplayActivity extends Activity {
	
	private final float RATIO = 0.34375f;
	private final char[] STANDARD_TUNNING = {'e','B','G','D','A','E'};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new MyView(this));
		
	}
	
	public class MyView extends View {
        public MyView(Context context) {
             super(context);
        }

        @Override
        protected void onDraw(Canvas canvas) {
           super.onDraw(canvas);

           
           Paint paint = new Paint();
           paint.setStyle(Paint.Style.FILL);
           paint.setColor(Color.WHITE);
           canvas.drawPaint(paint);
           drawNameSong(paint, canvas);
           drawStrings(paint, canvas);
           drawCursor(paint, canvas);
       }
        
       private void drawNameSong(Paint paint, Canvas canvas){
    	   paint.setTextSize(48f);
           paint.setColor(Color.GRAY);
           canvas.drawText("Jeux interdits", 50, 100, paint);
       }
       
       private void drawStrings(Paint paint, Canvas canvas){
           paint.setColor(Color.BLACK);
           paint.setTextSize(36f);
           paint.setStrokeWidth(2f);
           for (int i = 0; i < 6; i++) {
        	   canvas.drawLine(getWidth()/8, RATIO*getHeight() + i*getHeight()/16, getWidth(), RATIO*getHeight() + i*getHeight()/16, paint);
        	   canvas.drawText(STANDARD_TUNNING[i]+"", getWidth()/16, RATIO*getHeight() + i*getHeight()/16, paint);
           }
           canvas.drawLine(getWidth()/8, RATIO*getHeight(), getWidth()/8, RATIO*getHeight() + 5*getHeight()/16, paint);
       }
       
       private void drawCursor(Paint paint, Canvas canvas){
    	   paint.setColor(Color.RED);
    	   paint.setStrokeWidth(10f);
    	   canvas.drawLine(getWidth()/3, getHeight()/4, getWidth()/3,3*getHeight()/4, paint);
    	   paint.setStrokeWidth(1f);
       }
    }
}
