package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;

/**
 * @author pikkle TablatureView describe the view holding the tablature.
 */
public class TablatureView extends View {
    private final Paint paint = new Paint();
    private final Instrument myInstr;
    private Tab myTab;
    private int myPace;
    private int endOfTab;
    private float padding;
    private int startingPos; // Display initially starts at 100px
    private int firstNotePos; // First note's position
    private Bitmap bmp;
    private boolean firstDraw = true;
    private int mes = 1;

    private final int mesureTextSize = 36;

    /**
     * 
     * @param context
     * @param attrs
     */
    public TablatureView(Context context, Tab tab, Instrument instr, int pace) {
        super(context);

        this.myTab = tab;
        this.myInstr = instr;
        this.myPace = pace;
        this.setBackgroundColor(Color.WHITE);

        // TODO : clean with non deprecated methods
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);

        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;
        padding = height / 8;
        startingPos = width / 8;
        firstNotePos = startingPos + 2 * pace;

        tab.initTimeMap(firstNotePos);

        // TODO : Can be a field in the JSON
        Point end = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(end);
        endOfTab = end.y;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (firstDraw) {
            initBmp(canvas);
        }
        canvas.drawBitmap(bmp, 500, 500, null);
    }

    /**
     * @param canvas
     * @param y
     */
    private void drawGrid(Canvas canvas, float y) {
        for (int i = 1; i <= myInstr.getNumOfStrings(); i++) {
            canvas.drawLine(startingPos, i * y + padding, 3000,
                    i * y + padding, paint);
        }
    }

    /**
     * Draws a thicker vertical line, starting from the top one to the last one.
     * 
     * @param canvas
     * @param x
     * @param y
     */
    private void drawVerticalLineOnTab(Canvas canvas, int x, int y, Paint p) {
        canvas.drawLine(x, y + padding, x, (float) myInstr.getNumOfStrings()
                * y + padding, p);
    }

    /**
     * @return
     */
    public boolean isTerminated() {
        return getScrollX() > endOfTab - 135;
    }

    /**
     * Draws the tablature into a cavans, and stores is in a BMP, so redraws are
     * much faster.
     * 
     * @param canvas
     */
    public void initBmp(Canvas canvas) {
        int tabLineMargin = (int) (6 * canvas.getHeight() / 8 / (myInstr
                .getNumOfStrings() + 1));
        float textSize = canvas.getHeight() * 0.1f;
        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setTextSize(textSize);
        bmp = Bitmap.createBitmap(3000, (int) canvas.getHeight(), Bitmap.Config.ARGB_8888);
        
        Canvas c = new Canvas(bmp);
        drawVerticalLineOnTab(c, startingPos, tabLineMargin, paint);
        drawVerticalLineOnTab(c, endOfTab, tabLineMargin, paint);
        drawGrid(c, tabLineMargin);
        drawNotes(c);
        c.setBitmap(bmp);
        
        firstDraw = false;
    }

    public void drawMesure(int pos, Canvas canvas) {
        Paint mesurePaint = new Paint();
        mesurePaint.setTextSize(mesureTextSize);
        canvas.drawText(Integer.toString(mes++), pos + myPace / 2, 8
                * startingPos - padding, mesurePaint);
        mesurePaint.setStrokeWidth(2);
        drawVerticalLineOnTab(
                canvas,
                pos + myPace / 2,
                (int) (6 * canvas.getHeight() / 8 / (myInstr.getNumOfStrings() + 1)),
                mesurePaint);
    }

    /**
     * Draws the notes
     * 
     * @param canvas
     */
    private void drawNotes(Canvas canvas) {
        int pos = firstNotePos;
        Rect noteRect = new Rect();
        double durationSum = 0;

        for (int i = 0; i < myTab.length(); i++) {
            double noteDuration = Duration.valueOf(
                    myTab.getTime(i).getDuration()).getDuration();

            durationSum += noteDuration;
            pos += myPace * noteDuration;

            if (durationSum % 4d == 0d) {
                drawMesure(pos, canvas);
            }

            paint.setTextSize(canvas.getHeight() * 0.09f);

            if (pos - getScrollX() > 0 && pos - getScrollX() < getWidth()) { 
                for (int j = 0; j < myInstr.getNumOfStrings(); j++) {
                    paint.getTextBounds(myTab.getTime(i).getNote(j), 0, myTab
                            .getTime(i).getNote(j).length(), noteRect);
                    float textHeight = (j + 1)
                            * (int) (6 * canvas.getHeight() / 8 / (myInstr
                                    .getNumOfStrings() + 1))
                            + canvas.getHeight() * 0.1f / 3 + padding;

                    // Draws the rectangle under the note
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(pos,
                            textHeight - canvas.getHeight() * 0.1f, pos
                                    + noteRect.right, textHeight, paint);

                    // Draws the note itself
                    paint.setColor(Color.BLACK);
                    canvas.drawText(myTab.getTime(i).getNote(j), pos,
                            textHeight, paint);
                }
            }
        }
        endOfTab = pos + 200;
    }
}