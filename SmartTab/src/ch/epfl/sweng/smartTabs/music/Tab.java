package ch.epfl.sweng.smartTabs.music;

import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 *@author chrisgaubla
 */
public class Tab {
	private final String tabName;
	private final boolean complex;
	private final int tempo;
	private final List<String> signatures;
	private final List<Time> timeList;
	
	private Tab(String aTabName,boolean isComplex, int aTempo, List<String> someSignatures, List<Time> aTimeList){
		this.tabName = aTabName;
		this.complex = isComplex;
		this.tempo = aTempo;
		this.signatures = someSignatures;
		this.timeList = aTimeList;
	}
	
	public static Tab parseTabFromJSON(JSONObject jsonTab) throws JSONException{
		String jsonTabName = jsonTab.getString("tabName");
		boolean jsonComplex = jsonTab.getBoolean("complex");
		int jsonTempo = jsonTab.getInt("tempo");
		JSONArray jsonSignatures = jsonTab.getJSONArray("signatures");
		JSONArray jsonTimeList = jsonTab.getJSONArray("partition");
		
		
		return null;
	}
	
	
	
	public String getTabName() {
		return tabName;
	}
	public int length(){
		return timeList.size();
	}

	public boolean isComplex() {
		return complex;
	}

	public int getTempo() {
		return tempo;
	}

	public String getSignatures(int index) {
		return signatures.get(index);
	}
	public Time getTime(int index){
		return timeList.get(index);
	}

}