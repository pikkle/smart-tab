package ch.epfl.sweng.smartTabs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import ch.epfl.sweng.smartTabs.gfx.NoteView;
import ch.epfl.sweng.smartTabs.gfx.TabAnimationThread;
import ch.epfl.sweng.smartTabs.music.Tab;

/**
 * @author fatonramadani
 * The activity which displays the tabs, handle the animation 
 */
public class DisplayActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		//Intent intent = getIntent();
		//Tab tab = (Tab) intent.getExtras().getSerializable("tab");
		//setContentView(new GridView(this,tab));
		NoteView n = new NoteView(this);
		//GridView t = new GridView(this);
		
		//TabViewGroup tvg = new TabViewGroup(this);
		//tvg.addView(n);
		//tvg.addView(t);
		setContentView(n);
		
		TabAnimationThread thread = new TabAnimationThread(n);
		thread.start();
	}
}