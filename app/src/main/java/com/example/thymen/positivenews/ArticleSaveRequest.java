//package com.example.thymen.positivenews;
//
//import android.content.Context;
//import android.util.Log;
//
//import com.google.firebase.auth.FirebaseAuth;
//import com.google.firebase.auth.FirebaseUser;
//import com.google.firebase.database.DataSnapshot;
//import com.google.firebase.database.DatabaseError;
//import com.google.firebase.database.DatabaseReference;
//import com.google.firebase.database.FirebaseDatabase;
//import com.google.firebase.database.Query;
//import com.google.firebase.database.ValueEventListener;
//
//import java.util.ArrayList;
//import java.util.Random;
//
//public class ArticleSaveRequest {
//    public Callback activity;
//    public Context context;
//    private DatabaseReference databaseReference;
//    private String userId;
//    private String category;
//    private float oldScore;
//    private ArrayList<NewsArticle> savedArticleList;
//
//
//    public interface Callback {
//        void gotArticleSave (ArrayList<NewsArticle> savedArticleList);
//        void gotArticleSaveError (String message);
//    }
//
//    public ArticleSaveRequest(Context context) {
//        this.context = context;
//    }
//
//    public void getArticleSave (final Callback activity) {
//        this.activity = activity;
//
//        databaseReference = FirebaseDatabase.getInstance().getReference();
//        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
//        userId = user.getUid();
//        Query updatePreferenceScore =  databaseReference.child("users").child(userId);
//        updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot postSnapshot) {
//                if (postSnapshot.child("savedArticles").getValue(ArrayList.class) == null){
//                    savedArticleList = new ArrayList();
//                    NewsArticle saveArticle = new NewsArticle();
//                    saveArticle.setTitle("testing");
//                    saveArticle.setCategories("testing");
//                    savedArticleList.add(saveArticle);
//                    databaseReference.child("users").child(userId).child("savedArticles").setValue(savedArticleList);
//                } else {
//                    savedArticleList =  postSnapshot.child("users").child(userId).child("savedArticles").getValue(ArrayList.class);
//                    NewsArticle saveArticle = new NewsArticle();
//                    saveArticle.setTitle("testing");
//                    saveArticle.setCategories("testing");
//                    savedArticleList.add(saveArticle);
//                    databaseReference.child("users").child(userId).child("savedArticles").setValue(savedArticleList);
//                }
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//
//        });
//    }
//
//}
