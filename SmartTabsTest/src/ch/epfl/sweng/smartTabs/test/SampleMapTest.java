package ch.epfl.sweng.smartTabs.test;

import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.SampleMap;
import android.media.AudioManager;
import android.media.SoundPool;
import android.test.AndroidTestCase;

/**
 * @author fatonramadani
 * Testing the sample map
 */
public class SampleMapTest extends AndroidTestCase{
    
    private SoundPool pool = new SoundPool(50, AudioManager.STREAM_MUSIC, 0);
    private Note[] emptyTuning = new Note[0];
    private Note note = new Note(Height.A, 3);
    
    public void emptyMap(){
        SampleMap m = new SampleMap(getContext(), pool, emptyTuning) ;
        try {
            m.getSampleId(note);
        } 
        catch (NullPointerException e) {
            e.printStackTrace();
        }        
    }
}