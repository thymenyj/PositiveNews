package com.example.thymen.positivenews;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class ArticleActivity extends AppCompatActivity {
    private FloatingActionButton likeButton;
    private DatabaseReference databaseReference;
    private String categoryOfArticle;
    float oldScore, newScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        // intent
        categoryOfArticle = "business";

        databaseReference = FirebaseDatabase.getInstance().getReference();

        likeButton = findViewById(R.id.articleLike);

        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ValueEventListener postListener = new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userId = user.getUid();
                        oldScore = dataSnapshot.child("users").child(userId).child("preferences").child(categoryOfArticle).getValue(Float.class);
                        newScore = (oldScore + 1);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("TAG", "something went wrong", databaseError.toException());
                    }
                };
                databaseReference.addValueEventListener(postListener);



                ValueEventListener postListener2 = new ValueEventListener() {

                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {
                        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                        String userId = user.getUid();
                        databaseReference.child("users").child(userId).child("preferences").child(categoryOfArticle).setValue(newScore);
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {
                        Log.d("TAG", "something went wrong", databaseError.toException());
                    }
                };
                databaseReference.addValueEventListener(postListener2);

            }
        });

    }


}
