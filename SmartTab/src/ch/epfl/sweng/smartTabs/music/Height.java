package ch.epfl.sweng.smartTabs.music;

/**
 * @author Faton Ramadani
 */
public enum Height {
	C (1), CD (2), D (3), DD (4), E (5), F (6), FD(7) , G (8), GD (9), A (10), AD (11), B (12);

	private final static int MAXHALFTON = 12;
	private final int myIndex;
	Height(int index) {
		this.myIndex = index;
	}
	
	public int getIndex() {
		return myIndex;
	}
	
	public static int getMax() {
		return MAXHALFTON;
	}

	public Height get(int i) {
		if (i < 0 || i > MAXHALFTON) {
			throw new IllegalArgumentException();
		}
		return Height.values()[i];
	}
}