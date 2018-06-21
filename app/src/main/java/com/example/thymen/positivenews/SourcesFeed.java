package com.example.thymen.positivenews;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import javax.xml.transform.Source;

public class SourcesFeed extends AppCompatActivity implements SourcesFeedRequest.Callback{
    public ListView sourcesFeedListView;
    private NewsSource newsSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sources_feed);
        sourcesFeedListView = findViewById(R.id.sourcesFeed);

        if (getIntent() != null) {
            newsSource = (NewsSource) getIntent().getSerializableExtra("clickedItem");
        }
        String sourceName = newsSource.getName();


        SourcesFeedRequest sourcesFeedRequest = new SourcesFeedRequest(this);
        sourcesFeedRequest.getSourcesFeed(this, sourceName);

        sourcesFeedListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsArticle clickedItem = (NewsArticle) parent.getItemAtPosition(position);

                Intent intent = new Intent(SourcesFeed.this, ArticleActivity.class);
                intent.putExtra("clickedItem", clickedItem);
                startActivity(intent);
            }
        });
    }

    public void gotSourcesFeed(ArrayList<NewsArticle> sourcesFeed) {
        ArrayAdapter<NewsArticle> adapter = new FeedLayout(this, R.layout.layout_feed, sourcesFeed);
        sourcesFeedListView.setAdapter(adapter);
    }

    public void gotSourcesFeedError(String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }
}
