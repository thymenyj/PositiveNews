package com.example.thymen.positivenews.Layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.thymen.positivenews.Object.NewsArticle;
import com.example.thymen.positivenews.R;
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
        TextView titleItem = convertView.findViewById(R.id.titleItem);
        ImageView imageItem =  convertView.findViewById(R.id.imageItem);

        titleItem.setText(object.getTitle());
        Picasso.with(getContext()).load(object.getImage()).into(imageItem);

        return convertView;
    }

    public FeedLayout(@NonNull Context context, int resource, @NonNull ArrayList<NewsArticle> objects) {
        super(context, resource, objects);

        item = objects;
    }
}
