package com.example.danceforhealth;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;

public class FragmentTypeAndFeel extends Fragment implements FragmentDataCollection{
	
	private Spinner spinner;
	private String selection;
	private View fragmentView;
	private RadioGroup rg_liked;
	private RadioGroup rg_fun;
	private RadioGroup rg_tired;
	private EditText edittext;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.fragment_type_and_feel, container, false);
		fragmentView = view;
		spinner = (Spinner) view.findViewById(R.id.workoutTypeSpinner_v1);
		rg_liked = (RadioGroup) view.findViewById(R.id.radioGroup1_v1);
		rg_fun = (RadioGroup) view.findViewById(R.id.RadioGroup01_v1);
		rg_tired = (RadioGroup) view.findViewById(R.id.RadioGroup04_v1);
		edittext = (EditText) view.findViewById(R.id.editText_v1);
		setSpinnerContent(view);
		selection = "Dance";
		Bundle bundle = getArguments();
		Workout workout = bundle.getParcelable("workout_info");
		initWorkoutInfo(workout);
		
		
		return view;
	} 
	
	private void setSpinnerContent( View view )
	{	
		spinner = (Spinner) view.findViewById(R.id.workoutTypeSpinner_v1);
		ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(getActivity(),
		        R.array.workouts_array, android.R.layout.simple_spinner_item);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter( adapter );
		spinner.setOnItemSelectedListener(new OnItemSelectedListener() {

			@Override
			public void onItemSelected(AdapterView<?> parent, View view,
					int position, long id) {
				switch(position) {
				case 0:
					selection = "Dance";
					break;
				case 1:
					selection = "Run";
					break;
				case 2:
					selection = "Walk";
					break;
				case 3:
					selection = "Bike";
					break;
				case 4:
					selection = "Swim";
				}
				
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {
				// TODO Auto-generated method stub
				
			}
			
		});

	}	
	

	@Override
	public void updateWorkoutInfo(Workout workout) {
		
		int id1 = rg_liked.getCheckedRadioButtonId();
		RadioButton b1 = (RadioButton) fragmentView.findViewById(id1);
		int index1 = rg_liked.indexOfChild(b1);
		workout.setLikedIndex(id1);

		int id2 = rg_fun.getCheckedRadioButtonId();
		RadioButton b2 = (RadioButton) fragmentView.findViewById(id2);
		int index2 = rg_fun.indexOfChild(b2);
		workout.setFunIndex(id2);
		
		int id3 = rg_tired.getCheckedRadioButtonId();
		RadioButton b3 = (RadioButton) fragmentView.findViewById(id3);
		int index3 = rg_tired.indexOfChild(b3);
		workout.setTiredIndex(id3);
		
		int liked = 7 - index1 + 1;
		int fun = 7 - index2 + 1;
		int tired = index3 + 1;
		
		// strain is average of survey
		int strain = (liked + fun + tired)/3;
		
		workout.setStrain(strain);
		
		int duration = Integer.parseInt(edittext.getText().toString());
		workout.setType(selection.toString());
		workout.setTime(duration);
		
	}

	@Override
	public void initWorkoutInfo(Workout workout) {

		if (workout != null){
			String type = workout.getType();
            if (!type.equals(""))
            	setSpinnerSelection(type, spinner);
			edittext.setText(Integer.toString(workout.getTime()));
			rg_liked.check(workout.getLikedIndex());
			rg_fun.check(workout.getFunIndex());
			rg_tired.check(workout.getTiredIndex());
		}
		
	}
	
	private void setSpinnerSelection(String type, Spinner spinner) {
		if(type.equals("Dance")) {
			spinner.setSelection(0);
		}
		else if(type.equals("Run")) {
			spinner.setSelection(1);
		}
		else if(type.equals("Walk")) {
			spinner.setSelection(2);
		}
		else if(type.equals("Bike")) {
			spinner.setSelection(3);
		}
		else if(type.equals("Swim")) {
			spinner.setSelection(4);
		}
	}
}
