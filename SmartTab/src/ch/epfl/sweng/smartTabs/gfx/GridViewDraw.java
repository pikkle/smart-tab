package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;

/**
 * 
 * @author lourichard ( swag tmtc =D)
 *
 */
public class GridViewDraw extends Drawable{
	private final Paint paint = new Paint();
	private final Height[] stantardTuning = {Height.E, Height.B, Height.G, Height.D, Height.A, Height.E };
	private int mWidth;
	private int mHeight;
	private Instrument myInstrument;
	private Tab myTab;
	
	//TODO add an option to show the tablature and/or the standard partition
	private boolean displayTab = true;
	private boolean displayPartition = true;
	private Resources mRes;
	
	private static final int STANDARD_NOTATION_LINES_NUMBER = 5;
	
	private Rect screenRect; // full screen
	private Rect headerRect; // title, ..
	private Rect bodyRect; // main content
	private Rect leftPartRect; // music header (Clef, tuning)
	private Rect rightPartRect;  // music content
	private Rect topContentRect; // standard notation
	private Rect bottomContentRect; // tablature
	private Rect centerScreenRect; // standard/tablature notation only
	private Rect leftTopPartRect; // Clef for the music sheet
	private Rect leftBottomPartRect; // Tuning for the tablature
	private Rect leftCenterPartRect; // Clef OR Tuning 
	private Rect rightTopPartRect; // Clef for the music sheet
	private Rect rightBottomPartRect; // Tuning for the tablature
	private Rect rightCenterPartRect; // Clef OR Tuning 
	
	private static final float HEADER_RATIO = 0.1f;
	private static final float TOP_CONTENT_RATIO = 0.4f;
	private static final float LEFT_SIDE_RATIO = 0.25f;
		
	private static final float TITLE_WIDTH_MARGIN_DELTA = 0.99f;
	private static final float TITLE_HEIGHT_MARGIN_DELTA = 0.1f;
	private static final float CLEF_TUNING_LEFT_MARGIN = 0.5f;
	private static final float MIDDLE_SMALL_MARGIN = 0.5f;
	private static final float BIG_MARGIN = 0.25f;
	private static final float MARGIN = 0.125f;
	private static final float STRING_SHIFT = 0.5f;
	private static final float STRING_SHIFT_BOTTOM = 0.33f;
	
	private static final float THIN_LINE_WIDTH = 2f;
	private static final float MED_LINE_WIDTH = 5f;
	private static final float HARD_LINE_WIDTH = 15f;
	
	private static final float TUNING_TEXT_SIZE = 36f;
	private static final float TITLE_FONT_SIZE = 48f;
	private static final float CURSOR_WIDTH = 10f;
	
	private Bitmap clefDeSol;
	
	
	public GridViewDraw(int width, int height, Instrument instru, Tab tab, Resources res) {
		super();
		mWidth = width;
		mHeight = height;
		myInstrument = instru;
		myTab = tab;
		mRes = res;
		
		createBoxes();
		initializeBitmaps();
		
	}


	@Override
	public void draw(Canvas canvas) {
		clearScreen(canvas);
		drawNameSong(canvas);
		if (displayTab) {
			drawTablatureGrid(canvas, !displayPartition);
		}
		if (displayPartition) {
			drawStandardGrid(canvas, !displayTab);
		}
	}

	@Override
	public int getOpacity() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void setAlpha(int alpha) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setColorFilter(ColorFilter cf) {
		// TODO Auto-generated method stub
		
	}
	
	private void clearScreen(Canvas canvas) {
		paint.setStyle(Paint.Style.FILL);
		paint.setColor(Color.WHITE);
		canvas.drawPaint(paint);
	}
	
	private void drawNameSong(Canvas canvas) {
		paint.setTextSize(TITLE_FONT_SIZE);
		paint.setColor(Color.GRAY);
		Rect titleRect = Box.marginRect(headerRect, TITLE_WIDTH_MARGIN_DELTA, TITLE_HEIGHT_MARGIN_DELTA);
		canvas.drawText(myTab.getTabName(), titleRect.left, titleRect.top, paint);
	}

