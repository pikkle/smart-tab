package ch.epfl.sweng.smartTabs.gfx;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;
import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;

/**
 * @author pikkle
 * 
 */
public class MusicSheetView extends View {
    private final Paint paint;
    private final Tab mTab;
    private int mPace;
    private int mEndOfTab;
    private final static int PARTITIONLINES = 5;
    private final static int IMAGERATIO = 35;
    private final static int STARTPOSITIONRATIO = 8;
    private final static int MARGINRATIO = 10;
    private final static int TRANSLATIONVALUE = 3;
    private final static int KEYPOSLEFT = 300;
    private final static int KEYPOSTOP = 60;
    private final static int TIMESINMEASURE = 4;

    private final static double CENTERRATIO = 7/8;
    private final static double OCTAVESHIFT = 7;
    private final static double NOTEHEIGHT = 1.8;
    
    

    private Bitmap mNoire = BitmapFactory.decodeResource(getResources(), R.raw.noire);
    private Bitmap mDoubleCroche = BitmapFactory.decodeResource(getResources(), R.raw.double_croche);
    private Bitmap mCroche = BitmapFactory.decodeResource(getResources(), R.raw.croche);
    private Bitmap mBlanche = BitmapFactory.decodeResource(getResources(), R.raw.blanche);
    private Bitmap mRonde = BitmapFactory.decodeResource(getResources(), R.raw.ronde);
    private Bitmap mCle = BitmapFactory.decodeResource(getResources(), R.raw.cledesol);
    private Bitmap mSharp = BitmapFactory.decodeResource(getResources(), R.raw.diese);
    
    private int mStartingPos;
    private int mLineMargin;
    private boolean mFirstDraw = true;
    private double mNoteDuration;
    private Bitmap mCurrNoteImage;
    private int mPos;

    private List<Integer> mesure = new ArrayList<Integer>();
    
    /**
     * @param context
     * @param attrs
     */
    public MusicSheetView(Context context, Tab tab, int pace) {
        super(context);
        this.paint = new Paint();
        this.setBackgroundColor(Color.WHITE);
        this.mTab = tab;
        this.mPace = pace;
        mEndOfTab = context.getResources().getDisplayMetrics().widthPixels;

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        if (mFirstDraw) {
            int width = canvas.getWidth();
            int noteWidth = width / IMAGERATIO; 
            mStartingPos = canvas.getWidth() / STARTPOSITIONRATIO;
            mLineMargin = (int) (canvas.getHeight() / MARGINRATIO);
            initializeBitmaps(noteWidth);
            paint.setColor(Color.BLACK);
            mPos = mStartingPos + 2 * mPace;
        }
        canvas.translate(0, mLineMargin*TRANSLATIONVALUE);
        
        drawCle(canvas);

        drawGrid(canvas, mLineMargin);
        drawNotes(canvas, mPos);
        drawMesure(canvas);
        drawVerticalLineOnTab(canvas, mStartingPos, mLineMargin);
        drawVerticalLineOnTab(canvas, mEndOfTab, mLineMargin);
        mFirstDraw = false;

    }
    
    private void drawCle(Canvas canvas) {
        canvas.drawBitmap(mCle, KEYPOSLEFT, KEYPOSTOP-(2*mLineMargin), paint);
    }

    public void drawNotes(Canvas c, int pos) {
        double temp = 0d;
        for (int i = 0; i < mTab.length(); i++) {
            
            if (mFirstDraw) {
                temp += mNoteDuration;
                if (temp % TIMESINMEASURE == 0d) {
                    mesure.add((int) (pos + mPace*mNoteDuration/2));
                }
            }
            mNoteDuration = Duration.valueOf(mTab.getTime(i).getDuration()).getDuration();

            int noteHeightPos = (int)(mLineMargin * NOTEHEIGHT);
			int imageWidth = getNoteWithDuration("Noire").getWidth();
            if (pos - getScrollX() > -imageWidth && pos - getScrollX() < getWidth()+imageWidth) {
                
                mCurrNoteImage = getNoteWithDuration(mTab.getTime(i).getDuration());
                
                Time time = mTab.getTime(i);
                for (int j = 0; j <= PARTITIONLINES; j++) {
                    if (time.getPartitionNote(j) != null) {
                        
                        Note currNote = time.getPartitionNote(j);
                        Note sharpNote = sharpNote(currNote);
                        Boolean isSharp = sharpNote != currNote;
                        currNote = sharpNote;
                        int posHeight = noteHeightPos
                                - getNoteCenter(mCurrNoteImage) - mLineMargin
                                / 2 * getNoteHeightPosition(currNote);
                        c.drawBitmap(mCurrNoteImage, pos, posHeight,
                                paint);
                        if (isSharp) {
                        	c.drawBitmap(mSharp, pos+mCurrNoteImage.getWidth(), posHeight 
                        	        + mCurrNoteImage.getHeight() - mSharp.getHeight() +  (mLineMargin / 2), paint);
                        }
                    }
                }
            }
            pos += mPace * mNoteDuration;
        }
        mEndOfTab = pos + mPace;
    }

