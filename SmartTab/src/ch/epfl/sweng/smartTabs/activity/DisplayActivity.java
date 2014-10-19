package ch.epfl.sweng.smartTabs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import ch.epfl.sweng.smartTabs.gfx.GridView;
import ch.epfl.sweng.smartTabs.music.Tab;

/**
 * @author fatonramadani
 * The activity which displays the tabs, handle the animation 
 */
public class DisplayActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		Intent intent = getIntent();
		Tab tab = (Tab) intent.getExtras().getSerializable("tab");
		setContentView(new GridView(this,tab));
	}
}