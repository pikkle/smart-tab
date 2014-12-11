package ch.epfl.sweng.smartTabs.gfx;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.Rect;
import android.view.View;
import android.view.WindowManager;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;

/**
 * @author pikkle
 * 
 *
 */
public class TablatureView extends View{
    private final Paint paint = new Paint();
    private final Instrument instrument;
    private final Tab tab;
    private final List<Integer> mesure = new ArrayList<Integer>();
    
    private final int HEIGHTSCALE = 10;
    
    private int pace;
    private int endOfTab;
    private float padding;
    private int startingPos; 
    private int firstNotePos;
    private boolean firstDraw;
    private int tabLineMargin;

    
    private int width;
    private int height;
    private final Rect noteRect;
    private String note;
    private int pos;
    private double noteDuration;
    private float textHeight ;
    private float textSize;
    private Time time;
   
    
    
    /**
     * @param context
     * @param attrs
     */
    public TablatureView(Context context, Tab tab, Instrument instrument, int pace) {
        super(context);

        // Constructor attribut
        this.tab = tab;
        
        this.instrument = instrument;
        this.pace = pace;
       
        // Prior settings before any drawing
        this.setBackgroundColor(Color.WHITE);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        int width = size.x;
        int height = size.y;
        padding = height / 8;
        startingPos = width / 8;
        firstNotePos = startingPos + 2*pace;
        tabLineMargin = 0;
        firstDraw = true;
        
        noteRect = new Rect();
        tab.initTimeMap(firstNotePos);

        Point end = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(end);
        endOfTab = context.getResources().getDisplayMetrics().widthPixels;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        canvas.translate(0, padding);
        
        if (firstDraw) {
            
            width =  canvas.getWidth();
            height = canvas.getHeight();
            
            tabLineMargin = height/HEIGHTSCALE;
            textSize = 3*height/(4*HEIGHTSCALE);
            
            paint.setColor(Color.BLACK);
            paint.setStrokeWidth(1);
            paint.setTextSize(textSize);
        }
        
        drawVerticalLineOnTab(canvas, startingPos);
        drawVerticalLineOnTab(canvas, endOfTab);
        
        drawGrid(canvas);
        drawNotes(canvas); 
        drawMesure(canvas);
        firstDraw = false;
    }


    /**
     * Draws the notes
     * @param canvas
     */
    private void drawNotes(Canvas canvas) {
        pos = firstNotePos;
        
        if (firstDraw) {
            paint.setColor(Color.BLACK);
        }
        double temp = 0d;
        for (int i = 0 ; i < tab.length(); i++) {
            
            if (firstDraw) {
                temp += noteDuration;
                if (temp % 4 == 0d) {
                    mesure.add((int) (pos + pace*noteDuration / 2));
                }
            }
            
            noteDuration = Duration.valueOf(tab.getTime(i).getDuration()).getDuration();
            
            

            if (pos-getScrollX() > 0 && pos-getScrollX() < width) {
                
                time = tab.getTime(i);
                
                for (int j = 0; j < instrument.getNumOfStrings(); j++) {
                    note = time.getNote(j);
                    paint.getTextBounds(note, 0, note.length(), noteRect);
                    textHeight = (j+1) * tabLineMargin + height*0.1f/3;
                    canvas.drawText(note, pos, textHeight, paint);
                }
            }
            pos += pace*noteDuration;
        }
        endOfTab = pos + pace;
    }

    /**
     * @param canvas
     * @param y
     */
    private void drawGrid(Canvas canvas) {
        for (int i = 1; i <= instrument.getNumOfStrings(); i++) {  
            canvas.drawLine(startingPos, i * tabLineMargin , endOfTab, i * tabLineMargin , paint);
        }   
    }
    private void drawMesure(Canvas canvas) {
        final Paint mesurePaint = new Paint();
        mesurePaint.setTextSize(24);
        for (int i = 0 ; i < mesure.size(); i++) {  
            drawVerticalLineOnTab(canvas, mesure.get(i));
            canvas.drawText(i+"", mesure.get(i), 0, mesurePaint);
        }   
    }

    /**
     * Draws a thicker vertical line, starting from the top one to the last one.
     * @param canvas
     * @param x
     * @param y
     */
    private void drawVerticalLineOnTab(Canvas canvas, int x) {
        canvas.drawLine(x, tabLineMargin , x, (float) instrument.getNumOfStrings()*tabLineMargin , paint);
    }

    public boolean isTerminated() {
        return getScrollX() > endOfTab;
    }

    public int getEndOfTab() {
        return endOfTab;
    }
}