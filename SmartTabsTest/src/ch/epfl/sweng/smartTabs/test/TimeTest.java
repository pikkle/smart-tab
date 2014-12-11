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
	Time time = new Time(notes, null, "5" , 1);
	
	public void testNote(){
	assertEquals(notes[0], time.getNote(0));
	assertEquals("", time.getNote(1));
	assertEquals("0", time.getNote(2));
	}
	
	public void testMesure(){
		assertEquals(1, time.getMesure());
	}
}