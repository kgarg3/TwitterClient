package com.codepath.apps.twittertimeline.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.text.Html;
import android.view.Menu;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.models.User;

public class ProfileActivity extends FragmentActivity {

	private User user;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_profile);

		user = (User) getIntent().getSerializableExtra(TimelineActivity.USER);
		
		getActionBar().setTitle(Html.fromHtml(" <small><font color='#777777'>@" +
                user.getScreenName() + "</font></small>"));
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.profile, menu);
		return true;
	}
	
	public User getUser() {
		return user;
	}

}