    private int noteHeight(Bitmap note, int noteWidth) {
        return note.getHeight() * noteWidth / note.getWidth();
    }

    private void drawGrid(Canvas canvas, float y) {
        for (int i = 1; i <= PARTITIONLINES; i++) {
            canvas.drawLine(mStartingPos, i * y, mEndOfTab, i * y, paint);
        }
    }
    
    private void drawMesure(Canvas canvas) {
        for (int i = 0; i < mesure.size(); i++) {  
            drawVerticalLineOnTab(canvas, mesure.get(i), mLineMargin);
        }   
    }

    private int getNoteCenter(Bitmap note) {
        int center = (int) CENTERRATIO * note.getHeight(); 
        //if (note == ronde) {
         //   center = 5 * note.getHeight() / 8;
        //}
        return center;
    }

    private Bitmap getNoteWithDuration(String duration) {
        Bitmap note = mNoire;
        if (duration.equals("DoubleCroche")) {
            note = mDoubleCroche;
        } else if (duration.equals("Croche")) {
            note = mCroche;
        } else if (duration.equals("Blanche")) {
            note = mBlanche;
        } else if (duration.equals("Ronde")) {
            note = mRonde;
        }
        return note;
    }

    public Note sharpNote(Note note) {
        switch (note.getHeight()) {
        case CD:
        	return new Note(Height.C, note.getOctave());
        	
        case DD:
        	return new Note(Height.D, note.getOctave());
        	
        case FD:
        	return new Note(Height.F, note.getOctave());
        	
        case GD:
        	return new Note(Height.G, note.getOctave());
        	
        case AD:
        	return new Note(Height.A, note.getOctave());
        	
        default:
        	return note;
        	
        }
        
    }

    public int getNoteHeightPosition(Note note) {
        int height = -1;
        switch (note.getHeight()) {
        case E:
            height = 0;
            break;
        case F:
            height = 1;
            break;
        case G:
            height = 2;
            break;
        case A:
            height = 3;
            break;
        case B:
            height = 4;
            break;
        case C:
            height = 5;
            break;
        case D:
            height = 6;
            break;
         default:

        }
        if ((note.getOctave() > 1) && (height < 2)) {
            height += OCTAVESHIFT;
        }
        return height;
    }

    private void drawVerticalLineOnTab(Canvas canvas, int x, int y) {
        canvas.drawLine(x, y, x, PARTITIONLINES * y, paint);
    }
    
    private void initializeBitmaps(int noteWidth) {
    	mDoubleCroche = Bitmap.createScaledBitmap(mDoubleCroche, noteWidth,
                noteHeight(mDoubleCroche, noteWidth), false);
        mCroche = Bitmap.createScaledBitmap(mCroche, noteWidth,
                noteHeight(mCroche, noteWidth), false);
        mNoire = Bitmap.createScaledBitmap(mNoire, noteWidth,
                noteHeight(mNoire, noteWidth), false);
        mBlanche = Bitmap.createScaledBitmap(mBlanche, noteWidth,
                noteHeight(mBlanche, noteWidth), false);
        mRonde = Bitmap.createScaledBitmap(mRonde, noteWidth,
                noteHeight(mRonde, noteWidth), false);
        mCle = Bitmap.createScaledBitmap(mCle, noteWidth,
                noteHeight(mCle, noteWidth), false);
        mSharp = Bitmap.createScaledBitmap(mSharp, mSharp.getWidth() * 2 
                * mLineMargin / mSharp.getHeight(), 2 * mLineMargin, false);
    }
}
