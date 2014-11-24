package ch.epfl.sweng.smartTabs.gfx;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

public class Metronome {
    private Paint mPaint;

    public Metronome() {
        mPaint = new Paint();
        mPaint.setColor(Color.rgb(255, 165, 0));
        // mPaint.setStyle(Paint.Style.STROKE);
    }

    protected void draw(Canvas canvas, int posX, int posY, int radius, int alpha) {
        mPaint.setAlpha(alpha);
        canvas.drawCircle(posX, posY, radius, mPaint);
    }

}
