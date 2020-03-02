package com.example.danceforhealth;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.view.Display;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.danceforhealth.util.SystemUiHider;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 * 
 * @see SystemUiHider
 */
public class CongratulationsActivity extends Activity implements OnClickListener {
	/**
	 * Whether or not the system UI should be auto-hidden after
	 * {@link #AUTO_HIDE_DELAY_MILLIS} milliseconds.
	 */
	private static final boolean AUTO_HIDE = true;

	/**
	 * If {@link #AUTO_HIDE} is set, the number of milliseconds to wait after
	 * user interaction before hiding the system UI.
	 */
	private static final int AUTO_HIDE_DELAY_MILLIS = 3000;

	/**
	 * If set, will toggle the system UI visibility upon interaction. Otherwise,
	 * will show the system UI visibility upon interaction.
	 */
	private static final boolean TOGGLE_ON_CLICK = true;

	/**
	 * The flags to pass to {@link SystemUiHider#getInstance}.
	 */
	private static final int HIDER_FLAGS = SystemUiHider.FLAG_HIDE_NAVIGATION;

	/**
	 * The instance of the {@link SystemUiHider} for this activity.
	 */
	private SystemUiHider mSystemUiHider;
	private int level;
	private int workingTime;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Display display = getWindowManager().getDefaultDisplay();
		Point size = new Point();
		display.getSize(size);
		FrameLayout.LayoutParams levellp = new FrameLayout.LayoutParams((int)(0.1*size.x), (int)(0.1*size.y));
		levellp.setMargins((int)(0.45 * size.x), (int)(0.41*size.y), 0, 0);
		FrameLayout.LayoutParams totalTimelp = new FrameLayout.LayoutParams((int)(0.5*size.x), (int)(0.1*size.y));
		totalTimelp.setMargins((int)(0.33 * size.x), (int)(0.52*size.y), 0, 0);
		FrameLayout.LayoutParams fbShareButtonlp = new FrameLayout.LayoutParams((int)(0.5*size.x), (int)(0.1*size.y));
		fbShareButtonlp.setMargins((int)(0.28 * size.x), (int)(0.8*size.y), 0, 0);
		
		setContentView(R.layout.activity_congratulations);
		Bundle extras = getIntent().getExtras();
 		if (extras != null) {
 			level = extras.getInt("level");
 			workingTime = extras.getInt("workingTime");
 			TextView levelNum = (TextView) findViewById(R.id.levelNumTextview);
 			TextView totalTime = (TextView) findViewById(R.id.workoutTimeTextview);
 			
 			
 			int hour = workingTime/60;
 			int minute = workingTime%60;
 			
 			levelNum.setText(String.valueOf(level));
 			levelNum.setLayoutParams(levellp);
 			totalTime.setText(String.valueOf(hour) + "h" + String.valueOf(minute) + "min");
 			totalTime.setLayoutParams(totalTimelp);
 			
 		}
 		
 		ImageView facebookShareButton = (ImageView) findViewById(R.id.facebookShareButton);
 		facebookShareButton.setLayoutParams(fbShareButtonlp);	
		facebookShareButton.setOnClickListener(this);
 		
		final View controlsView = findViewById(R.id.fullscreen_content_controls);
		final View contentView = findViewById(R.id.levelNumTextview);

