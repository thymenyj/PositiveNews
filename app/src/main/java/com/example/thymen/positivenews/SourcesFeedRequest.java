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

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SourcesFeedRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    public Callback activity;
    public Context context;

    public interface Callback {
        void gotSourcesFeed(ArrayList<NewsArticle> sourcesFeed);
        void gotSourcesFeedError(String message);
    }

    public SourcesFeedRequest(Context context) {
        this.context = context;
    }

    public void getSourcesFeed(Callback activity, String source) {
        this.activity = activity;
        source = source.replaceAll("\\s+","-");
        String url = "https://newsapi.org/v2/top-headlines?sources=" + source + "&apiKey=95523811729048518c1cf1c3da766379";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    public void onResponse(JSONObject response) {
        try {
            JSONArray array = response.getJSONArray("articles");
            int length = array.length();
            ArrayList<NewsArticle> sourcesFeed = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                NewsArticle newsArticle = new NewsArticle();
                JSONObject item = array.getJSONObject(i);
                String title = item.getString("title");
                String image = item.getString("urlToImage");
                String url = item.getString("url");
                String description = item.getString("description");

                newsArticle.setTitle(title);
                newsArticle.setImage(image);
                newsArticle.setUrl(url);
                newsArticle.setBody(description);
                sourcesFeed.add(newsArticle);

            }
            activity.gotSourcesFeed(sourcesFeed);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("RESPONSE", response.toString());
        }
    }

    public void onErrorResponse(VolleyError error) {
        String message = error.getMessage();
        activity.gotSourcesFeedError(message);
    }



}
