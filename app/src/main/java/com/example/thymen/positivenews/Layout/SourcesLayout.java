package com.example.thymen.positivenews.Layout;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.thymen.positivenews.Object.NewsSource;
import com.example.thymen.positivenews.R;

import java.util.ArrayList;

public class SourcesLayout extends ArrayAdapter<NewsSource> {

    private ArrayList<NewsSource> item;

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        NewsSource object = item.get(position);
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.layout_sources, parent, false);
        }
        TextView item = convertView.findViewById(R.id.titleSource);

        item.setText(object.getName());

        return convertView;
    }

    public SourcesLayout(@NonNull Context context, int resource, @NonNull ArrayList<NewsSource> objects) {
        super(context, resource, objects);

        item = objects;
    }
}
