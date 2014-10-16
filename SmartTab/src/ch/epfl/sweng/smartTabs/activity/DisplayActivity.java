package ch.epfl.sweng.smartTabs.activity;

import ch.epfl.sweng.smartTabs.gfx.TabView;
import ch.epfl.sweng.smartTabs.music.Tab;
import android.app.Activity;
import android.os.Bundle;

public class DisplayActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new TabView(this, new Tab()));
	}
}
