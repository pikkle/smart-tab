package ch.epfl.sweng.smartTabs.music;

/**
 * @author chrisgaubla
 *
 */
public class Time {
	public enum Duration {
		WHOLE, HALF, QUARTER, EIGHT, SIXTEENTH
	}
	private final Duration duration;
	private final String[] notes;
	
	public Time(String[] someNotes, Duration aDuration){
		this.notes = someNotes;
		this.duration = aDuration;
	}
	public String getNote(int string){
		return notes[string];
	}
	public Duration getDuration(){
		return duration;
	} 
	
	
	
	
	
	

}
