package com.example.danceforhealth;

import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import com.opencsv.CSVWriter;
import com.parse.FindCallback;
import com.parse.ParseObject;
import com.parse.ParseQuery;
import com.parse.ParseUser;
import com.parse.ParseException;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class PrevWorkoutActivity extends ListActivity {
	private List<Workout> workoutDataStore;
	private MySimpleArrayAdapter adapter;
  @Override
public void onCreate(Bundle icicle) {
    super.onCreate(icicle);
    
    
	
    View header = getLayoutInflater().inflate(R.layout.header, null);
    View footer = getLayoutInflater().inflate(R.layout.footer, null);
    ListView listView = getListView();
    listView.addHeaderView(header);
    listView.addFooterView(footer);
    //test work out
    adapter = new MySimpleArrayAdapter(this, new ArrayList<Workout>());
	setListAdapter((ListAdapter) adapter);
	
    TextView t = (TextView) findViewById(R.id.SignupTextView);
	Button b = (Button) findViewById(R.id.preWorkoutButton);
	Button c = (Button) findViewById(R.id.exportCsvButton);
	getWorkoutDatastore();
  }
  
	public void onBackButtonClick(View view) {
		// create an Intent using the current Activity 
		// and the Class to be created
		Intent intent = new Intent(this, HomeActivity.class);

		// pass the Intent to the Activity, 
		// using the specified request code
		startActivity(intent);
		finish();	
	}
	
	
	public void onExportButtonClick(View view) {
		if(saveDataToCsvFile()){
			Toast.makeText(getApplicationContext(),
					"You can find your DanceForHealth Log in the File Manager ", Toast.LENGTH_SHORT).show();
		}
	}
  
  
  @Override
  protected void onListItemClick(ListView l, View v, int position, long id) {
    Workout item = (Workout) getListAdapter().getItem(position - 1);
    Toast.makeText(this, item + " selected", Toast.LENGTH_LONG).show();
    
	Intent intent = new Intent();
	Bundle b = new Bundle();
	b.putParcelable("workout", item);
	intent.putExtras(b);

	intent.setClass(v.getContext(), WorkoutSummary.class);

	// pass the Intent to the Activity, 
	// using the specified request code
	startActivity(intent);    
	finish();
  }
  
  private void getWorkoutDatastore(){
	  workoutDataStore = new ArrayList<Workout>();
	  ParseQuery<WorkoutDataStore> query = ParseQuery.getQuery(WorkoutDataStore.class);
	  query.whereEqualTo("User", ParseUser.getCurrentUser());
	  query.fromLocalDatastore();
	  query.findInBackground(new FindCallback<WorkoutDataStore>() {
		  
		  @Override
	      public void done(List<WorkoutDataStore> workouts, ParseException e) {
	          if (e == null) {
	        	  workoutDataStore.clear();
	        	  for (WorkoutDataStore workout : workouts ){
	        		  workoutDataStore.add(workout.toWorkoutObject());
	        	  }
	        	  adapter.clear();
	        	  adapter.addAll(workoutDataStore);
	          } else {
	        	  Toast.makeText(getApplicationContext(), "fails", Toast.LENGTH_SHORT);
	          }
	      }
	  });
	 
  }
  
  
  private boolean saveDataToCsvFile() {

		String csv = "/mnt/sdcard/DanceForHealthLog.csv";

		try {
			CSVWriter writer = new CSVWriter(new FileWriter(csv), ',');
			String[] header = { "Date","Type", "Weight", "Post-Dance HR",
					"Post-Dance Pedometer", "PACE Score(strain)" };
			writer.writeNext(header);
			for (Workout w : workoutDataStore) {
				String[] entries = {  w.getDate(),w.getType(),
						""+w.getWeight(), "" + w.getHR(), "" + w.getSteps(),
						"" + w.getStrain() };
				writer.writeNext(entries);
			}
			writer.close();
			return true;

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}
  
} 