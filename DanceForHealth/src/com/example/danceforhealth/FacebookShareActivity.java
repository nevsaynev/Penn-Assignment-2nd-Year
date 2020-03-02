package com.example.danceforhealth;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.MalformedURLException;

import org.json.JSONException;
import org.json.JSONObject;

import com.facebook.HttpMethod;
import com.facebook.Session;
import com.facebook.android.AsyncFacebookRunner;
import com.facebook.android.AsyncFacebookRunner.RequestListener;
import com.facebook.android.DialogError;
import com.facebook.android.Facebook;
import com.facebook.android.Facebook.DialogListener;
import com.facebook.android.FacebookError;
import com.facebook.android.Util;

import android.support.v7.app.ActionBarActivity;
import android.app.DownloadManager.Request;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.StrictMode;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

public class FacebookShareActivity extends ActionBarActivity implements OnClickListener {
	ImageView loginButton;
	Facebook fb;
	SharedPreferences sp;
	ImageView share;
	AsyncFacebookRunner asyncRunner;
	Bitmap bitmap;
	private int level;
	private int workingTime;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_facebook_share);
		
		Intent intent = getIntent();
		bitmap = (Bitmap) intent.getParcelableExtra("sctreenshot");
		TakeScreenshot.savePic(bitmap, "sdcard/bitmap.png");
		
		Bundle extras = getIntent().getExtras();
		level = extras.getInt("level");
		workingTime = extras.getInt("workoutTime");
		
		sp = getPreferences(MODE_PRIVATE);
		String access_token = sp.getString("access_token", null);
		long expires = sp.getLong("access_expires", 0);
		
		String APP_ID = getString(R.string.facebook_app_id);
		fb = new Facebook(APP_ID);
		asyncRunner = new AsyncFacebookRunner(fb);
		
		if(access_token != null){
			fb.setAccessToken(access_token);
		}
		if(expires != 0){
			fb.setAccessExpires(expires);
		}
		
		
		loginButton = (ImageView)findViewById(R.id.login);
		loginButton.setOnClickListener(this);
		share = (ImageView)findViewById(R.id.shareButton);
		updateButtonImage();
		
		
	}

	private void updateButtonImage() {
		// TODO Auto-generated method stub
		if(fb.isSessionValid()){
			share.setVisibility(Button.VISIBLE);
			loginButton.setVisibility(Button.INVISIBLE);
//			loginButton.setImageResource(R.drawable.logout);
			
			
		}else{
			share.setVisibility(Button.INVISIBLE);
			loginButton.setImageResource(R.drawable.login);
		}
	}
