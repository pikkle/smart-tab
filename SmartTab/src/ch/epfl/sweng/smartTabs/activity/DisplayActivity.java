package ch.epfl.sweng.smartTabs.activity;

import android.app.Activity;
import android.os.Bundle;
import ch.epfl.sweng.smartTabs.gfx.GridView;

public class DisplayActivity extends Activity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(new GridView(this, null));
	}
}