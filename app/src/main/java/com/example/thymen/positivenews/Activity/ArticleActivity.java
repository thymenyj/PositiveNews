package com.example.thymen.positivenews.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.thymen.positivenews.Callback.ArticleLikeRequest;
import com.example.thymen.positivenews.Callback.ArticleSaveRequest;
import com.example.thymen.positivenews.Object.NewsArticle;
import com.example.thymen.positivenews.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ArticleActivity extends AppCompatActivity implements ArticleLikeRequest.Callback, ArticleSaveRequest.Callback {
    private FloatingActionButton likeButton, saveButton;
    private DatabaseReference databaseReference;
    private String categoryOfArticle, titleOfArticle;
    private ArrayList<NewsArticle> savedArticleList;
    float oldScore, newScore;
    private String userId;
    private FirebaseDatabase firebaseDatabase;
    private WebView webView;
    private AlertDialog alertDialog;
    private NewsArticle newsArticle;
    private String urlArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        if (getIntent() != null) {
            newsArticle = (NewsArticle) getIntent().getSerializableExtra("clickedItem");
            urlArticle = newsArticle.getUrl();
            webView.loadUrl(urlArticle);
        }

        categoryOfArticle = newsArticle.getCategories();
        titleOfArticle = newsArticle.getTitle();

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
                articleLikeRequest.getArticleLike(ArticleActivity.this, categoryOfArticle);

            }

        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ArticleSaveRequest articleSaveRequest = new ArticleSaveRequest(getApplicationContext());
                articleSaveRequest.getArticleSave(ArticleActivity.this);

            }
        });

    }

    public void gotArticleLike(float oldScore) {

        newScore = (oldScore + 1);
        databaseReference.child("users").child(userId).child("preferences").child(categoryOfArticle).setValue(newScore);

    }

    public void gotArticleLikeError (String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }

    public void gotArticleSave(ArrayList<NewsArticle> savedArticleList) {
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setTitle(titleOfArticle);
        newsArticle.setCategories(categoryOfArticle);
        newsArticle.setUrl(urlArticle);
        savedArticleList.add(newsArticle);
        databaseReference.child("users").child(userId).child("savedArticles").setValue(savedArticleList);

    }

    public void gotArticleSaveError (String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }

}
