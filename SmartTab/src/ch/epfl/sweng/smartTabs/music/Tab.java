package ch.epfl.sweng.smartTabs.music;

import java.util.List;

import org.json.JSONObject;

/**
 *@author chrisgaubla
 */
public class Tab {
	private final String tabName;
	private final List<Time> timeList;
	
	private Tab(String aTabName, List<Time> aTimeList){
		this.tabName = aTabName;
		this.timeList = aTimeList;
	}
	
	public static Tab parseTabFromJSON(JSONObject jsonTab){
		return null;
	}
	
	
	
	public String getTabName() {
		return tabName;
	}
	public Time getTime(int index){
		return timeList.get(index);
	}
	public int length(){
		return timeList.size();
	}

}
