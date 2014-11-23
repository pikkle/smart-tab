package ch.epfl.sweng.smartTabs.test;

import ch.epfl.sweng.smartTabs.music.Instrument;
import junit.framework.TestCase;

public class InstrumentTest extends TestCase{
	
	private Instrument guitar = Instrument.GUITAR;
	
	public void numOfStringTest(){
		assertEquals("Mismatched number of strings", 6, guitar.getNumOfStrings());
	}
	
}