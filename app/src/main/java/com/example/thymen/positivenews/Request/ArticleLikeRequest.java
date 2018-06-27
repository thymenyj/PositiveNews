/*
    ArticleLikeRequest gets the category of the article. Then it calls firebase and
    updates the score of the category with 1 point.
 */

package com.example.thymen.positivenews.Request;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class ArticleLikeRequest {
    public Callback activity;
    public Context context;
    private float oldScore;


    public interface Callback {
        void gotArticleLike (float oldScore);
        void gotArticleLikeError (String message);
    }

    public ArticleLikeRequest(Context context) {
        this.context = context;
    }

    public void getArticleLike (final Callback activity, final String categoryOfArticle) {
        this.activity = activity;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        Query updatePreferenceScore =  databaseReference.child("users").child(userId).child("preferences");
        updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot postSnapshot) {
                oldScore = postSnapshot.child(categoryOfArticle).getValue(Float.class);
                activity.gotArticleLike(oldScore);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                activity.gotArticleLikeError(message);
            }
        });

    }

}
