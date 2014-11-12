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
	private final String[] mNotes;
	private final int mMesure;
	private final boolean mTernary;
	private final int mStep;
	private final static int NUMCHORDS = 6;

	public Time(String[] notes, String duration, int mesure, boolean ternary,
			int step) {
		mNotes = notes;
		mDuration = duration;
		mMesure = mesure;
		mTernary = ternary;
		mStep = step;
	}

	public static Time parseTimeFromJson(JSONObject jsonTime)
		throws JSONException {
		String[] jsonNotes = new String[NUMCHORDS];
		for (int i = 1; i <= NUMCHORDS; i++) {
			String corde = "string_" + i;
			jsonNotes[i - 1] = jsonTime.getString(corde);
			System.out.println(jsonNotes[i - 1]);
		}
		String jsonDuration;
		try {
			jsonDuration = jsonTime.getString("length");
		} catch (JSONException e) {
			jsonDuration = Duration.Noir.name();
		}
		int jsonMesure;
		try {
			jsonMesure = jsonTime.getInt("measure");
		} catch (JSONException e) {
			jsonMesure = 0;
		}
		boolean jsonTernary;
		try {
			jsonTernary = jsonTime.getBoolean("ternary");
		} catch (JSONException e) {
			jsonTernary = false;
		}
		int jsonStep;
		try {
			jsonStep = jsonTime.getInt("step");
		} catch (JSONException e) {
			jsonStep = 0;
		}

		return new Time(jsonNotes, jsonDuration, jsonMesure, jsonTernary,
				jsonStep);
	}

	public String getNote(int string) {
		return mNotes[string];
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
		for (String n : mNotes) {
			if (n.equals("")) {
				s += "-";
			} else {
				s += n;
			}
		}
		return s;
	}
}