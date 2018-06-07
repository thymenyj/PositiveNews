package com.example.thymen.positivenews;

import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PersonalFeedFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_personal_feed, null);
    }
}







//package com.example.thymen.positivenews;
//
//import android.content.Intent;
//import android.support.annotation.NonNull;
//import android.support.design.widget.BottomNavigationView;
//import android.support.v7.app.AppCompatActivity;
//import android.os.Bundle;
//import android.view.MenuItem;
//import android.view.View;
//import android.widget.AdapterView;
//import android.widget.ArrayAdapter;
//import android.widget.ListView;
//import android.widget.Toast;
//
//import java.util.ArrayList;
//
//public class PersonalFeedFragment extends AppCompatActivity implements PersonalFeedRequest.Callback  {
//
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.fragment_personal_feed);
//
//
//        PersonalFeedRequest personalFeedRequest = new PersonalFeedRequest(this);
//        personalFeedRequest.getPersonalFeed(this);
//
//        ListView listView = findViewById(R.id.listviewMenu);
//        ListItemClickListener click = new ListItemClickListener();
//        listView.setOnItemClickListener(click);
//
//
//    }
//
//    @Override
//    public void gotPersonalFeed(ArrayList<NewsArticle> personalFeed) {
//        ArrayAdapter<NewsArticle> adapter = new FeedLayout(this, R.layout.layout_feed, personalFeed);
//        ListView listView = findViewById(R.id.listviewMenu);
//        listView.setAdapter(adapter);
//    }
//
//    @Override
//    public void gotPersonalFeedError(String message) {
//        Toast.makeText(getApplicationContext(), message,
//                Toast.LENGTH_LONG).show();
//    }
//
//    private class ListItemClickListener implements AdapterView.OnItemClickListener {
//
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            NewsArticle clickedItem = (NewsArticle) parent.getItemAtPosition(position);
//
//            Intent intent = new Intent(PersonalFeedFragment.this, ArticleActivity.class);
//            intent.putExtra("clickedItem", clickedItem);
//            startActivity(intent);
//        }
//    }
//}