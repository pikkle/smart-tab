package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Tab;
import android.R;
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
	private final float ratio = 0.34375f;
	private int mWidth;
	private int mHeight;
	private Instrument myInstrument;
	private Tab myTab;
	
	//TODO add an option to show the tablature and/or the standard partition
	private boolean displayTab = true;
	private boolean displayPartition = true;
	private Resources mRes;
	
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
	private static final float LEFT_SIDE_RATIO = 0.25f;
	private static final float MARGIN = 0.125f;
	
	private Bitmap mGKeyImage;
	
	
	public GridViewDraw(int width, int height, Instrument instru, Tab tab, Resources res) {
		super();
		mWidth = width;
		mHeight = height;
		myInstrument = instru; // Swaggy Baby tu PESES dans le GAME
		myTab = tab;
		mRes = res;
		
		createBoxes();
		
		mGKeyImage = BitmapFactory.decodeResource(mRes, ch.epfl.sweng.smartTabs.R.raw.cledesol);
		mGKeyImage = mGKeyImage.copy(Bitmap.Config.ARGB_8888, true);
		int newHeight = (int) (mHeight*ratio/1.2f) ;
		int newWidth = mGKeyImage.getWidth() *  newHeight / mGKeyImage.getHeight();
		mGKeyImage = Bitmap.createScaledBitmap(mGKeyImage, newWidth, newHeight, false);
	}

	
	@Override
	public void draw(Canvas canvas) {
		clearScreen(canvas);
		drawNameSong(canvas);
		
		boolean tabOnly = !displayPartition && displayTab;
		boolean standardOnly = displayPartition && !displayTab;
		drawStrings(canvas, tabOnly);
		drawStandardGrid(canvas, standardOnly);
		
		drawCursor(canvas);
		
		
	}

	private void drawStandardGrid(Canvas canvas, boolean centered) {
		paint.setColor(Color.BLACK);
		paint.setTextSize(36f);
		paint.setStrokeWidth(2f);
		
		if (centered) {
			for (int i = 0; i < 5; i++) {
				canvas.drawLine(mWidth/10, ratio*mHeight + i*mHeight/20, mWidth, ratio*mHeight + i*mHeight/20, paint);
				canvas.drawText(stantardTuning[i]+"", mWidth/16, ratio*mHeight + i*mHeight/16, paint);
				}
			canvas.drawLine(mWidth/10, ratio*mHeight, mWidth/10, ratio*mHeight + 5*mHeight/16, paint);
			
		} else {
			float margin = mHeight/8;
			float distributedHeight = mHeight/8 + margin;
			for (int i = 0; i < 5; i++) {
				canvas.drawLine(mWidth/10, distributedHeight + i*mHeight/20, mWidth, distributedHeight + i*mHeight/20, paint);
			}
			paint.setStrokeWidth(15f);
			canvas.drawLine(mWidth/10 + 2f, distributedHeight, mWidth/10 + 2f, distributedHeight + 4*mHeight/20, paint);
			canvas.drawBitmap(mGKeyImage, mWidth/8, mHeight/5f, paint);
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
		paint.setTextSize(48f);
		paint.setColor(Color.GRAY);
		canvas.drawText(myTab.getTabName(), 50, 100, paint);
	}
	
	private void drawStrings(Canvas canvas, boolean centered) {
		paint.setColor(Color.BLACK);
		paint.setTextSize(36f);
		paint.setStrokeWidth(2f);
		if (centered){
			for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
				canvas.drawLine(mWidth/10, ratio*mHeight + i*mHeight/16, mWidth, ratio*mHeight + i*mHeight/16, paint);
				canvas.drawText(stantardTuning[i]+"", mWidth/16, ratio*mHeight + i*mHeight/16, paint);
			}
			canvas.drawLine(mWidth/10, ratio*mHeight, mWidth/10, ratio*mHeight + 5*mHeight/16, paint);
		} else {
			float margin = mHeight/8;
			float distributedHeight = mHeight*7/16 + margin;
			float centerText = mHeight/80;
			for (int i = 0; i < myInstrument.getNumOfStrings(); i++) {
				canvas.drawLine(mWidth/10, distributedHeight + i*mHeight/16, mWidth, distributedHeight + i*mHeight/16, paint);
				canvas.drawText(stantardTuning[i]+"", mWidth/16, distributedHeight + centerText + i*mHeight/16, paint);
			}
			canvas.drawLine(mWidth/10, distributedHeight, mWidth/10, distributedHeight + 5*mHeight/16, paint);
		}
		
	}

	private void drawCursor(Canvas canvas) {
		paint.setColor(Color.RED);
		paint.setStrokeWidth(10f);
		float margin = mHeight/10;
		canvas.drawLine(mWidth/4, mHeight/8 + margin, mWidth/4, 15*mHeight/16, paint);
		paint.setStrokeWidth(1f);
	}
	
	/**
	 * Initialize the screen boxes
	 */
	private void createBoxes() {
		//TODO Consider margin within the sub boxes
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
		topContentRect = new Rect(bodyRect.left, bodyRect.bottom, bodyRect.right, bodyRect.centerY());
		bottomContentRect = new Rect(bodyRect.left, bodyRect.centerY(), bodyRect.right, bodyRect.bottom);
		int verticalMargin = (bodyRect.height()-bottomContentRect.height())/2;
		centerScreenRect = new Rect(bodyRect.top+verticalMargin, bodyRect.left, 
				bodyRect.bottom-verticalMargin, bodyRect.right);
		
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
		rightCenterPartRect = new Rect(leftPartRect.left, centerScreenRect.top, 
				rightPartRect.right, centerScreenRect.bottom);
	}
	
}
