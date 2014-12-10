package ch.epfl.sweng.smartTabs.music;

import java.io.Serializable;

/**
 * @author Faton Ramadani A note represents the a musical note, with its height
 *         and octave
 */

public class Note implements Serializable {

    private static final long serialVersionUID = 1L;
    private final int myOctave;
    private final Height myHeight;
    private final Duration myDuration;

    public Note(Height height, int octave, Duration duration) {
        if (height == null) {
            throw new NullPointerException();
        }
        if (duration == null) {
            throw new NullPointerException();
        }

        myOctave = octave;
        myHeight = height;
        myDuration = duration;
    }

    public Note(Height height, int octave) {
        if (height == null) {
            throw new NullPointerException();
        }
        myOctave = octave;
        myHeight = height;
        // by default
        myDuration = Duration.Noire;
    }

    public Note addHalfTones(int delta) {
        int index = this.myHeight.getIndex();
        int newOct = myOctave + ((index + delta) / Height.getMax());
        Height height = Height.get((index + delta) % Height.getMax());
        return new Note(height, newOct);
    }

    public Height getHeight() {
        return myHeight;
    }

    public int getOctave() {
        return myOctave;
    }

    public Duration getDuration() {
        return myDuration;
    }

    public String toString() {
        return myHeight.name().toLowerCase() + myOctave;
    }
}
