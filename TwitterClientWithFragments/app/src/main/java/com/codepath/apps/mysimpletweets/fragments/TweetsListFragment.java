package com.codepath.apps.mysimpletweets.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.codepath.apps.mysimpletweets.R;
import com.codepath.apps.mysimpletweets.adapter.TweetsArrayAdapter;
import com.codepath.apps.mysimpletweets.models.Tweet;

import java.util.ArrayList;

public class TweetsListFragment extends Fragment{
    private ArrayList<Tweet> tweets;
    private TweetsArrayAdapter atweets;
    private ListView lvTweets;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup parent, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list,parent,false);

        //find the list view
        lvTweets = (ListView)findViewById(R.id.lvTimeLine);
        //Connect adapter to listview
        lvTweets.setAdapter(atweets);
        return v;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //Create the arraylist of datasource
        tweets = new ArrayList<>();
        //Construct the adapter from data source
        atweets = new TweetsArrayAdapter(this,tweets);

    }


}

