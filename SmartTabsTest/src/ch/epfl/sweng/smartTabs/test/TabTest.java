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
	public String str ="{\"name\": \"Jeux Interdits\", \"complex\": false,\"tempo\": 120,\"partition\": [ {\"string_1\": \"7\",\"string_2\": \"\",\"string_3\": \"\",\"string_4\": \"\",\"string_5\": \"\",\"string_6\": \"0\"}]";


	//return new Tab(jsonTabName, jsonComplex, jsonTempo, parsedSignatures,parsedTimeList);

	public void testJsonParsing() throws JSONException{
		JSONObject jObj = new JSONObject(str);
		Tab parsedTab = Tab.parseTabFromJSON(jObj);
		assertNotNull(parsedTab);
	}




}
