/*
    PositiveWordsRequest requests the positiveWordList from firebase to check the positivity
    of the titles of the articles.
 */
package com.example.thymen.positivenews.Request;

import android.content.Context;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

public class PositiveWordsRequest {
    public Callback activity;
    public Context context;
    private String positiveWords;

    public interface Callback {
        void gotPositiveWords(String positiveWords);
        void gotPositiveWordsError(String message);
    }

    public PositiveWordsRequest(Context context) {
        this.context = context;
    }

    public void getPositiveWords(final Callback activity) {
        this.activity = activity;

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        Query getPositiveWords =  databaseReference.child("postiveWords");
        getPositiveWords.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                for (DataSnapshot snapshotSavedArticles: snapshot.getChildren()) {
                    positiveWords = snapshotSavedArticles.getValue(String.class);
                    activity.gotPositiveWords(positiveWords);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                activity.gotPositiveWordsError(message);
            }
        });

    }

}
