package com.codepath.apps.twittertimeline.fragment;

import com.codepath.apps.twittertimeline.TwitterClient.TimelineType;

public class HomeTimelineFragment extends TimelineFragment{

	@Override
	protected TimelineType getTimelineType() {
		return TimelineType.HOME_TIMELINE;
	}
	
}
