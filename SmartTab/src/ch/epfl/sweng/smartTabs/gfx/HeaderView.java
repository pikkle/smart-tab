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
    private Paint paint;
    private String mTitle;

    private final int titleAlpha = 255;
    private final float titleSizeRatio = 0.5f;
    private Metronome metronome;
    private double myRatio = 0.1;

    /**
     * Contains the title of the song.
     * 
     * @param context
     * @param title
     */
    public HeaderView(Context context, String title) {
        super(context);
        this.paint = new Paint();
        this.setBackgroundColor(Color.WHITE);
        this.mTitle = title;
        metronome = new Metronome();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        paint.setTextSize(canvas.getHeight() * titleSizeRatio);
        paint.setAlpha(titleAlpha);
        paint.setColor(Color.GRAY);
        canvas.drawText(mTitle, canvas.getWidth() / 32, canvas.getHeight() / 2,
                paint);

        // Drawing the Metronome
        int padding = 4;
        int radius = (int) (myRatio * (canvas.getHeight() - 2 * padding) / 2);
        int posX = canvas.getWidth() - (canvas.getHeight() - 2 * padding) / 2
                - padding / 2;
        int posY = canvas.getHeight() / 2;
        int alpha = (int) (myRatio * 255);
        metronome.draw(canvas, posX, posY, radius, alpha);
        
    }

    private void drawDebugBox(Canvas canvas) {
        paint.setColor(Color.RED);
        paint.setAlpha(100);
        canvas.drawRect(0, 0, canvas.getWidth(), canvas.getHeight(), paint);
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
        if(percentage ==  0){
            //System.out.println("Percentage = "+percentage+ ", playingPosition = "+ position);
        }
        if (percentage >= 0.0 && percentage <= 0.2) {
            myRatio = (-2.5) * percentage + 1;
        } else if (percentage > 0.2 && percentage < 0.8) {
            myRatio = 0.5;
        } else if (percentage >= 0.8 && percentage < 1) {
            myRatio = (2.5) * percentage - 1.5;
        }
     }
    
    
}
