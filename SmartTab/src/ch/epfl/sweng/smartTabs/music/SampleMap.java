package ch.epfl.sweng.smartTabs.music;

import android.content.Context;
import android.media.SoundPool;
import ch.epfl.sweng.smartTabs.R;

/**
 * @author imani92
 * Ismail Imani
 */
public class SampleMap {
	private final int strings = 6;
	private final int frets = 20;

	private int [][] noteMap = new int[strings][frets];

	public SampleMap(Context a, SoundPool pool) {

		noteMap[0][0] = pool.load(a, R.raw.e3, 1);		//E3
		noteMap[0][12] = pool.load(a, R.raw.e4, 1);		//E4
		noteMap[1][5] = getSampleId(0, 0);				//E3
		noteMap[2][9] = getSampleId(0, 0);				//E3
		noteMap[3][2] = pool.load(a, R.raw.e2, 1);		//E2
		noteMap[4][7]  = getSampleId(3, 2);				//E2
		noteMap[5][12] = getSampleId(3, 2);				//E2
		noteMap[5][0] = pool.load(a, R.raw.e1, 1);		//E1

		noteMap[0][1] = pool.load(a, R.raw.f3, 1);		//F3
		noteMap[0][2] = pool.load(a, R.raw.fd3, 1);		//F#3
		noteMap[0][13] = pool.load(a, R.raw.f4, 1);		//F4
		noteMap[0][14] = pool.load(a, R.raw.fd4, 1);	//F#4
		noteMap[1][6] = getSampleId(0, 1);				//F3
		noteMap[1][7] = getSampleId(0, 2);				//F#3
		noteMap[2][10] = getSampleId(0, 1);				//F3
		noteMap[2][11] = getSampleId(0, 2);				//F#3
		noteMap[3][3] = pool.load(a, R.raw.f2, 1);		//F2
		noteMap[3][4] = pool.load(a, R.raw.fd2, 1);		//F#2
		noteMap[4][8] = getSampleId(3, 3);				//F2
		noteMap[4][9] = getSampleId(3, 4);				//F#2
		noteMap[5][1] = pool.load(a, R.raw.f1, 1);		//F1
		noteMap[5][2] = pool.load(a, R.raw.fd1, 1);		//F#1
		noteMap[5][13] = getSampleId(3, 3);				//F2
		noteMap[5][14] = getSampleId(3, 4);				//F#2


		noteMap[0][3] = pool.load(a, R.raw.g3, 1);		//G3
		noteMap[0][4] = pool.load(a, R.raw.gd3, 1);		//G#3
		noteMap[1][8] = getSampleId(0, 3);				//G3
		noteMap[1][9] = getSampleId(0, 4);				//G#3
		noteMap[2][0] = pool.load(a, R.raw.g2, 1);		//G2
		noteMap[2][1] = pool.load(a, R.raw.gd2, 1);		//G#2
		noteMap[2][12] = getSampleId(0, 3);				//G3
		noteMap[2][13] = getSampleId(0, 4);				//G#3
		noteMap[3][5] = getSampleId(2, 0);				//G2
		noteMap[3][6] = getSampleId(2, 1);				//G#2
		noteMap[4][10] = getSampleId(2, 0);				//G2
		noteMap[4][11] = getSampleId(2, 1);				//G#2
		noteMap[5][3] = pool.load(a, R.raw.g1, 1);		//G1
		noteMap[5][4] = pool.load(a, R.raw.gd1, 1);		//G#1


		noteMap[0][5] = pool.load(a, R.raw.a3, 1);		//A3
		noteMap[0][6] = pool.load(a, R.raw.ad3, 1);		//A#3
		noteMap[1][10] = getSampleId(0, 5);				//A3
		noteMap[1][11] = getSampleId(0, 6);				//A#3
		noteMap[2][2] = pool.load(a, R.raw.a2, 1);		//A2
		noteMap[2][3] = pool.load(a, R.raw.ad2, 1);		//A#2
		noteMap[3][7] = getSampleId(2, 2);				//A2
		noteMap[3][8] = getSampleId(2, 3);				//A#2
		noteMap[4][12] = getSampleId(2, 2);				//A2
		noteMap[4][13] = getSampleId(2, 3);				//A#2
		noteMap[4][0] = pool.load(a, R.raw.a1, 1);		//A1
		noteMap[4][1] = pool.load(a, R.raw.ad1, 1);		//A#1
		noteMap[5][5] = getSampleId(4, 0);				//A1
		noteMap[5][6] = getSampleId(4, 1);				//A#1
		
		noteMap[0][7] = pool.load(a, R.raw.b3, 1);		//B3
		noteMap[1][0] = pool.load(a, R.raw.b2, 1);		//B2
		noteMap[1][12] = getSampleId(0, 7);				//B3
		noteMap[2][4] = getSampleId(1, 0);				//B2
		noteMap[3][9] = getSampleId(1, 0);				//B2
		noteMap[4][2] = pool.load(a, R.raw.b1, 1);		//B1
		noteMap[5][7] = getSampleId(4, 2);				//B1

		noteMap[0][8] = pool.load(a, R.raw.c4, 1);		//C4
		noteMap[0][9] = pool.load(a, R.raw.cd4, 1);		//C#4
		noteMap[1][1] = pool.load(a, R.raw.c3, 1);		//C3
		noteMap[1][2] = pool.load(a, R.raw.cd3, 1);		//C#3
		noteMap[1][13] = getSampleId(0, 8);				//C4
		noteMap[1][14] = getSampleId(0, 9);				//C#4
		noteMap[2][5] = getSampleId(1, 1);				//C3
		noteMap[2][6] = getSampleId(1, 2);				//C#3
		noteMap[3][10] = getSampleId(1, 1);				//C3
		noteMap[3][11] = getSampleId(1, 2);				//C#3
		noteMap[4][3] = pool.load(a, R.raw.c2, 1);		//C2
		noteMap[4][4] = pool.load(a, R.raw.cd2, 1);		//C#2
		noteMap[5][8] = getSampleId(4, 3);				//C2
		noteMap[5][9] = getSampleId(4, 4);				//C#2

		noteMap[0][10] = pool.load(a, R.raw.d4, 1);		//D4
		noteMap[0][11] = pool.load(a, R.raw.dd4, 1);	//D#4
		noteMap[1][3] = pool.load(a, R.raw.d3, 1);		//D3
		noteMap[1][4] = pool.load(a, R.raw.dd3, 1);		//D#3
		noteMap[2][7] = getSampleId(1, 3);				//D3
		noteMap[2][8] = getSampleId(1, 4);				//D#3
		noteMap[3][0] = pool.load(a, R.raw.d2, 1);		//D2
		noteMap[3][1] = pool.load(a, R.raw.dd2, 1);		//D#2
		noteMap[3][12] = getSampleId(1, 3);				//D3
		noteMap[3][13] = getSampleId(1, 4);				//D#3
		noteMap[4][5] = getSampleId(3, 0);				//D2
		noteMap[4][6] = getSampleId(3, 1);				//D#2
		noteMap[5][10] = getSampleId(3, 0);				//D2
		noteMap[5][11] = getSampleId(3, 1);				//D#2
	}

	/**
	 * This method returns the soundId of
	 * the sample corresponding to the note
	 *  played in the tab.
	 * @param string index of the string
	 * @param fret index of the fret
	 * @return soundId of the sample to play
	 */
	public final int getSampleId(final int string, final int fret) {
		return noteMap[string][fret];
	}
	
	public final int[][] fillMap(){
		int[][] sampleMap = new int[strings][frets];
		
		/*
		 * Algorithm to auto-fill the sample map	
		 *
		*/
		
		return sampleMap;
	}
}
