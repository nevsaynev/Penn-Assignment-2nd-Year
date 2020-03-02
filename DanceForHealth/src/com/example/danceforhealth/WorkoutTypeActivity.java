package com.example.danceforhealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.model.CategorySeries;
import org.achartengine.model.SeriesSelection;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;


import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WorkoutTypeActivity extends ActionBarActivity {

	private Button backButton;

	private static int[] COLORS = new int[] { Color.parseColor("#FF9900"), 
											  Color.parseColor("#33b5e5"),
											  Color.parseColor("#66FF99"), 
											  Color.parseColor("#FF47A3"), 
											  Color.parseColor("#FFFF66")};    
	private static double[] VALUES = new double[5];
	private static String[] NAME_LIST = new String[] { "Dance", "Run", "Walk", "Bike", "Swim"};  
	private CategorySeries mSeries = new CategorySeries("");  
	private DefaultRenderer mRenderer = new DefaultRenderer();  
	private GraphicalView mChartView; 
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_type);
		
		backButton = (Button) findViewById(R.id.back);
		
	     // Get previous workouts
	    	PrevWorkout pw = PrevWorkout.getInstance();
	    	List<Workout> workouts = pw.getPrevious();

	    	double danceCnt = 1;
	    	double runCnt = 1;
	    	double walkCnt = 1;
	    	double bikeCnt = 1;
	    	double swimCnt = 1;
	    	VALUES[0] = danceCnt;
			VALUES[1] = runCnt;
			VALUES[2] = walkCnt;
			VALUES[3] = bikeCnt;
			VALUES[4] = swimCnt;
			
			for (Workout workout : workouts) {
				if (workout.getType().equals("Dance")) {
					danceCnt += workout.getTime();
				} else if (workout.getType().equals("Run")) {
					runCnt += workout.getTime();
				} else if (workout.getType().equals("Walk")) {
					walkCnt += workout.getTime();
				} else if (workout.getType().equals("Bike")) {
					bikeCnt += workout.getTime();
				} else if (workout.getType().equals("Swim")) {
					swimCnt += workout.getTime();
				}
				
				VALUES[0] = danceCnt;
				VALUES[1] = runCnt;
				VALUES[2] = walkCnt;
				VALUES[3] = bikeCnt;
				VALUES[4] = swimCnt;
			}
			
			plotWorkoutTypePie();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workout_type, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void onBackButtonClick(View view) {
		//end activity and go back to the home page
		finish();
	}
	
	private void plotWorkoutTypePie(){		
		mRenderer.setApplyBackgroundColor(true);  
		mRenderer.setBackgroundColor(Color.TRANSPARENT);  
		mRenderer.setChartTitleTextSize(20);  
		//mRenderer.setLabelsTextSize(15);  
		//mRenderer.setLegendTextSize(15);  
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });
//		mRenderer.setMargins(new int[] { 4, 6, 3, 0 });
		mRenderer.setZoomButtonsVisible(false);  
		mRenderer.setStartAngle(90); 
		
		mRenderer.setLabelsTextSize(1);
		mRenderer.setLabelsColor(Color.parseColor("#FFFFFA"));
		mRenderer.setLegendTextSize(66);
		  
		for (int i = 0; i < VALUES.length; i++) {  
		mSeries.add(NAME_LIST[i] + " " + VALUES[i], VALUES[i]);  
		SimpleSeriesRenderer renderer = new SimpleSeriesRenderer();  
		renderer.setColor(COLORS[(mSeries.getItemCount() - 1) % COLORS.length]);  
		mRenderer.addSeriesRenderer(renderer);  
		}  
		  
		if (mChartView != null) {  
		mChartView.repaint();  
		} 
	}


	@Override  
	protected void onResume() {  
	super.onResume();  
	if (mChartView == null) {  
		LinearLayout layout = (LinearLayout) findViewById(R.id.chart);  
		mChartView = ChartFactory.getPieChartView(this, mSeries, mRenderer);  
		mRenderer.setClickEnabled(false);  
		mRenderer.setSelectableBuffer(10);  
		
		layout.addView(mChartView, new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.FILL_PARENT));  
		}  
		else {  
			mChartView.repaint();  
		}  
	} 
	
}
