package com.example.thymen.positivenews;

import android.content.Context;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
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

        titleSavedArticleTextView = convertView.findViewById(R.id.titleSavedArticle);
        titleSavedArticleTextView.setText(titleSavedArticle);

        return convertView;
    }

    public SavedArticlesListLayout(@NonNull Context context, int resource, @NonNull ArrayList<NewsArticle> objects) {
        super(context, resource, objects);

        savedArticlesList = objects;
    }
}

