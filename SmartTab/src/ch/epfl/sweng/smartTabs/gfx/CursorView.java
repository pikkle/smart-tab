package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.view.View;

/**
 * @author pikkle Draws the orange cursor, on top on the tablature view and the
 *         music sheet view.
 */
public class CursorView extends View {

    private Paint paint;
    private final static int CURSORSIZE = 5;
    private final static int CURSORPOSITION = 8;
    private int posX;

    /**
     * Draw the cursor
     * 
     * @param context
     * @param attrs
     */
    public CursorView(Context context) {
        super(context);
        paint = new Paint();
        posX = context.getResources().getDisplayMetrics().widthPixels / CURSORPOSITION;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStrokeWidth(CURSORSIZE);
        paint.setColor(CURSORPOSITION);
        canvas.drawLine(canvas.getWidth() / CURSORPOSITION, 0,
                canvas.getWidth() / CURSORPOSITION, canvas.getHeight(), paint);

    }

    public int getPosX() {
        return posX;
    }
}