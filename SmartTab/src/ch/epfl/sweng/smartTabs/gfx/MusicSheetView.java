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
import android.view.WindowManager;
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
    private int pace;
    private int endOfTab;
    private final int PARTITIONLINES = 5;

    private Bitmap noire = BitmapFactory.decodeResource(getResources(), R.raw.noire);
    private Bitmap doubleCroche = BitmapFactory.decodeResource(getResources(), R.raw.double_croche);
    private Bitmap croche = BitmapFactory.decodeResource(getResources(), R.raw.croche);
    private Bitmap blanche = BitmapFactory.decodeResource(getResources(), R.raw.blanche);
    private Bitmap ronde = BitmapFactory.decodeResource(getResources(), R.raw.ronde);
    private Bitmap cle = BitmapFactory.decodeResource(getResources(), R.raw.cledesol);
    
    private int startingPos;
    private int lineMargin;
    private boolean firstDraw = true;
    double noteDuration;
    Bitmap currNoteImage;
    int pos;

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
        this.pace = pace;
        endOfTab = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        
        
        if (firstDraw) {
            int width = canvas.getWidth();
            int noteWidth = width / 35;
            initializeBitmaps(noteWidth);
            startingPos = canvas.getWidth() / 8;
            lineMargin = (int) (canvas.getHeight() / (10));
            paint.setColor(Color.BLACK);
            
            pos = startingPos + 2 * pace;
        }
        canvas.translate(0, lineMargin*3);
        
        drawCle(canvas);

        drawGrid(canvas, lineMargin);
        drawNotes(canvas, pos);
        drawMesure(canvas);
        drawVerticalLineOnTab(canvas, startingPos, lineMargin);
        drawVerticalLineOnTab(canvas, endOfTab, lineMargin);
        firstDraw = false;

    }
    
    private void drawCle(Canvas canvas) {
        canvas.drawBitmap(cle, 300, 60-(2*lineMargin),paint);
    }

    public void drawNotes(Canvas c, int pos) {
        double temp = 0d;
        for (int i = 0; i < mTab.length(); i++) {
            
            if (firstDraw) {
                temp += noteDuration;
                if (temp % 4 == 0d) {
                    mesure.add((int) (pos + pace*noteDuration / 2));
                }
            }
            noteDuration = Duration.valueOf(mTab.getTime(i).getDuration()).getDuration();

            int noteHeightPos = lineMargin * 5;
            if (pos - getScrollX() > 0 && pos - getScrollX() < getWidth()) { 
                
                currNoteImage = getNoteWithDuration(mTab.getTime(i).getDuration());
                
                Time time = mTab.getTime(i);
                for (int j = 0; j <= PARTITIONLINES; j++) {
                    if (time.getPartitionNote(j) != null) {
                        
                        Note currNote = time.getPartitionNote(j);
                        currNote = isSharp(currNote);
                        c.drawBitmap(currNoteImage, pos, noteHeightPos
                                - getNoteCenter(currNoteImage) - lineMargin
                                / 2 * getNoteHeightPosition(currNote),
                                paint);
                    }
                }
            }
            pos += pace * noteDuration;
        }
        endOfTab = pos + pace;
    }

    private int noteHeight(Bitmap note, int noteWidth) {
        return note.getHeight() * noteWidth / note.getWidth();
    }

    private void drawGrid(Canvas canvas, float y) {
        for (int i = 1; i <= PARTITIONLINES; i++) {
            canvas.drawLine(startingPos, i * y, endOfTab, i * y, paint);
        }
    }
    
    private void drawMesure(Canvas canvas) {
        for (int i = 0 ; i < mesure.size(); i++) {  
            drawVerticalLineOnTab(canvas, mesure.get(i),lineMargin);
        }   
    }

    private int getNoteCenter(Bitmap note) {
        int center = 7 * note.getHeight() / 8;
        //if (note == ronde) {
         //   center = 5 * note.getHeight() / 8;
        //}
        return center;
    }

    private Bitmap getNoteWithDuration(String duration) {
        Bitmap note = noire;
        if (duration.equals("DoubleCroche")) {
            note = doubleCroche;
        } else if (duration.equals("Croche")) {
            note = croche;
        } else if (duration.equals("Blanche")) {
            note = blanche;
        } else if (duration.equals("Ronde")) {
            note = ronde;
        }
        return note;
    }

    public Note isSharp(Note note) {
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
            height += 7;
        }
        return height;
    }

    private void drawVerticalLineOnTab(Canvas canvas, int x, int y) {
        canvas.drawLine(x, y, x, PARTITIONLINES * y, paint);
    }
    
    private void initializeBitmaps(int noteWidth) {
    	doubleCroche = Bitmap.createScaledBitmap(doubleCroche, noteWidth,
                noteHeight(doubleCroche, noteWidth), false);
        croche = Bitmap.createScaledBitmap(croche, noteWidth,
                noteHeight(croche, noteWidth), false);
        noire = Bitmap.createScaledBitmap(noire, noteWidth,
                noteHeight(noire, noteWidth), false);
        blanche = Bitmap.createScaledBitmap(blanche, noteWidth,
                noteHeight(blanche, noteWidth), false);
        ronde = Bitmap.createScaledBitmap(ronde, noteWidth,
                noteHeight(ronde, noteWidth), false);
        cle = Bitmap.createScaledBitmap(cle, noteWidth,
                noteHeight(cle, noteWidth), false);
    }
}
