package com.codepath.apps.twittertimeline.activity;

import android.app.ActionBar;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.Menu;
import android.widget.TextView;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.fragment.ProfileFragment;
import com.codepath.apps.twittertimeline.models.Tweet;
import com.codepath.apps.twittertimeline.models.User;
import com.codepath.apps.twittertimeline.util.Util;

public class TweetDetailsActivity extends FragmentActivity {
	
	public static final String TWEET = "tweet";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_tweet_details);
		
		Tweet tweet = (Tweet) getIntent().getSerializableExtra(TweetDetailsActivity.TWEET);
		User user = tweet.getUser();		
		
		//pass in the user to the profile fragment
		FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
		ProfileFragment profileFragment = ProfileFragment.newInstance(user);
		ft.replace(R.id.flTweetDetailsActivityProfileView, profileFragment);
		ft.commit();
		
		TextView tweetBody = (TextView) findViewById(R.id.tvTweetDetailsActivityTweetBody);
		tweetBody.setText(Html.fromHtml(tweet.getBody()));
		
		TextView tweetTimestamp = (TextView) findViewById(R.id.tvTweetDetailsActivityTweetTimestamp);
		tweetTimestamp.setText(Util.getRelativeTS(this, tweet.getTimestamp()));
		
		//set UP enabled 
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.tweet_details, menu);
		return true;
	}

}
