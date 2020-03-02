package com.example.danceforhealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

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
import android.graphics.Paint;
import android.graphics.Typeface;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class MonthProgressActivity extends Activity {

	private XYPlot plot;
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_month_progress);
		
		getWorkoutDatastoreFromUser();

		// initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);

        // Get previous workouts
    	PrevWorkout preWorkout = PrevWorkout.getInstance();
    	List<Workout> workouts = preWorkout.getPrevious();
 
        // get current date and month
        Date d = new Date();
        SimpleDateFormat format = 
				new SimpleDateFormat ("E M dd yyyy");
		String current = format.format(d);
		String[] dateString = current.toString().split(" ");
		int currentDay = Integer.parseInt(dateString[2]);
		String currentMonth = dateString[1];
		
		//Log.v("day of month", Integer.toString(currentDay));
		
        
		int count = 0;
		Number[] values = new Number[6];
		int[] calorieWeek = new int[6];
		int[] calorieMonthAcumu = new int[6];
		
		for(int i = 0; i< calorieWeek.length; i++){
    		calorieWeek[i] = 0;
    		calorieMonthAcumu[i] = 0;
    	}
		
		
		for (Workout workout : workouts) {
			String[] date_pre = workout.getDate().split("  ");
			String[] date = date_pre[1].split("/");
			
//			Log.v("day of month ", date[1]);

			
			Integer day_Integer = Integer.parseInt(date[1]);
			int day = day_Integer;
			String month = date[0];
			currentMonth = monthConver(currentMonth);
			//String dow = date_pre[0];
			
			Log.v("month", month);
			Log.v("current month", currentMonth);
			
    		if (month.replaceAll("\\s+","").equals(currentMonth.replaceAll("\\s+",""))) {
    			Log.v("Enter", "Enter");
    			if (day/7 == 0) {
    				calorieWeek[1] += caloriCalculate(workout);
    			}
    			else if (day/7 == 1) {
    				calorieWeek[2] += caloriCalculate(workout);
    			}
    			else if (day/7 == 2) {
    				calorieWeek[3] += caloriCalculate(workout);
    			}
    			else if (day/7 == 3) {
    				calorieWeek[4] += caloriCalculate(workout);
    			}
    			else if (day/7 == 4) {
    				calorieWeek[5] += caloriCalculate(workout);
    			}
    			//if (day - sortDay(dow)<= 0) values[1] = workout.getWeight();
    			//else if ((day - sortDay(dow))/7 == 4) values[(day - sortDay(dow))/7 + 1] = workout.getWeight();
    			//else values[(day - sortDay(dow))/7 + 2] = workout.getWeight();
    			//count++;
    		}	
		}
		
		
		calorieMonthAcumu[0] = calorieWeek[0];
    	for(int i = 1; i < calorieMonthAcumu.length; i++){
    		calorieMonthAcumu[i] = calorieMonthAcumu[i-1] + calorieWeek[i];
    		if (calorieMonthAcumu[i] >= 2100){
    			calorieMonthAcumu[i] = 2100;
    		}
    	}
    	values[0] = 0;
    	for(int i = 1; i < values.length; i++){
    		if (i <= (currentDay/7 + 1)){
    			values[i] = calorieMonthAcumu[i];
//    			Log.v("values", values[i].toString());
    		}
    		else{
    			values[i] = null;
    		}
    	}
			
		/*
		if ((count < currentDay) && (workouts.size() > count)) {
    		int base = workouts.get(workouts.size() - count - 1).getWeight();
    		boolean toggle = true;
    		for (int i = 1; i < currentDay/7 + 1; i++) {
    			if ((Integer)values[i] == null) {
    				if (toggle) values[i] = base;
    				else values[i] = values[i-1];
    			}
    			else toggle = false;
    		}
    	}
    	*/
		plotMonthProgress(values);
		
	}
	
	private void plotMonthProgress(Number[] values) {
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
		
		
		// Turn the above arrays into XYSeries':
        XYSeries series = new SimpleXYSeries(
                Arrays.asList(values),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "This Month");                           // Set the display title of the series
 
        // Create a formatter to use for drawing a series using LineAndPointRenderer
        //LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.RED, Color.RED, null, null);
        
     // Create a formatter to use for drawing a series using LineAndPointRenderer
        LineAndPointFormatter seriesFormat = new LineAndPointFormatter(Color.parseColor("#33b5e5"), 
        		Color.parseColor("#33b5e5"), 
        		Color.parseColor("#33b5e5"), 
        		null);

        seriesFormat.setFillPaint(lineFill);
        // add a new series' to the xyplot:
        plot.addSeries(series, seriesFormat);
 
        // reduce the number of range labels
        plot.getGraphWidget().setDomainLabelOrientation(-45);
        plot.setDomainBoundaries(1, 5, BoundaryMode.FIXED);
        plot.setRangeBoundaries(0, 2100, BoundaryMode.FIXED);
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1.0);
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 300.0);

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
	
	private String monthConver(String monthInt) {
		if (monthInt.equals("12"))
			return "Dec";
		else if (monthInt.equals("11"))
			return "Nov";
		else if (monthInt.equals("10"))
			return "Oct";
		else if (monthInt.equals("9"))
			return "Sep";
		else if (monthInt.equals("8"))
			return "Aug";
		else if (monthInt.equals("7"))
			return "Jul";
		else if (monthInt.equals("6"))
			return "Jun";
		else if (monthInt.equals("5"))
			return "May";
		else if (monthInt.equals("4"))
			return "Apr";
		else if (monthInt.equals("3"))
			return "Mar";
		else if (monthInt.equals("2"))
			return "Feb";
		else if (monthInt.equals("1"))
			return "Jan";
		else return "Dec";
	}
	
	
	private int sortDay(String day) {
		if (day.equals("Mon")) {
			return 0;
		} else if (day.equals("Tue")) {
			return 1;
		} else if (day.equals("Wed")) {
			return 2;
		} else if (day.equals("Thu")) {
			return 3;
		} else if (day.equals("Fri")) {
			return 4;
		} else if (day.equals("Sat")) {
			return 5;
		} else {
			return 6;
		}
	}
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.month_progress, menu);
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
