package ch.epfl.sweng.smartTabs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import ch.epfl.sweng.smartTabs.R;

/**
 * @author Raphael Khoury
 * Launcher Activity. Displays image for 1 second and then launches mainActivity, to show tablatures.
 * Quitting the app on this activity will kill the process and does not launch mainActivity after.
 */

public class SplashActivity extends Activity {
	Thread background;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		background = new Thread(){
			public void run(){
				try {
					sleep(1000);
				} catch (InterruptedException e) {
					android.os.Process.killProcess(android.os.Process.myPid());
				}
				startActivity(new Intent(getApplicationContext(), MainActivity.class));

			}
		};
		background.start();
	}
	
	@Override
	protected void onDestroy(){
		super.onDestroy();
		background.interrupt();
	}
}