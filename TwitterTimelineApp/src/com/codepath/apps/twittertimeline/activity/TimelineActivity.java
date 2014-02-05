package com.codepath.apps.twittertimeline.activity;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.TwitterClientApp;
import com.codepath.apps.twittertimeline.adapter.TweetsAdapter;
import com.codepath.apps.twittertimeline.listener.EndlessScrollListener;
import com.codepath.apps.twittertimeline.models.Tweet;
import com.codepath.apps.twittertimeline.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Main activity where the user sees tweets from their timeline. 
 * 
 * @author gargka
 *
 */
public class TimelineActivity extends Activity {

	private final int REQUEST_CODE = 10;
	public static final String STATUS = "Status";
	public static final String PROFILE_IMG = "profileImage";
	public static final String PROFILE_NAME = "profileName";

	private ListView lvTweets;
	private TweetsAdapter adapter;

	private User loggedInUser;

	/**
	 * Stores the lowest ID of the tweets received. On scrolling, tweets lesser than this ID are fetched. 
	 */
	private String maxID;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		//set up list view 
		lvTweets = (ListView) findViewById(R.id.lvTweets);
		adapter = new TweetsAdapter(this, new ArrayList<Tweet>());
		lvTweets.setAdapter(adapter);

		//scrolling should load more tweets 
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				showTimelineTweetsWrapper(); 
			}
		});

		// Set a listener to be invoked when the list should be refreshed.
//		lvTweets.setOnRefreshListener(new OnRefreshListener() {
//			@Override
//			public void onRefresh() {
//				// Your code to refresh the list contents
//				// Make sure you call listView.onRefreshComplete()
//				// once the loading is done. This can be done from here or any
//				// place such as when the network request has completed successfully.
//				adapter.clear();
//				maxID = null;
//				showTimelineTweets();
//
//			}
//		});

		getLoggedInUser();
		showTimelineTweets();
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
			adapter.clear();
			maxID = null;
			showTimelineTweets();	
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


	/**
	 * Adjusts the maxID before calling the api.
	 */
	private void showTimelineTweetsWrapper() {	
		maxID = String.valueOf(Long.valueOf(maxID) - 1);
		showTimelineTweets();
	}

	/**
	 * Calls the REST api to get home timeline tweets
	 */
	private void showTimelineTweets() {		
		TwitterClientApp.getRestClient().getTwitterHomeTimeline(maxID, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				if(tweets != null && tweets.size() > 0) {
					ArrayList<Long> tweetIds = new ArrayList<Long>();
					for(int i=0; i<tweets.size(); i++ ) {
						tweetIds.add(tweets.get(i).getId());
					}
					//set maxID to the lowest tweet ID, so that the next time on scroll only tweets
					//with ID lower than this will be returned. Tweets are always returned in 
					//decreasing order of TS. 
					maxID = Long.toString(Collections.min(tweetIds));
				}
				adapter.addAll(tweets);

				 // Now we call onRefreshComplete to signify refresh has finished
			//	lvTweets.onRefreshComplete();
				//Log.d("DEBUG", jsonTweets.toString());
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
