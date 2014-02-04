package com.codepath.apps.twittertimeline.activity;

import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.TwitterClientApp;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.nostra13.universalimageloader.core.ImageLoader;

/**
 * Compose Activity to allow users to compose their tweets and update their statuses. 
 * 
 * @author gargka
 *
 */
public class ComposeActivity extends Activity {
	private EditText etComposedTweet;
	private ImageView ivProfileImage;
	private TextView tvProfileName;
	private Menu menu;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_compose);
		
		Intent intent = getIntent();
		
		//set up the views
		ivProfileImage = (ImageView) findViewById(R.id.ivProfileImg);
		ImageLoader.getInstance().displayImage(intent.getStringExtra(TimelineActivity.PROFILE_IMG), ivProfileImage);
		
		tvProfileName = (TextView) findViewById(R.id.tvProfileName);
		tvProfileName.setText(intent.getStringExtra(TimelineActivity.PROFILE_NAME));
		
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
				intent.putExtra(TimelineActivity.STATUS, etComposedTweet.getText().toString());
				setResult(TimelineActivity.RESULT_OK, intent); 
				finish(); 
			}
			
			@Override
			public void onFailure(Throwable e, JSONObject obj){
				Log.d("DEBUG", obj.toString());
				Log.d("DEBUG", e.getMessage());
			}
		});
		
	}
}
