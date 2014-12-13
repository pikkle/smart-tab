package ch.epfl.sweng.smartTabs.gfx;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.view.View;

/**
 * The top view containing the title and the metronome.
 */
public class HeaderView extends View {
    private final static float TITLESIZERATIO = 0.5f;
    private final static int PADDING = 4;
    private final static int ALPHA = 255;
    private final static int TEXTPOSRATIO = 32;
    private final static double SLOPE1 = -2.5;
    private final static double VERTICALOFFSET1 = 1;
    private final static double SLOPE2 = 2.5;
    private final static double VERTICALOFFSET2 = -1.5;
    private final static double STABLERATIO = 0.5;
    private final static double MINDECREASINGPOS = 0.0;
    private final static double MAXDECREASINGPOS = 0.2;
    private final static double MININCREASINGPOS = 0.8;
    private final static double MAXINCREASINGPOS = 1.0;

    private Paint mPaint;
    private String mTitle;
    private String mArtist;
    private double myRatio;
    private Metronome mMetronome;

    /**
     * Contains the title of the song.
     * 
     * @param context
     * @param title
     */
    public HeaderView(Context context, String title, String artist) {
        super(context);
        this.mPaint = new Paint();
        this.setBackgroundColor(Color.WHITE);
        this.mTitle = title;
        this.mArtist = artist;
        mMetronome = new Metronome();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mPaint.setTextSize(canvas.getHeight() * TITLESIZERATIO);
        mPaint.setAlpha(ALPHA);
        mPaint.setColor(Color.GRAY);
        canvas.drawText(mTitle + " - " + mArtist, canvas.getWidth() / TEXTPOSRATIO, canvas.getHeight() / 2, mPaint);

        // Drawing the Metronome
        int padding = PADDING;
        int radius = (int) (myRatio * (canvas.getHeight() - 2 * padding) / 2);
        int posX = canvas.getWidth() - (canvas.getHeight() - 2 * padding) / 2 - padding / 2;
        int posY = canvas.getHeight() / 2;
        int alpha = (int) (myRatio * ALPHA);
        mMetronome.draw(canvas, posX, posY, radius, alpha);

    }

    /**
     * Compute the ratio of the metronome displayed
     * 
     * @param position
     * @param pace
     * @return
     */
    public void computeRatio(int position, int pace) {
        double percentage = ((double) position % (double) pace) / (double) pace;
        if (percentage == 0) {
            // System.out.println("Percentage = "+percentage+
            // ", playingPosition = "+ position);
        }
        if (percentage >= MINDECREASINGPOS && percentage <= MAXDECREASINGPOS) {
            myRatio = SLOPE1 * percentage + VERTICALOFFSET1;
        } else if (percentage > MAXDECREASINGPOS && percentage < MININCREASINGPOS) {
            myRatio = STABLERATIO;
        } else if (percentage >= MININCREASINGPOS && percentage < MAXINCREASINGPOS) {
            myRatio = SLOPE2 * percentage + VERTICALOFFSET2;
        }
    }

}
