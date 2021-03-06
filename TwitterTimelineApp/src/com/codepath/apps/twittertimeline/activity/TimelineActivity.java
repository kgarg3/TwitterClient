package com.codepath.apps.twittertimeline.activity;

import org.json.JSONObject;

import android.app.ActionBar;
import android.app.ActionBar.Tab;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.TwitterClientApp;
import com.codepath.apps.twittertimeline.fragment.HomeTimelineFragment;
import com.codepath.apps.twittertimeline.fragment.MentionsTimelineFragment;
import com.codepath.apps.twittertimeline.listener.FragmentTabListener;
import com.codepath.apps.twittertimeline.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Main activity where the user sees tweets from their timeline. 
 * 
 * @author gargka
 *
 */
public class TimelineActivity extends FragmentActivity /*implements TabListener*/ {

	private final int REQUEST_CODE = 10;
	public static final String STATUS = "Status";
	public static final String USER = "User";
	
	private static final String TAB_HOME_TIMELINE_TAG = "HomeTimelineFragment";
	private static final String TAB_MENTIONS_TIMELINE_TAG = "MentionsTimelineFragment";

	private User loggedInUser;
	private boolean mReturningWithResult = false;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_timeline);

		setupNavigationTabs();
		getLoggedInUser();
		
		
		 // Associate searchable configuration with the SearchView
	    SearchManager searchManager =
	           (SearchManager) getSystemService(Context.SEARCH_SERVICE);
	    SearchView searchView = (SearchView) findViewById(R.id.svTimelineActionBarSearch);
	    searchView.setSearchableInfo(
	            searchManager.getSearchableInfo(getComponentName()));
	    
	    handleIntent(getIntent());

	}
	
	@Override
    protected void onNewIntent(Intent intent) {
       
		handleIntent(intent);
    }

	private void handleIntent(Intent intent) {

        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            //use the query to search your data somehow
        }
    }


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.timeline, menu);
		return true;
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


	/** TODO:
	 * On clicking message, should show the users message 
	 * @param item
	 */
	public void viewMessages(MenuItem item) {

	}


	/**
	 * Called when compose is clicked in the action bar.
	 * @param mi
	 */
	public void onComposeAction(View v) {
		Intent intent = new Intent(this, ComposeActivity.class);
		intent.putExtra(USER, loggedInUser);
		startActivityForResult(intent, REQUEST_CODE);
	}


	/**
	 * Called when user profile icon is clicked in the action bar.
	 * @param mi
	 */
	public void viewUserProfile(View v) {
		Intent intent = new Intent(this, ProfileActivity.class);
		intent.putExtra(USER, loggedInUser);
		startActivity(intent);
	}


	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		if (resultCode == RESULT_OK && requestCode == REQUEST_CODE) {	
			mReturningWithResult = true;
		}
	} 

	@Override
	protected void onPostResume() {
		super.onPostResume();
		if (mReturningWithResult) {
			// Commit your transactions here.
			//when compose successful, show the users timeline with recent post.
			android.support.v4.app.FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
			transaction.replace(R.id.flTimelineActivityTweets, new HomeTimelineFragment());
			transaction.commit();
		}

		// Reset the boolean flag back to false for next time.
		mReturningWithResult = false;
	}

	private void setupNavigationTabs() {
		ActionBar actionBar = getActionBar();
		//setup the navigation tabs
		actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_TABS);
		//add the custom flag to show custom layout AND also set the Home flag, else the tabs
		//come on top of the action bar
		actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
		actionBar.setCustomView(R.layout.action_bar_timeline_title);
		//HACK: Make the icon transparent, else you get icon, user profile icon and title in that order 
		actionBar.setIcon(android.R.color.transparent);

		Tab tabHome = actionBar.newTab().setText(R.string.tab_home).setIcon(R.drawable.ic_home)
				.setTag(TAB_HOME_TIMELINE_TAG).setTabListener(
						new FragmentTabListener<HomeTimelineFragment>(R.id.flTimelineActivityTweets, this, 
								TAB_HOME_TIMELINE_TAG, HomeTimelineFragment.class));

		Tab tabMentions = actionBar.newTab().setText(R.string.tab_mentions).setIcon(R.drawable.ic_mentions)
				.setTag(TAB_MENTIONS_TIMELINE_TAG).setTabListener(
						new FragmentTabListener<MentionsTimelineFragment>(R.id.flTimelineActivityTweets, this, 
								TAB_MENTIONS_TIMELINE_TAG, MentionsTimelineFragment.class));

		actionBar.addTab(tabHome);
		actionBar.addTab(tabMentions);
		actionBar.selectTab(tabHome);
	}

	/**
	 * Get the logged in user 
	 */
	private void getLoggedInUser() {
		TwitterClientApp.getRestClient().getLoggedInUser(new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONObject jsonUser) {
				loggedInUser = User.fromJson(jsonUser);

				setUserScreenNameInActionBar();
			}

			@Override
			public void onFailure(Throwable e, JSONObject obj){
				Toast.makeText(TimelineActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show(); 

				//since we cannot fetch the user, set the action bar's title to the activity title
				TextView tvUserScreenName = (TextView) getActionBar().getCustomView().findViewById(R.id.tvTimelineActionBarUserScreenName);
				tvUserScreenName.setText(getTitle());
			}
		});
	}

	/**
	 * Sets the user's screen name in the action bar title. 
	 */
	private void setUserScreenNameInActionBar() {	
		TextView tvUserScreenName = (TextView) getActionBar().getCustomView().findViewById(R.id.tvTimelineActionBarUserScreenName);
		tvUserScreenName.setText(loggedInUser.getScreenName());
	}

}
