/**
 * 
 */
package ch.epfl.sweng.smartTabs.test;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import junit.framework.Assert;

import org.json.JSONException;
import org.mockito.Mockito;

import ch.epfl.sweng.smartTabs.network.NetworkClient;


/**
 * @author rphkhr
 * Raphael El-Khoury
 * 212765
 */
public class NetworkClientTest extends MockTestCase {
	private NetworkClient netClient;
	private HttpURLConnection connection;

    protected void setUp() throws Exception {
    	super.setUp();
    	connection = Mockito.mock(HttpURLConnection.class);
    	netClient = Mockito.mock(NetworkClient.class);
    	Mockito.doReturn(connection).when(netClient).getConnection(Mockito.any(URL.class));
    	
    }

	/*
    public void configureCrash(int status) throws IOException {
    	InputStream dataStream = Mockito.mock(InputStream.class);
    	Mockito.when(dataStream.read()).thenReturn(0x20, 0x20, 0x20, 0x20).thenThrow(new IOException());
    	
    	Mockito.doReturn(status).when(connection).getResponseCode();
    	Mockito.doReturn(dataStream).when(connection).getInputStream();
    }
*/
	

	public void testJSONParsing() throws JSONException, MalformedURLException{
		Map <String, URL> map = new HashMap<String, URL> ();
		map.put("foo", new URL("http://bar.com/"));
		String str = "[{\"name\":\"foo\",\"filename\":\"http://bar.com/\"}]";
		System.out.println(netClient.parseFromJson(str));
		assertEquals(map, netClient.parseFromJson(str));
	}

	public void testConnectionAndParsing() throws MalformedURLException, IOException{
		final String url = "http://mpikkle.com/getTabsTest";
		assertNotNull(netClient.downloadContent(new URL(url)));

		try{
			netClient.downloadContent(new URL(""));
			Assert.fail("Should throw MalformedURLException");
		}
		catch (MalformedURLException e){

		}

	}

}
