package com.example.danceforhealth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

public class FragmentWeightAndStep extends Fragment implements FragmentDataCollection{
	private View fragmentView;
	private EditText stepEditText;
	private EditText weightEditText;
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.fragment_weight_and_step, container, false);
		fragmentView = view;
		Bundle bundle = getArguments();
		Workout workout = bundle.getParcelable("workout_info");
		initWorkoutInfo(workout);
		
		return view;
	} 
	

	@Override
	public void updateWorkoutInfo(Workout workout) {
		
		stepEditText = (EditText) fragmentView.findViewById(R.id.stepEditText_v1);
		weightEditText = (EditText) fragmentView.findViewById(R.id.weightEditText_v1);
		if(!stepEditText.getText().toString().trim().equals("")) {
			int steps = Integer.parseInt(stepEditText.getText().toString());
			workout.setSteps(steps);
		}
		else {
			workout.setSteps(0);
		}
		if(!weightEditText.getText().toString().trim().equals("")) {
			int weight = Integer.parseInt(weightEditText.getText().toString());
			workout.setWeight(weight);
		}
		else {
			workout.setWeight(0);
		}
		
	}


	@Override
	public void initWorkoutInfo(Workout workout) {
		
		if (workout != null){
			stepEditText = (EditText) fragmentView.findViewById(R.id.stepEditText_v1);
			weightEditText = (EditText) fragmentView.findViewById(R.id.weightEditText_v1);
			stepEditText.setText(Integer.toString(workout.getSteps()));
			weightEditText.setText(Integer.toString(workout.getWeight()));
		}
	}
}
