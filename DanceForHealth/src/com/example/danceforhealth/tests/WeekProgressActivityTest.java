package com.example.danceforhealth.tests;

import com.example.danceforhealth.GraphActivity;
import com.example.danceforhealth.R;
import com.example.danceforhealth.WeekProgressActivity;
import com.example.danceforhealth.WorkoutTypeActivity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;

public class WeekProgressActivityTest extends ActivityUnitTestCase<WeekProgressActivity>{
	
	WeekProgressActivity mActivity;
	Intent mLaunchIntent;

	public WeekProgressActivityTest() {
		super(WeekProgressActivity.class);
	}
	
	public WeekProgressActivityTest(Class<WeekProgressActivity> activityClass) {
		super(activityClass);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), WeekProgressActivity.class);
		startActivity(mLaunchIntent, null, null);
		mActivity = getActivity();
	}
	
	public void testPreconditions() {
	    assertNotNull("mActivity is null", mActivity);
	}
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}




}
