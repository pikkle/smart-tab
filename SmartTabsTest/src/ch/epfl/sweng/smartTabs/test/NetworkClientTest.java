/**
 * 
 */
package ch.epfl.sweng.smartTabs.test;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.json.JSONException;

import android.test.AndroidTestCase;
import android.view.ViewDebug.ExportedProperty;
import ch.epfl.sweng.smartTabs.network.NetworkClient;


/**
 * @author rphkhr
 * Raphael El-Khoury
 * 212765
 */
public class NetworkClientTest extends AndroidTestCase{
	
	public void testJSONParsing() throws JSONException, MalformedURLException{
		Map <String, URL> map = new HashMap<String, URL> ();
		map.put("foo", new URL("http://bar.com/"));
		String str = "[{\"name\":\"foo\",\"filename\":\"http://bar.com/\"}]";
		NetworkClient netClient = new NetworkClient();
		System.out.println(netClient.parseFromJson(str));
		assertEquals(map, netClient.parseFromJson(str));
	}
	
	public void testConnectionAndParsing() throws MalformedURLException, IOException{
		//TODO: Input Test URL here.
		final String url = "http://mpikkle.com/getTabs";
		NetworkClient netClient = new NetworkClient();
		assertNotNull(netClient.downloadContent(new URL(url)));
		
		try{
			netClient.downloadContent(new URL(""));
			Assert.fail("Should throw MalformedURLException");
		}
		catch (MalformedURLException e){
			
		}
		
	}
	
}
