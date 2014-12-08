package ch.epfl.sweng.smartTabs.music;

import android.graphics.Canvas;

/**
 * @author fatonramadani
 */
public interface MusicDraw {
    public void drawNotes(Canvas canvas);
    public void drawGrid(Canvas canvas, float y);
    public void drawVerticalLineOnTab(Canvas canvas, int x, int y);
}