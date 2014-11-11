package ch.epfl.sweng.smartTabs.activity;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.network.NetworkClient;

/**
 * author: Raphael Khoury
 * The MainActivity shows the menu, downloads the tabs and start the displayActivity with the tab 
 */
public class MainActivity extends Activity {
	private NetworkClient netClient;
	private ListView listV;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		netClient = new NetworkClient();
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connMgr.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnected()) {
			new DownloadWebpageTask().execute();
		} else {
			Toast.makeText(this, "Error retrieving tablatures. Please try again.", Toast.LENGTH_LONG).show();
		}
	}


	/**
	 * Author: Raphael Khoury
	 * Add items to Action Bar, define actions when clicked
	 * call method that launches new Activity when Settings is clicked
	 */
	@Override
	public boolean onCreateOptionsMenu(Menu menu){
		MenuInflater inflater = getMenuInflater();
		inflater.inflate(R.menu.main_activity_action_bar, menu);
		return super.onCreateOptionsMenu(menu);
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle presses on the action bar items
		switch (item.getItemId()) {
		case R.id.action_settings:
			startActivity(new Intent(this, PreferencesActivity.class));
			return true;
		case R.id.action_refresh:
			Toast.makeText(getApplicationContext(), "Refreshing Tab List.", Toast.LENGTH_SHORT).show();
			new DownloadWebpageTask().execute();
			Toast.makeText(getApplicationContext(), "Tab List Fetched.", Toast.LENGTH_SHORT).show();
			return true;
		case R.id.action_search:
			//code here for searching.
			return true;
		default:
			return super.onOptionsItemSelected(item);
		}
	}


	/**
	 *	This private class downloads JSONs from the server
	 */
	private class DownloadWebpageTask extends AsyncTask<Void, Void, Map<String, URL>> {
		@Override
		protected Map<String, URL> doInBackground(Void... params) {
			try {
				return netClient.fetchTabMap(getString(R.string.serverURL));
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}
		//Call this method after fetching the map containing tab names and corresponding URL.
		//Generate menu buttons here and on-click listeners that fetch URL contents.
		@Override
		protected void onPostExecute(final Map<String, URL> map) {
			listV = (ListView) findViewById(R.id.list);
			String [] values = new String[map.size()];
			int count = 0;
			for (String key : map.keySet()) {
				values[count]=key;
				count++;
			}

			ArrayAdapter<String> adap = new ArrayAdapter<String>(getApplicationContext(), 
					android.R.layout.simple_list_item_1, values);
			listV.setAdapter(adap);
			listV.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
					String item = (String) listV.getAdapter().getItem(position);
					URL url = map.get(item);
					new DownloadTabs(url).execute();
				}
			});
		}
	}

	/**
	 * @author Faton Ramadani
	 */
	private class DownloadTabs extends AsyncTask<Void, Void, Tab> {

		private URL myUrl;

		public DownloadTabs(final URL url) {
			myUrl = url;
		}
		@Override
		protected Tab doInBackground(Void... params) {
			try {
				return netClient.fetchTab(myUrl);
			} catch (IOException e) {
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			}
			return null;
		}

		@Override
		protected void onPostExecute(final Tab tab) {
			Intent i = new Intent(MainActivity.this,
					DisplayActivity.class);
			i.putExtra("tab", tab);
			startActivity(i);
		}
	}
}
