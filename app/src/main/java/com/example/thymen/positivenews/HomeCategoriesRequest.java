package com.example.thymen.positivenews;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.firebase.database.DatabaseReference;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

public class HomeCategoriesRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    public Callback activity;
    public Context context;

    private String url;
    private String category;
    private ArrayList<NewsArticle> categoriesFeed;
    HashMap<String, String> hashMap;

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

        url = "https://newsapi.org/v2/top-headlines?category=" + category + "&country=us&apiKey=95523811729048518c1cf1c3da766379";
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
            ArrayList<NewsArticle> categoriesFeed = new ArrayList<>();
            for (int i = 0; i < length; i++) {
                NewsArticle newsArticle = new NewsArticle();
                JSONObject item = array.getJSONObject(i);
                String title = item.getString("title");
                String image = item.getString("urlToImage");
                String url = item.getString("url");
                String description = item.getString("description");
                String date = item.getString("publishedAt");
                if (checkPositivity(title)) {
                    newsArticle.setTitle(title);
                    newsArticle.setImage(image);
                    newsArticle.setUrl(url);
                    newsArticle.setBody(description);
                    newsArticle.setCategories(date);
                    newsArticle.setCategories(category);
                    if (image != null) {
                        categoriesFeed.add(newsArticle);
                    }

                }
            }
            Log.d("categoriesFeed", categoriesFeed.toString());
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
        for ( String wordString : arr) {
            String word = hashMap.get(wordString);
            if (word != null) {
                scorePositivity = scorePositivity + 1;
            }
        }
        Log.d("scorePOsCheck", Float.toString(scorePositivity));
        if (scorePositivity > 0) {
            return true;
        }
        else {
            return false;
        }
    }

}
