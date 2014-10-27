package ch.epfl.sweng.smartTabs.music;

import java.util.ArrayList;

/**
 * @author imani92
 * Ismail Imani
 */

public class TabGenerationThread extends Thread {
	private final Tab mTab;
	private final ArrayList<Time> mTimes;
	private final int numNotes = 18;


	public TabGenerationThread(final Tab tab, final ArrayList<Time> times) {
		mTab = tab;
		mTimes = times;
	}

	@Override
	public final void run() {
		for (int i = 0; i < numNotes; i++) {
			mTimes.add(mTab.getTime(i));
		}
	}
}
