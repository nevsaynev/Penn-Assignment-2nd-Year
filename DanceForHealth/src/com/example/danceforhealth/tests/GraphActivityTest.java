package com.example.danceforhealth.tests;

import com.example.danceforhealth.GraphActivity;
import com.example.danceforhealth.R;

import android.content.Intent;
import android.test.ActivityUnitTestCase;
import android.widget.Button;



public class GraphActivityTest extends ActivityUnitTestCase<GraphActivity>{
	GraphActivity mActivity;
	Intent mLaunchIntent;
	private Button backButton;
	private Button weekButton;
	private Button monthButton;
	private Button yeatButton;
	private Button feelButton;
	private Button typeButton; 
	
	public GraphActivityTest() {
		super(GraphActivity.class);
	}
	
	public GraphActivityTest(Class<GraphActivity> activityClass) {
		super(activityClass);
	}
	
	protected void setUp() throws Exception {
		super.setUp();
		mLaunchIntent = new Intent(getInstrumentation()
                .getTargetContext(), GraphActivity.class);
		startActivity(mLaunchIntent, null, null);
		mActivity = getActivity();
	}
	
	public void testPreconditions() {
	    assertNotNull("mActivity is null", mActivity);
	}
	
	public void testLayout() {
		backButton = (Button) mActivity.findViewById(R.id.back);
		assertNotNull("backButton is null", backButton);
		
		weekButton = (Button) mActivity.findViewById(R.id.week);
		assertNotNull("weekButton is null", weekButton);
		
		monthButton = (Button) mActivity.findViewById(R.id.month);
		assertNotNull("monthButton is null", monthButton);
		
		yeatButton = (Button) mActivity.findViewById(R.id.year);
		assertNotNull("yeatButton is null", yeatButton);
		
		feelButton = (Button) mActivity.findViewById(R.id.feel);
		assertNotNull("feelButton is null", feelButton);
		
		typeButton = (Button) mActivity.findViewById(R.id.type);
		assertNotNull("typeButton is null", typeButton);
		
	}
	
	
	protected void tearDown() throws Exception {
		super.tearDown();
	}


}
