package com.codepath.apps.twittertimeline.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Tweet implements Serializable{
	private static final long serialVersionUID = 1L;
	
	private User user;
	private String body;
	private long id;
	private boolean isFavorited; 
	private boolean isRetweeted;
	private String timestamp;

	public User getUser() {
		return user;
	}

	public long getId() {
		return id;
	}

	public String getBody() {
		return body;
	}


	public boolean isFavorited() {
		return isFavorited;
	}

	public boolean isRetweeted() {
		return isRetweeted;
	}

	public String getTimestamp() {
		return timestamp;
	}

	// Decodes business json into business model object
	public static Tweet fromJson(JSONObject jsonObject) {
		Tweet tweet = new Tweet();
		// Deserialize json into object fields
		try {
			tweet.id = Long.valueOf(jsonObject.getString("id"));
			tweet.body = jsonObject.getString("text");
			tweet.isFavorited = Boolean.valueOf(jsonObject.getString("favorited")); 	 
			tweet.isRetweeted = Boolean.valueOf(jsonObject.getString("retweeted"));
			tweet.timestamp = jsonObject.getString("created_at");
			tweet.user = User.fromJson(jsonObject.getJSONObject("user"));
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}
		
		return tweet;
	}

	public static ArrayList<Tweet> fromJson(JSONArray jsonArray) {
		ArrayList<Tweet> tweets = new ArrayList<Tweet>(jsonArray.length());

		for (int i=0; i < jsonArray.length(); i++) {
			JSONObject tweetJson = null;
			try {
				tweetJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			Tweet tweet = Tweet.fromJson(tweetJson);
			if (tweet != null) {
				tweets.add(tweet);
			}
		}

		return tweets;
	}
}
