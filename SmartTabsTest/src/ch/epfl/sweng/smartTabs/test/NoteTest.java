package ch.epfl.sweng.smartTabs.test;

import android.test.AndroidTestCase;
import ch.epfl.sweng.smartTabs.music.Duration;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Note;
import junit.framework.TestCase;

/**
 * @author Faton Ramadani
 * Test for Note class
 */
public class NoteTest extends AndroidTestCase{
	private Height nullHeight = null;
	private Duration nullDuration = null;
	private Note test3 = new Note(Height.C, 1, Duration.Noire);
	private Note test4 = new Note(Height.C, 1);
	@SuppressWarnings("unused")
	private Note test1;
	@SuppressWarnings("unused")
	private Note test2;
	
	/**
	 * Test whether the constructor catches null arguments.
	 */
	public void noteConstructorTest() {
		try {
			test1 = new Note(nullHeight,0,nullDuration);
			fail("Should have thrown an NullPointerException");
		} catch (NullPointerException e) {
			// success
		}
	}
	
	/**
	 * Test whether the constructor catches null arguments.
	 */
	public void noteConstructorWithoutDurationTest() {
		try {
			test2 = new Note(nullHeight,0);
			fail("Should have thrown an NullPointerException");
		} catch (NullPointerException e) {
			// success
		}
	}
	
	
	
	/**
	 * Check whether the two constructor set the correct duration.
	 */
	public void durationTest () {
		assertEquals("Mismatched duration", test3.getDuration(), test4.getDuration());
	}
	
	/**
	 * Check whether the two constructor set the correct height.
	 */
	public void heightTest () {
		assertEquals("Mismatched height", test3.getHeight(), test4.getHeight());
	}
	
	/**
	 * Check whether the two constructor set the correct octave.
	 */
	public void octaveTest () {
		assertEquals("Mismatched octave", test3.getOctave(), test4.getOctave());
	}
	
	
	/**
	 * Test, adding tone in various manners.
	 */
	public void addHalfTones() {
		assertEquals("Mismatched notes", test3.addHalfTones(2), new Note(Height.D, 1, Duration.Noire));
		assertEquals(
				"Mismatched notes", 
				(new Note(Height.B, 1, Duration.Noire)).addHalfTones(1), 
				new Note(Height.C, 2, Duration.Noire)
		);
		
	}
}  