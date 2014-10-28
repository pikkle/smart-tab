package ch.epfl.sweng.smartTabs.gfx;

import ch.epfl.sweng.smartTabs.music.TabGenerationThread;

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
	private final TabGenerationThread thread;

	public TabAnimationThread(NoteView noteview) {
		myNoteview = noteview;
		this.running = true;
		thread = new TabGenerationThread(noteview.getTab(), noteview.getTimes());
		thread.start();
	}

	public void switchRunning() {
		if (running) {
			running = false;
		} else {
			running = true;
		}
	}

	public void stopPlaying() {
		running = false;
		playing = false;
	}

	@Override
	public void run()  {
		try {
			thread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

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
