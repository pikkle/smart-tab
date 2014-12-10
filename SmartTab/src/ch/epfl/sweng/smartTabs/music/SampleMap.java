package ch.epfl.sweng.smartTabs.music;

import java.util.HashMap;
import java.util.Map;

import android.content.Context;
import android.media.SoundPool;

/**
 * @author imani92 Ismail Imani
 */
public class SampleMap {
    private final int frets = 13;
    private final Map<String, Integer> samples = new HashMap<String, Integer>();

    public SampleMap(Context context, SoundPool pool, Note[] tuning) {

        for (int i = 0; i < tuning.length; i++) {
            for (int j = 0; j < frets; j++) {
                Note newNote = tuning[i].addHalfTones(j);
                String noteName = newNote.toString();
                if (!samples.containsKey(noteName)) {
                    samples.put(noteName, pool.load(
                            context,
                            context.getResources().getIdentifier(noteName,
                                    "raw", context.getPackageName()), 1));
                }
            }
        }
    }

    /**
     * This method returns the soundId of the sample corresponding to the note
     * played in the tab.
     * 
     * @param string
     *            index of the string
     * @param fret
     *            index of the fret
     * @return soundId of the sample to play
     */
    public final int getSampleId(Note note) {
        return samples.get(note.toString());
    }
}
