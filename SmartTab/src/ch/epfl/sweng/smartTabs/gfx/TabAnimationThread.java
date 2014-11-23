package ch.epfl.sweng.smartTabs.gfx;

/**
 * @author Faton Ramadani
 * The thread which handles the animation, can pause , increase or decrease the speed
 * of the animation.
 */
public class TabAnimationThread extends Thread {

	private NoteView myNoteview;
	private boolean running = false;
	private boolean playing = true;
	private int speed = 1;
	private final long delay = 10;
	private final int threshold = 5;

	public TabAnimationThread(NoteView noteview) {
		if (noteview == null) {
			throw new IllegalArgumentException();
		}
		myNoteview = noteview;
		this.running = true;
	}

	public void switchRunning() {
		running = !running;
	}

	public void stopPlaying() {
		running = false;
		playing = false;
	}

	@Override
	public void run()  {
		while (playing) {
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
	}

	// Change the playing speed, can't be negative, 
	// nor too high ( The threshold is yet to be defined).
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
