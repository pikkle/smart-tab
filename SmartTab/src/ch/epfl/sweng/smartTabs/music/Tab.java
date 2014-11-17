package ch.epfl.sweng.smartTabs.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author chrisgaubla
 */
public final class Tab implements Serializable {

	private static final long serialVersionUID = 1L;
	
	private final String mTabName;
	private final boolean mComplex;
	private final int mTempo;
	private final List<String> mSignatures;
	private final List<Time> mTimeList;
	private final HashMap<Float, Time> mTimeMap = new HashMap<Float, Time>(); //maps time with the place it is in time
	private final int pace = 200;
	
	private Tab(String tabName, boolean complex, int tempo,
			List<String> signatures, List<Time> timeList) {
		mTabName = tabName;
		mComplex = complex;
		mTempo = tempo;
		mSignatures = signatures;
		mTimeList = timeList;		
	}

	public static Tab parseTabFromJSON(JSONObject jsonTab) throws JSONException {
		String jsonTabName = jsonTab.getString("name");
		boolean jsonComplex = jsonTab.getBoolean("complex");
		int jsonTempo = jsonTab.getInt("tempo");
		JSONArray jsonSignatures;
		try {
			jsonSignatures = jsonTab.getJSONArray("signatures");
		} catch (JSONException e) {
			jsonSignatures = new JSONArray();
		}
		
		JSONArray jsonTimeList = jsonTab.getJSONArray("partition");
		
		List<String> parsedSignatures = new ArrayList<String>();
		List<Time> parsedTimeList = new ArrayList<Time>();
		
		for (int i = 0; i < jsonSignatures.length(); i++) {
			parsedSignatures.add(jsonSignatures.getString(i));
		}
		for (int i = 0; i < jsonTimeList.length(); i++) {
			Time jsonTime = Time.parseTimeFromJson(jsonTimeList.getJSONObject(i));
			parsedTimeList.add(jsonTime);
		}
		return new Tab(jsonTabName, jsonComplex, jsonTempo, parsedSignatures, parsedTimeList);
	}
	
	/**
	 * This method is used to initialise the timeMap.
	 * The purpose of this method is to synchronise the positions in the map
	 *  with the position of the first time in TablatureView
	 * @param firstPos the position of the first time in the TablatureView
	 */
	public void initTimeMap(int firstPos){
		float timePos = firstPos; 							//Position of the time (initial value equal to the position of the first time)
		System.out.println("Filling timeMap !");
		mTimeMap.put(timePos, mTimeList.get(0)); 			//init the first item of the map
		for (int i = 1; i < mTimeList.size(); i++) {
			double dur = Duration.valueOf(mTimeList.get(i-1).getDuration()).getDuration();
			timePos += dur*pace;							//Position of the current time is the pos. of the previous time + the distance between them (dur*pace)
			mTimeMap.put(Float.valueOf((float) timePos), mTimeList.get(i));
		}
	}

	public String getTabName() {
		return mTabName;
	}

	public int length() {
		return mTimeList.size();
	}

	public boolean isComplex() {
		return mComplex;
	}

	public int getTempo() {
		return mTempo;
	}

	public String getSignatures(int index) {
		return mSignatures.get(index);
	}

	public Time getTime(int index) {
		return mTimeList.get(index);
	}
	
	public Time getTimeAt(float time) {
		return mTimeMap.get(Float.valueOf(time));
	}
	
	public boolean timeMapContains(float key) {
		return mTimeMap.containsKey(key);
	}
}
