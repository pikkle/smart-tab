package ch.epfl.sweng.smartTabs.activity;

import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import ch.epfl.sweng.smartTabs.R;

public class PreferencesActivity extends PreferenceActivity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFrag()).commit();
	
	}
	
	
	public static class PreferencesFrag extends PreferenceFragment{
		
		@Override
		public void onCreate(Bundle savedInstanceState){
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
		}
	}

}
