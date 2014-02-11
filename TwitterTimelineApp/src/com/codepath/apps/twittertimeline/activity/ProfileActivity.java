package com.codepath.apps.twittertimeline.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Html;
import android.view.Menu;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.fragment.ProfileFragment;
import com.codepath.apps.twittertimeline.fragment.UserTimelineFragment;
import com.codepath.apps.twittertimeline.models.User;

public class ProfileActivity extends FragmentActivity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		//pass the user object to the underlying fragments 
		User user = (User) getIntent().getSerializableExtra(TimelineActivity.USER);		
		
		if (savedInstanceState == null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();		
			//pass in the user to the profile fragment 
			ProfileFragment profileFragment = ProfileFragment.newInstance(user);
			ft.replace(R.id.flProfileView, profileFragment);
			//pass in the user to the usertimelinefragment to fetch the tweets for this particular user. 
			UserTimelineFragment userTimelineFragment = UserTimelineFragment.newInstance(user);
			ft.replace(R.id.flUserTweetsView, userTimelineFragment);
			ft.commit();
		}

		//set the title as the screen name
		getActionBar().setTitle(Html.fromHtml(" <small><font color='#777777'>@" +
				user.getScreenName() + "</font></small>"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}

}
