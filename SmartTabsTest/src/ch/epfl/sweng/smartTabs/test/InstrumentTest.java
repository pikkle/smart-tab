package ch.epfl.sweng.smartTabs.test;

import android.test.AndroidTestCase;
import ch.epfl.sweng.smartTabs.music.Instrument;

/**
 * @author fatonramadani
 * Test for the instrument enumeration
 */
public class InstrumentTest extends AndroidTestCase{
	
	private Instrument guitar = Instrument.GUITAR;
	private Instrument ukelele = Instrument.UKELELE;
	private Instrument bass = Instrument.BASS;
	
	public void numOfStringTestGuitar(){
		assertEquals("Mismatched number of strings", 6, guitar.getNumOfStrings());
	}
	
	public void numOfStringTestUkelele(){
		assertEquals("Mismatched number of strings", 6, ukelele.getNumOfStrings());
	}
	
	public void numOfStringTestBass(){
		assertEquals("Mismatched number of strings", 6, bass.getNumOfStrings());
	}
}