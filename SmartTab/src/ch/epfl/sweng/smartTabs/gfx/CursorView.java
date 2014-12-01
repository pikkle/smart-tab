package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

/**
 * @author pikkle Draws the orange cursor, on top on the tablature view and the
 *         music sheet view.
 */
public class CursorView extends View {

    private Paint paint;
    private final int cursorSize = 5;
    private final int cursorColor = Color.rgb(255, 165, 0);
    private final int cursorPosition = 8;
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
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        posX = display.getWidth() / 8;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setStrokeWidth(cursorSize);
        paint.setColor(cursorColor);
        canvas.drawLine(canvas.getWidth() / cursorPosition, 0,
                canvas.getWidth() / cursorPosition, canvas.getHeight(), paint);

    }

    public int getPosX() {
        return posX;
    }
}