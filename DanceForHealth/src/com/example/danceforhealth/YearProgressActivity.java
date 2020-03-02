package com.example.danceforhealth;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import android.app.Activity;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.androidplot.xy.BoundaryMode;
import com.androidplot.xy.LineAndPointFormatter;
import com.androidplot.xy.SimpleXYSeries;
import com.androidplot.xy.XYPlot;
import com.androidplot.xy.XYSeries;
import com.androidplot.xy.XYStepMode;
import com.parse.FindCallback;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class YearProgressActivity extends Activity {

	private XYPlot plot;
	private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_year_progress);
		
		getWorkoutDatastoreFromUser();

		// initialize our XYPlot reference:
        plot = (XYPlot) findViewById(R.id.mySimpleXYPlot);
 
     // Get previous workouts
    	PrevWorkout pw = PrevWorkout.getInstance();
    	List<Workout> workouts = pw.getPrevious();
    	
        // get current date and month
        Date d = new Date();
        SimpleDateFormat format = 
				new SimpleDateFormat ("E M dd yyyy");
		String current = format.format(d);
		String[] dateString = current.toString().split(" ");
		int currentDay = Integer.parseInt(dateString[2]);
		String currentMonth = dateString[1];
		String currentYear = dateString[3];
    	
    	Number[] values = new Number[13];
    	int[] monthTime = new int[13];
    	int[] monthAccu = new int[13];
    	
		for(int i = 0; i< monthTime.length; i++){
			monthTime[i] = 0;
			monthAccu[i] = 0;
			values[i] = null;
    	}
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
		
		
		for (Workout workout : workouts) {
			String[] date_pre = workout.getDate().split("  ");
			String[] date = date_pre[1].split("/");
			
			Integer day_Integer = Integer.parseInt(date[1]);
			int day = day_Integer;
			String month = date[0];
			String year = date[2];
//			Integer workoutYear = Integer.parseInt(date[2]); //2014
			currentMonth = monthConver(currentMonth);
			
//			String[] date = workout.getDate().split(" ");
//    		int month = Integer.parseInt(date[1]);
//    		String year = date[3];
    		if (year.replaceAll("\\s+","").equals(currentYear.replaceAll("\\s+",""))) {
    			Log.v("Enter year", "Enter year");
    			if (month.replaceAll("\\s+","").equals("Jan")) {
    				monthTime[1] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("Feb")) {
    				monthTime[2] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("Mar")) {
    				monthTime[3] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("Apr")) {
    				monthTime[4] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("May")) {
    				monthTime[5] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("Jun")) {
    				monthTime[6] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("Jul")) {
    				monthTime[7] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("Aug")) {
    				monthTime[8] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("Sep")) {
    				monthTime[9] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("Oct")) {
    				monthTime[10] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("Nov")) {
    				monthTime[11] += workout.getTime();
    			}
    			else if (month.replaceAll("\\s+","").equals("Dec")) {
    				monthTime[12] += workout.getTime();
    			}
//    			if (values[month] == null) count++;
//    			values[month] = workout.getWeight();
    		}
		}
		
		monthAccu[0]= monthTime[0];
    	for(int i = 1; i < monthAccu.length; i++){
    		monthAccu[i] = monthAccu[i-1] + monthTime[i];
    		if (monthAccu[i] >= 25200){
    			monthAccu[i] = 25200;
    		}
    	}
    	values[0] = 0;
    	for(int i = 1; i < values.length; i++){
    		values[i] = monthAccu[i];

    	}	
		plotYearProgress(values);	
	}
	
	private void plotYearProgress(Number[] values) {
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
		// Turn the above arrays into XYSeries':
        XYSeries series = new SimpleXYSeries(
                Arrays.asList(values),          // SimpleXYSeries takes a List so turn our array into a List
                SimpleXYSeries.ArrayFormat.Y_VALS_ONLY, // Y_VALS_ONLY means use the element index as the x value
                "This Year");                           // Set the display title of the series
 
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
        plot.setDomainBoundaries(1, 12, BoundaryMode.FIXED);
        plot.setRangeBoundaries(0, 25200, BoundaryMode.FIXED);
        plot.setDomainStep(XYStepMode.INCREMENT_BY_VAL, 1.0);
        plot.setRangeStep(XYStepMode.INCREMENT_BY_VAL, 3600.0);

//		Typeface font_two = Typeface.createFromAsset(getAssets(), "Komika_display.ttf");
		backButton = (Button) findViewById(R.id.back);
//		backButton.setTypeface(font_two);	
	}
	
	

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.year_progress, menu);
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

}
