package com.codepath.apps.twittertimeline.activity;

import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.TwitterClientApp;
import com.codepath.apps.twittertimeline.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Main activity where the user sees tweets from their timeline. 
 * 
 * @author gargka
 *
 */
public class TimelineActivity extends FragmentActivity {

	private final int REQUEST_CODE = 10;
	public static final String STATUS = "Status";
	public static final String PROFILE_IMG = "profileImage";
	public static final String PROFILE_NAME = "profileName";

	private User loggedInUser;

	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);
		
		getLoggedInUser();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
	}

	/**
	 * Called when compose is clicked in the action bar.
	 * @param mi
	 */
	public void onComposeAction(MenuItem mi) {
		Intent intent = new Intent(this, ComposeActivity.class);
		intent.putExtra(STATUS, "");
		intent.putExtra(PROFILE_IMG, loggedInUser.getProfileImageUrl());
		intent.putExtra(PROFILE_NAME, loggedInUser.getScreenName());
		startActivityForResult(intent, REQUEST_CODE);
	}

	/**
	 * On clicking logout, clear the access token and take the user back to the login screen. 
	 * @param item
	 */
	public void logout(MenuItem item) {
		TwitterClientApp.getRestClient().clearAccessToken();
		Intent intent = new Intent(this, LoginActivity.class);
		startActivity(intent);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {	
			//clear out the adapter to reload the timeline from the top and set maxID to null, so 
			//that all the tweets are returned. 
//			tweetsListFragment.getAdapter().clear();
//			maxID = null;
//			showTimelineTweets();	
		}
	} 

	private void getLoggedInUser() {
		TwitterClientApp.getRestClient().getLoggedInUser(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonUser) {
				loggedInUser = User.fromJson(jsonUser);
			}

			@Override
			public void onFailure(Throwable e, JSONObject obj){
				Toast.makeText(TimelineActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show(); 

				//log to debug
				Log.d("DEBUG", obj.toString());
				Log.d("DEBUG", e.getMessage());
			}
		});
	}


}
