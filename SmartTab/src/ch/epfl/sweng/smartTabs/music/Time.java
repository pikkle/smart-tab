package ch.epfl.sweng.smartTabs.music;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author chrisgaubla
 * 
 */
public class Time implements Serializable {

    private final static int NUMSTRINGS = 6;
    private final static int NUMFRETS = 16;
    private final static Note[] TUNING = {new Note(Height.E, 3), new Note(Height.B, 2), new Note(Height.G, 2),
        new Note(Height.D, 2), new Note(Height.A, 1), new Note(Height.E, 1) };
    private static final long serialVersionUID = 1L;

    private final String mDuration;
    private final String[] mTabNotes;
    private final Note[] mPartitionNotes;

    public Time(String[] notes, Note[] partitionNotes, String duration) {
        mTabNotes = notes;
        mDuration = duration;
        mPartitionNotes = partitionNotes;
    }

    public static Time parseTimeFromJson(JSONObject jsonTime) throws JSONException {
        String[] jsonNotes = new String[NUMSTRINGS];
        Note[] partitionNotes = new Note[NUMSTRINGS];
        String jsonDuration = null;
        try {
            for (int i = 1; i <= NUMSTRINGS; i++) {
                String corde = "string_" + i;
                jsonNotes[i - 1] = jsonTime.getString(corde);
                if (!jsonNotes[i - 1].equals("")) {
                    int fretNumber = Integer.parseInt(jsonNotes[i - 1]);
                    if (fretNumber < 0 && fretNumber > NUMFRETS) {
                        throw new JSONException("fretNumber invalid");
                    }
                    partitionNotes[i - 1] = TUNING[i - 1].addHalfTones(fretNumber);

                }
            }
            jsonDuration = jsonTime.getString("length");
            if (!jsonDuration.equals("Noire") && !jsonDuration.equals("Blanche") && !jsonDuration.equals("Croche")
                    && !jsonDuration.equals("DoubleCroche") && !jsonDuration.equals("Ronde")) {
                throw new JSONException("The duration is not valid");
            }
        } catch (JSONException e) {
            throw new JSONException("The length value is not valid");
        }

        return new Time(jsonNotes, partitionNotes, jsonDuration);
    }

    public String getNote(int string) {
        return mTabNotes[string];
    }

    public Note[] getPartition() {
        return mPartitionNotes;
    }

    public Note getPartitionNote(int index) {
        return mPartitionNotes[index];
    }

    public String getDuration() {
        return mDuration;
    }

    public boolean isEmpty() {
        for (int i = 0; i < mTabNotes.length; i++) {
            if (!mTabNotes[i].equals("")) {
                return false;
            }
        }
        return true;

    }

    public String toString() {
        String s = "";
        for (String n : mTabNotes) {
            if (n.equals("")) {
                s += "-";
            } else {
                s += n;
            }
        }
        return s;
    }
}