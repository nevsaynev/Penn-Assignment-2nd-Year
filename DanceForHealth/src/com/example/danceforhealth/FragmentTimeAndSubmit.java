package com.example.danceforhealth;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class FragmentTimeAndSubmit extends Fragment implements FragmentDataCollection{
	
	private TimePicker timePicker;
	private Button setTimePickerButton;
	private Button setDatePickerButton;
	private Button submitButton;
	private Button cancelButton;
	private TextView submitTimeView;
	private TextView submitDateView;
	private Communicator communicator;
	private View fragmentView;
	private Date date;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.fragment_time_and_submit, container, false);
		fragmentView = view;
		Calendar calendar = Calendar.getInstance();
		timePicker = (TimePicker) view.findViewById(R.id.timePicker1);
		setTimePickerButton = (Button) view.findViewById(R.id.setTimePicker);
		setDatePickerButton = (Button) view.findViewById(R.id.setDatePicker);
		submitButton = (Button) view.findViewById(R.id.dateAndTimeSubmit);
		cancelButton = (Button) view.findViewById(R.id.dateAndTimeCancel);
		submitTimeView = (TextView) view.findViewById(R.id.submitTimeView);
		submitDateView = (TextView) view.findViewById(R.id.submitDateView);
		submitTimeView.setText(timePicker.getCurrentHour() + " : " + timePicker.getCurrentMinute());
		submitDateView.setText(getFormatDate(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar  
                .get(Calendar.DAY_OF_MONTH)));
		Bundle bundle = getArguments();
		Workout workout = bundle.getParcelable("workout_info");
		initWorkoutInfo(workout);
		
		
		setTimePickerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				submitTimeView.setText(timePicker.getCurrentHour() + " : " + timePicker.getCurrentMinute());
				Toast.makeText(v.getContext(), "Set Time:"+timePicker.getCurrentHour() + " : " + timePicker.getCurrentMinute(), Toast.LENGTH_SHORT).show();
			}
		});
		
		
		setDatePickerButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Calendar calendar = Calendar.getInstance();  
				new DatePickerDialog(v.getContext(), new DatePickerDialog.OnDateSetListener() {  
					  
     				@Override
					public void onDateSet(DatePicker view, int year, int month,
							int day) {
						// TODO Auto-generated method stub
     					submitDateView.setText(getFormatDate(year, month, day));
     					date = new Date(year - 1900, month, day);
     					Toast.makeText(view.getContext(), "Set Time:"+getFormatDate(year, month, day), Toast.LENGTH_SHORT).show();
					}  
                }  , calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar  
                        .get(Calendar.DAY_OF_MONTH)).show();
			}
		});
		
		cancelButton.setOnClickListener(new OnClickListener() {		
			@Override
			public void onClick(View v) {		
				communicator.cancelWorkoutData();
			}
		});
		submitButton.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				communicator.collectData(new Workout());
			}
		});
		
		return view;
	} 
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState){
		super.onActivityCreated(savedInstanceState);
		communicator = (Communicator) getActivity();
	}
	
	
	private String getFormatDate(int year, int month,
			int day){
		SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
		sdf.applyPattern("E  MMM/dd/yyyy");
		date = new Date(year - 1900, month, day);
		return sdf.format(date);
	}
	
	@Override
	public void updateWorkoutInfo(Workout workout) {
		
		String workoutDate = (String) submitDateView.getText();
		String workoutTime = (String) submitTimeView.getText();
		workout.setWorkoutDate(workoutDate);
		workout.setWorkoutTime(workoutTime);
		SimpleDateFormat f1 = new SimpleDateFormat("w");
		SimpleDateFormat f2 = new SimpleDateFormat("D");
		workout.setWeek(Integer.parseInt(f1.format(date)));
		workout.setDay(Integer.parseInt(f2.format(date)));
		
	}

	@Override
	public void initWorkoutInfo(Workout workout) {
		
		if (workout != null){
			submitTimeView.setText(workout.getWorkoutTime());
			submitDateView.setText(workout.getDate());
		}
		
	}
}
