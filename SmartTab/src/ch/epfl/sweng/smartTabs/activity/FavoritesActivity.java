package ch.epfl.sweng.smartTabs.activity;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.Map;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.music.Tab;

public class FavoritesActivity extends Activity {
	private static final String PREFS_NAME = "MyPrefsFile";
	private ListView listV;

	private SharedPreferences sharedPrefs;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_favorites);

		listV = (ListView) findViewById(R.id.fav_list);

		sharedPrefs = getSharedPreferences(PREFS_NAME, 0);

		Map<String, ?> favs = sharedPrefs.getAll();
		
		
		ArrayAdapter<String> adap = new ArrayAdapter<String>(
			getApplicationContext(),
			R.layout.listview_layout);
		

		for (Map.Entry<String, ?> entry : favs.entrySet()) {
			adap.add(entry.getKey());
		}
		
		listV.setAdapter(adap);
		listV.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				String tabName = (String)listV.getAdapter().getItem(position);
				System.out.println(tabName);
				String tabSerial = sharedPrefs.getString(tabName, "Error");
				Intent i = new Intent(FavoritesActivity.this, DisplayActivity.class);
				i.putExtra("tab", deserialize(tabSerial));
				startActivity(i);
			}
			
		});
	}
	
    public Tab deserialize(String s){
   	 try {
   	     byte[] b = Base64.decode(s.getBytes(), Base64.DEFAULT); 
   	     ByteArrayInputStream bi = new ByteArrayInputStream(b);
   	     ObjectInputStream si = new ObjectInputStream(bi);
   	     return (Tab) si.readObject();
   	 } catch (IOException e) {
   		 e.printStackTrace();
   		 return null; //a changer
   	 } catch (ClassNotFoundException e) {
		e.printStackTrace();
		return null;
	}
   }

}
