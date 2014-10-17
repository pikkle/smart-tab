package ch.epfl.sweng.smartTabs.gfx;

import android.graphics.Canvas;


/**
 * @author Faton Ramadani
 */
public class TabAnimationThread extends Thread{
	
	private NoteView noteview;
	private boolean running = false;
	
	public TabAnimationThread(NoteView noteview){
		this.noteview = noteview;
	}
	
	public void setRunning(boolean run) {
        running = run;    
	}
	
	@Override
	public void run() {
		while(running){
			Canvas canvas = noteview.getHolder().lockCanvas();
			if(canvas != null){
				synchronized (noteview.getHolder()) {
			    	noteview.drawNotes(canvas);
			    }
			    noteview.getHolder().unlockCanvasAndPost(canvas);
			}
		}
	}
}
