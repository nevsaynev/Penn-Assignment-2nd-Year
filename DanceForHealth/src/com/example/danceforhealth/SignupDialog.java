package com.example.danceforhealth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.parse.ParseException;
import com.parse.ParseUser;
import com.parse.SignUpCallback;

public class SignupDialog extends DialogFragment{
	
	private EditText username;
	private EditText password;
	private EditText firstName;
	private EditText lastName;
	private EditText email;
	private View view;
	private Dialog mDialog;
	private Activity parent;
	
	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		parent = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.popup_window_signup, null);
		this.view = view;
		username = (EditText) view.findViewById(R.id.usernameEditText);
		password = (EditText) view.findViewById(R.id.passwordEditText);
		firstName = (EditText) view.findViewById(R.id.firstNameEditText);
		lastName = (EditText) view.findViewById(R.id.lastNameEditText);
		email = (EditText) view.findViewById(R.id.emailEditText);
		builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				
			}
		});
		builder.setPositiveButton(R.string.signup, new DialogInterface.OnClickListener() {
			
			@Override
			public void onClick(DialogInterface dialog, int which) {
				signup();
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
		
		return dialog;
	}	
	
	private boolean usernameOrPasswordEmpty(){
		username = (EditText) view.findViewById(R.id.usernameEditText);
		password = (EditText) view.findViewById(R.id.passwordEditText);
		if (username.getText().toString().trim().equals("") || password.getText().toString().trim().equals("")){
			return true;
		}
		return false;
	}

	private void signup(){
	
		
		ParseUser user = new ParseUser();
		user.setUsername(username.getText().toString());
		user.setPassword(password.getText().toString());
		if (!email.getText().toString().trim().equals(""))
			user.setEmail(email.getText().toString());
		user.put("lastName", lastName.getText().toString());
		user.put("firstName", firstName.getText().toString());
		user.signUpInBackground(new SignUpCallback() {
			  public void done(ParseException e) {
			    if (e == null) {
			    	Toast.makeText(parent.getApplicationContext(), "Successfully Sign up!", Toast.LENGTH_SHORT).show();
			    } else {
			    	switch (e.getCode()) {
                    	case ParseException.USERNAME_TAKEN:{
                    		Toast.makeText(parent.getApplicationContext(), "Sorry, this username has already been taken!", Toast.LENGTH_SHORT).show();
                    		break;
                    	}
                    	case ParseException.CONNECTION_FAILED:{
                    		Toast.makeText(parent.getApplicationContext(), "No Internet connection!", Toast.LENGTH_SHORT).show();
                            break;
                    	}
                    	case ParseException.EMAIL_MISSING :{
                    		Toast.makeText(parent.getApplicationContext(), "Missing Email Address!", Toast.LENGTH_SHORT).show();
                            break;
                    	}
                    	case ParseException.EMAIL_TAKEN :{
                    		Toast.makeText(parent.getApplicationContext(), "Email address is already taken!", Toast.LENGTH_SHORT).show();
                            break;
                    	}
                    	case ParseException.INVALID_EMAIL_ADDRESS :{
                    		Toast.makeText(parent.getApplicationContext(), "Invalid Email Address!", Toast.LENGTH_SHORT).show();
                            break;
                    	}
                        default:{
                        	Toast.makeText(parent.getApplicationContext(), "Sign up Error", Toast.LENGTH_SHORT).show();
                            break;
                        }
                    }
			    }
			  }
			});
	
	}
	

}


