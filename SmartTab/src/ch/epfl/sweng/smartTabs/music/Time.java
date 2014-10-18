package ch.epfl.sweng.smartTabs.music;

/**
 * @author chrisgaubla
 *
 */
public class Time {
	
	private final double duration;
	private final String[] notes;
	private final int mesure;
	private final boolean ternary;
	private final int step;
	
	public Time(String[] someNotes, double aDuration, int aMesure, boolean isTernary, int aStep){
		this.notes = someNotes;
		this.duration = aDuration;
		this.mesure = aMesure;
		this.ternary = isTernary;
		this.step = aStep;
	}
	public String getNote(int string){
		return notes[string];
	}
	public double getDuration(){
		return duration;
	}
	public int getMesure() {
		return mesure;
	}
	public boolean isTernary() {
		return ternary;
	} 
	public int getStep(){
		return step;
	}
	
	
	
	
	
	

}