package com.example.thymen.positivenews.Layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.thymen.positivenews.Object.NewsArticle;
import com.example.thymen.positivenews.R;
import com.google.firebase.database.DatabaseReference;

import java.util.ArrayList;

public class SavedArticlesListLayout extends ArrayAdapter<NewsArticle> {
    private DatabaseReference databaseReference;
    private ArrayList<NewsArticle> savedArticlesList;
    private String titleSavedArticle;
    private TextView titleSavedArticleTextView;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsArticle object = savedArticlesList.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_saved_articles_list, parent, false);
        }
        titleSavedArticle = object.getTitle().toString();

        titleSavedArticleTextView = convertView.findViewById(R.id.titleItem);
        titleSavedArticleTextView.setText(titleSavedArticle);

        return convertView;
    }

    public SavedArticlesListLayout(@NonNull Context context, int resource, @NonNull ArrayList<NewsArticle> objects) {
        super(context, resource, objects);

        savedArticlesList = objects;
    }
}

