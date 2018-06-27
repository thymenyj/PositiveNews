/*
    HomeCategoriesRequest returns a list containing the articles based on the clicked category.
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
import java.util.HashMap;

public class HomeCategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    public Callback activity;
    public Context context;
    private String category;
    private ArrayList<NewsArticle> categoriesFeed;
    private HashMap<String, String> hashMap;

    public interface Callback {
        void gotCategoriesFeed(ArrayList<NewsArticle> menu);
        void gotCategoriesFeedError(String message);
    }

    public HomeCategoriesRequest(Context context) {
        this.context = context;
    }

    public void getCategoriesFeed(Callback activity, String category, HashMap<String, String> hashMap) {
        this.activity = activity;
        this.category = category;
        this.categoriesFeed = categoriesFeed;
        this.hashMap = hashMap;

        String url = "https://newsapi.org/v2/top-headlines?category=" + category + "&country=us&apiKey=95523811729048518c1cf1c3da766379";
        RequestQueue queue = Volley.newRequestQueue(context);
        JsonObjectRequest jsonObjectRequest =
                new JsonObjectRequest(url, null, this, this);
        queue.add(jsonObjectRequest);
    }

    public void onResponse(JSONObject response) {
        try {
            JSONArray array = response.getJSONArray("articles");
            int length = array.length();
            ArrayList<NewsArticle> categoriesFeed = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                NewsArticle newsArticle = new NewsArticle();
                JSONObject item = array.getJSONObject(i);
                String title = item.getString("title");
                String image = item.getString("urlToImage");
                String url = item.getString("url");
                String date = item.getString("publishedAt");
                if (checkPositivity(title)) {
                    newsArticle.setTitle(title);
                    newsArticle.setImage(image);
                    newsArticle.setUrl(url);
                    newsArticle.setCategories(date);
                    newsArticle.setCategories(category);
                    if (image != null) {
                        categoriesFeed.add(newsArticle);
                    }
                }
            }
            activity.gotCategoriesFeed(categoriesFeed);
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("RESPONSE", response.toString());
        }
    }

    public void onErrorResponse(VolleyError error) {
        String message = error.getMessage();
        activity.gotCategoriesFeedError(message);
    }

    public Boolean checkPositivity(String titleArticle) {
        int scorePositivity = 0;

        String[] arr = titleArticle.split("\\s*(=>|,|\\s)\\s*");
        for (String wordString : arr) {
            String word = hashMap.get(wordString);
            if (word != null) {
                scorePositivity = scorePositivity + 1;
            }
        }
        if (scorePositivity > 0) {
            return true;
        }
        else {
            return false;
        }
    }

}
