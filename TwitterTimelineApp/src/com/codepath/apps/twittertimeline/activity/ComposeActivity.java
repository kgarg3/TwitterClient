package com.codepath.apps.twittertimeline.activity;

import org.json.JSONObject;

import android.app.ActionBar;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.TwitterClientApp;
import com.codepath.apps.twittertimeline.fragment.ProfileFragment;
import com.codepath.apps.twittertimeline.models.User;
import com.loopj.android.http.JsonHttpResponseHandler;

/**
 * Compose Activity to allow users to compose their tweets and update their statuses. 
 * 
 * @author gargka
 *
 */
public class ComposeActivity extends FragmentActivity {
	private EditText etComposedTweet;
	private Menu menu;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);

		//pass the user object to the underlying fragments 
		User user = (User) getIntent().getSerializableExtra(TimelineActivity.USER);		

		if (savedInstanceState == null) {
			FragmentTransaction ft = getSupportFragmentManager().beginTransaction();		
			//pass in the user to the profile fragment 
			ProfileFragment profileFragment = ProfileFragment.newInstance(user);
			ft.replace(R.id.flComposeActivityProfileView, profileFragment);
			ft.commit();
		}

		etComposedTweet = (EditText) findViewById(R.id.etComposeTweet);
		etComposedTweet.addTextChangedListener( new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before, int count) { }

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

			@Override
			public void afterTextChanged(Editable s) {
				//if the status has no text, then disable tweet else enable it.
				menu.findItem(R.id.mi_tweet).setEnabled(
						etComposedTweet.getText().toString().length() > 0 ? true : false);
			}
		});

		//set UP enabled 
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		this.menu = menu;
		getMenuInflater().inflate(R.menu.compose, menu);
		return true;
	}

	public void onTweetAction(MenuItem mi) {

		//Post the tweet using the rest client
		TwitterClientApp.getRestClient().postTweet(etComposedTweet.getText().toString(), new JsonHttpResponseHandler() {

			@Override
			public void onSuccess(JSONObject jsonObj) {
				Intent intent = new Intent();
				setResult(TimelineActivity.RESULT_OK, intent); 
				finish(); 
			}

			@Override
			public void onFailure(Throwable e, JSONObject obj){
				Toast.makeText(ComposeActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
			}
		});

	}
}
