package com.example.danceforhealth;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.content.DialogInterface.OnShowListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.parse.ParseUser;

public class AccountDialog extends DialogFragment {
	private TextView username;
	private TextView firstName;
	private TextView lastName;
	private TextView email;
	private View view;
	private Dialog mDialog;
	private Activity parent;

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		
		parent = getActivity();
		AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
		LayoutInflater inflater = getActivity().getLayoutInflater();
		View view = inflater.inflate(R.layout.popup_window_account, null);
		this.view = view;
		
		//TODO set user infomation
		username = (TextView) view.findViewById(R.id.usernameTextView);
		firstName = (TextView) view.findViewById(R.id.firstNameTextView);
		lastName =(TextView) view.findViewById(R.id.lastNameTextView);
		email = (TextView) view.findViewById(R.id.emailTextView);
		ParseUser currentUser = ParseUser.getCurrentUser();
		if (currentUser.getObjectId() != null) {
			username.setText("User Name: "+ currentUser.getUsername());
			firstName.setText("First Name: " +currentUser.get("firstname"));
			lastName.setText("Last Name: " + currentUser.get("lastname"));
			email.setText("Email Address: "+currentUser.getEmail()==null?"":currentUser.getEmail());
		}
		
		builder.setView(view);
		Dialog dialog = builder.create();
		mDialog = dialog;
		
		((AlertDialog) dialog).setOnShowListener(new OnShowListener() {
			
			@Override
			public void onShow(DialogInterface dialog) {
				
				((AlertDialog) dialog).getButton(AlertDialog.BUTTON_POSITIVE).setEnabled(false);
				
			}
		});
		return mDialog;
	}
}
