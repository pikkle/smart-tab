package ch.epfl.sweng.smartTabs.ui;

import android.test.ActivityInstrumentationTestCase2;

import ch.epfl.sweng.smartTabs.R;
import ch.epfl.sweng.smartTabs.activity.MainActivity;

import static com.google.android.apps.common.testing.ui.espresso.Espresso.onView;
import static com.google.android.apps.common.testing.ui.espresso.matcher.ViewMatchers.withId;
import static com.google.android.apps.common.testing.ui.espresso.action.ViewActions.typeText;;

public class testMainActivity extends ActivityInstrumentationTestCase2<MainActivity>{

    MainActivity mainActivity;
    public testMainActivity() {
        super(MainActivity.class);
    }  
    
    public void setUp() throws Exception {
        super.setUp();
        mainActivity = getActivity();
    }
    
    public void testSearch() {
        onView(withId(R.id.action_search)).perform(typeText("qijq2u8ue02193i09i2193i12i31i2j301h391j231923i913 _DAwdwd aèdwd awijdi ajwidj aw"));
    }
}