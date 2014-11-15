package ch.epfl.sweng.smartTabs.activity;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceClickListener;
import android.preference.PreferenceActivity;
import android.preference.PreferenceFragment;
import ch.epfl.sweng.smartTabs.R;

/**
 * TODO : Javadoc
 */
public class PreferencesActivity extends PreferenceActivity {

	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		getFragmentManager().beginTransaction().replace(android.R.id.content, new PreferencesFrag()).commit();
		
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		actionBar.setTitle(getString(R.string.title_settings));

	}
	
	/**
	 * TODO : Javadoc
	 */
	public static class PreferencesFrag extends PreferenceFragment{
		
		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			addPreferencesFromResource(R.xml.preferences);
			Preference helpPref = (Preference) findPreference("pref_help");
			helpPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				
				@Override
				public boolean onPreferenceClick(Preference preference) {
					Intent i = new Intent(getActivity(), HelpActivity.class);
					startActivity(i);
					return false;
				}
			});
			
			Preference abtDevPref = (Preference) findPreference("pref_abt_dev");
			abtDevPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				
				@Override
				public boolean onPreferenceClick(Preference preference) {
					Intent i = new Intent(getActivity(), AboutDevsActivity.class);
					startActivity(i);
					return false;
				}
			});
			
			Preference abtAppPref = (Preference) findPreference("pref_abt_app");
			abtAppPref.setOnPreferenceClickListener(new OnPreferenceClickListener() {
				
				
				@Override
				public boolean onPreferenceClick(Preference preference) {
					Intent i = new Intent(getActivity(), AboutAppActivity.class);
					startActivity(i);
					return false;
				}
			});
		}
	}

}
