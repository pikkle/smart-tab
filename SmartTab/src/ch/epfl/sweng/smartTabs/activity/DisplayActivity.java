package ch.epfl.sweng.smartTabs.activity;

import ch.epfl.sweng.smartTabs.gfx.TabView;
import android.app.Activity;
import android.os.Bundle;

public class DisplayActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new TabView(this));
	}
}
