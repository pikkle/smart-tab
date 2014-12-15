package ch.epfl.sweng.smartTabs.activity;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import ch.epfl.sweng.smartTabs.R;

/**
 * Activity detailing the version etc of the app
 */
public class AboutAppActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_app);

        // Enabling Up / Back navigation
        ActionBar actionBar = getActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);
        actionBar.setTitle(getString(R.string.title_about_app));
    }
}