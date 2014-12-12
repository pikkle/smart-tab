package ch.epfl.sweng.smartTabs.test;

import org.json.JSONException;
import org.json.JSONObject;

import ch.epfl.sweng.smartTabs.gfx.MusicSheetView;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.Tab;
import android.test.AndroidTestCase;

public class MusicSheetViewTest extends AndroidTestCase {
    
    public String goodTabString = "{ \"name\": \"foo\",\"tempo\": 120, \"partition\": [ { \"string_1\": \"2\", \"string_2\": \"1\", \"string_3\": \"2\", \"string_4\": \"2\", \"string_5\": \"0\", \"string_6\": \"0\", \"step\": 1, \"length\" : \"Noire\"}]}";
    
    public void musicSheet() throws JSONException {
        JSONObject jObj1 = new JSONObject(goodTabString);
        Tab gParsedTab = Tab.parseTabFromJSON(jObj1);
        MusicSheetView musicSheet = new MusicSheetView(getContext(),gParsedTab,200);
        sharpNoteTest(musicSheet, gParsedTab);
        heightNoteTest(musicSheet, gParsedTab);
    }
    public void sharpNoteTest(MusicSheetView musicSheet, Tab tab) {
    	Note sNote = musicSheet.sharpNote(tab.getTime(0).getPartitionNote(0));
    	assertEquals("Sharp note problem", 5, sNote.getHeight().getIndex());
    }
    
    public void heightNoteTest(MusicSheetView musicSheet, Tab tab) {
    	Note note = tab.getTime(0).getPartitionNote(1);
    	assertEquals("Not the right height", 5, note.getHeight().getIndex());
    }
    
    
    
}