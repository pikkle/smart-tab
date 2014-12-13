package ch.epfl.sweng.smartTabs.music;

/**
 * @author Faton Ramadani The height represents the usual height, in order from
 *         Do to Si, with half height ( C#).
 */
public enum Height {
    C(0), CD(1), D(2), DD(3), E(4), F(5), FD(6), G(7), GD(8), A(9), AD(10), B(11);

    private static final int MAXHALFTONE = 12;
    private final int mIndex;

    private Height(int index) {
        this.mIndex = index;
    }

    public int getIndex() {
        return mIndex;
    }

    public static int getMax() {
        return MAXHALFTONE;
    }

    /**
     * @param i
     * @return The height indexed at i
     */
    public static Height get(int i) {
        if (i < 0 || i > MAXHALFTONE) {
            throw new ArrayIndexOutOfBoundsException();
        }
        return Height.values()[i];
    }
}