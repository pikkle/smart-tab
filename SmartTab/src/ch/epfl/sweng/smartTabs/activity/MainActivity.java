package ch.epfl.sweng.smartTabs.activity;

import java.io.IOException;
import java.net.URL;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;
import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.music.Tab;

public class MainActivity extends Activity {
	private NetworkClient netClient;
	private ListView listV;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		//download list of tablatures URL.
		String serverURL = getString(R.string.serverURL);
		netClient = new NetworkClient(serverURL);
		ConnectivityManager connMgr = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo netInfo = connMgr.getActiveNetworkInfo();

		if (netInfo != null && netInfo.isConnected()) {
			new DownloadWebpageTask().execute();
		} else {
			Toast.makeText(this, "Error retrieving tablature. Please try again.", Toast.LENGTH_LONG).show();
		}
	}

	private class DownloadWebpageTask extends AsyncTask<Void, Void, Map<String,URL>>{
		@Override
		protected Map<String,URL> doInBackground(Void... params) {
			try {
				return netClient.fetchTabMap(getString(R.string.serverURL));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return null;
		}
		//Call this method after fetching the map containing tab names and corresponding URL.
		//Generate menu buttons here and on-click listeners that fetch URL contents.
		@Override
		protected void onPostExecute(final Map<String, URL> map) {
			listV = (ListView) findViewById(R.id.list);
			//Add tab names to list view by iteration.
			String [] values = new String[map.size()];
			int count = 0;
			for (String key : map.keySet()){
				values[count]=key;
				count++;
			}
			//create & add adapter to listview
			ArrayAdapter<String> adap = new ArrayAdapter<String>(getApplicationContext(), 
					android.R.layout.simple_list_item_1, values);
			listV.setAdapter(adap);
			listV.setOnItemClickListener(new OnItemClickListener() {

				@Override
				public void onItemClick(AdapterView<?> parent, View view,
						int position, long id) {
					String item = (String) listV.getAdapter().getItem(position);
					URL url = map.get(item);
					String toRet = null;
					
					try {
						toRet = netClient.downloadContent(url);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					try {
						JSONObject jsonTablature = new JSONObject(toRet);
						Tab tablature = Tab.parseTabFromJSON(jsonTablature);
						Intent i = new Intent(MainActivity.this, DisplayActivity.class);
						i.putExtra("tab", tablature);
						startActivity(i);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
					
					
					//TODO Christopherrrrr, t'as l'URL ici, je fetch le contenu en tant que string, et fait
					//ce que tu veux avec. capiche or no capiche? <3
					/*Voil� l� tu te retrouve avec un nouvel objet Tab avec touts ce qu'il faut dedans, par
					 * contre c'est pas moi qui utilise cet objet c'est la display activity donc tu demandes 
					 * � faton ce qu'il veut en faire.
					 */
				}
			});
			
		}
	}
}
