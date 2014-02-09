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
import com.codepath.apps.twittertimeline.activity.ProfileActivity;
import com.codepath.apps.twittertimeline.models.User;
import com.nostra13.universalimageloader.core.ImageLoader;

public class ProfileFragment extends Fragment {
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		// Defines the xml file for the fragment
		return inflater.inflate(R.layout.fragment_profile, container, false);		
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		
		User user = ((ProfileActivity)getActivity()).getUser();
		
		ImageView imageView = (ImageView) getActivity().findViewById(R.id.imgProfile);
        ImageLoader.getInstance().displayImage(user.getProfileImageUrl(), imageView);
        
        TextView nameView = (TextView) getActivity().findViewById(R.id.tvUserName);
        String formattedName = "<b>" + user.getName() + "</b>";
        nameView.setText(Html.fromHtml(formattedName));

        TextView taglineView = (TextView) getActivity().findViewById(R.id.tvUserTagline);
        taglineView.setText(Html.fromHtml(user.getTagline()));
        
        TextView followingView = (TextView) getActivity().findViewById(R.id.tvFollowing);
        followingView.setText(user.getFollowingCount() + " " + getString(R.string.user_following));
        
        TextView followersView = (TextView) getActivity().findViewById(R.id.tvFollowers);
        followersView.setText(user.getFollowersCount() + " " + getString(R.string.user_follower));
	}

}
