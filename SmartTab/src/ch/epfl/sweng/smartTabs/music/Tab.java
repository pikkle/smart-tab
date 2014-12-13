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
    private final static int PACE = 200;

    private final String mTabName;
    private final String mTabArtist;
    private final int mTempo;
    private final List<Time> mTimeList;
    private final HashMap<Float, Time> mTimeMap = new HashMap<Float, Time>();

    private Tab(String tabName, String tabArtist, int tempo, List<Time> timeList) {
        mTabName = tabName;
        mTabArtist = tabArtist;
        mTempo = tempo;
        mTimeList = timeList;
    }

    public static Tab parseTabFromJSON(JSONObject jsonTab) throws JSONException {
        try {
            String jsonTabName = jsonTab.getString("name");
            String jsonTabArtist = jsonTab.getString("artist");
            int jsonTempo = jsonTab.getInt("tempo");

            JSONArray jsonTimeList = jsonTab.getJSONArray("partition");

            List<Time> parsedTimeList = new ArrayList<Time>();

            for (int i = 0; i < jsonTimeList.length(); i++) {
                Time jsonTime = Time.parseTimeFromJson(jsonTimeList.getJSONObject(i));
                if (!jsonTime.isEmpty()) {
                    parsedTimeList.add(jsonTime);
                }
            }
            return new Tab(jsonTabName, jsonTabArtist, jsonTempo, parsedTimeList);
        } catch (JSONException e) {
            throw new JSONException("Bad Json");
        }
    }

    /**
     * This method is used to initialise the timeMap. The purpose of this method
     * is to synchronise the positions in the map with the position of the first
     * time in TablatureView
     * 
     * @param firstPos
     *            the position of the first time in the TablatureView
     */
    public void initTimeMap(int firstPos) {
        // Position of the time (initial value equal to the position of the
        // first time)
        float timePos = firstPos;
        int ptr = 0;
        while (mTimeList.get(ptr).isEmpty()) {
            ptr++;
        }
        mTimeMap.put(timePos, mTimeList.get(ptr++)); // init the first item of
                                                     // the map
        for (int i = ptr; i < mTimeList.size(); i++) {
            if (!mTimeList.get(i).isEmpty()) {
                double dur = Duration.valueOf(mTimeList.get(i - 1).getDuration()).getDuration();
                // Position of the current time is the pos. of the previous time
                // +
                // the distance between them (dur*pace)
                timePos += dur * PACE;
                mTimeMap.put(timePos, mTimeList.get(i));
            }
        }
    }

    public String getTabName() {
        return mTabName;
    }

    public String getTabArtist() {
        return mTabArtist;
    }

    public int length() {
        return mTimeList.size();
    }

    public int getTempo() {
        return mTempo;
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

    public double getTotalDuration() {
        double total = 0;
        for (Time t : mTimeList) {
            total = total + Duration.valueOf(t.getDuration()).getDuration();
        }
        return total * PACE;

    }
}
