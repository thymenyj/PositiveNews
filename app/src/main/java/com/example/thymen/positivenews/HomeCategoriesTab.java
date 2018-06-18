package com.example.thymen.positivenews;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
//import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.getbase.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;


public class HomeCategoriesTab extends Fragment implements HomeCategoriesRequest.Callback {
    public ListView categoriesListView;
    private String category;
    private FloatingActionButton categoryBusiness, categoryEntertainment, categoryHealth, categoryScience, categorySports, categoryTechnology;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.activity_home_categories_feed, container, false);

        categoriesListView = view.findViewById(R.id.categoryListView);
        categoryBusiness = view.findViewById(R.id.categoryBusiness);
        categoryEntertainment = view.findViewById(R.id.categoryEntertainment);
        categoryHealth = view.findViewById(R.id.categoryHealth);
        categoryScience = view.findViewById(R.id.categoryScience);
        categorySports = view.findViewById(R.id.categorySports);
        categoryTechnology = view.findViewById(R.id.categoryTechnology);

        categoriesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsArticle clickedItem = (NewsArticle) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), ArticleActivity.class);
                intent.putExtra("clickedItem", clickedItem);
                startActivity(intent);
            }
        });

        categoryBusiness.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "business";
                HomeCategoriesRequest homeCategoriesRequest = new HomeCategoriesRequest(getContext());
                homeCategoriesRequest.getCategoriesFeed(HomeCategoriesTab.this, category);
            }
        });

        categoryEntertainment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "entertainment";
                HomeCategoriesRequest homeCategoriesRequest = new HomeCategoriesRequest(getContext());
                homeCategoriesRequest.getCategoriesFeed(HomeCategoriesTab.this, category);
            }
        });

        categoryHealth.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "health";
                HomeCategoriesRequest homeCategoriesRequest = new HomeCategoriesRequest(getContext());
                homeCategoriesRequest.getCategoriesFeed(HomeCategoriesTab.this, category);
            }
        });

        categoryScience.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "science";
                HomeCategoriesRequest homeCategoriesRequest = new HomeCategoriesRequest(getContext());
                homeCategoriesRequest.getCategoriesFeed(HomeCategoriesTab.this, category);
            }
        });

        categorySports.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "sports";
                HomeCategoriesRequest homeCategoriesRequest = new HomeCategoriesRequest(getContext());
                homeCategoriesRequest.getCategoriesFeed(HomeCategoriesTab.this, category);
            }
        });

        categoryTechnology.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                category = "technology";
                HomeCategoriesRequest homeCategoriesRequest = new HomeCategoriesRequest(getContext());
                homeCategoriesRequest.getCategoriesFeed(HomeCategoriesTab.this, category);
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
}