package com.example.danceforhealth;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;

public class GraphActivity extends Activity {
	
	private Button backButton;
	private Button weekButton;
	private Button monthButton;
	private Button yeatButton;
	private Button feelButton;
	private Button typeButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_graph);
		

		backButton = (Button) findViewById(R.id.back);
		weekButton = (Button) findViewById(R.id.week);
		monthButton = (Button) findViewById(R.id.month);
		yeatButton = (Button) findViewById(R.id.year);
		feelButton = (Button) findViewById(R.id.feel);
		typeButton = (Button) findViewById(R.id.type);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.graph, menu);
		return true;
	}
	
	public void onBackButtonClick(View view) {
		//end activity and go back to the home page
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
		finish();
	}
	
	public void onWeekButtonClick(View view) {
		// create an Intent using the current Activity 
		// and the Class to be created
		Intent intent = new Intent(this, WeekProgressActivity.class);

		// pass the Intent to the Activity, 
		// using the specified request code
		startActivity(intent);
	}
	
	public void onMonthButtonClick(View view) {
		// create an Intent using the current Activity 
		// and the Class to be created
		Intent intent = new Intent(this, MonthProgressActivity.class);

		// pass the Intent to the Activity, 
		// using the specified request code
		startActivity(intent);
	}
	
	public void onYearButtonClick(View view) {
		// create an Intent using the current Activity 
		// and the Class to be created
		Intent intent = new Intent(this, YearProgressActivity.class);

		// pass the Intent to the Activity, 
		// using the specified request code
		startActivity(intent);
	}
	
	public void onTypeButtonClick(View view) {
		// create an Intent using the current Activity 
		// and the Class to be created
		Intent intent = new Intent(this, WorkoutTypeActivity.class);

		// pass the Intent to the Activity, 
		// using the specified request code
		startActivity(intent);
	}
	
	public void onFeelButtonClick(View view) {
		// create an Intent using the current Activity 
		// and the Class to be created
		Intent intent = new Intent(this, WorkoutFeelActivity.class);

		// pass the Intent to the Activity, 
		// using the specified request code
		startActivity(intent);	
	}

}
