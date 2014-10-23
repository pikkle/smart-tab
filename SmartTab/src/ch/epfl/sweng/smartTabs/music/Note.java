package ch.epfl.sweng.smartTabs.music;

/**
 * @author Faton Ramadani
 */
public class Note {
	private final int myOctave;
	private final Height myHeight;
	private final Duration myDuration;
	
	public Note(int octave, Height height, Duration duration) {
		myOctave = octave;
		myHeight = height;
		myDuration = duration;
	}
}
