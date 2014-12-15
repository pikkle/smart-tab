package ch.epfl.sweng.smartTabs.ui;

import android.test.ActivityInstrumentationTestCase2;

import ch.epfl.sweng.smartTabs.activity.MainActivity;


public class testMainActivity extends ActivityInstrumentationTestCase2<MainActivity>{

    MainActivity a;
    public testMainActivity() {
        super(MainActivity.class);
    }  
    
    public void setUp() throws Exception {
        super.setUp();
        a = getActivity();
    }   
}