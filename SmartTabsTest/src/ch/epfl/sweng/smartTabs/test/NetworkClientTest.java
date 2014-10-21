/**
 * 
 */
package ch.epfl.sweng.smartTabs.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;

import android.test.AndroidTestCase;
import ch.epfl.sweng.smartTabs.activity.NetworkClient;


/**
 * @author rphkhr
 * Raphael El-Khoury
 * 212765
 */
public class NetworkClientTest extends AndroidTestCase{
	
	public void testJSONParsing() throws JSONException, MalformedURLException{
		Map <String, URL> map = new HashMap<String, URL> ();
		map.put("foo", new URL("http://bar.com"));
		String str = "[{\"name\":\"foo\",\"filename\":\"http://bar.com\"}]";
		NetworkClient netClient = new NetworkClient();

		assertEquals(map, netClient.parseFromJson(str));
	}
	
	public void testConnectionAndParsing() throws MalformedURLException, IOException{
		//TODO: Input Test URL here.
		final String url = "";
		NetworkClient netClient = new NetworkClient();
		assertNotNull(netClient.downloadContent(new URL(url)));
	}
	
}
