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
	
	public Note(int octave, Height height) {
		myOctave = octave;
		myHeight = height;
		// by default
		myDuration = Duration.Noire;
	}
	
	public Note addHalfTone(int delta) {
		int index = this.myHeight.getIndex();
		int newOct = myOctave + ((index + delta) / Height.getMax());
		Height height =  this.myHeight.get((index + delta) % Height.getMax());		
		return new Note(newOct, height);	
	}
	
}