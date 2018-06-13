package com.example.thymen.positivenews;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ArticleActivity extends AppCompatActivity implements ArticleLikeRequest.Callback{
    private FloatingActionButton likeButton, saveButton;
    private DatabaseReference databaseReference;
    private String categoryOfArticle, savedArticle;
    private ArrayList<NewsArticle> savedArticleList;
    float oldScore, newScore;
    private String userId;
    private FirebaseDatabase firebaseDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        // intent
        categoryOfArticle = "business";
        savedArticle = "testarticle";

        firebaseDatabase = FirebaseDatabase.getInstance();

        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();

        likeButton = findViewById(R.id.articleLike);
        saveButton = findViewById(R.id.articleSave);


        likeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArticleLikeRequest articleLikeRequest = new ArticleLikeRequest(getApplicationContext());
                articleLikeRequest.getArticleLike(ArticleActivity.this);

            }

        });

//        saveButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                ArticleSaveRequest articleSaveRequest = new ArticleSaveRequest(getApplicationContext());
//                articleSaveRequest.getArticleSave(ArticleActivity.this);
//
//            }
//        });

    }

    public void gotArticleLike(float oldScore) {
        // repeating because of crash ofter pressing floatingbutton
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        newScore = (oldScore + 1);
        databaseReference.child("users").child(userId).child("preferences").child(categoryOfArticle).setValue(newScore);

    }

    public void gotArticleLikeError (String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }

//    public void gotArticleSave(ArrayList<NewsArticle> savedArticleList) {
//        NewsArticle newsArticle = new NewsArticle();
//        newsArticle.setTitle("testing");
//        savedArticleList.add(newsArticle);
//        databaseReference.child("users").child(userId).child("preferences").child("").setValue(newScore);
//
//    }
//
//    public void gotArticleSaveError (String message) {
//        Toast.makeText(this, message,
//                Toast.LENGTH_LONG).show();
//    }

}
