package com.example.thymen.positivenews.FragmentTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.thymen.positivenews.Activity.ArticleActivity;
import com.example.thymen.positivenews.Request.ProfileSavedArticlesRequest;
import com.example.thymen.positivenews.Object.NewsArticle;
import com.example.thymen.positivenews.R;
import com.example.thymen.positivenews.Layout.SavedArticlesListLayout;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class ProfileSavedArticlesTab extends Fragment  implements ProfileSavedArticlesRequest.Callback {
    private View view;
    private ListView listView;
    private ArrayAdapter<NewsArticle> arrayAdapter;
    private ArrayList<NewsArticle> savedArticlesList;
    private DatabaseReference databaseReference;

    public ProfileSavedArticlesTab() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_profile_saved_articles, container, false);

        listView = view.findViewById(R.id.savedArticleListView);
        savedArticlesList = new ArrayList<>();

       ProfileSavedArticlesRequest profileSavedArticlesRequest = new ProfileSavedArticlesRequest(getContext());
       profileSavedArticlesRequest.getProfileSavedArticles(this, savedArticlesList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public void gotProfileSavedArticles(ArrayList<NewsArticle> savedArticleList) {
        arrayAdapter = new SavedArticlesListLayout(getContext(), R.layout.layout_saved_articles_list, savedArticlesList);
        listView.setAdapter(arrayAdapter);
    }

    public void gotProfileSavedArticlesError (String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }
}
