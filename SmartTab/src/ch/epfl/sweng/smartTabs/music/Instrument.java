package ch.epfl.sweng.smartTabs.music;

/**
 * @author Faton Ramadani
 * Instrument is the set of all instruments handled.
 */
public enum Instrument {
	GUITAR(6), UKELELE(4), BASS(4);
	
	private int myStrings;
	   
	private Instrument(int strings) {	
		myStrings = strings;
	}
	
	public double getNumOfStrings() {
		return myStrings;
	}
}