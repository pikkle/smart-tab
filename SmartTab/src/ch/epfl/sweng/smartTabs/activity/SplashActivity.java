package ch.epfl.sweng.smartTabs.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import ch.epfl.sweng.smartTabs.R;

public class SplashActivity extends Activity {
	Thread background;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_splash);

		background = new Thread(){
			public void run(){
				try {
					sleep(2000);
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
