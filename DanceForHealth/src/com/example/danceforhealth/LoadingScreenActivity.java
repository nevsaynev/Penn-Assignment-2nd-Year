package com.example.danceforhealth;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.util.Log;
import android.view.Menu;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.ParseQuery;

public class LoadingScreenActivity extends Activity {

//	private Util util = new Util();
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		final Intent i = new Intent(this, HomeActivity.class);
		setContentView(R.layout.loading_screen);

		CountDownTimer timer = new CountDownTimer(1000, 1000) {

			@Override
			public void onTick(long millisUntilFinished) {

			}

			@Override
			public void onFinish() {
				startActivity(i);
				finish();
			}

		};
		
		Context context = this;
		clearWorkout();

//		getWorkoutDatastore();
//		PrevWorkout pw = PrevWorkout.getInstance();
//		ArrayList<Workout> previous = (ArrayList<Workout>) pw.getPrevious();
//		Log.v("loading", previous.toString());
//		for(int i1 = 0;  i1<previous.size();i1++){
//			Log.v("loading", previous.get(i1).toString());
//		}
		
//		setState();
//		if (file.exists()) {
//			try {
//				getWorkoutDatastore();
//				Log.v("loading", "read from log file");
//				setState();
//
//			} catch (Exception e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//		} else {
//			file = new File(context.getFilesDir(), "data_workout");
//			Log.v("loading", "created data file");
//		}
		timer.start();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.new_workout, menu);
		return true;
	}

	

	private void clearWorkout() {
		List<Workout> workouts = PrevWorkout.getInstance().getPrevious();
		workouts.clear();
	}

	

//	public void setState() {
//		State s = State.getInstance();
//		s.setLostWeight(util.calculateWeightLoss());
//		int workingTime = util.calculateTotalWorkingTime();
//		s.setWorkingTime(workingTime);
//		s.setLevel(workingTime / 300 + 1);
//		int workingWeeks = util.calculateContinuousWorkingWeeks();
//		s.setWorkingWeeks(workingWeeks);
//	}

	

//	private void getWorkoutDatastore() {
//		ParseQuery<WorkoutDataStore> query = ParseQuery
//				.getQuery(WorkoutDataStore.class);
//		
//		query.fromLocalDatastore();
//		query.findInBackground(new FindCallback<WorkoutDataStore>() {
//			PrevWorkout pw = PrevWorkout.getInstance();
//			List<Workout> workoutDataStore = (ArrayList<Workout>) pw
//					.getPrevious();
//
//			@Override
//			public void done(List<WorkoutDataStore> workouts,
//					com.parse.ParseException e) {
//				if (e == null) {
//					workoutDataStore.clear();
//					for (WorkoutDataStore workout : workouts) {
//						workoutDataStore.add(workout.toWorkoutObject());
//
//					}
//
//				} else {
//					Toast.makeText(getApplicationContext(), "fails",
//							Toast.LENGTH_SHORT);
//				}
//			}
//		});
//
//	}

}
