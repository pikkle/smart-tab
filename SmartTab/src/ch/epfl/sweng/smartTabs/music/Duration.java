package ch.epfl.sweng.smartTabs.music;

/**
 * @author Faton Ramadani The duration enumeration represents the possible
 *         duration of a note.
 */
public enum Duration {
    DoubleCroche(0.25), Croche(0.5), Noire(1), Blanche(2), Ronde(4);

    private double mDuration;

    private Duration(double duration) {
        mDuration = duration;
    }

    /**
     * @return The integer that represents the duration
     */
    public double getDuration() {
        return mDuration;
    }
}