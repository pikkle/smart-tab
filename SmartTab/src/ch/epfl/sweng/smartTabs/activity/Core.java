package ch.epfl.sweng.smartTabs.activity;

import ch.epfl.sweng.smartTabs.gfx.Metronome;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.SampleMap;
import ch.epfl.sweng.smartTabs.music.Time;
import android.app.Activity;
import android.os.SystemClock;
import android.view.View;

public class Core implements Runnable {

    private Activity myCaller;

    private long timeZero = 0l;
    private int myTempo;
    private int myPace;
    private int myDelayInNanos;
    private double myDelay;
    private static final int millisInMin = 60000;
    private static final long nanosInMin = 60000000000l;

    public Core(Activity caller, int tempo, int pace) {
        myTempo = tempo;
        myPace = pace;
        myCaller = caller;
        myDelayInNanos = computeDelayInNanoS(myTempo, myPace, millisInMin);
    }

    @Override
    public void run() {
    }

    /**
     * This method computes the delay for which the thread has to sleep
     * 
     * @param tempo
     * @param pace
     * @param speed
     * @param millisinmin
     * @return the delay
     */

    private int computeDelayInNanoS(int tempo, int pace, int millisinmin) {
        double tmp = (nanosInMin / ((double) tempo * (double) pace));
        return (int) (tmp);
    }

}
