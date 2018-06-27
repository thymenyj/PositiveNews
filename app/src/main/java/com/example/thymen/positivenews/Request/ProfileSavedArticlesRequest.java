/*
    ProfileSavedArticlesRequest gets the list with saved articles from the user.
 */

package com.example.thymen.positivenews.Request;

import android.content.Context;
import android.util.Log;

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

public class ProfileSavedArticlesRequest {
    public Callback activity;
    public Context context;

    public interface Callback {
        void gotProfileSavedArticles(ArrayList<NewsArticle> savedArticleList);
        void gotProfileSavedArticlesError (String message);
    }

    public ProfileSavedArticlesRequest (Context context) {
        this.context = context;
    }

    public void getProfileSavedArticles (final Callback activity, final ArrayList<NewsArticle> savedArticleList) {
        this.activity = activity;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        Query updatePreferenceScore =  databaseReference.child("users").child(userId).child("savedArticles");
        updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snapshotSavedArticles: snapshot.getChildren()) {
                    NewsArticle newsArticle = snapshotSavedArticles.getValue(NewsArticle.class);
                    savedArticleList.add(newsArticle);
                }
                activity.gotProfileSavedArticles(savedArticleList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                activity.gotProfileSavedArticlesError(message);
            }

        });
    }

}
