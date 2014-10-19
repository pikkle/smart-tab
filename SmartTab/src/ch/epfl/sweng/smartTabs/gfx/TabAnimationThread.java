package ch.epfl.sweng.smartTabs.gfx;


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
			noteview.invalidate();
			try {
			    sleep(30);
			} catch (InterruptedException e) {
			    // TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}