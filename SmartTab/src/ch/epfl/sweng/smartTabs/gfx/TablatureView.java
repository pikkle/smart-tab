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
    private final Paint mPaint = new Paint();
    private final Instrument mInstrument;
    private final Tab mTab;
    private final List<Integer> mMesure = new ArrayList<Integer>();
    
    private final static int HEIGHTSCALE = 10;
    private final static int PADDINGDIVISOR = 8;
    private final static int TIMEINMEASURE = 4;
    private final static double RATIO = 0.75;
    private final static float TEXTRATIO = 30f;
    private final static int TEXTSIZE = 24;
    
    
    
    private int mPace;
    private int mEndOfTab;
    private float mPadding;
    private int mStartingPos; 
    private int mFirstNotePos;
    private boolean mFirstDraw;
    private int mTabLineMargin;

    
    private int mWidth;
    private int mHeight;
    private final Rect mNoteRect;
    private String mNote;
    private int mPos;
    private double mNoteDuration;
    private float mTextHeight;
    private float mTextSize;
    private Time mTime;
   
    
    
    /**
     * @param context
     * @param attrs
     */
    public TablatureView(Context context, Tab tab, Instrument instrument, int pace) {
        super(context);

        // Constructor attribut
        mTab = tab;
        
        mInstrument = instrument;
        mPace = pace;
       
        // Prior settings before any drawing
        setBackgroundColor(Color.WHITE);
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        Point size = new Point();
        wm.getDefaultDisplay().getSize(size);
        mWidth = size.x;
        mHeight = size.y;
        mPadding = mHeight / PADDINGDIVISOR;
        mStartingPos = mWidth / PADDINGDIVISOR;
        mFirstNotePos = mStartingPos + 2*pace;
        mTabLineMargin = 0;
        mFirstDraw = true;
        
        mNoteRect = new Rect();
        tab.initTimeMap(mFirstNotePos);

        Point end = new Point();
        ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE))
                .getDefaultDisplay().getSize(end);
        mEndOfTab = end.y;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        canvas.translate(0, mPadding);
        
        if (mFirstDraw) {
            
            mWidth =  canvas.getWidth();
            mHeight = canvas.getHeight();
            
            mTabLineMargin = mHeight/HEIGHTSCALE;
            mTextSize = (float) RATIO * mHeight / HEIGHTSCALE;
            
            mPaint.setColor(Color.BLACK);
            mPaint.setStrokeWidth(1);
            mPaint.setTextSize(mTextSize);
        }
        
        drawVerticalLineOnTab(canvas, mStartingPos);
        drawVerticalLineOnTab(canvas, mEndOfTab);
        
        drawGrid(canvas);
        drawNotes(canvas); 
        drawMesure(canvas);
        mFirstDraw = false;
    }


    /**
     * Draws the notes
     * @param canvas
     */
    private void drawNotes(Canvas canvas) {
        mPos = mFirstNotePos;
        
        if (mFirstDraw) {
            mPaint.setColor(Color.BLACK);
        }
        double temp = 0d;
        for (int i = 0; i < mTab.length(); i++) {
            
            if (mFirstDraw) {
                temp += mNoteDuration;
                if (temp % TIMEINMEASURE == 0d) {
                    mMesure.add((int) (mPos + mPace*mNoteDuration / 2));
                }
            }
            
            mNoteDuration = Duration.valueOf(mTab.getTime(i).getDuration()).getDuration();
            
            

            if (mPos-getScrollX() > 0 && mPos-getScrollX() < mWidth) {
                
                mTime = mTab.getTime(i);
                
                for (int j = 0; j < mInstrument.getNumOfStrings(); j++) {
                    mNote = mTime.getNote(j);
                    mPaint.getTextBounds(mNote, 0, mNote.length(), mNoteRect);
                    mTextHeight =  (j+1) * mTabLineMargin + mHeight/TEXTRATIO;
                    canvas.drawText(mNote, mPos, mTextHeight, mPaint);
                }
            }
            mPos += mPace*mNoteDuration;
        }
        mEndOfTab = mPos + mPace;
    }

    /**
     * @param canvas
     * @param y
     */
    private void drawGrid(Canvas canvas) {
        for (int i = 1; i <= mInstrument.getNumOfStrings(); i++) {  
            canvas.drawLine(mStartingPos, i * mTabLineMargin , mEndOfTab, i * mTabLineMargin , mPaint);
        }   
    }
    private void drawMesure(Canvas canvas) {
        final Paint mesurePaint = new Paint();
        mesurePaint.setTextSize(TEXTSIZE);
        for (int i = 0; i < mMesure.size(); i++) {  
            drawVerticalLineOnTab(canvas, mMesure.get(i));
            canvas.drawText(i+"", mMesure.get(i), 0, mesurePaint);
        }   
    }

    /**
     * Draws a thicker vertical line, starting from the top one to the last one.
     * @param canvas
     * @param x
     * @param y
     */
    private void drawVerticalLineOnTab(Canvas canvas, int x) {
        canvas.drawLine(x, mTabLineMargin , x, (float) mInstrument.getNumOfStrings()*mTabLineMargin , mPaint);
    }

    public boolean isTerminated() {
        return getScrollX() > mEndOfTab;
    }

    public int getEndOfTab() {
        return mEndOfTab;
    }
}