package ch.epfl.sweng.smartTabs.test;

import ch.epfl.sweng.smartTabs.music.Instrument;
import junit.framework.TestCase;

/**
 * @author fatonramadani
 * Test for the instrument enumeration
 */
public class InstrumentTest extends TestCase{
	
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