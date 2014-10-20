/**
 * 
 */
package ch.epfl.sweng.smartTabs.activity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


/**
 * @author rphkhr
 * Raphael El-Khoury
 * 212765
 */
public class NetworkClient {
	final private int readTimeout = 10000;
	final private int connectTimeout = 15000;

	/*Creates a new NetworkClient that takes in parameter the serverURL, and returns a JSON with a list of
	 * partition names and corresponding URL containing the partition in a format we can understand and display.
	 */
	public NetworkClient(String url) {
	}

	//From the URL, download the String which is then parsed into JSONArray, containing JSONObjects with 
	//tablature name (coded with "name") and corresponding URL (coded with "filename").
	public Map<String,URL> fetchTabMap(String url) throws IOException, JSONException{
		String s = downloadContent(new URL(url));
		//test if we correctly downloaded the string.
		System.out.println(s);
		//create JSONArray from the string
		JSONArray jArray = new JSONArray(s);
		HashMap<String, URL> map = new HashMap<String, URL>();
		//Loop through the array, creating JSONObject on each iteration, and fetching JSONObjects contents
		//and putting them into the map.
		for (int i = 0; i<jArray.length(); i++){
			JSONObject jObj = new JSONObject();
			jObj = (JSONObject) jArray.get(i);
			map.put(jObj.getString("name"), new URL (jObj.getString("filename")));
		}

		return map;
	}

	/**
	 * @param url that contains link to all tablatures
	 * @return String that will be converted to JSON that contains tablatures. Parsing from JSON will be done later.
	 */

	public String downloadContent(URL url) throws IOException {
		InputStream is = null;

		try {
			//transform string into url
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
		BufferedReader reader;
		String line, output = "";
		try {
			reader = new BufferedReader(new InputStreamReader(is, "UTF-8"));

			while ((line = reader.readLine()) != null){
				output+=line;
			}

		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String(output);		
	}
}
