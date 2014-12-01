package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;

/**
 * @author pikkle
 *
 */
public class TablatureView extends View{
    private final Paint paint = new Paint();
    private Instrument myInstr;
    private Tab myTab;
    private int myPace;
    private int endOfTab;
    private float padding;
    private int startingPos; //Display initially starts at 100px
    private int firstNotePos; //First note's position


    /**
     * @param context
     * @param attrs
     */
    public TablatureView(Context context, Tab tab, Instrument instr, int pace) {
        super(context);

        this.myTab = tab;
        this.myInstr = instr;
        this.myPace = pace;
        this.setBackgroundColor(Color.WHITE);

        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        padding = display.getWidth()/8;
        startingPos = display.getWidth()/8;
        firstNotePos = startingPos + 2*pace;


        tab.initTimeMap(firstNotePos);

        // TODO : Can be a field in the JSON
        endOfTab = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        int tabLineMargin = (int) (6*canvas.getHeight()/8/(myInstr.getNumOfStrings()+2));
        float textSize = canvas.getHeight()*0.1f;

        paint.setColor(Color.BLACK);
        paint.setStrokeWidth(1);
        paint.setTextSize(textSize);

        drawVerticalLineOnTab(canvas, startingPos, tabLineMargin);
        drawVerticalLineOnTab(canvas, endOfTab, tabLineMargin);
        drawGrid(canvas, tabLineMargin);
        drawNotes(canvas);  
    }


    /**
     * Draws the notes
     * @param canvas
     */
    private void drawNotes(Canvas canvas) {
        int pos = firstNotePos;
        Rect noteRect = new Rect();

        for (int i = 0; i < myTab.length(); i++) {
            double noteDuration = Duration.valueOf(myTab.getTime(i).getDuration()).getDuration();


            pos += myPace*noteDuration;

            paint.setTextSize(canvas.getHeight()*0.09f);

            if (pos-getScrollX() > 0 && pos-getScrollX() < getWidth()) { //Draws only what is necessary
                for (int j = 0; j < myInstr.getNumOfStrings(); j++) {
                    paint.getTextBounds(myTab.getTime(i).getNote(j), 0, myTab.getTime(i).getNote(j).length(), noteRect);
                    float textHeight = (j+1) * (int) (6*canvas.getHeight()/8/(myInstr.getNumOfStrings()+1)) + canvas.getHeight()*0.1f/3 +padding;

                    // Draws the rectangle under the note
                    paint.setColor(Color.WHITE);
                    canvas.drawRect(pos, textHeight-canvas.getHeight()*0.1f , pos+noteRect.right, textHeight, paint);

                    // Draws the note itself
                    paint.setColor(Color.BLACK);
                    canvas.drawText(myTab.getTime(i).getNote(j), pos, textHeight, paint);
                }
            }
        }
        endOfTab = pos + 200;
    }

    /**
     * @param canvas
     * @param y
     */
    private void drawGrid(Canvas canvas, float y) {
        for (int i = 1; i <= myInstr.getNumOfStrings(); i++) {  
            canvas.drawLine(startingPos, i * y  + padding, endOfTab, i * y + padding, paint);
        }   
    }

    /**
     * Draws a thicker vertical line, starting from the top one to the last one.
     * @param canvas
     * @param x
     * @param y
     */
    private void drawVerticalLineOnTab(Canvas canvas, int x, int y) {
        paint.setStrokeWidth(2);
        canvas.drawLine(x, y  + padding, x, (float) myInstr.getNumOfStrings()*y  + padding, paint);
        paint.setStrokeWidth(1);
    }

    public boolean isTerminated() {
        return getScrollX() > endOfTab-135;
    }
    public int getEndOfTab() {
        return endOfTab;
    }
}