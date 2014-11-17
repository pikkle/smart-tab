/**
 * 
 */
package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.Tab;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.view.WindowManager;

/**
 * @author pikkle
 *
 */
public class MusicSheetView extends View{
	private Paint paint;
	private Resources res;
	private Tab mTab;
	private int pace = 200;
	private int endOfTab;
	//Bitmap factories to create Bitmap
	private Bitmap noire = BitmapFactory.decodeResource(getResources(), R.raw.noire);
	//to do : update with right resources
	private Bitmap doubleCroche = BitmapFactory.decodeResource(getResources(), R.raw.noire);
	private Bitmap croche = BitmapFactory.decodeResource(getResources(), R.raw.noire);
	private Bitmap blanche = BitmapFactory.decodeResource(getResources(), R.raw.noire);
	private Bitmap ronde = BitmapFactory.decodeResource(getResources(), R.raw.noire);

	
	/**
	 * @param context
	 * @param attrs
	 */
	public MusicSheetView(Context context, Tab tab) {
		super(context);
		paint = new Paint();
		this.mTab = tab;
		endOfTab = ((WindowManager) context.getSystemService(Context.WINDOW_SERVICE)).getDefaultDisplay().getWidth();
	}

	@Override
    protected void onDraw(Canvas canvas) {
		super.onDraw(canvas);
		paint.setColor(Color.CYAN);
		paint.setAlpha(100);
		canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
		paint.setColor(Color.BLACK);
		//canvas.drawLine(0, 0, 100000, 6000, paint);
		int noteWidth = (int) (canvas.getWidth()/35);
		//create the bitmaps
		doubleCroche = Bitmap.createScaledBitmap(doubleCroche, noteWidth, noteHeight(doubleCroche, noteWidth), false);
		croche = Bitmap.createScaledBitmap(croche, noteWidth, noteHeight(croche, noteWidth), false);
		noire = Bitmap.createScaledBitmap(noire, noteWidth, noteHeight(noire, noteWidth), false);
		blanche = Bitmap.createScaledBitmap(blanche, noteWidth, noteHeight(blanche, noteWidth), false);
		ronde = Bitmap.createScaledBitmap(ronde, noteWidth, noteHeight(ronde, noteWidth), false);
		
		//to do : create the grid
		int startingPos = 100;
		int lineMargin = (int) (canvas.getHeight()/(6));
		
		for (int i = 1; i <= 5; i++) { //Draws the grid
			paint.setStrokeWidth(1);
			canvas.drawLine(
					startingPos, 
					i * lineMargin, 
					endOfTab, //TODO: calculate size of tab
					i * lineMargin, 
					paint);
			
		}
		//adding notes to the grid
		int pos = startingPos + 100;
		Rect noteRect = new Rect();
		for (int i = 0; i < mTab.length(); i++) {
			double noteDuration = Duration.valueOf(mTab.getTime(i).getDuration()).getDuration();
			pos += pace*noteDuration;
			int noteHeightPos = lineMargin + lineMargin * 4;
			if (pos-getScrollX() > 0 && pos-getScrollX() < getWidth()) { //Draws only what is necessary
				for (int j = 0; j < 6; j++) {
					if(mTab.getTime(i).getPartitionNote(j) != null) {
						Bitmap currNoteImage = getNoteWithDuration(mTab.getTime(i).getDuration());
						int noteCenter = getNoteCenter(currNoteImage);
						Note currNote = mTab.getTime(i).getPartitionNote(j);
						System.out.println("note at time " + i + " for chord " + j + " : " + currNote);
						Boolean sharpNote = currNote != isSharp(currNote);
						System.out.println("note is sharp : " + sharpNote);
						currNote = isSharp(currNote);
						System.out.println("note after isSharp : " + currNote.getHeight());
						System.out.println("note height : " + noteHeight(currNoteImage, currNoteImage.getWidth()));
						System.out.println("note center : " + noteCenter);
						System.out.println("note height pos : " + getNoteHeightPosition(currNote) + ", note width pos : " + pos);
						canvas.drawBitmap(currNoteImage, pos, noteHeightPos - getNoteCenter(currNoteImage)  - lineMargin/2 * getNoteHeightPosition(currNote), paint);
					}
				}
			}
		}
		endOfTab = pos + 100;
	}
	
	private int noteHeight(Bitmap note, int noteWidth) {
		return note.getHeight() * noteWidth / note.getWidth();
	}
	
	private int getNoteCenter (Bitmap note) {
		int center = 7 * note.getHeight() / 8;
		if (note == ronde) {
			center = note.getHeight() / 2;
		}
		return center;
	}
	
	private Bitmap getNoteWithDuration(String duration) {
		Bitmap note = noire;
		if(duration.equals("doubleCroche")) {
			note = doubleCroche;
		} else if(duration.equals("croche")) {
			note = croche;
		} else if(duration.equals("blanche")) {
			note = blanche;
		} else if(duration.equals("ronde")) {
			note = ronde;
		}
		return note;
	}
	
	public Note isSharp(Note note) {
		Note sharpNote = note;
		if(note.getHeight() == Height.CD) {
			sharpNote = new Note(Height.C, note.getOctave());
		} else if(note.getHeight() == Height.DD) {
			sharpNote = new Note(Height.D, note.getOctave());
		} else if(note.getHeight() == Height.FD) {
			sharpNote = new Note(Height.F, note.getOctave());
		} else if(note.getHeight() == Height.GD) {
			sharpNote = new Note(Height.G, note.getOctave());
		} else if(note.getHeight() == Height.AD) {
			sharpNote = new Note(Height.A, note.getOctave());
		}
		return sharpNote;
	}
	
	public int getNoteHeightPosition(Note note) {
		int height = -1;
		switch(note.getHeight()) {
		case E :
			height = 0;
			break;
		case F :
			height = 1;
			break;
		case G :
			height = 2;
			break;
		case A :
			height = 3;
			break;
		case B :
			height = 4;
			break;
		case C :
			height = 5;
			break;
		case D :
			height = 6;
			break;
		
		}
		if((note.getOctave() > 1) && (height < 2)) {
			height += 7;
		}
		return height;
	}
	
}
