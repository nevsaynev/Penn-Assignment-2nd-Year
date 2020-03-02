package com.example.danceforhealth;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Arrays;
import java.util.List;

import com.androidplot.ui.AnchorPosition;
import com.androidplot.ui.XLayoutStyle;
import com.androidplot.ui.YLayoutStyle;
import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

import android.os.Bundle;
import android.app.Activity;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class WeekProgressActivity extends Activity {

	private XYPlot plot;
	private Button backButton;
	private int MAX_CALO_WEEK = 420;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_week_progress);
		
		//read from database
		getWorkoutDatastoreFromUser();
		
		// initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
               
    	PrevWorkout preWorkout = PrevWorkout.getInstance();
    	List<Workout> workouts = preWorkout.getPrevious();
    	
    	// get current date and week: Sun Nov 02 19:23:24 EST 2014
    	Date current = new Date();
		String[] dateString = current.toString().split(" ");
		Integer currentDay = Integer.parseInt(dateString[2]);
		String currentDOW = dateString[0];
		int min = sortDay(currentDOW);
			
    	// turn workouts into an array of values
    	Number[] values = new Number[8];
    	int count = 0;
    	
    	
    	Number[] caloriWeek = new Number[8];
    	int[] caloriDay = new int[8]; //record calori burnt each day of this week
    	int[] caloriWeekAccumu = new int[8]; //record accumulative calori each day of this week
    	
    	
    	
    	for(int i = 0; i< caloriDay.length; i++){
    		caloriDay[i] = 0;
    		caloriWeekAccumu[i] = 0;
    	}
    	
    	// identify workout in this week
    	for (Workout workout : workouts) {
    		
    		
    		//date format changed! Get exact format to continue
    		
    		String date_debug = workout.getDate();
    		Integer data_test = workout.getTime();
    		System.out.println(date_debug);
    		Log.v("workout date", date_debug);
    		Log.v("db object", data_test.toString());
    		
    		String[] date_pre = workout.getDate().split("  "); //this is correct
    		String[] date = date_pre[1].split("/");   		
    		String dow = date_pre[0]; //Fri
    		Integer workoutYear = Integer.parseInt(date[2]); //2014
    		String workoutMonth = date[0]; //Nov
    		Integer workoutDayOfMonth = Integer.parseInt(date[1]); //7
    		Integer monthNum = monthChar2Num(workoutMonth);//11

    		
    		
    		Log.v("day of week", dow);
    		Log.v("year", workoutYear.toString());
    		Log.v("month" ,workoutMonth);
    		Log.v("month_num" ,monthNum.toString());
    		Log.v("day of month" ,workoutDayOfMonth.toString());
    		
    		
    		
    		// check if day is in this week
    		Calendar c = Calendar.getInstance();
    		//must be month - 1, have no idea why
    		c.set(workoutYear, monthNum, workoutDayOfMonth);
    		long time = c.getTimeInMillis();//millisecond of the workout
    		//long now = current.getTime();
    		long now = Calendar.getInstance().getTimeInMillis();//millisecond of now
    		long millsInDay = 86400000;
    		
    		
    		Log.v("mili diff", Long.toString(now - time));
    		//Log.v("now mili", Long.toString(now));
    		
    		if ((now-time)/millsInDay <= min && (now-time)/millsInDay >= 0) {
    			count++;
    			//values[sortDay(dow)] = workout.getWeight();
    			caloriDay[sortDay(dow)] += caloriCalculate(workout);
    		}
    	}
    	 
    	caloriWeekAccumu[0] = caloriDay[0];
    	for(int i = 1; i < caloriWeekAccumu.length; i++){
    		caloriWeekAccumu[i] = caloriWeekAccumu[i-1] + caloriDay[i];
    		if (caloriWeekAccumu[i] >= MAX_CALO_WEEK){
    			caloriWeekAccumu[i] = MAX_CALO_WEEK;
    		}
    	}
    	
    	for(int i = 0; i < caloriWeek.length; i++){
    		if (i <= min){
    			caloriWeek[i] = caloriWeekAccumu[i];
    		}
    		else{
    			caloriWeek[i] = null;
    		}
    	}
		plotWeekProgress(caloriWeek);
	}
	
	private void plotWeekProgress (Number[] values) {

	 	plot.getGraphWidget().getBackgroundPaint().setColor(Color.TRANSPARENT);
	 	plot.getGraphWidget().getGridBackgroundPaint().setColor(Color.TRANSPARENT);
	 	plot.getBackgroundPaint().setColor(Color.TRANSPARENT);

	 	plot.getGraphWidget().getDomainLabelPaint().setColor(Color.parseColor("#33b5e5"));
	 	plot.getGraphWidget().getRangeLabelPaint().setColor(Color.parseColor("#33b5e5"));

	 	plot.getGraphWidget().getDomainOriginLabelPaint().setColor(Color.parseColor("#33b5e5"));
	 	plot.getGraphWidget().getDomainOriginLinePaint().setColor(Color.parseColor("#33b5e5"));
	 	plot.getGraphWidget().getRangeOriginLinePaint().setColor(Color.parseColor("#33b5e5"));

	    //Remove legend
	 	plot.getLayoutManager().remove(plot.getLegendWidget());
	 	plot.getLayoutManager().remove(plot.getDomainLabelWidget());
	 	//plot.getLayoutManager().remove(plot.getRangeLabelWidget());
	 	//plot.getLayoutManager().remove(plot.getTitleWidget());

	    // setup our line fill paint to be a slightly transparent gradient:
	 	Paint lineFill = new Paint();
	    //lineFill.setAlpha(200);
	    lineFill.setColor(Color.parseColor("#33b5e5"));
	    /*
	    lineFill.setShader(new LinearGradient(0, 0, 0, 250, 
	    		Color.WHITE, Color.GREEN, 
	    		Shader.TileMode.MIRROR));
	    */
		// start Turn the above arrays into XYSeries:
        XYSeries series1 = new SimpleXYSeries(
                Arrays.asList(values),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "This Week");                           // Set the display title of the series
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        LineAndPointFormatter series1Format = new LineAndPointFormatter(Color.parseColor("#33b5e5"), 
        		Color.parseColor("#33b5e5"), 
        		Color.parseColor("#33b5e5"), 
        		null);

        series1Format.setFillPaint(lineFill);
        // add a new series' to the xyplot:
        plot.addSeries(series1, series1Format);   
        // reduce the number of range labels
        plot.getGraphWidget().setDomainLabelOrientation(-45);
        plot.setDomainBoundaries(1, 7, BoundaryMode.FIXED);
        plot.setRangeBoundaries(0, MAX_CALO_WEEK, BoundaryMode.FIXED);
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1.0);
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 60.0);
//		Typeface font_two = Typeface.createFromAsset(getAssets(), "Komika_display.ttf");
		backButton = (Button) findViewById(R.id.back);
