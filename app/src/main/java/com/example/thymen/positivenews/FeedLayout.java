package com.example.thymen.positivenews;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class FeedLayout extends ArrayAdapter<NewsArticle> {

    private ArrayList<NewsArticle> item;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsArticle object = item.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_feed, parent, false);
        }
        TextView item = convertView.findViewById(R.id.titleItem);

        item.setText(object.getTitle());

        return convertView;
    }

    public FeedLayout(@NonNull Context context, int resource, @NonNull ArrayList<NewsArticle> objects) {
        super(context, resource, objects);

        item = objects;
    }
}
