package ch.epfl.sweng.smartTabs.ui;

import android.test.ActivityInstrumentationTestCase2;

import ch.epfl.sweng.smartTabs.activity.DisplayActivity;

public class testDisplayActivity extends ActivityInstrumentationTestCase2<DisplayActivity>{
    
    DisplayActivity displayActivity;
    public testDisplayActivity() {
        super(DisplayActivity.class);
    }  
    
    public void setUp() throws Exception {
        super.setUp();
        displayActivity = getActivity();
    }
}