	private void drawStandardGrid(Canvas canvas, boolean isCentered) {
		Rect left = isCentered ? leftCenterPartRect : leftTopPartRect;
		Rect right = isCentered ? rightCenterPartRect : rightTopPartRect;
		int margin = (int) (left.width() * MARGIN);
		Rect standardRect;
		if (isCentered) {
			left = Box.marginRect(left, margin, margin, 0, margin);
			right = Box.marginRect(right, 0, margin, 0, margin);
			standardRect = Box.marginRect(Box.bigUnion(left, right), 
					(int) (left.width()*CLEF_TUNING_LEFT_MARGIN), 
					(int) (left.height()*BIG_MARGIN), 0, (int) (left.height()*BIG_MARGIN));
		} else {
			left = Box.marginRect(left, margin, margin, 0, (int) (margin*MIDDLE_SMALL_MARGIN));
			right = Box.marginRect(right, 0, margin, 0, (int) (margin*MIDDLE_SMALL_MARGIN));
			standardRect = Box.marginRect(Box.bigUnion(left, right), 
					(int) (left.width()*CLEF_TUNING_LEFT_MARGIN), 0, 0, 0);
		}
		Rect clefRect = Box.intersection(standardRect, left);
		Rect sheetRect = Box.intersection(standardRect, right);
		
		int newHeight = clefRect.height();
		int newWidth = clefDeSol.getWidth() * newHeight / clefDeSol.getHeight(); // keeps the image ratio
		clefDeSol = Bitmap.createScaledBitmap(clefDeSol, newWidth, newHeight, false);
		
		float lineMargin = (float) (sheetRect.height()/STANDARD_NOTATION_LINES_NUMBER);
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(THIN_LINE_WIDTH);
		for (int i = 0; i < STANDARD_NOTATION_LINES_NUMBER; i++) {
			canvas.drawLine(clefRect.left, clefRect.top + (i+STRING_SHIFT) * lineMargin,
					sheetRect.right, sheetRect.top + (i+STRING_SHIFT) * lineMargin,
					paint);
		}
		paint.setStrokeWidth(HARD_LINE_WIDTH);
		canvas.drawLine(clefRect.left+HARD_LINE_WIDTH/2, clefRect.top + (STRING_SHIFT*lineMargin), 
				clefRect.left+HARD_LINE_WIDTH/2, clefRect.bottom - (STRING_SHIFT*lineMargin), paint);
		canvas.drawBitmap(clefDeSol, clefRect.left + 2*HARD_LINE_WIDTH, clefRect.top, paint);
		
		paint.setColor(Color.RED);
		paint.setStrokeWidth(CURSOR_WIDTH);
		canvas.drawLine(clefRect.right, clefRect.top, 
				clefRect.right, clefRect.bottom, paint);
	}
	
	private void drawTablatureGrid(Canvas canvas, boolean isCentered) {
		Rect left = isCentered ? leftCenterPartRect : leftBottomPartRect;
		Rect right = isCentered ? rightCenterPartRect : rightBottomPartRect;
		int margin = (int) (left.width() * MARGIN);
		Rect tabRect;
		if (isCentered) {
			left = Box.marginRect(left, margin, margin, 0, margin);
			right = Box.marginRect(right, 0, margin, 0, margin);
			tabRect = Box.marginRect(Box.bigUnion(left, right), 
					(int) (left.width() * CLEF_TUNING_LEFT_MARGIN), (int) (left.height()*BIG_MARGIN), 
					0, (int) (left.height()*BIG_MARGIN));
		} else {
			left = Box.marginRect(left, margin, margin/2, 0, margin);
			right = Box.marginRect(right, 0, margin/2, 0, margin);
			tabRect = Box.marginRect(Box.bigUnion(left, right), 
					(int) (left.width()*CLEF_TUNING_LEFT_MARGIN), 0, 0, 0);
		}
		Rect nutRect = Box.intersection(tabRect, left);
		Rect neckRect = Box.intersection(tabRect, right);
		float lineMargin = (float) (neckRect.height()/myInstrument.getNumOfStrings());
		
		paint.setColor(Color.BLACK);
		paint.setStrokeWidth(THIN_LINE_WIDTH);
		paint.setTextSize(TUNING_TEXT_SIZE);
		for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
			canvas.drawLine(nutRect.left, nutRect.top + (i+STRING_SHIFT) * lineMargin,
					neckRect.right, neckRect.top + (i+STRING_SHIFT) * lineMargin,
					paint);
			canvas.drawText(stantardTuning[i].toString(), 
					nutRect.left-TUNING_TEXT_SIZE, 
					nutRect.top + ((i+STRING_SHIFT) * lineMargin) + (TUNING_TEXT_SIZE*STRING_SHIFT_BOTTOM), 
					 paint);
		}
		paint.setStrokeWidth(MED_LINE_WIDTH);
		canvas.drawLine(nutRect.left, nutRect.top + (STRING_SHIFT * lineMargin), 
				nutRect.left, nutRect.bottom - (STRING_SHIFT * lineMargin), paint);
		
