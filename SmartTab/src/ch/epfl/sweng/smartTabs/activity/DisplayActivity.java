package ch.epfl.sweng.smartTabs.activity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.v4.view.MotionEventCompat;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.Toast;
import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.gfx.CursorView;
import ch.epfl.sweng.smartTabs.gfx.FooterView;
import ch.epfl.sweng.smartTabs.gfx.HeaderView;
import ch.epfl.sweng.smartTabs.gfx.MusicSheetView;
import ch.epfl.sweng.smartTabs.gfx.TablatureView;
import ch.epfl.sweng.smartTabs.music.Height;
import ch.epfl.sweng.smartTabs.music.Instrument;
import ch.epfl.sweng.smartTabs.music.Note;
import ch.epfl.sweng.smartTabs.music.SampleMap;
import ch.epfl.sweng.smartTabs.music.Tab;
import ch.epfl.sweng.smartTabs.music.Time;

/**
 * @author fatonramadani
 * 
 */
public class DisplayActivity extends Activity {

    private static final String PREFS_NAME = "MyPrefsFile";

    private static final int PACE = 200;
    private static final double MILLISINMIN = 60000.0;
    private static final int OFFSET = 50;
    private static final int NBOFSREAMS = 50;
    private static final int WEIGHT3 = 3;
    private static final int WEIGHT7 = 7;
    private static final int WEIGHT8 = 8;
    private static final int MINIMALSCROLL = 30;
    private static final int PERCENT = 100;

    private static final int SHIFTAFTERSCROLL = 4 * OFFSET;
    private static final long SAMPLE_TIME = 1000;
    private static final int DELAY = 2000;

    private HeaderView mHeaderView;
    private FooterView mFooterView;
    private CursorView mCursorView;
    private LinearLayout mWrapper;
    private LinearLayout mMusicWrapper;
    private FrameLayout mTestWrapper;
    private boolean mRunning;
    private SharedPreferences mSharedPrefs;
    private Tab mTab;

    private boolean mBackPressedOnce = false;
    private SoundPool mPool = new SoundPool(NBOFSREAMS, AudioManager.STREAM_MUSIC, 0);

    private int playingPosition; // Position of the time to play
    // (Intital value corresponds to the future cursor position)

    private double mDefaultDelay;
    private double mDelay;
    private double mSpeedFactor;
    private int dx = 1;
    private TablatureView mTablatureView;
    private MusicSheetView mMusicSheetView;
    private float mLastX;
    private float mNewX;
    private int mTabPosX;
    private boolean mScrolled = false;
    private SampleMap mSampleMap;
    private final Note[] tuning = {new Note(Height.E, 3), new Note(Height.B, 2), new Note(Height.G, 2),
        new Note(Height.D, 2), new Note(Height.A, 1), new Note(Height.E, 1) };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        checkDialog(this);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Intent intent = getIntent();
        mTab = (Tab) intent.getExtras().getSerializable("tab");

        mSpeedFactor = 1;
        mDefaultDelay = computeDelay(mTab.getTempo(), PACE, dx, MILLISINMIN);
        mDelay = mDefaultDelay;

