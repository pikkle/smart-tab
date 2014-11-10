package ch.epfl.sweng.smartTabs.music;

import java.util.ArrayList;

/**
 * @author imani92
 * Ismail Imani
 */

public class TimesArrayGenerationThread extends Thread {
	private final Tab mTab;
	private final ArrayList<Time> mTimes;
	private final int numNotes = 18;
	private final ArrayList<Integer> mPosX;
	private final int stdPace = 120;


	public TimesArrayGenerationThread(final Tab tab, final ArrayList<Time> times, final ArrayList<Integer> posX) {
		mTab = tab;
		mTimes = times;
		mPosX = posX;
	}

	@Override
	public final void run() {
		for (int i = 0; i < numNotes; i++) {
			
			mTimes.add(mTab.getTime(i));
			if(i==0){
				mPosX.add(0);
			} else {
				double noteDuration = Duration.valueOf(mTab.getTime(i).getDuration()).getDuration();
				mPosX.add((int) (mPosX.get(i-1) - stdPace*noteDuration));				
			}
		}
	}
}
