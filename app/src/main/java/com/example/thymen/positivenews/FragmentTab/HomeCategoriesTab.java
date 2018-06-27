/*
    HomeCategoriesTab shows a feed based on the chosen category.
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
import com.example.thymen.positivenews.Request.HomeCategoriesRequest;
import com.example.thymen.positivenews.Layout.FeedLayout;
import com.example.thymen.positivenews.Globals.MyApplication;
import com.example.thymen.positivenews.Object.NewsArticle;
import com.example.thymen.positivenews.R;
import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.HashMap;


public class HomeCategoriesTab extends Fragment implements HomeCategoriesRequest.Callback {
    public ListView categoriesListView;
    private String category;
    private FloatingActionButton categoryBusiness, categoryEntertainment, categoryHealth, categoryScience, categorySports, categoryTechnology;
    private HashMap<String, String> hashMap;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_home_categories_feed, container, false);

        initializeVariables(view);

        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
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

    public void gotCategoriesFeed(ArrayList<NewsArticle> categoriesFeed) {
        ArrayAdapter<NewsArticle> adapter = new FeedLayout(getContext(), R.layout.layout_feed, categoriesFeed);
        categoriesListView.setAdapter(adapter);
    }

    public void gotCategoriesFeedError(String message) {
        Toast.makeText(getContext(), message,
        Toast.LENGTH_LONG).show();
    }

    public void initializeVariables(View view) {
        hashMap = ((MyApplication) getActivity().getApplication()).getPositiveWords();

        categoriesListView = view.findViewById(R.id.categoryListView);
        categoryBusiness = view.findViewById(R.id.categoryBusiness);
        categoryEntertainment = view.findViewById(R.id.categoryEntertainment);
        categoryHealth = view.findViewById(R.id.categoryHealth);
        categoryScience = view.findViewById(R.id.categoryScience);
        categorySports = view.findViewById(R.id.categorySports);
        categoryTechnology = view.findViewById(R.id.categoryTechnology);

        categoryBusiness.setOnClickListener(setCategory);
        categoryEntertainment.setOnClickListener(setCategory);
        categoryHealth.setOnClickListener(setCategory);
        categoryScience.setOnClickListener(setCategory);
        categorySports.setOnClickListener(setCategory);
        categoryTechnology.setOnClickListener(setCategory);
    }

    private View.OnClickListener setCategory = new View.OnClickListener() {

        @Override
        public void onClick(View view) {
            switch (view.getId()) {
                case R.id.categoryBusiness:
                    category = "business";
                case R.id.categoryEntertainment:
                    category = "entertainment";
                case R.id.categoryHealth:
                    category = "health";
                case R.id.categoryScience:
                    category = "science";
                case R.id.categorySports:
                    category = "sports";
                case R.id.categoryTechnology:
                    category = "technology";
            }
            HomeCategoriesRequest homeCategoriesRequest = new HomeCategoriesRequest(getContext());
            homeCategoriesRequest.getCategoriesFeed(HomeCategoriesTab.this, category, hashMap);
        }
    };
}