		// Set up an instance of SystemUiHider to control the system UI for
		// this activity.
		mSystemUiHider = SystemUiHider.getInstance(this, contentView,
				HIDER_FLAGS);
		mSystemUiHider.setup();
		mSystemUiHider
				.setOnVisibilityChangeListener(new SystemUiHider.OnVisibilityChangeListener() {
					// Cached values.
					int mControlsHeight;
					int mShortAnimTime;

					@Override
					@TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
					public void onVisibilityChange(boolean visible) {
						if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
							// If the ViewPropertyAnimator API is available
							// (Honeycomb MR2 and later), use it to animate the
							// in-layout UI controls at the bottom of the
							// screen.
							if (mControlsHeight == 0) {
								mControlsHeight = controlsView.getHeight();
							}
							if (mShortAnimTime == 0) {
								mShortAnimTime = getResources().getInteger(
										android.R.integer.config_shortAnimTime);
							}
							controlsView
									.animate()
									.translationY(visible ? 0 : mControlsHeight)
									.setDuration(mShortAnimTime);
						} else {
							// If the ViewPropertyAnimator APIs aren't
							// available, simply show or hide the in-layout UI
							// controls.
							controlsView.setVisibility(visible ? View.VISIBLE
									: View.GONE);
						}

						if (visible && AUTO_HIDE) {
							// Schedule a hide().
							delayedHide(AUTO_HIDE_DELAY_MILLIS);
						}
					}
				});

		// Set up the user interaction to manually show or hide the system UI.
		contentView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				if (TOGGLE_ON_CLICK) {
					mSystemUiHider.toggle();
				} else {
					mSystemUiHider.show();
				}
			}
		});

		// Upon interacting with UI controls, delay any scheduled hide()
		// operations to prevent the jarring behavior of controls going away
		// while interacting with the UI.
		findViewById(R.id.homeButton).setOnTouchListener(
				mDelayHideTouchListener);
				
		//final Intent i = new Intent(this, HomeActivity.class);
		//CountDownTimer timer = new CountDownTimer(2000, 1000) {

			//@Override
			//public void onTick(long millisUntilFinished) {				
			//}

			//@Override
			//public void onFinish() {
				
			//	startActivity(i);
			//}			
		//};
		//timer.start();
	}

	@Override
	protected void onPostCreate(Bundle savedInstanceState) {
		super.onPostCreate(savedInstanceState);

		// Trigger the initial hide() shortly after the activity has been
		// created, to briefly hint to the user that UI controls
		// are available.
		delayedHide(100);
	}

	/**
	 * Touch listener to use for in-layout UI controls to delay hiding the
	 * system UI. This is to prevent the jarring behavior of controls going away
	 * while interacting with activity UI.
	 */
	View.OnTouchListener mDelayHideTouchListener = new View.OnTouchListener() {
		@Override
		public boolean onTouch(View view, MotionEvent motionEvent) {
			if (AUTO_HIDE) {
				delayedHide(AUTO_HIDE_DELAY_MILLIS);
			}
			return false;
		}
	};

	Handler mHideHandler = new Handler();
	Runnable mHideRunnable = new Runnable() {
		@Override
		public void run() {
			mSystemUiHider.hide();
		}
	};

	/**
	 * Schedules a call to hide() in [delay] milliseconds, canceling any
	 * previously scheduled calls.
	 */
	private void delayedHide(int delayMillis) {
		mHideHandler.removeCallbacks(mHideRunnable);
		mHideHandler.postDelayed(mHideRunnable, delayMillis);
	}
	
	public void onHomeClick(View view) {
		Intent intent = new Intent(this, HomeActivity.class);
		startActivity(intent);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		// take a sceenshot
		
		Bitmap bm = TakeScreenshot.takeScreenshot(this);
		
		Bitmap scaledBm = scaleDownBitmap(bm, 100, this);
		//TakeScreenshot.savePic(scaledBm, "sdcard/ScaledBm.png");
		//TakeScreenshot.savePic(bm, "sdcard/bm.png");
        
		Intent i = new Intent(this, FacebookShareActivity.class).putExtra("sctreenshot", scaledBm).putExtra("level", level).putExtra("workoutTime", workingTime);
		startActivity(i);
	}
	
	public Bitmap scaleDownBitmap(Bitmap photo, int newHeight, Context context) {

		 final float densityMultiplier = context.getResources().getDisplayMetrics().density;        

		 int h= (int) (newHeight*densityMultiplier);
		 int w= (int) (h * photo.getWidth()/((double) photo.getHeight()));

		 photo=Bitmap.createScaledBitmap(photo, w, h, true);

		 return photo;
	}
	

}
