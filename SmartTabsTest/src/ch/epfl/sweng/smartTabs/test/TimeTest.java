package ch.epfl.sweng.smartTabs.test;

import ch.epfl.sweng.smartTabs.music.Time;
import android.test.AndroidTestCase;

/**
 * @author rphkhr
 * Raphael El-Khoury
 * 212765
 */
public class TimeTest extends AndroidTestCase{
	String [] notes = {"7", "", "0"};
	Time time = new Time(notes, null, "5" , 1, true, 2);
	
	public void testNote(){
	assertEquals(notes[0], time.getNote(0));
	assertEquals("", time.getNote(1));
	assertEquals("0", time.getNote(2));
	}
	
	public void testDuration(){
		assertEquals(5, time.getDuration());
	}
	
	public void testTernary(){
		assertEquals(true, time.isTernary());
	}
	
	public void testStep(){
		assertEquals(2, time.getStep());
	}
	
	public void testParsing(){
		
	}
	
	public void testMesure(){
		assertEquals(1, time.getMesure());
	}
}