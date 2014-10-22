package ch.epfl.sweng.smartTabs.music;

/**
 * @author Faton Ramadani
 * Squelette de la partie musical
 */
public enum Duration {
	DoubleCroche (0.25),
	Croche (0.5),
	Noire (1),
	Blanche (2),
	Ronde (4);
	
	private double myDuration = 0D;
	   
	private Duration(double duration) {	
		myDuration = duration;
	}
	
	public double getDuration(Duration d) {
		return d.myDuration;
	}
}
