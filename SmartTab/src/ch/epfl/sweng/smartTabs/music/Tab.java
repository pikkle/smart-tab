package ch.epfl.sweng.smartTabs.music;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * @author chrisgaubla
 */
public final class Tab implements Serializable{

	private static final long serialVersionUID = 1L;
	
	private final String mTabName;
	private final boolean mComplex;
	private final int mTempo;
	private final List<String> mSignatures;
	private final List<Time> mTimeList;

	private Tab(String tabName, boolean complex, int tempo,
			List<String> signatures, List<Time> timeList) {
		mTabName = tabName;
		mComplex = complex;
		mTempo = tempo;
		mSignatures = signatures;
		mTimeList = timeList;
	}

	public static Tab parseTabFromJSON(JSONObject jsonTab) throws JSONException {
		String jsonTabName = jsonTab.getString("tabName");
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
			parsedSignatures.set(i, jsonSignatures.getString(i));
		}
		for (int i = 0; i < jsonTimeList.length(); i++) {
			Time jsonTime = Time.parseTimeFromJson(jsonTimeList
					.getJSONObject(i));
			parsedTimeList.set(i, jsonTime);
		}
		return new Tab(jsonTabName, jsonComplex, jsonTempo, parsedSignatures,
				parsedTimeList);
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

}
