package com.codepath.apps.twittertimeline.fragment;

import java.util.ArrayList;
import java.util.Collections;

import org.json.JSONArray;
import org.json.JSONObject;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.codepath.apps.twittertimeline.TwitterClientApp;
import com.codepath.apps.twittertimeline.listener.EndlessScrollListener;
import com.codepath.apps.twittertimeline.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import eu.erikw.PullToRefreshListView.OnRefreshListener;

public class HomeTimelineFragment extends TweetsListFragment{
	
	/**
	 * Stores the lowest ID of the tweets received. On scrolling, tweets lesser than this ID are fetched. 
	 */
	private String maxID;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		showTimelineTweets();		
	}
	
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = super.onCreateView(inflater, container, savedInstanceState);
		
		//scrolling should load more tweets 
		lvTweets.setOnScrollListener(new EndlessScrollListener() {
			@Override
			public void onLoadMore(int page, int totalItemsCount) {
				if(maxID != null)
					maxID = String.valueOf(Long.valueOf(maxID) - 1);
				showTimelineTweets(); 
			}
		});

		// Set a listener to be invoked when the list should be refreshed.
		lvTweets.setOnRefreshListener(new OnRefreshListener() {
			@Override
			public void onRefresh() {
				// Your code to refresh the list contents
				// Make sure you call listView.onRefreshComplete()
				// once the loading is done. This can be done from here or any
				// place such as when the network request has completed successfully.
				adapter.clear();
				maxID = null;
				showTimelineTweets();

				// Now we call onRefreshComplete to signify refresh has finished
				lvTweets.onRefreshComplete();

			}
		});
		
		return view;
	}
	
	
	
	
	/**
	 * Calls the REST api to get home timeline tweets
	 */
	private void showTimelineTweets() {		
		TwitterClientApp.getRestClient().getTwitterHomeTimeline(maxID, new JsonHttpResponseHandler() {
			@Override
			public void onSuccess(JSONArray jsonTweets) {
				ArrayList<Tweet> tweets = Tweet.fromJson(jsonTweets);
				if(tweets != null && tweets.size() > 0) {
					ArrayList<Long> tweetIds = new ArrayList<Long>();
					for(int i=0; i<tweets.size(); i++ ) {
						tweetIds.add(tweets.get(i).getId());
					}
					//set maxID to the lowest tweet ID, so that the next time on scroll only tweets
					//with ID lower than this will be returned. Tweets are always returned in 
					//decreasing order of TS. 
					maxID = Long.toString(Collections.min(tweetIds));
				}
				
				//add tweets to adapter
				getAdapter().addAll(tweets);
			}

			@Override
			public void onFailure(Throwable e, JSONObject obj){
				Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show(); 
			}
		});
	}


}
