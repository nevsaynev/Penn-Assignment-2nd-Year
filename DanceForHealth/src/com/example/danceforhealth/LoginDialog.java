package com.example.danceforhealth;

import java.util.Arrays;
import java.util.List;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.FindCallback;
import com.parse.LogInCallback;
import com.parse.ParseException;
import com.parse.ParseFacebookUtils;
import com.parse.ParseQuery;
import com.parse.ParseUser;

public class LoginDialog extends DialogFragment{
	
	private EditText username;
	private EditText password;
	private Button facebookBtn;
	private View view;
	private Dialog mDialog;
	private Activity parent;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		parent = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.popup_window_login, null);
		this.view = view;
		username = (EditText) view.findViewById(R.id.usernameEditText);
		password = (EditText) view.findViewById(R.id.passwordEditText);
		facebookBtn = (Button) view.findViewById(R.id.facebookBtn);
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.setPositiveButton(R.string.login, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				
				login();
			}
		});
		
		
		builder.setView(view);
		Dialog dialog = builder.create();
		mDialog = dialog;
		
		((AlertDialog) dialog).setOnShowListener(new OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				
				((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				
			}
		});
		
		username.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				if (!usernameOrPasswordEmpty()){
					((AlertDialog) mDialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
				}else{
					((AlertDialog) mDialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				}
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		});
		
		password.addTextChangedListener(new TextWatcher() {
			
			@Override
			public void afterTextChanged(Editable s) {
				if (!usernameOrPasswordEmpty()){
					((AlertDialog) mDialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(true);
				}else{
					((AlertDialog) mDialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				}
				
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub
				
			}
		});
		
		facebookBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View view) {
				List<String> permissions = Arrays.asList("public_profile", "user_friends", "user_about_me",
			            "user_relationships", "user_birthday", "user_location");
				
				ParseFacebookUtils.logIn(permissions, parent, new LogInCallback() {
					
					  @Override
					  public void done(ParseUser user, ParseException err) {
					    if (user == null) {
					      Log.v("1", "2");
					      if (user == null) {
					          Toast.makeText(parent.getApplicationContext(), err.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
					          Log.v("1", err.getLocalizedMessage());
					    }
					    } else if (user.isNew()) {
					    	Log.v("1", "Signed up and logged in through Facebook!");
					    	Toast.makeText(parent.getApplicationContext(), "Signed up and logged in through Facebook!", Toast.LENGTH_SHORT).show();
					    } else {
					    	Log.v("1", "Logged in through Facebook!");
					    	Toast.makeText(parent.getApplicationContext(), "Logged in through Facebook!", Toast.LENGTH_SHORT).show();
					    	retriveCloudData(user);
					    }
					    mDialog.dismiss();
					  }
					});
				
			}
		});
		
		return dialog;
	}	
	
	
	@Override
    public void onStart()
    {
        super.onStart();
        Button pButton =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_POSITIVE);
        Button nButton =  ((AlertDialog) getDialog()).getButton(DialogInterface.BUTTON_NEGATIVE);

        pButton.setBackground(getResources().getDrawable(R.drawable.apptheme_btn_default_holo_light));
        nButton.setBackground(getResources().getDrawable(R.drawable.apptheme_btn_default_holo_light));
        pButton.setTextColor(Color.WHITE);
        nButton.setTextColor(Color.WHITE);
    }
	
	private boolean usernameOrPasswordEmpty(){
		username = (EditText) view.findViewById(R.id.usernameEditText);
		password = (EditText) view.findViewById(R.id.passwordEditText);
		if (username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")){
			return true;
		}
		return false;
	}
	
	private void login(){
		
		String userNameText = username.getText().toString();
        String passwordText = password.getText().toString();
		ParseUser.logInInBackground(userNameText, passwordText, new LogInCallback() {
			  public void done(ParseUser user, ParseException e) {
			    if (user != null) {
			    	Toast.makeText(parent.getApplicationContext(), "Successfully Logged in!", Toast.LENGTH_SHORT).show();
			    	retriveCloudData(user);
			    }else if(e.getCode() == ParseException.CONNECTION_FAILED){ 
			    	Toast.makeText(parent.getApplicationContext(), "No Internet connection!", Toast.LENGTH_SHORT).show();
			    }else {
			    	Toast.makeText(parent.getApplicationContext(), "Wrong username/password!", Toast.LENGTH_SHORT).show();
			    }
			  }
		});
	}
	
	private void retriveCloudData(ParseUser user){
		ParseQuery<WorkoutDataStore> query = ParseQuery.getQuery(WorkoutDataStore.class);
		query.whereEqualTo("User", user);
		query.findInBackground(new FindCallback<WorkoutDataStore>() {
            @Override
            public void done(List<WorkoutDataStore> workouts, ParseException e) {
                 if (e == null) {
                	 for (WorkoutDataStore workout : workouts){
                		 workout.pinInBackground();
                	 }
                 }else{

                 }   
            }
        });
	}
}
