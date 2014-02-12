package com.codepath.apps.twittertimeline.adapter;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.twittertimeline.R;
import com.codepath.apps.twittertimeline.activity.ProfileActivity;
import com.codepath.apps.twittertimeline.activity.TimelineActivity;
import com.codepath.apps.twittertimeline.activity.TweetDetailsActivity;
import com.codepath.apps.twittertimeline.models.Tweet;
import com.codepath.apps.twittertimeline.util.Util;
import com.nostra13.universalimageloader.core.ImageLoader;

public class TweetsAdapter extends ArrayAdapter<Tweet> {

	public TweetsAdapter(Context context, List<Tweet> tweets) {
		super(context, 0, tweets);
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
	    View view = convertView;
	    if (view == null) {
	    	LayoutInflater inflater = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	    	view = inflater.inflate(R.layout.tweet_item, null);
	    }
	    
        final Tweet tweet = getItem(position);
        
        view.setOnClickListener(new OnClickListener() {			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), TweetDetailsActivity.class);
				intent.putExtra(TweetDetailsActivity.TWEET, tweet);
				getContext().startActivity(intent);
			}
		});
        
        ImageView imageView = (ImageView) view.findViewById(R.id.ivProfile);
        ImageLoader.getInstance().displayImage(tweet.getUser().getProfileImageUrl(), imageView);
        imageView.setOnClickListener( new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(getContext(), ProfileActivity.class);
				intent.putExtra(TimelineActivity.USER, tweet.getUser());
				getContext().startActivity(intent);
			}
		});
        
        TextView tvName = (TextView) view.findViewById(R.id.tvName);
        String formattedName = "<b>" + tweet.getUser().getName() + "</b>" + " <small><font color='#777777'>@" +
                tweet.getUser().getScreenName() + "</font></small>";
        tvName.setText(Html.fromHtml(formattedName));

        TextView tvBody = (TextView) view.findViewById(R.id.tvBody);
        tvBody.setText(Html.fromHtml(tweet.getBody()));
        
        TextView tvTimestamp = (TextView) view.findViewById(R.id.tvTimestamp);
        tvTimestamp.setText(Util.getRelativeTSForTimeline(getContext(), tweet.getTimestamp()));
        
        return view;
	}
}
