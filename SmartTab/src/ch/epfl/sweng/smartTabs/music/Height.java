package ch.epfl.sweng.smartTabs.music;

/**
 * @author Faton Ramadani
 * Squelette de la partie musical
 */
public enum Height {
	// D est le dièze
	C (1), CD (2), D (3), DD (4), E (5), F (6), G (7), GD (8), A (9), AD (10), B (11);

	private final int index;
	Height(int index) {
		this.index = index;
	}
	
	public int getIndex() {
		return index;
	}

	public Height get(int i) {
		return new Height(i);
	}
}
