package com.example.danceforhealth.tests;

import com.example.danceforhealth.R;
import com.example.danceforhealth.WorkoutFeelActivity;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.ContextThemeWrapper;
import android.widget.Button;



public class WorkoutFeelActivityTest extends ActivityUnitTestCase<WorkoutFeelActivity>{
	WorkoutFeelActivity mActivity;
	Intent mLaunchIntent;
	private Button backButton;
	
	public WorkoutFeelActivityTest() {
		super(WorkoutFeelActivity.class);
	}
	
	public WorkoutFeelActivityTest(Class<WorkoutFeelActivity> activityClass) {
		super(activityClass);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
	    setActivityContext(context);
	    mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), WorkoutFeelActivity.class);
		startActivity(mLaunchIntent, null, null);
		mActivity = getActivity();
	}
	
	public void testPreconditions() {
	    assertNotNull("mActivity is null", mActivity);
	}
	
	public void testLayout() {
		backButton = (Button) mActivity.findViewById(R.id.back);
		assertNotNull("backButton is null", backButton);
		
	}
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}


}
