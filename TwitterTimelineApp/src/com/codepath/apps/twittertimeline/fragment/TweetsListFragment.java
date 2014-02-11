package com.codepath.apps.twittertimeline.fragment;

import java.util.ArrayList;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.adapter.TweetsAdapter;
import com.codepath.apps.twittertimeline.models.Tweet;

import eu.erikw.PullToRefreshListView;

public class TweetsListFragment extends Fragment{

	protected  PullToRefreshListView lvTweets;
	protected TweetsAdapter adapter;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Defines the xml file for the fragment
		View view = inflater.inflate(R.layout.fragment_tweets_list, container, false);
		
		// Setup handles to view objects here
		lvTweets = (PullToRefreshListView) view.findViewById(R.id.lvTweets);		
		adapter = new TweetsAdapter(getActivity(), new ArrayList<Tweet>());
		lvTweets.setAdapter(adapter);
		
		return view;
	}

	public TweetsAdapter getAdapter() {
		return adapter;
	}


}
