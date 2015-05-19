package ua.sanya5791.photogalleryflyckr;

import android.test.ActivityInstrumentationTestCase2;

/**
 * Created by sanya on 05.05.2015.
 */
public class DFrServicesTest extends ActivityInstrumentationTestCase2<MainActivity>{

    MainActivity mainActivity;
    DFrServices dFrServices;

    public DFrServicesTest(Class<DFrServices> activityClass) {
        super(MainActivity.class);

    }

    public void testPreconditions(){


    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        mainActivity = (MainActivity)getActivity();
//        dFrServices = (DFrServices) mainActivity.fra
    }

    @Override
    protected void tearDown() throws Exception {
        super.tearDown();
    }
}
