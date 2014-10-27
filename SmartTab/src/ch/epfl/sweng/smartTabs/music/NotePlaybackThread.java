package ch.epfl.sweng.smartTabs.music;

import android.media.SoundPool;

/**
 * @author imani92
 * Ismail Imani
 */

public class NotePlaybackThread extends Thread {
	private SoundPool mPool;
	private SampleMap mSamples;
	
	public NotePlaybackThread(SoundPool pool, SampleMap samples) {
		mPool = pool;
		mSamples = samples;
	}
	
	public void playNote(Time time) {
		for (int i = 0; i < (Instrument.GUITAR).getNumOfStrings(); i++) {
			if (!time.getNote(i).equals("")) {
				mPool.play(mSamples.getSampleId(i, 
						Integer.parseInt(time.getNote(i))), 1, 1, 1, 0, 1);
			}
		}
	}

}