        setContentView(R.layout.activity_display);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);

        mSharedPrefs = getSharedPreferences(PREFS_NAME, 0);

        mWrapper = (LinearLayout) (this.findViewById(R.id.wrapper));
        mMusicWrapper = new LinearLayout(getBaseContext());
        mMusicWrapper.setOrientation(LinearLayout.VERTICAL);
        mTestWrapper = new FrameLayout(getBaseContext());

        mHeaderView = new HeaderView(getBaseContext(), mTab.getTabName(), mTab.getTabArtist());
        mFooterView = new FooterView(getBaseContext(), mSharedPrefs.contains(mTab.getTabName()),
                (int) (mSpeedFactor * PERCENT));
        mTablatureView = new TablatureView(getBaseContext(), mTab, Instrument.GUITAR, PACE);
        mMusicSheetView = new MusicSheetView(getBaseContext(), mTab, PACE);
        mCursorView = new CursorView(getBaseContext());

        playingPosition = mCursorView.getPosX();

        mMusicWrapper.addView(mMusicSheetView, weight(WEIGHT3));
        mMusicWrapper.addView(mTablatureView, weight(WEIGHT7));

        mTestWrapper.addView(mMusicWrapper, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        mTestWrapper.addView(mCursorView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT,
                FrameLayout.LayoutParams.MATCH_PARENT));

        mWrapper.addView(mHeaderView, weight(1));
        mWrapper.addView(mTestWrapper, weight(WEIGHT8));
        mWrapper.addView(mFooterView, weight(1));

        mSampleMap = new SampleMap(getApplicationContext(), mPool, tuning);

        // Basic scrolling
        Thread t = new Thread(new Runnable() {

            @Override
            public void run() {
                while (true) {
                    if (mRunning && !mTablatureView.isTerminated()) {
                        mTablatureView.scrollBy(dx, 0);
                        mMusicSheetView.scrollBy(dx, 0);
                        playingPosition += dx;
                        mHeaderView.computeRatio(playingPosition, PACE);
                        mHeaderView.postInvalidate();

                    }
                    if (mTab.timeMapContains(playingPosition)) {
                        final Time t = mTab.getTimeAt(playingPosition);
                        if (t != null && mRunning) {

                            for (int i = 0; i < (Instrument.GUITAR).getNumOfStrings(); i++) {
                                String fret = t.getNote(i);
                                if (!fret.equals("")) {
                                    int fretNumber = Integer.parseInt(fret);
                                    final Note note = tuning[i].addHalfTones(fretNumber);
                                    final int sampleId = mPool.play(mSampleMap.getSampleId(note), 1, 1, 1, 0, 1);
                                    new Thread(new Runnable() {

                                        @Override
                                        public void run() {
                                            try {
                                                Thread.sleep((long) (SAMPLE_TIME * note.getDuration().getDuration()));
                                            } catch (InterruptedException e) {
                                                // TODO Auto-generated catch
                                                // block
                                                e.printStackTrace();
                                            }
                                            mPool.stop(sampleId);
                                        }
                                    }).start();
                                }
                            }
                        }
                    }

                    try {
                        Thread.sleep((int) mDelay, decimalPart(mDelay));
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            }
        });
        t.start();
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
    private double computeDelay(double tempo, double pace, double speed, double millisinmin) {
        return speed * millisinmin / (tempo * pace);
    }

    private LinearLayout.LayoutParams weight(int i) {
        return new LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, 0, i);
    }

    /**
     * This method computes the decimal part of the delay and returns it as an
     * integer, which is the number of nanosecs the thread has to sleep for.
     * 
     * @param delay
     * @return the number of nanosecs
     */
    private int decimalPart(double delay) {
        return (int) (delay - Math.floor(delay));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        final float x = event.getX();
        final float y = event.getY();

        if (x > mFooterView.getFavPosX() && y > mFooterView.getY()
                && MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) {
            mFooterView.getFav().setIsFav();
            mFooterView.invalidate();
            SharedPreferences.Editor editor = mSharedPrefs.edit();
            if (!mSharedPrefs.contains(mTab.getTabName())) {
                editor.putString(mTab.getTabName(), serialize(mTab));
                editor.commit();
                Toast.makeText(this, "Added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                editor.remove(mTab.getTabName());
                editor.commit();
                Toast.makeText(this, "Removed from favorites", Toast.LENGTH_SHORT).show();
            }
        } else if (x >= mFooterView.getSpeedDownPosX()
                && x <= mFooterView.getSpeedDownPosX() + mFooterView.getSpeedIconWidth() && y >= mFooterView.getY()
                && MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) {
            decSpeed();
            mFooterView.setSpeedFactor((int) (mSpeedFactor * PERCENT));
            mFooterView.invalidate();
            // Toast.makeText(getApplicationContext(), "Speed : " + (int)
            // (mySpeedFactor*100) + " %", Toast.LENGTH_SHORT).show();
        } else if (x >= mFooterView.getSpeedUpPosX()
                && x <= mFooterView.getSpeedUpPosX() + mFooterView.getSpeedIconWidth() && y >= mFooterView.getY()
                && MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) {
            incSpeed();
            mFooterView.setSpeedFactor((int) (mSpeedFactor * PERCENT));
            mFooterView.invalidate();

            // Toast.makeText(getApplicationContext(), "Speed : " + (int)
            // (mySpeedFactor*100) + " %", Toast.LENGTH_SHORT).show();
        } else {
            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_DOWN) {
                mTabPosX = mTablatureView.getScrollX();
                this.mLastX = x;
                mScrolled = false;
            }

            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_MOVE) {
                this.mNewX = x;
                int delta = (int) (mLastX - mNewX);

                if (Math.abs(delta) >= MINIMALSCROLL) {
                    mRunning = false;
                    mScrolled = true;
                    mFooterView.setRunning(false);
                    int newPosX = mTabPosX + delta;
                    if (mLastX != mNewX && newPosX >= 0 && newPosX <= mTablatureView.getEndOfTab()) {
                        mTablatureView.scrollTo(newPosX, 0);
                        mMusicSheetView.scrollTo(newPosX, 0);
                        playingPosition = (int) (mTablatureView.getScrollX() + mCursorView.getX() + SHIFTAFTERSCROLL);
                    }
                }
            }

            if (MotionEventCompat.getActionMasked(event) == MotionEvent.ACTION_UP) {
                if (!mScrolled) {
                    mRunning = !mRunning;
                    mFooterView.playPause();
                }
            }
        }

        return true;
    }

    private void decSpeed() {
        if (mSpeedFactor >= 0.4) {
            mSpeedFactor -= 0.2;
        }
        updateThreadDelay();
    }

    private void incSpeed() {
        if (mSpeedFactor <= 1.4) {
            mSpeedFactor += 0.2;
        }
        updateThreadDelay();
    }

    public void updateThreadDelay() {
        if (mSpeedFactor < 0.2) {
            mSpeedFactor = 0.2;
        } else if (mSpeedFactor > 1.6) {
            mSpeedFactor = 1.6;
        }
        mDelay = mDefaultDelay / mSpeedFactor;
    }

    @Override
    public void onBackPressed() {
        if (mBackPressedOnce) {
            super.onBackPressed();
            mPool.release();
            mRunning = !mRunning;
        } else {
            mBackPressedOnce = true;
            Toast.makeText(this, "Press BACK again to exit", Toast.LENGTH_SHORT).show();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    mBackPressedOnce = false;
                }
            }, DELAY);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Author: Raphael Khoury Initialize shared preferences and sync with
     * preferences fragment Call to method that actually create dialog
     * (createDialog(Context))
     */

    private void checkDialog(Context cont) {
        SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(cont);
        Boolean showHelp = pref.getBoolean("pref_show_help", true);
        if (showHelp) {
            createDialog(this);
        }

    }

    public String serialize(Tab t) {
        try {
            ByteArrayOutputStream bo = new ByteArrayOutputStream();
            ObjectOutputStream so = new ObjectOutputStream(bo);
            so.writeObject(t);
            so.flush();
            return new String(Base64.encode(bo.toByteArray(), Base64.DEFAULT));
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
    }

    /**
     * Author:
     * 
     * @param Context
     *            Create dialog box and display it
     */
    public void createDialog(final Context cont) {
        final CheckBox checkBox = new CheckBox(cont);
        checkBox.setText(R.string.show_help);
        checkBox.setEnabled(true);
        checkBox.setChecked(true);
        LinearLayout linLayout = new LinearLayout(cont);
        linLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT));
        linLayout.addView(checkBox);

        AlertDialog.Builder adBuilder = new AlertDialog.Builder(cont);
        adBuilder.setView(linLayout);
        adBuilder.setTitle(R.string.title_help);
        adBuilder.setMessage(R.string.help_content_dialog);
        adBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface arg0, int arg1) {
                SharedPreferences pref = PreferenceManager.getDefaultSharedPreferences(cont);
                SharedPreferences.Editor editor = pref.edit();
                editor.putBoolean("pref_show_help", checkBox.isChecked()).commit();
                Toast.makeText(cont, "Saved new preferences.", Toast.LENGTH_SHORT).show();
            }
        });
        adBuilder.create().show();
    }
}
