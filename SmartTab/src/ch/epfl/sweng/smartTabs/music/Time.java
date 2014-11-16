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
	private final static Note[] tuning = {
		new Note(3, Height.E), new Note(2, Height.B),
		new Note(2, Height.G), new Note(2, Height.D), 
		new Note(1, Height.A), new Note(1, Height.E)
	};

	public Time(String[] notes, Note[] partitionNotes, String duration, int mesure, boolean ternary,
			int step) {
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
			if(!jsonNotes[i - 1].equals("")) {
				int fretNumber = Integer.parseInt(jsonNotes[i - 1]);
				partitionNotes[i - 1] = tuning[i].addHalfTones(fretNumber);
			}
		}
		String jsonDuration;
		try {
			jsonDuration = jsonTime.getString("length");
		} catch (JSONException e) {
//			throw new JSONException("The length value is not valid");
			jsonDuration = "Noir";
		}
		int jsonMesure;
		try {
			jsonMesure = jsonTime.getInt("measure");
		} catch (JSONException e) {
			//throw new JSONException("The measure value is not valid");
			jsonMesure = 0;
		}
		boolean jsonTernary;
		try {
			jsonTernary = jsonTime.getBoolean("ternary");
		} catch (JSONException e) {
//			throw new JSONException("The ternary value is not valid");
			jsonTernary = false;
		}
		int jsonStep;
		try {
			jsonStep = jsonTime.getInt("step");
		} catch (JSONException e) {
			throw new JSONException("The step value is not valid");
		}

		return new Time(jsonNotes, partitionNotes, jsonDuration, jsonMesure, jsonTernary,
				jsonStep);
	}

	public String getNote(int string) {
		return mTabNotes[string];
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