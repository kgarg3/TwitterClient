package com.codepath.apps.twittertimeline.fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.activity.TimelineActivity;
import com.codepath.apps.twittertimeline.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileFragment extends Fragment {
	private User user;

	public static ProfileFragment newInstance(User user) {
		ProfileFragment profileFragment = new ProfileFragment();
		Bundle args = new Bundle();
		args.putSerializable(TimelineActivity.USER,user);
		profileFragment.setArguments(args);
		return profileFragment;
	}

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		// Get back arguments
		this.user = (User) getArguments().getSerializable(TimelineActivity.USER); 
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Defines the xml file for the fragment
		View view =  inflater.inflate(R.layout.fragment_profile, container, false);
		
		ImageView imgBckgrdView = (ImageView) view.findViewById(R.id.imgProfileFragmentUserBackgroundImage);
		ImageLoader.getInstance().displayImage(user.getProfileBackgroundImageUrl(), imgBckgrdView);

		ImageView imgView = (ImageView) view.findViewById(R.id.imgProfileFragmentUserImage);
		ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), imgView);

		TextView nameView = (TextView) view.findViewById(R.id.tvProfileFragmentUserName);
		String formattedName = "<b>" + user.getName() + "</b>";
		nameView.setText(Html.fromHtml(formattedName));

		TextView taglineView = (TextView) view.findViewById(R.id.tvProfileFragmentUserTagline);
		taglineView.setText(Html.fromHtml(user.getTagline()));
		
		TextView numTweetsView = (TextView) view.findViewById(R.id.tvProfileFragmentUserNumTweets);
		numTweetsView.setText(Html.fromHtml("<font color='#000000'><b>" + user.getNumTweets() + "</font></b>" 
				+ "<br />" + getString(R.string.user_num_tweets)));

		TextView followingView = (TextView) view.findViewById(R.id.tvProfileFragmentUserFollowing);
		followingView.setText(Html.fromHtml("<font color='#000000'><b>" + user.getFollowingCount() + "</font></b>" 
				+ "<br />" + getString(R.string.user_following)));

		TextView followersView = (TextView) view.findViewById(R.id.tvProfileFragmentUserFollowers);
		followersView.setText(Html.fromHtml("<font color='#000000'><b>" + user.getFollowersCount() + "</font></b>" 
				+  "<br />" + getString(R.string.user_follower)));

		return view;
	}

}
