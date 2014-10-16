/**
 * 
 */
package ch.epfl.sweng.smartTabs.activity;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;

/**
 * @author rphkhr
 * Raphael El-Khoury
 * 212765
 */
public class NetworkClient {
	final private int readTimeout = 10000;
	final private int connectTimeout = 15000;
	final private int maxBufferSize = 1000;

	/*Creates a new NetworkClient that takes in parameter the serverURL, and returns a JSON with a list of
	 * partition names and corresponding URL containing the partition in a format we can understand and display.
	 */
	public NetworkClient() {
	}

	public Array fetchTabList(String url){
		List<E> tabArray = null;
		try{
			String tmpStringArray = downloadContent(url);
			JSONArray jsonArray = new JSONArray(tmpStringArray);
			for (int i = 0; i<jsonArray.length(); i++){
				HashMap tmpHash = jsonArray.getJSONArray(i);
				tabArray.add(jsonArray);
			}
		} catch (IOException e) {
			System.err.println("InputStream not valid");
			e.printStackTrace();
		} catch (JSONException e) {
			System.err.println("JSON creation not available.");			
			e.printStackTrace();
		}
		
		
		
		return tabArray;
	}

	/**
	 * @param url that contains link to all tablatures
	 * @return String that will be converted to JSON that contains tablatures. Parsing from JSON will be done later.
	 */
	private String downloadContent(String urlString) throws IOException {
		InputStream is = null;

		try {
			//transform string into url
			URL url = new URL(urlString);
			//open network connection and set timeouts and request method
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setReadTimeout(readTimeout /* milliseconds */);
			conn.setConnectTimeout(connectTimeout /* milliseconds */);
			conn.setRequestMethod("GET");
			//connect
			conn.connect();
			is = conn.getInputStream();

			String stringContent = readStream(is);
			return stringContent;
		} finally {
			if (is != null) {
				is.close();
			}
		}
	}

	private String readStream(InputStream is) {
		char[] buffer = new char[maxBufferSize];
		Reader reader = null;
		try {
			reader = new InputStreamReader(is, "UTF-8");
			reader.read(buffer);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}        
		return new String(buffer);		
	}
}
