package com.example.thymen.positivenews;

import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.widget.Toast;

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
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class PersonalFeedRequest implements Response.Listener<JSONObject>, Response.ErrorListener, PersonalFeedUrlRequest.Callback {
    public Callback activity;
    public Context context;

    private String url;
    private DatabaseReference databaseReference;

    private float totalPreference;
    private String category;
    private String userId;

    public interface Callback {
        void gotPersonalFeed(ArrayList<NewsArticle> menu);

        void gotPersonalFeedError(String message);
    }

    public PersonalFeedRequest(Context context) {
        this.context = context;
    }

    public void getPersonalFeed(Callback activity) {
        this.activity = activity;

        PersonalFeedUrlRequest personalFeedUrlRequest = new PersonalFeedUrlRequest(context);
        personalFeedUrlRequest.getPersonalFeedUrl(this);

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
                    newsArticle.setCategories(category);
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


    public void gotPersonalFeedUrl(String category) {
        url = "https://newsapi.org/v2/top-headlines?category=" + category + "&apiKey=95523811729048518c1cf1c3da766379";
        RequestQueue queue = Volley.newRequestQueue(context);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://eventregistry.org/json/article?query=%7B%22%24query%22%3A%7B%22conceptUri%22%3A%7B%22%24and%22%3A%5B%22http%3A%2F%2Fen.wikipedia.org%2Fwiki%2FNature%22%5D%7D%7D%7D&action=getArticles&resultType=articles&articlesSortBy=date&articlesCount=20&includeArticleBody=false&includeArticleImage=true&articleBodyLen=0&apiKey=deb0810a-91ec-426b-a2bc-4d3c4de99be5&callback=JSON_CALLBACK", null, this, this);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);

    }

    public void gotPersonalFeedUrlError(String message) {
        Toast.makeText(context, message,
                Toast.LENGTH_LONG).show();
    }
}
