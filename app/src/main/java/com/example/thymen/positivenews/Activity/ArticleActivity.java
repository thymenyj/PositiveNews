package com.example.thymen.positivenews.Activity;

import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

import com.example.thymen.positivenews.Request.ArticleLikeRequest;
import com.example.thymen.positivenews.Request.ArticleSaveRequest;
import com.example.thymen.positivenews.Object.NewsArticle;
import com.example.thymen.positivenews.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.lang.reflect.Array;
import java.util.ArrayList;

// This activity shows a newsarticle based on a webView. The article activity contains
// two buttons: likeArticle and saveArticle. The likeArticle updates the preferences of the user
// the saveArticle stores the article in the savedArticleList

public class ArticleActivity extends AppCompatActivity implements ArticleLikeRequest.Callback, ArticleSaveRequest.Callback {
    private DatabaseReference databaseReference;
    private String categoryOfArticle, titleOfArticle;
    private ArrayList<NewsArticle> savedArticleList;
    private String userId;
    private String urlArticle;
    private NewsArticle duplicateArticle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);

        initializeDatabase();
        initializeVariables();
        getArticleContent();
    }

    public void gotArticleLike(float oldScore) {
        float newScore = (oldScore + 1);
        databaseReference.child("users").child(userId).child("preferences").child(categoryOfArticle).setValue(newScore);
        Toast.makeText(this, "Updated preferences", Toast.LENGTH_SHORT).show();
    }

    public void gotArticleLikeError (String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }

    public void gotArticleSave(ArrayList<NewsArticle> savedArticleList) {
        this.savedArticleList = savedArticleList;
        NewsArticle newsArticle = new NewsArticle();
        newsArticle.setTitle(titleOfArticle);
        newsArticle.setCategories(categoryOfArticle);
        newsArticle.setUrl(urlArticle);
        if (checkDuplicate(newsArticle)) {
            savedArticleList.add(newsArticle);
            Toast.makeText(this, "Added to the list", Toast.LENGTH_SHORT).show();
        } else {
            savedArticleList.remove(duplicateArticle);
            Toast.makeText(this, "Deleted from the list", Toast.LENGTH_SHORT).show();
        }
        databaseReference.child("users").child(userId).child("savedArticles").setValue(savedArticleList);
    }

    public void gotArticleSaveError (String message) {
        Toast.makeText(this, message,
                Toast.LENGTH_LONG).show();
    }

    public boolean checkDuplicate(NewsArticle newsArticle) {
        ArrayList<String> titleArrayList = new ArrayList<>();
        for (int i = 0; i < savedArticleList.size(); i++) {
            String titleCheck = savedArticleList.get(i).getTitle();
            titleArrayList.add(titleCheck);
        }
        String title = newsArticle.getTitle();
        int k = 0;
        for(String str: titleArrayList) {
            if(str.trim().contains(title)) {
                duplicateArticle = savedArticleList.get(k);
                return false;
            }
            k++;
        }
        return true;
    }

    public void likeArticle(View view) {
        ArticleLikeRequest articleLikeRequest = new ArticleLikeRequest(getApplicationContext());
        articleLikeRequest.getArticleLike(ArticleActivity.this, categoryOfArticle);
    }

    public void saveArticle(View view) {
        ArticleSaveRequest articleSaveRequest = new ArticleSaveRequest(getApplicationContext());
        articleSaveRequest.getArticleSave(ArticleActivity.this);
    }

    public void initializeDatabase() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        userId = user.getUid();
    }

    public void getArticleContent() {
        WebView webView = findViewById(R.id.webView);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new WebViewClient());

        if (getIntent() != null) {
            NewsArticle newsArticle = (NewsArticle) getIntent().getSerializableExtra("clickedItem");
            urlArticle = newsArticle.getUrl();
            categoryOfArticle = newsArticle.getCategories();
            titleOfArticle = newsArticle.getTitle();
            webView.loadUrl(urlArticle);
        }
    }

    public void initializeVariables() {
        savedArticleList = new ArrayList<>();
    }
}
