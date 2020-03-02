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

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;

import android.os.Bundle;
import android.app.ActionBar;
import android.app.ActionBar.LayoutParams;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class WorkoutFeelActivity extends Activity {
	private Button backButton;

	private static int[] COLORS = new int[] { Color.parseColor("#FF9900"), Color.parseColor("#33b5e5"),Color.parseColor("#66FF99") };    
	private static double[] VALUES = new double[3];
	private static String[] NAME_LIST = new String[] { "GREAT", "GOOD", "OKAY"};  
	private CategorySeries mSeries = new CategorySeries("");  
	private DefaultRenderer mRenderer = new DefaultRenderer();  
	private GraphicalView mChartView; 
	
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_workout_feel_1);
		
		backButton = (Button) findViewById(R.id.back);
		
     // Get previous workouts
    	PrevWorkout pw = PrevWorkout.getInstance();
    	List<Workout> workouts = pw.getPrevious();
    	
    	/*
        // get current date and month
    	Date d = new Date();
        SimpleDateFormat format = 
				new SimpleDateFormat ("E M dd yyyy");
		String current = format.format(d);
		String[] dateString = current.toString().split(" ");
		int currentMonth = Integer.parseInt(dateString[1]);
		String currentYear = dateString[3];
		*/
		
    	//int count = 0;
		//Number[] values = new Number[13];
    	double greatCnt = 1;
    	double goodCnt = 1;
    	double okayCnt = 1;
    	VALUES[0] = greatCnt;
		VALUES[1] = goodCnt;
		VALUES[2] = okayCnt;
		for (Workout workout : workouts) {
			if (workout.getStrain() < 3) {
				greatCnt++;
			} else if (workout.getStrain() < 5) {
				goodCnt++;
			} else {
				okayCnt++;
			}
			 VALUES[0] = greatCnt;
			 VALUES[1] = goodCnt;
			 VALUES[2] = okayCnt;
			
			//String[] date = workout.getDate().split(" ");
    		//int month = Integer.parseInt(date[1]);
    		//String year = date[3];
    		//if (year.equals(currentYear)) {
    			//if (values[month] == null) count++;
    			//values[month] = workout.getWeight();
    		//}
		}
		
		plotWorkoutFeelPie();
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.workout_feel, menu);
		return true;
	}

	public void onBackButtonClick(View view) {
		//end activity and go back to the home page
		finish();
	}
	
	
	private void plotWorkoutFeelPie(){		
		mRenderer.setApplyBackgroundColor(true);  
		mRenderer.setBackgroundColor(Color.TRANSPARENT);  
		mRenderer.setChartTitleTextSize(20);  
		//mRenderer.setLabelsTextSize(15);  
		//mRenderer.setLegendTextSize(15);  
		mRenderer.setMargins(new int[] { 20, 30, 15, 0 });  
		mRenderer.setZoomButtonsVisible(false);  
		mRenderer.setStartAngle(90); 
		mRenderer.setLabelsTextSize(1);
		mRenderer.setLabelsColor(Color.parseColor("#FFFFFA"));
		mRenderer.setLegendTextSize(55);
		  
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
