package com.example.thymen.positivenews;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class PersonalFeedRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    public Callback activity;
    public Context context;

    public interface Callback {
        void gotPersonalFeed(ArrayList<NewsArticle> menu);
        void gotPersonalFeedError(String message);
    }

    public PersonalFeedRequest(Context context) {
        this.context = context;
    }

    public void getPersonalFeed(Callback activity) {
        this.activity = activity;
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://eventregistry.org/json/article?query=%7B%22%24query%22%3A%7B%22conceptUri%22%3A%7B%22%24and%22%3A%5B%22http%3A%2F%2Fen.wikipedia.org%2Fwiki%2FNature%22%5D%7D%7D%7D&action=getArticles&resultType=articles&articlesSortBy=date&articlesCount=20&includeArticleBody=false&includeArticleImage=true&articleBodyLen=0&apiKey=deb0810a-91ec-426b-a2bc-4d3c4de99be5&callback=JSON_CALLBACK", null, this, this);
//        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest("https://newsapi.org/v2/top-headlines?country=nl&category=health&apiKey=95523811729048518c1cf1c3da766379", null, this, this);
        queue.add(jsonObjectRequest);
    }

    public void onResponse(JSONObject response) {
        try {
            JSONObject object = response.getJSONObject("articles");
            JSONArray array = object.getJSONArray("results");
            int length = array.length();
            ArrayList<NewsArticle> personalFeed = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                NewsArticle newsArticle = new NewsArticle();
                JSONObject item = array.getJSONObject(i);
                String title = item.getString("title");
                String image = item.getString("image");

                newsArticle.setTitle(title);
                newsArticle.setImage(image);

                personalFeed.add(newsArticle);
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

}
