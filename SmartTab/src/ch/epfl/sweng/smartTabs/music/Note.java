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
	
	public Note addHalfTon(int delta){
		// Represente the case when we need to change the octave
		int index = this.myHeight.getIndex();
		int deltaOct = index % 11;
		Height height =  this.myHeight.get(index + delta);
		return new Note(deltaOct, height, this.myDuration);	
	}
}
