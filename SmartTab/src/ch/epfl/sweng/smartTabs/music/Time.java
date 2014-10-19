package ch.epfl.sweng.smartTabs.music;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author chrisgaubla
 *
 */
public class Time {
	
	private final double mDuration;
	private final String[] mNotes;
	private final int mMesure;
	private final boolean mTernary;
	private final int mStep;
	
	public Time(String[] notes, double duration, int mesure, boolean ternary, int step){
		mNotes = notes;
		mDuration = duration;
		mMesure = mesure;
		mTernary = ternary;
		mStep = step;
	}
	
	public static Time parseTimeFromJson(JSONObject jsonTime) throws JSONException{
		double jsonDuration = jsonTime.getDouble("duree");
		String[] jsonNotes = new String[6];
		for(int i = 1; i<= 6; i++){
			String corde = "corde_"+i;
			jsonNotes[i-1] = jsonTime.getString(corde);
		}
		int jsonMesure = jsonTime.getInt("mesure");
		boolean jsonTernary = jsonTime.getBoolean("ternaire");
		int jsonStep = jsonTime.getInt("pas");
		return new Time(jsonNotes, jsonDuration, jsonMesure, jsonTernary, jsonStep);
	}
	public String getNote(int string){
		return mNotes[string];
	}
	public double getDuration(){
		return mDuration;
	}
	public int getMesure() {
		return mMesure;
	}
	public boolean isTernary() {
		return mTernary;
	} 
	public int getStep(){
		return mStep;
	}
	
	
	
	
	
	

}