/**
 * 
 */
package ch.epfl.sweng.smartTabs.test;

import org.json.JSONException;
import org.json.JSONObject;

import android.test.AndroidTestCase;

import ch.epfl.sweng.smartTabs.music.Tab;

/**
 * @author rphkhr
 * Raphael El-Khoury
 * 212765
 */
public class TabTest extends AndroidTestCase{
	public String str ="{ \"name\": \"Test\", \"complex\": false, \"tempo\": 120, \"partition\": [ { \"string_1\": \"0\", \"string_2\": \"1\", \"string_3\": \"2\", \"string_4\": \"2\", \"string_5\": \"0\", \"string_6\": \"0\"}]}";

	public void testJSONParsing() throws JSONException{
		JSONObject jObj = new JSONObject(str);
		Tab parsedTab = Tab.parseTabFromJSON(jObj);
		assertNotNull(parsedTab);
	}
	
	public void testJSONName() throws JSONException{
		JSONObject jObj = new JSONObject(str);
		Tab parsedTab = Tab.parseTabFromJSON(jObj);
		assertEquals(parsedTab.getTabName(), jObj.getString("name"));
		assertEquals("foo", parsedTab.getTabName());
		assertEquals("foo", jObj.getString("name"));
	}

	/*public void testJSONComplex() throws JSONException{
		JSONObject jObj = new JSONObject(str);
	}

	public void testJSONTempo() throws JSONException{
		JSONObject jObj = new JSONObject(str);
	}

	public void testJSONTimeList() throws JSONException{
		JSONObject jObj = new JSONObject(str);
	}
*/

}
