package com.codepath.apps.twittertimeline.fragment;

import com.codepath.apps.twittertimeline.TwitterClient.TimelineType;


public class MentionsTimelineFragment extends TimelineFragment{

	@Override
	protected TimelineType getTimelineType() {
		return TimelineType.MENTIONS_TIMELINE;
	}

}
