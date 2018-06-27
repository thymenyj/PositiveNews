/*
    ArticleSaveRequest calls the firebase to store the current article inside the savedArticle list
 */

package com.example.thymen.positivenews.Request;

import android.content.Context;

import com.example.thymen.positivenews.Object.NewsArticle;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ArticleSaveRequest {
    public Callback activity;
    public Context context;
    private ArrayList<NewsArticle> savedArticleList;


    public interface Callback {
        void gotArticleSave (ArrayList<NewsArticle> savedArticleList);
        void gotArticleSaveError (String message);
    }

    public ArticleSaveRequest(Context context) {
        this.context = context;
    }

    public void getArticleSave (final Callback activity) {
        this.activity = activity;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        Query updatePreferenceScore =  databaseReference.child("users").child(userId).child("savedArticles");
        updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                savedArticleList = new ArrayList<>();
                for (DataSnapshot postSnapshot: snapshot.getChildren()) {
                    NewsArticle newsArticle = postSnapshot.getValue(NewsArticle.class);
                    savedArticleList.add(newsArticle);
                }
                activity.gotArticleSave(savedArticleList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                activity.gotArticleSaveError(message);
            }

        });
    }

}
