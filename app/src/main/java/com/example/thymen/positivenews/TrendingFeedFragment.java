package com.example.thymen.positivenews;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class TrendingFeedFragment extends Fragment implements TrendingFeedRequest.Callback{
    public ListView trendingListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_trending_feed, container, false);

        trendingListView = view.findViewById(R.id.trendingListView);

        TrendingFeedRequest trendingFeedRequest = new TrendingFeedRequest(getContext());
        trendingFeedRequest.getTrendingFeed(this);

        return view;
    }

    public void gotTrendingFeed(ArrayList<NewsArticle> trendingFeed) {
        ArrayAdapter<NewsArticle> adapter = new FeedLayout(getContext(), R.layout.layout_feed, trendingFeed);
        trendingListView.setAdapter(adapter);
    }

    public void gotTrendingFeedError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }
}
