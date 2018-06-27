/*
    HomePersonalTab shows a feed base
 */

package com.example.thymen.positivenews.FragmentTab;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thymen.positivenews.Activity.ArticleActivity;
import com.example.thymen.positivenews.Request.HomePersonalIndexRequest;
import com.example.thymen.positivenews.Request.HomePersonalRequest;
import com.example.thymen.positivenews.Layout.FeedLayout;
import com.example.thymen.positivenews.Globals.MyApplication;
import com.example.thymen.positivenews.Object.NewsArticle;
import com.example.thymen.positivenews.R;

import java.util.ArrayList;
import java.util.HashMap;


public class HomePersonalTab extends Fragment implements HomePersonalRequest.Callback, HomePersonalIndexRequest.Callback{
    private ListView personalListView;
    private ArrayList<NewsArticle> personalNewsArticlesList;
    private HashMap<String, String> hashMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_home_personal_feed, container, false);

        initializeVariables(view);

        HomePersonalRequest homePersonalRequest = new HomePersonalRequest(getContext());
        homePersonalRequest.getPersonalFeed(this, hashMap);

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

    public void gotPersonalFeed(ArrayList<NewsArticle> personalFeed, ArrayList<Integer> lengthsList) {
        this.personalNewsArticlesList = personalFeed;
        HomePersonalIndexRequest homePersonalIndexRequest = new HomePersonalIndexRequest(getContext());
        homePersonalIndexRequest.getPersonalIndex(this, lengthsList);
    }

    public void gotPersonalFeedError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }

    public void gotPersonalIndex(ArrayList<Integer> indexList) {
        ArrayList<NewsArticle> finalList = new ArrayList<>();
        for (int i = 0; i < indexList.size(); i++) {
            int index = indexList.get(i);
            finalList.add(personalNewsArticlesList.get(index));
        }
        ArrayAdapter<NewsArticle> adapter = new FeedLayout(getContext(), R.layout.layout_feed, finalList);
        personalListView.setAdapter(adapter);
    }

    public void gotPersonalIndexError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }

    public void initializeVariables(View view) {
        personalListView = view.findViewById(R.id.personalListView);
        personalNewsArticlesList = new ArrayList<>();
        hashMap = ((MyApplication) getActivity().getApplication()).getPositiveWords();
    }


}


