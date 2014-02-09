package com.codepath.apps.twittertimeline.fragment;

import com.codepath.apps.twittertimeline.TwitterClient.TimelineType;

public class UserTimelineFragment extends TimelineFragment{

	@Override
	protected TimelineType getTimelineType() {
		return TimelineType.USERS_TIMELINE;
	}

}
