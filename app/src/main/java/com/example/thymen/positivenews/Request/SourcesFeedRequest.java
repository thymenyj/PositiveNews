/*
    SourcesFeedRequest calls the api to get a newsfeed based on the clicked news source.
 */

package com.example.thymen.positivenews.Request;

import android.content.Context;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.thymen.positivenews.Object.NewsArticle;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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
                String categories = "general";
                String image = item.getString("urlToImage");
                String url = item.getString("url");
                String date = item.getString("publishedAt");

                newsArticle.setTitle(title);
                newsArticle.setCategories(categories);
                newsArticle.setImage(image);
                newsArticle.setUrl(url);
                newsArticle.setDate(date);
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
