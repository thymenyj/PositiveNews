package com.example.thymen.positivenews;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonalFeedRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    public Callback activity;
    public Context context;
    private String url;
    private DatabaseReference databaseReference;

    private float businessPreference, entertainmentPreference, healthPreference, sciencePreference, sportsPreference, technologyPreference;
    private float businessScore, entertainmentScore, healthScore, scienceScore, sportsScore, technologyScore;
    private float totalPreference;
    private String category;

    public interface Callback {
        void gotPersonalFeed(ArrayList<NewsArticle> menu);
        void gotPersonalFeedError(String message);
    }

    public PersonalFeedRequest(Context context) {
        this.context = context;
    }

    public void getPersonalFeed(Callback activity) {
        this.activity = activity;
        url = getURL();
        RequestQueue queue = Volley.newRequestQueue(context);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://eventregistry.org/json/article?query=%7B%22%24query%22%3A%7B%22conceptUri%22%3A%7B%22%24and%22%3A%5B%22http%3A%2F%2Fen.wikipedia.org%2Fwiki%2FNature%22%5D%7D%7D%7D&action=getArticles&resultType=articles&articlesSortBy=date&articlesCount=20&includeArticleBody=false&includeArticleImage=true&articleBodyLen=0&apiKey=deb0810a-91ec-426b-a2bc-4d3c4de99be5&callback=JSON_CALLBACK", null, this, this);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    public void onResponse(JSONObject response) {
        try {
            JSONArray array = response.getJSONArray("articles");
            int length = array.length();
            ArrayList<NewsArticle> personalFeed = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                NewsArticle newsArticle = new NewsArticle();
                JSONObject item = array.getJSONObject(i);
                String title = item.getString("title");
                String image = item.getString("urlToImage");
                String description = item.getString("description");
                if (image != null) {
                    newsArticle.setTitle(title);
                    newsArticle.setImage(image);
                    newsArticle.setBody(description);
                    personalFeed.add(newsArticle);
                }

            }
            activity.gotPersonalFeed(personalFeed);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("RESPONSE", response.toString());
        }
    }

    public void onErrorResponse(VolleyError error) {
        String message = error.getMessage();
        activity.gotPersonalFeedError(message);
    }

    public String getURL() {
        String categoryUrl = "business";
        url = "https://newsapi.org/v2/top-headlines?category="+ categoryUrl + "&apiKey=95523811729048518c1cf1c3da766379";
        return url;
    }

    public String calculatePreferenceCategory() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                businessPreference = dataSnapshot.child("users").child(userId).child("preferences").child("business").getValue(Float.class);
                entertainmentPreference = (dataSnapshot.child("users").child(userId).child("preferences").child("entertainment").getValue(Float.class) + businessPreference);
                healthPreference = (dataSnapshot.child("users").child(userId).child("preferences").child("health").getValue(Float.class) + entertainmentPreference);
                sciencePreference = (dataSnapshot.child("users").child(userId).child("preferences").child("science").getValue(Float.class) + healthPreference);
                sportsPreference = (dataSnapshot.child("users").child(userId).child("preferences").child("sports").getValue(Float.class) + sciencePreference);
                technologyPreference = (dataSnapshot.child("users").child(userId).child("preferences").child("technology").getValue(Float.class) + sportsPreference);
                totalPreference = technologyPreference;

                Log.d("bpref", Float.toString(businessPreference));
                Log.d("entpref", Float.toString(entertainmentPreference));
                Log.d("heapref", Float.toString(healthPreference));
                Log.d("scipref", Float.toString(sciencePreference));
                Log.d("sportpref", Float.toString(sportsPreference));
                Log.d("techpref", Float.toString(technologyPreference));
                Log.d("totalpref", Float.toString(totalPreference));

                businessScore = businessPreference/ totalPreference;
                entertainmentScore = entertainmentPreference/ totalPreference;
                healthScore = healthPreference/ totalPreference;
                scienceScore = sciencePreference/ totalPreference;
                sportsScore = sportsPreference/ totalPreference;
                technologyScore = technologyPreference/ totalPreference;

                Log.d("busscore", Float.toString(businessScore));
                Log.d("entscore", Float.toString(entertainmentScore));
                Log.d("heascore", Float.toString(healthScore));
                Log.d("sciscore", Float.toString(scienceScore));
                Log.d("sportscore", Float.toString(sportsScore));
                Log.d("techscore", Float.toString(technologyScore));

                float rangeMin = 0.0f;
                float rangeMax = 1.0f;
                Random r = new Random();
                float createdRanNum = rangeMin + (rangeMax - rangeMin) * r.nextFloat();
                Log.d("randomFloat", Float.toString(createdRanNum));

                if (createdRanNum >= 0 && createdRanNum < businessScore) {
                    category = "business";
                }
                else if (createdRanNum >= businessScore && createdRanNum < entertainmentScore) {
                    category = "entertainment";
                }
                else if (createdRanNum >= entertainmentScore && createdRanNum < healthScore) {
                    category = "health";
                }
                else if (createdRanNum >= healthScore && createdRanNum < scienceScore) {
                    category = "science";
                }
                else if (createdRanNum >= scienceScore && createdRanNum < sportsScore) {
                    category = "sports";
                }
                else if (createdRanNum >= sportsScore && createdRanNum < technologyScore) {
                    category = "entertainment";
                }
                else {
                    category = "technology";
                }
                Log.d("category", category);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", "something went wrong", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);


        return category;
    }


}
