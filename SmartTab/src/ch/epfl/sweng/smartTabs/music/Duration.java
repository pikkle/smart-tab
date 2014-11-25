package ch.epfl.sweng.smartTabs.music;

/**
 * @author Faton Ramadani The duration enumeration represents the possible
 *         duration of a note.
 */
public enum Duration {
    DoubleCroche(0.25), Croche(0.5), Noir(1), Blanche(2), Ronde(4);

    private double myDuration;

    private Duration(double duration) {
        myDuration = duration;
    }

    /**
     * @return The integer that represents the duration
     */
    public double getDuration() {
        // In case, we call the getDuration with a null
        if (this == null) {
            throw new NullPointerException();
        }
        return myDuration;
    }
}