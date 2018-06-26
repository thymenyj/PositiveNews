package com.example.thymen.positivenews.Fragment;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thymen.positivenews.Activity.ArticleActivity;
import com.example.thymen.positivenews.Request.SourcesFeedRequest;
import com.example.thymen.positivenews.Layout.FeedLayout;
import com.example.thymen.positivenews.Object.NewsArticle;
import com.example.thymen.positivenews.Object.NewsSource;
import com.example.thymen.positivenews.R;
import com.example.thymen.positivenews.Layout.SourcesLayout;
import com.example.thymen.positivenews.Request.SourcesRequest;

import java.util.ArrayList;


public class SourcesFragment extends Fragment implements SourcesRequest.Callback, SourcesFeedRequest.Callback {
    private ListView sourcesList, sourceFeed;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sources, container, false);

        sourcesList = view.findViewById(R.id.sources_list);
        sourceFeed = view.findViewById(R.id.source_feed);

        SourcesRequest sourcesRequest = new SourcesRequest(getContext());
        sourcesRequest.getSources(this);

        sourcesList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsSource clickedItem = (NewsSource) parent.getItemAtPosition(position);
                String sourceName = clickedItem.getName();

                SourcesFeedRequest sourcesFeedRequest = new SourcesFeedRequest(getContext());
                sourcesFeedRequest.getSourcesFeed(SourcesFragment.this, sourceName);

            }
        });

        return view;
    }

    public void gotSources(ArrayList<NewsSource> sources) {
        ArrayAdapter<NewsSource> adapter = new SourcesLayout(getContext(), R.layout.layout_sources, sources);
        sourcesList.setAdapter(adapter);

        sourceFeed.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsArticle clickedItem = (NewsArticle) parent.getItemAtPosition(position);
                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("clickedItem", clickedItem);
                startActivity(intent);
            }
        });
    }

    public void gotSourcesError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }

    public void gotSourcesFeed(ArrayList<NewsArticle> sourcesFeed) {
        ArrayAdapter<NewsArticle> adapter = new FeedLayout(getContext(), R.layout.layout_feed, sourcesFeed);
        sourceFeed.setAdapter(adapter);
    }

    public void gotSourcesFeedError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }

}
