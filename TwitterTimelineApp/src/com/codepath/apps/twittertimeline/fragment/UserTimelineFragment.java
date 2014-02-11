package com.codepath.apps.twittertimeline.fragment;

import android.os.Bundle;

import com.codepath.apps.twittertimeline.TwitterClient.TimelineType;
import com.codepath.apps.twittertimeline.activity.TimelineActivity;
import com.codepath.apps.twittertimeline.models.User;

public class UserTimelineFragment extends TimelineFragment{	
	private User user;
	
	public static UserTimelineFragment newInstance(User user) {
		UserTimelineFragment userTimelineFragment = new UserTimelineFragment();
		Bundle args = new Bundle();
		args.putSerializable(TimelineActivity.USER,user);
		userTimelineFragment.setArguments(args);
		return userTimelineFragment;
	}
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get back arguments
		this.user = (User) getArguments().getSerializable(TimelineActivity.USER); 
	}
	
	@Override
	protected TimelineType getTimelineType() {
		return TimelineType.USERS_TIMELINE;
	}

	@Override
	protected void addRequestParams() {
		super.addRequestParams();
		map.put("user_id", user.getId());
	}
	
}
