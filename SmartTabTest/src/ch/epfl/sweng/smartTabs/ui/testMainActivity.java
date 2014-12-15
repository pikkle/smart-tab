package ch.epfl.sweng.smartTabs.ui;

import android.test.ActivityInstrumentationTestCase2;

import ch.epfl.sweng.smartTabs.activity.MainActivity;

import com.google.android.apps.common.testing.ui.espresso.Espresso;
import com.google.android.apps.common.testing.ui.espresso.action.ViewActions;
import com.google.android.apps.common.testing.ui.espresso.assertion.ViewAssertions;
import com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers;

public class testMainActivity extends ActivityInstrumentationTestCase2<MainActivity>{

    public testMainActivity() {
        super(MainActivity.class);
    }  
    
    public void setUp() throws Exception {
        getActivity();
    }
}