package ch.epfl.sweng.smartTabs.gfx;



import android.content.Context;
import android.graphics.Canvas;
import android.view.SurfaceHolder;
import android.view.SurfaceView;

public class NoteView extends SurfaceView{


	private SurfaceHolder surfaceHolder;
	private TabAnimationThread thread;
	private final int DELTAX = 5;

	public NoteView(Context context) {
		super(context);
		init();
	}

	private void init() {
		surfaceHolder.addCallback(new SurfaceHolder.Callback(){
			@Override
			public void surfaceCreated(SurfaceHolder holder) {
				thread.setRunning(true);
				thread.start();
			}

			@Override
			public void surfaceChanged(SurfaceHolder holder, 
					int format, int width, int height) {
				// TODO Auto-generated method stub

			}

			@Override
			public void surfaceDestroyed(SurfaceHolder holder) {
				boolean retry = true;
				thread.setRunning(false);
				while (retry) {
					try {
						thread.join();
						retry = false;
					} catch (InterruptedException e) {
					}
				}
			}
		});
	}




	public void drawNotes(Canvas canvas) {

	}

}
