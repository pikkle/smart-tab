package ch.epfl.sweng.smartTabs.music;

import android.graphics.Canvas;

/**
 * @author fatonramadani
 */
public interface MusicDraw {
    void drawNotes(Canvas canvas);
    void drawGrid(Canvas canvas, float y);
    void drawVerticalLineOnTab(Canvas canvas, int x, int y);
}