//		backButton.setTypeface(font_two);		
	}
	
	
	private int caloriCalculate(Workout workout){
//		if(workout.getType().equals("Dance"))
//			return workout.getTime()*12;
//		else if(workout.getType().equals("Run"))
//			return workout.getTime()*10;
//		else if(workout.getType().equals("Walk"))
//			return workout.getTime()*5;
//		else if(workout.getType().equals("Bike"))
//			return workout.getTime()*8;
//		else if(workout.getType().equals("Swim"))
//			return workout.getTime()*13;
//		else return 0;
		return workout.getTime();
	}
	
	
	private Integer monthChar2Num(String monthInChar) {
		return 11;
	}
	
	
	private int sortDay(String day) {
		if (day.equals("Mon")) {
			return 1;
		} else if (day.equals("Tue")) {
			return 2;
		} else if (day.equals("Wed")) {
			return 3;
		} else if (day.equals("Thu")) {
			return 4;
		} else if (day.equals("Fri")) {
			return 5;
		} else if (day.equals("Sat")) {
			return 6;
		} else {
			return 7;
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.week_progress, menu);
		return true;
	}
	
	public void onBackButtonClick(View view) {
		//end activity and go back to the home page
		finish();
	}
	
	private void getWorkoutDatastoreFromUser() {
		ParseQuery<WorkoutDataStore> query = ParseQuery
				.getQuery(WorkoutDataStore.class);
		query.whereEqualTo("User", ParseUser.getCurrentUser());
		query.fromLocalDatastore();
		query.findInBackground(new FindCallback<WorkoutDataStore>() {
			PrevWorkout pw = PrevWorkout.getInstance();
			List<Workout> workoutDataStore = (ArrayList<Workout>) pw
					.getPrevious();
			

			@Override
			public void done(List<WorkoutDataStore> workouts,
					com.parse.ParseException e) {
				if (e == null) {
					workoutDataStore.clear();
					for (WorkoutDataStore workout : workouts) {
						workoutDataStore.add(workout.toWorkoutObject());

					}

				} else {
					Toast.makeText(getApplicationContext(), "fails",
							Toast.LENGTH_SHORT);
				}
			}
		});

	}
}
