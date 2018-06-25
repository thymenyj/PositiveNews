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

import org.w3c.dom.Text;

import java.util.ArrayList;

public class FeedLayout extends ArrayAdapter<NewsArticle> {

    private ArrayList<NewsArticle> item;
    TextView titleItem, dateItem;
    ImageView imageItem;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsArticle object = item.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_feed, parent, false);
        }
        titleItem = convertView.findViewById(R.id.titleItem);
        imageItem =  convertView.findViewById(R.id.imageItem);

        titleItem.setText(object.getTitle());
        Picasso.with(getContext()).load(object.getImage()).into(imageItem);

        return convertView;
    }

    public FeedLayout(@NonNull Context context, int resource, @NonNull ArrayList<NewsArticle> objects) {
        super(context, resource, objects);

        item = objects;
    }
}
