package com.codepath.apps.twittertimeline.models;

import java.io.Serializable;
import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class User implements Serializable{
	private static final long serialVersionUID = -3248195833653435147L;

	private String name;
	private long id;
	private String screenName;
	private String profileImageUrl;
	private String profileBackgroundImageUrl;
	private int numTweets;
	private int followers;
	private int following;
	private String tagline;


	public String getName() {
		return name;
	}

	public long getId() {
		return id;
	}

	public String getScreenName() {
		return screenName;
	}

	public String getProfileImageUrl() {
		return profileImageUrl;
	}

	public String getProfileBackgroundImageUrl() {
		return profileBackgroundImageUrl;
	}

	public int getNumTweets() {
		return numTweets;
	}

	public int getFollowersCount() {
		return followers;
	}

	public int getFollowingCount() {
		return following;
	}

	public String getTagline() {
		return tagline;
	}

	public static User fromJson(JSONObject jsonObject) {
		User user = new User();
		// Deserialize json into object fields
		try {
			user.id = Long.valueOf(jsonObject.getString("id"));
			user.name = jsonObject.getString("name");
			user.screenName = jsonObject.getString("screen_name"); 	 
			user.profileImageUrl = jsonObject.getString("profile_image_url");
			user.profileBackgroundImageUrl = jsonObject.getString("profile_background_image_url");
			user.numTweets = Integer.valueOf(jsonObject.getString("statuses_count"));
			user.followers = Integer.valueOf(jsonObject.getString("followers_count"));
			user.following = Integer.valueOf(jsonObject.getString("friends_count")); 	 
			user.tagline = jsonObject.getString("description");
		} catch (JSONException e) {
			e.printStackTrace();
			return null;
		}

		return user;
	}
	
	public static ArrayList<User> fromJson(JSONArray jsonArray) {
		ArrayList<User> users = new ArrayList<User>(jsonArray.length());

		for (int i=0; i < jsonArray.length(); i++) {
			JSONObject userJson = null;
			try {
				userJson = jsonArray.getJSONObject(i);
			} catch (Exception e) {
				e.printStackTrace();
				continue;
			}

			User user = User.fromJson(userJson);
			if (user != null) {
				users.add(user);
			}
		}

		return users;
	}


}