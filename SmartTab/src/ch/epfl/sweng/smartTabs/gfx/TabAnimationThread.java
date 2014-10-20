package ch.epfl.sweng.smartTabs.gfx;


/**
 * @author Faton Ramadani
 */
public class TabAnimationThread extends Thread{
	
	private NoteView noteview;
	private boolean running = false;
	private int speed = 1;
	private long delay = 10l;
	
	public TabAnimationThread(NoteView noteview){
		this.noteview = noteview;
		this.running = true;
	}
	
	public void setRunning(boolean run) {
        running = run;    
	}
	
	@Override
	public void run() {
		while(running){
			noteview.slideNotes(speed);
			noteview.postInvalidate();
			try {
			    sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setSpeed(int newSpeed){
		this.speed = newSpeed;
	}
}