/*
	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.facebook_share, menu);
		return true;
	}
*/
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}
	
	public void shareButtonClick(View v){
		switch(v.getId()){
		case R.id.shareButton:
			// post
			Bundle params = new Bundle();
			params.putString("name", "DanceForHealth");
			params.putString("caption", "Dance For Health");
			params.putString("description", "I am using the DanceForHealth app to keep track of my workout. I am at level " + level + " and I have worked out for "+ workingTime + " min. This app is really awesome! Let's work out and stay healthy together!");
			params.putString("link", "http://www.facebook.com");
			
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
			byte[] byteArray = stream.toByteArray();
			params.putString("picture", "http://www.ontariothedanceshop.com/dance.png");
			
		
			fb.dialog(FacebookShareActivity.this, "feed", params, new DialogListener(){

				@Override
				public void onComplete(Bundle values) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onFacebookError(FacebookError e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onError(DialogError e) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onCancel() {
					// TODO Auto-generated method stub
					
				}});
			
		}
	}
	/*
	public void shareButtonClick(View v){
		switch(v.getId()){
		case R.id.shareButton:
			
	        try
	        {
	            
	            fb.authorize(FacebookShareActivity.this, new String[]{"publish_stream", "status_update", "email"}, new DialogListener(){
					@Override
					public void onFacebookError(FacebookError e){
						Toast.makeText(FacebookShareActivity.this, "fbError", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onError(DialogError e){
						Toast.makeText(FacebookShareActivity.this, "onError", Toast.LENGTH_SHORT).show();
					}
					
					@Override
					public void onComplete(Bundle values){	
						Editor editor = sp.edit();
						editor.putString("access_token", fb.getAccessToken());
						editor.putLong("access_expires", fb.getAccessExpires());
						editor.commit();
						updateButtonImage();
						
					}
					
					@Override
					public void onCancel(){
						Toast.makeText(FacebookShareActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
					}

					
				});
	            
	            Bundle params = new Bundle();
	            byte[] data = null;
	            ByteArrayOutputStream baos = new ByteArrayOutputStream();
	            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
	            data = baos.toByteArray();
	            //params.putString("app_id", uid);
	            //params.putString("message", "picture caption");
	            //params.putByteArray("picture", data);
	            params.putString(Facebook.TOKEN, fb.getAccessToken());              
	            params.putString("method", "photos.upload");              
	            params.putByteArray("picture", data);    
	       
	            AsyncFacebookRunner mAsyncRunner = new AsyncFacebookRunner(fb);
	            //fb.authorize(this, new String[]{"publish_stream", "status_update"}, new LoginDialogListener());
	            mAsyncRunner.request("me/feed", params, "POST", new SampleUploadListener(), null);
	            
	            
	        }
	        catch (Exception e)
	        {
	            e.printStackTrace();
	        }	
		}
	}
	*/
	public class SampleUploadListener extends BaseRequestListener {

	    public void onComplete(final String response, final Object state) {
	        try {
	            // process the response here: (executed in background thread)
	            Log.d("Facebook-Example", "Response: " + response.toString());
	            JSONObject json = Util.parseJson(response);
	            final String src = json.getString("src");

	            // then post the processed result back to the UI thread
	            // if we do not do this, an runtime exception will be generated
	            // e.g. "CalledFromWrongThreadException: Only the original
	            // thread that created a view hierarchy can touch its views."

	        } catch (JSONException e) {
	            Log.w("Facebook-Example", "JSON Error in response");
	        } catch (FacebookError e) {
	            Log.w("Facebook-Example", "Facebook Error: " + e.getMessage());
	        }
	    }

	    @Override
	    public void onFacebookError(FacebookError e, Object state) {    
	    }
	}

	
	 public abstract class BaseRequestListener implements RequestListener {

		 public void onFacebookError(FacebookError e, final Object state) {
		     Log.e("Facebook", e.getMessage());
		     e.printStackTrace();
		 }

		 public void onFileNotFoundException(FileNotFoundException e,
		         final Object state) {
		     Log.e("Facebook", e.getMessage());
		     e.printStackTrace();
		 }

		 public void onIOException(IOException e, final Object state) {
		     Log.e("Facebook", e.getMessage());
		     e.printStackTrace();
		 }

		 public void onMalformedURLException(MalformedURLException e,
		         final Object state) {
		     Log.e("Facebook", e.getMessage());
		     e.printStackTrace();
		 }
	 }
	 
	 private final class LoginDialogListener implements DialogListener {
	        public void onComplete(Bundle values) {
	            //SessionEvents.onLoginSuccess();
	        }

	        public void onFacebookError(FacebookError error) {
	            //SessionEvents.onLoginError(error.getMessage());
	        }

	        public void onError(DialogError error) {
	            //SessionEvents.onLoginError(error.getMessage());
	        }

	        public void onCancel() {
	            //SessionEvents.onLoginError("Action Canceled");
	        }
	    }
	 
	@SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if(fb.isSessionValid()){
			// buttion close our session, logout facebook
			Log.v("logout","logout!!!");			
			try {
				fb.logout(getApplicationContext());
				updateButtonImage();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
							
		}else{
			Log.v("login","login!!!");
			
			//login to facebook
			fb.authorize(FacebookShareActivity.this, new String[]{"publish_stream", "status_update", "email"}, new DialogListener(){
				@Override
				public void onFacebookError(FacebookError e){
					Toast.makeText(FacebookShareActivity.this, "fbError", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onError(DialogError e){
					Toast.makeText(FacebookShareActivity.this, "onError", Toast.LENGTH_SHORT).show();
				}
				
				@Override
				public void onComplete(Bundle values){
					Editor editor = sp.edit();
					editor.putString("access_token", fb.getAccessToken());
					editor.putLong("access_expires", fb.getAccessExpires());
					editor.commit();
					updateButtonImage();
					
				}
				
				@Override
				public void onCancel(){
					Toast.makeText(FacebookShareActivity.this, "onCancel", Toast.LENGTH_SHORT).show();
				}

				
			});
			
		}
	}
	
	@SuppressWarnings("deprecation")
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data){
		super.onActivityResult(requestCode, resultCode, data);
		fb.authorizeCallback(requestCode, resultCode, data);
	}
	
	public void onHomeClick(View view) {
		// create an Intent using the current Activity 
		// and the Class to be created
		Intent intent = new Intent(this, HomeActivity.class);

		// pass the Intent to the Activity, 
		// using the specified request code
		startActivity(intent);
		finish();	
	}
}
















