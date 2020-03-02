package com.example.danceforhealth;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.PopupWindow;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.TextView;

public class FragmentHeartRate extends Fragment implements FragmentDataCollection{
	
	private EditText edittext;
	private TextView textview;
	private TextView twoTextView;
	private Button timerButton;
	private TextView threeTextView;
	private Button heartRateButton;
	private TextView heartRateTextView;
	private Button viewSummaryButton;
	private CountDownTimer timer;
	private View fragmentView;
	private Button mButton;
    private PopupWindow mPopupWindow;

	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle saveInstanceState){
		View view = inflater.inflate(R.layout.fragment_heartrate, container, false);
		fragmentView = view;
		heartRateTextView = (TextView)view.findViewById(R.id.heartRateTextView_v1);
		textview = (TextView)view.findViewById(R.id.timerTextView_v1);
		edittext = (EditText)view.findViewById(R.id.heartRateEditText_v1);
		timerButton = (Button) view.findViewById(R.id.timerButton_v1);
		Bundle bundle = getArguments();
		Workout workout = bundle.getParcelable("workout_info");
		initWorkoutInfo(workout);
		
		
		 View popupView = inflater.inflate(R.layout.pop_up_image, null);

	        mPopupWindow = new PopupWindow(popupView, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT, true);
	        mPopupWindow.setTouchable(true);
	        mPopupWindow.setOutsideTouchable(true);
	        mPopupWindow.setBackgroundDrawable(new BitmapDrawable(getResources(), (Bitmap) null));
	        mPopupWindow.getContentView().setFocusable(true);

	        mButton = (Button) view.findViewById(R.id.imageButton);
	        mButton.setOnClickListener(new OnClickListener() {

	            @Override
	            public void onClick(View v) {
	                mPopupWindow.showAsDropDown(v);
	            }
	        });
	        
	        
		timerButton.setOnClickListener(new OnClickListener(){
			@Override
            public void onClick(View v)
            {
				timer.start();
            } 
		});
		
		
		heartRateButton = (Button) view.findViewById(R.id.heartRateButton_v1);
		heartRateButton.setOnClickListener(new OnClickListener(){
			@Override
            public void onClick(View v)
            {
				String input = edittext.getText().toString();
				int numIn = Integer.parseInt(input) * 4;
				heartRateTextView.setText("\nYour heart rate is: " + numIn + "\n");
            }
		});
		
        timer = new CountDownTimer(18000, 1000) {

            @Override
			public void onTick(long millisUntilFinished) {
            	if (millisUntilFinished > 17000) {
            		textview.setText("Ready?");
            	} else if (millisUntilFinished > 16000) {
            		textview.setText("Set?");
            	} else if (millisUntilFinished > 15000) {
            		textview.setText("Start Counting!");
            	} else {
            		textview.setText("Time left: " + millisUntilFinished / 1000);
            	}
            }

            @Override
			public void onFinish() {
            	textview.setText("Stop Counting!");
            }
         };
		
		
		return view;
	} 

	@Override
	public void updateWorkoutInfo(Workout workout) {
		
		String input = edittext.getText().toString();
		int heartrate = Integer.parseInt(input) * 4;
		workout.setHeartrate(heartrate);
	}

	@Override
	public void initWorkoutInfo(Workout workout) {
		
		if (workout != null){
			edittext.setText(Integer.toString(workout.getHeartrate()/4));
		}
		
	}
}
