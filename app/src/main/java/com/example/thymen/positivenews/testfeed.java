package com.example.thymen.positivenews;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class testfeed extends AppCompatActivity implements PersonalFeedRequest.Callback  {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_testfeed);


        PersonalFeedRequest personalFeedRequest = new PersonalFeedRequest(this);
        personalFeedRequest.getPersonalFeed(this);

        ListView listView = findViewById(R.id.articleList);
        ListItemClickListener click = new ListItemClickListener();
        listView.setOnItemClickListener(click);


    }

    @Override
    public void gotPersonalFeed(ArrayList<NewsArticle> personalFeed) {
        ArrayAdapter<NewsArticle> adapter = new FeedLayout(this, R.layout.layout_feed, personalFeed);
        ListView listView = findViewById(R.id.articleList);
        listView.setAdapter(adapter);
    }

    @Override
    public void gotPersonalFeedError(String message) {
        Toast.makeText(getApplicationContext(), message,
                Toast.LENGTH_LONG).show();
    }

    private class ListItemClickListener implements AdapterView.OnItemClickListener {

        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            NewsArticle clickedItem = (NewsArticle) parent.getItemAtPosition(position);

            Intent intent = new Intent(testfeed.this, ArticleActivity.class);
            intent.putExtra("clickedItem", clickedItem);
            startActivity(intent);
        }
    }
}