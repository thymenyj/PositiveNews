package com.example.thymen.positivenews;

import android.content.Context;
import android.util.Log;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Random;

public class ProfileSavedArticlesRequest {
    public Callback activity;
    public Context context;
    private DatabaseReference databaseReference;
    private String userId;
    private ArrayList<NewsArticle> savedArticleList;


    public interface Callback {
        void gotProfileSavedArticles(ArrayList<NewsArticle> savedArticleList);
        void gotProfileSavedArticlesError (String message);
    }

    public ProfileSavedArticlesRequest (Context context) {
        this.context = context;
    }

    public void getProfileSavedArticles (final Callback activity) {
        this.activity = activity;
        databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
        savedArticleList = new ArrayList<>();
        Query updatePreferenceScore =  databaseReference.child("users").child(userId).child("savedArticles");
//        updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot snapshot) {
//                for (DataSnapshot snapshotSavedArticles: snapshot.getChildren()) {
//                    NewsArticle newsArticle = snapshotSavedArticles.getValue(NewsArticle.class);
//                    savedArticleList.add(newsArticle);
//                    Log.d("counter", "a");
//                }
//
//                Log.d("length", Integer.toString(savedArticleList.size()));
//                activity.gotProfileSavedArticles(savedArticleList);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//                String message = databaseError.getMessage();
//                activity.gotProfileSavedArticlesError(message);
//            }
//
//        });
    }

}
