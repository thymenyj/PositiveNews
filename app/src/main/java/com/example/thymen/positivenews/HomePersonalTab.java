package com.example.thymen.positivenews;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class HomePersonalTab extends Fragment implements HomePersonalRequest.Callback{
    public ListView personalListView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal_feed, container, false);
        Log.d("startPersonal", "Start");
        personalListView = view.findViewById(R.id.personalListView);

        HomePersonalRequest homePersonalRequest = new HomePersonalRequest(getContext());
        homePersonalRequest.getPersonalFeed(this);

        personalListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsArticle clickedItem = (NewsArticle) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("clickedItem", clickedItem);
                startActivity(intent);
            }
        });

        return view;
    }

    public void gotPersonalFeed(ArrayList<NewsArticle> personalFeed) {
        ArrayAdapter<NewsArticle> adapter = new FeedLayout(getContext(), R.layout.layout_feed, personalFeed);
        personalListView.setAdapter(adapter);
    }

    public void gotPersonalFeedError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }


}


