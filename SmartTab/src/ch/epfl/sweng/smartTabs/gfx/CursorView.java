package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * @author pikkle Draws the orange cursor, on top on the tablature view and the
 *         music sheet view.
 */
public class CursorView extends View {

    private final static int CURSORSIZE = 5;
    private final static int CURSORPOSITION = 8;
    private final static int CURSORCOLOR = Color.rgb(255, 165, 0);

    private Paint mPaint;
    private int mPosX;

    /**
     * Draw the cursor
     * 
     * @param context
     * @param attrs
     */
    public CursorView(Context context) {
        super(context);
        mPaint = new Paint();
        mPosX = context.getResources().getDisplayMetrics().widthPixels / CURSORPOSITION;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setStrokeWidth(CURSORSIZE);
        mPaint.setColor(CURSORCOLOR);
        canvas.drawLine(canvas.getWidth() / CURSORPOSITION, 0,
                canvas.getWidth() / CURSORPOSITION, canvas.getHeight(), mPaint);
    }

    public int getPosX() {
        return mPosX;
    }
}