		paint.setColor(Color.RED);
		paint.setStrokeWidth(CURSOR_WIDTH);
		canvas.drawLine(nutRect.right, nutRect.top, 
				nutRect.right, nutRect.bottom, paint);
	}
	
	/**
	 * Initialize the screen boxes
	 */
	private void createBoxes() {
		// Divides the screen into 2 main boxes: header and body
		screenRect = new Rect(0, 0, mWidth, mHeight);
		headerRect = new Rect(screenRect.left, screenRect.top, 
				screenRect.right, (int) (screenRect.height() * HEADER_RATIO));
		bodyRect = new Rect(screenRect.left, headerRect.bottom, screenRect.right, screenRect.bottom);
		
		// Splits in two parts vertically
		leftPartRect = new Rect(bodyRect.left, bodyRect.top, 
				(int) (bodyRect.right * LEFT_SIDE_RATIO), bodyRect.bottom);
		rightPartRect = new Rect(leftPartRect.right, bodyRect.top, bodyRect.right, bodyRect.bottom);
		
		// Splits the body in two parts horizontally or centers it
		topContentRect = new Rect(bodyRect.left, bodyRect.top, 
				bodyRect.right, (int) (bodyRect.top+(bodyRect.bottom*TOP_CONTENT_RATIO)));
		bottomContentRect = new Rect(bodyRect.left, topContentRect.bottom, bodyRect.right, bodyRect.bottom);
		int verticalMargin = (bodyRect.height()-bottomContentRect.height())/2;
		centerScreenRect = new Rect(bodyRect.left+verticalMargin, bodyRect.top, 
				bodyRect.right-verticalMargin, bodyRect.bottom);
		
		// Intersects the splits to get the left parts
		leftTopPartRect = new Rect(leftPartRect.left, topContentRect.top, leftPartRect.right, topContentRect.bottom);
		leftBottomPartRect = new Rect(leftPartRect.left, bottomContentRect.top, 
				leftPartRect.right, bottomContentRect.bottom);
		leftCenterPartRect = new Rect(leftPartRect.left, centerScreenRect.top, 
				leftPartRect.right, centerScreenRect.bottom);
		
		// Intersects the splits to get the right parts
		rightTopPartRect = new Rect(rightPartRect.left, topContentRect.top, rightPartRect.right, topContentRect.bottom);
		rightBottomPartRect = new Rect(rightPartRect.left, bottomContentRect.top, 
				rightPartRect.right, bottomContentRect.bottom);
		rightCenterPartRect = new Rect(rightPartRect.left, centerScreenRect.top, 
				rightPartRect.right, centerScreenRect.bottom);
		
	}
	
	
	/**
	 * Initialize different Bitmaps for the standard notation
	 */
	private void initializeBitmaps() {
		clefDeSol = BitmapFactory.decodeResource(mRes, R.raw.cledesol);
		
	}
	
}
