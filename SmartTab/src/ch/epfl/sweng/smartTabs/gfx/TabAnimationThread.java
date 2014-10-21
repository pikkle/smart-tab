package ch.epfl.sweng.smartTabs.gfx;

/**
 * @author Faton Ramadani
 * The thread which handles the animation
 */
public class TabAnimationThread extends Thread{
	
	private NoteView myNoteview;
	private boolean running = false;
	private int speed = 1;
	private final long delay = 10L;
	private final int threshold = 5;

	public TabAnimationThread(NoteView noteview) {
		myNoteview = noteview;
		this.running = true;
	}
	
	public void setRunning(boolean run) {
        running = run;    
	}
	
	@Override
	public void run() {
		while (running) {
			myNoteview.slideNotes(speed);
			myNoteview.postInvalidate();
			try {
			    sleep(delay);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	public void setSpeed(int newSpeed) {
		if (newSpeed < 0) {
			throw new IllegalArgumentException();
		} else if (newSpeed > threshold) {
			System.out.println("The new speed is too fast");
			throw new IllegalArgumentException();
		} else {
			this.speed = newSpeed;
		}
	}
}