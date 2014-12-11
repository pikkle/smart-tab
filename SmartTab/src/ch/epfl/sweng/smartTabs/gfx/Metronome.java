package ch.epfl.sweng.smartTabs.gfx;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Class implementing the draw method for the metronome 
 *
 */
public class Metronome {
    private Paint mPaint;
    private final static int RED = 255;
    private final static int GREEN = 165;
    private final static int BLUE = 0;
    

    public Metronome() {
        mPaint = new Paint();
        mPaint.setColor(Color.rgb(RED, GREEN, BLUE));
    }

    protected void draw(Canvas canvas, int posX, int posY, int radius, int alpha) {
        mPaint.setAlpha(alpha);
        canvas.drawCircle(posX, posY, radius, mPaint);
    }
}
