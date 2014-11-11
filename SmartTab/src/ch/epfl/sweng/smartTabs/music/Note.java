package ch.epfl.sweng.smartTabs.music;

import android.annotation.SuppressLint;

/**
 * @author Faton Ramadani
 * A note represents the a musical note, with its height and octave
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
		myDuration = Duration.Noir;
	}
	
	public Note addHalfTones(int delta) {
		int index = this.myHeight.getIndex();
		int newOct = myOctave + ((index + delta) / Height.getMax());
		Height height =  this.myHeight.get((index + delta) % Height.getMax());		
		return new Note(newOct, height);	
	}
	
	public Height getHeight() {
		return myHeight;
	}
	
	public int getOctave() {
		return myOctave;
	}
	// TODO : fix this
	@SuppressLint("DefaultLocale") 
	public String toString() { 
		return myHeight.name().toLowerCase()+myOctave;
	}
}
