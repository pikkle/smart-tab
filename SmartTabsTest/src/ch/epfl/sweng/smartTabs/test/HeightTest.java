package ch.epfl.sweng.smartTabs.test;

import android.test.AndroidTestCase;
import ch.epfl.sweng.smartTabs.music.Height;
import junit.framework.TestCase;

public class HeightTest extends AndroidTestCase{
	private Height nullHeight = null;
	
	public void nullHeightTest() {
		try {
			nullHeight.getIndex();
			fail("Should have thrown an NullPointer Exception ");
		} catch (NullPointerException e) {
			// success
		}
	}
	
	public void getMaxTest () {
		assertEquals("Mismatched max", 12, Height.getMax());
	}
	
	public void getNegativeIndexTest() {
		try {
			Height.get(-1);
			fail("Should have thrown an ArrayIndexOutOfBoundsException ");
		} catch (ArrayIndexOutOfBoundsException e) {
			// success
		}
	}
	
	public void getBigIndexTest() {
		try {
			Height.get(Height.getMax() + 1);
			fail("Should have thrown an ArrayIndexOutOfBoundsException ");
		} catch (ArrayIndexOutOfBoundsException e) {
			// success
		}
	}
	
}