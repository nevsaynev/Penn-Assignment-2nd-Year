package com.example.danceforhealth.tests;

import com.example.danceforhealth.HomeActivity;
import com.example.danceforhealth.R;
import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.view.ContextThemeWrapper;
import android.widget.Button;



public class HomeActivityTest extends ActivityUnitTestCase<HomeActivity>{
	HomeActivity mActivity;
	Intent mLaunchIntent;
	private Button previousButton;
	private Button newButton;
	private Button progressButton;
	
	public HomeActivityTest() {
		super(HomeActivity.class);
	}
	
	public HomeActivityTest(Class<HomeActivity> activityClass) {
		super(activityClass);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		
		ContextThemeWrapper context = new ContextThemeWrapper(getInstrumentation().getTargetContext(), R.style.AppTheme);
	    setActivityContext(context);
	    mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), HomeActivity.class);
		startActivity(mLaunchIntent, null, null);
		mActivity = getActivity();
	}
	
	public void testPreconditions() {
	    assertNotNull("mActivity is null", mActivity);
	}
	
	public void testLayout() {
		previousButton = (Button) mActivity.findViewById(R.id.preWorkoutButton);
		assertNotNull("backButton is null", previousButton);
		
		newButton = (Button) mActivity.findViewById(R.id.newWorkoutButton);
		assertNotNull("weekButton is null", newButton);
		
		progressButton = (Button) mActivity.findViewById(R.id.showProgressButton);
		assertNotNull("monthButton is null", progressButton);
		
	}
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}


}
