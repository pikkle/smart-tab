package ch.epfl.sweng.smartTabs.music;

import java.io.Serializable;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author chrisgaubla
 * 
 */
public class Time implements Serializable {

    private static final long serialVersionUID = 1L;
    private final String mDuration;
    private final String[] mTabNotes;
    private final Note[] mPartitionNotes;
    private final int mMesure;
    private final boolean mTernary;
    private final int mStep;
    private final static int NUMCHORDS = 6;
    private final static Note[] TUNING = { new Note(Height.E, 3),
            new Note(Height.B, 2), new Note(Height.G, 2),
            new Note(Height.D, 2), new Note(Height.A, 1), new Note(Height.E, 1)

    };

    public Time(String[] notes, Note[] partitionNotes, String duration,
            int mesure, boolean ternary, int step) {
        mTabNotes = notes;
        mDuration = duration;
        mMesure = mesure;
        mTernary = ternary;
        mStep = step;
        mPartitionNotes = partitionNotes;
    }

    public static Time parseTimeFromJson(JSONObject jsonTime)
            throws JSONException {
        String[] jsonNotes = new String[NUMCHORDS];
        Note[] partitionNotes = new Note[NUMCHORDS];
        for (int i = 1; i <= NUMCHORDS; i++) {
            String corde = "string_" + i;
            jsonNotes[i - 1] = jsonTime.getString(corde);
            if (!jsonNotes[i - 1].equals("")) {
                int fretNumber = Integer.parseInt(jsonNotes[i - 1]);
                partitionNotes[i - 1] = TUNING[i - 1].addHalfTones(fretNumber);
                
            }
        }
        String jsonDuration;
        try {
            jsonDuration = jsonTime.getString("length");
        } catch (JSONException e) {
            throw new JSONException("The length value is not valid");
        }
        int jsonMesure;
        try {
            jsonMesure = jsonTime.getInt("measure");
        } catch (JSONException e) {
            // throw new JSONException("The measure value is not valid");
            jsonMesure = 0;
        }
        boolean jsonTernary;
        try {
            jsonTernary = jsonTime.getBoolean("ternary");
        } catch (JSONException e) {
            // throw new JSONException("The ternary value is not valid");
            jsonTernary = false;
        }
        int jsonStep;
        try {
            jsonStep = jsonTime.getInt("step");
        } catch (JSONException e) {
            throw new JSONException("The step value is not valid");
        }

        return new Time(jsonNotes, partitionNotes, jsonDuration, jsonMesure,
                jsonTernary, jsonStep);
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

    public int getMesure() {
        return mMesure;
    }

    public boolean isTernary() {
        return mTernary;
    }

    public int getStep() {
        return mStep;
    }
    
    public boolean isEmpty(){
    	for (int i = 0; i < mTabNotes.length; i++) {
			if(!mTabNotes[i].equals("")){
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