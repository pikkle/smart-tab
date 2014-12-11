package ch.epfl.sweng.smartTabs.test;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.smartTabs.gfx.MusicSheetView;
import ch.epfl.sweng.smartTabs.music.Tab;
import android.test.AndroidTestCase;

public class MusicSheetViewTest extends AndroidTestCase {
    
    public String goodTabString = "{ \"name\": \"foo\", \"complex\": false, \"tempo\": 120, \"partition\": [ { \"string_1\": \"0\", \"string_2\": \"1\", \"string_3\": \"2\", \"string_4\": \"2\", \"string_5\": \"0\", \"string_6\": \"0\", \"step\": 1, \"length\" : \"Noire\"}]}";
    public String badTabString =  "{ \"name\": \"foo\", \"complex\": false, \"tempo\": 120, \"partition\": [ { \"string_1\": \"0\", \"string_2\": \"1\", \"string_3\": \"2\", \"string_4\": \"2\", \"string_7\": \"0\", \"string_6\": \"0\", \"step\": 1, \"length\" : \"Noire\"}]}";
    
    public void musicSheet() throws JSONException {
        JSONObject jObj1 = new JSONObject(goodTabString);
        Tab gParsedTab = Tab.parseTabFromJSON(jObj1);
    
        JSONObject jObj2 = new JSONObject(goodTabString);
        Tab bParsedTab = Tab.parseTabFromJSON(jObj2);
    
        MusicSheetView goodMusicSheet = new MusicSheetView(getContext(),gParsedTab,200);
        MusicSheetView badMusicSheet = new MusicSheetView(getContext(),bParsedTab,200);
    }
    
}