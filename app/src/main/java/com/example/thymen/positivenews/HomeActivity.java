package com.example.thymen.positivenews;


import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;

import java.util.ArrayList;

public class HomeActivity extends FragmentActivity implements BottomNavigationView.OnNavigationItemSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        BottomNavigationView navigation = findViewById(R.id.navigationView);
        navigation.setOnNavigationItemSelectedListener(this);

        loadFragment(new PersonalFeedFragment());
    }

    private Boolean loadFragment(Fragment fragment) {
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {

        Fragment fragment = null;

        switch (item.getItemId()) {

            case R.id.navigation_profile:
                fragment = new ProfileFragment();

                break;
            case R.id.navigation_personal:
                fragment = new PersonalFeedFragment();

//                ListView listView = this.findViewById<ListView>(R.id.personalListView);
//                ListItemClickListener click = new ListItemClickListener();
//                listView.setOnItemClickListener(click);

                break;
            case R.id.navigation_trending:
                fragment = new TrendingFeedFragment();
                break;

        }
        return loadFragment(fragment);
    }
}
//    public void gotPersonalFeed(ArrayList<NewsArticle> personalFeed) {
//        ArrayAdapter<NewsArticle> adapter = new FeedLayout(this, R.layout.layout_feed, personalFeed);
//        ListView listView = findViewById(R.id.personalListView);
//        listView.setAdapter(adapter);
//    }
//
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
//            Intent intent = new Intent(HomeActivity.this, ArticleActivity.class);
//            intent.putExtra("clickedItem", clickedItem);
//            startActivity(intent);
//        }
//    }

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