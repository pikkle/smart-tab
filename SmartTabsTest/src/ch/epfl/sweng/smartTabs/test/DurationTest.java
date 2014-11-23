package ch.epfl.sweng.smartTabs.test;


import junit.framework.TestCase;
import ch.epfl.sweng.smartTabs.music.Duration;

public class DurationTest extends TestCase{
  private Duration blanche = Duration.Blanche;
  
  public void durationMatchTest(){
	  assertEquals("Mismatched duraction", Duration.Blanche, blanche.getDuration());
  }
  
  private Duration nullDuration = null;
  
  public void nullDurationTest(){
	try {
		nullDuration.getDuration();
		fail("Should have thrown an NullPointer Exception ");
	} catch (NullPointerException e) {
		// success
	}
  }  
}