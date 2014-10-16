package ch.epfl.sweng.smartTabs.activity;

import java.lang.reflect.Array;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;
import ch.epfl.sweng.quizapp.QuizActivity.DownloadWebpageTask;
import ch.epfl.sweng.smartTabs.R;


public class MainActivity extends Activity {
	private NetworkClient netClient;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		String serverURL = getString(R.string.serverURL);
		netClient = new NetworkClient(serverURL);
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

		NetworkInfo netInfo = connMgr.getActiveNetworkInfo();
		if (netInfo != null && netInfo.isConnected()) {
			new DownloadWebpageTask().execute();
		} else {
			Toast.makeText(this, "Error retrieving tablature. Please try again.", Toast.LENGTH_LONG).show();
		}

		Intent i = new Intent(this, DisplayActivity.class);
		startActivity(i);
	}

	private class DownloadWebpageTask extends AsyncTask<Void, Void, Array>{







		@Override
		protected Array doInBackground(Void... params) {
			return null;
		}

	}


}
