package ch.epfl.sweng.smartTabs.music;

import java.util.ArrayList;

/**
 * @author imani92
 * Ismail Imani
 */

public class TabGenerationThread extends Thread {
	private final Tab mTab;
	private ArrayList<Time> mTimes;
	private final int numNotes = 18;


	public TabGenerationThread(Tab tab, ArrayList<Time> times) {
		mTab = tab;
		mTimes = times;
	}
	
	public void run() {
		for (int i = 0; i < numNotes; i++) {
			mTimes.add(mTab.getTime(i));
		}
	}
}
