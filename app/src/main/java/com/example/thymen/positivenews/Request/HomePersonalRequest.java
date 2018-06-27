/*
    HomePersonalRequest get the creates a ArrayList of newsArticles with a JSONRequest.
    The function getPersonalFeed gets for every category 100 articles and combines those
    articles into one larger personalFeedList. This list is returned to the main class.
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

public class HomePersonalRequest implements Response.Listener<JSONObject>, Response.ErrorListener {
    public Callback activity;
    public Context context;

    private HashMap<String, String> hashMap;
    private ArrayList<NewsArticle> personalFeed;
    private ArrayList<Integer> lengthsList;
    private int counter;
    private ArrayList<String> titleList;

    public interface Callback {
        void gotPersonalFeed(ArrayList<NewsArticle> personalFeed, ArrayList<Integer> lengthTempList);
        void gotPersonalFeedError(String message);
    }

    public HomePersonalRequest(Context context) {
        this.context = context;
    }

    public void getPersonalFeed(Callback activity, HashMap<String, String> hashMap) {
        this.activity = activity;
        this.hashMap = hashMap;

        counter = 0;
        titleList = new ArrayList<>();
        personalFeed = new ArrayList<>();
        lengthsList = new ArrayList<>();

        ArrayList<String> categoryList = new ArrayList<>();
        categoryList.add("business");
        categoryList.add("entertainment");
        categoryList.add("health");
        categoryList.add("science");
        categoryList.add("sports");
        categoryList.add("technology");

        for (int i = 0; i < categoryList.size(); i++) {
            String category = categoryList.get(i);
            String url = "https://newsapi.org/v2/top-headlines?category=" + category + "&language=en&pageSize=100&apiKey=95523811729048518c1cf1c3da766379";
            RequestQueue queue = Volley.newRequestQueue(context);
            JsonObjectRequest jsonObjectRequest =
                    new JsonObjectRequest(url, null, this, this);
            queue.add(jsonObjectRequest);
        }
    }

    public void onResponse(JSONObject response) {
        try {
            ArrayList<NewsArticle> tempPersonalFeed = new ArrayList<>();
            JSONArray array = response.getJSONArray("articles");
            int length = array.length();
            for (int i = 0; i < length; i++) {
                NewsArticle newsArticle = new NewsArticle();
                JSONObject item = array.getJSONObject(i);
                String title = item.getString("title");
                String image = item.getString("urlToImage");
                String url = item.getString("url");
                String date = item.getString("publishedAt");
                if (checkPositivity(title)) {
                    newsArticle.setTitle(title);
                    newsArticle.setUrl(url);
                    newsArticle.setImage(image);
                    newsArticle.setDate(date);
                    if (counter == 0) {
                        newsArticle.setCategories("business");
                    } else if (counter == 1) {
                        newsArticle.setCategories("entertainment");
                    } else if (counter == 2) {
                        newsArticle.setCategories("health");
                    } else if (counter == 3) {
                        newsArticle.setCategories("science");
                    } else if (counter == 4) {
                        newsArticle.setCategories("sports");
                    } else if (counter == 5) {
                        newsArticle.setCategories("technology");
                    }
                    if (checkDuplicate(newsArticle)) {
                        if (image != null) {
                            personalFeed.add(newsArticle);
                            tempPersonalFeed.add(newsArticle);
                        }
                    }
                }
            }
            int lengthTempList = tempPersonalFeed.size();
            lengthsList.add(lengthTempList);

            counter++;

            if (counter == 6) {
                activity.gotPersonalFeed(personalFeed, lengthsList);
            }
        } catch (JSONException e) {
            e.printStackTrace();
            Log.d("RESPONSE", response.toString());
        }
    }

    public void onErrorResponse(VolleyError error) {
        String message = error.getMessage();
        activity.gotPersonalFeedError(message);
    }

    private Boolean checkPositivity(String titleArticle) {
        int scorePositivity = 0;
        String[] arr = titleArticle.split("\\s*(=>|,|\\s)\\s*");
        for ( String wordString : arr) {
            String word = hashMap.get(wordString);
            if (word != null) {
                scorePositivity = scorePositivity + 1;
            }
        }
        if (scorePositivity > 0) {
            return true;
        } else {
            return false;
        }
    }

    private Boolean checkDuplicate(NewsArticle newsArticle) {
        String title = newsArticle.getTitle();
        for(String str: titleList) {
            if(str.trim().contains(title)) {
                return false;
            }
        }
        titleList.add(title);
        return true;
    }
}
