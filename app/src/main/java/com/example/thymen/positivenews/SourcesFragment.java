package com.example.thymen.positivenews;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;


public class SourcesFragment extends Fragment implements SourcesRequest.Callback{
    public GridView sourcesGridView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sources, container, false);

        sourcesGridView= view.findViewById(R.id.sourcesListview);

        SourcesRequest sourcesRequest = new SourcesRequest(getContext());
        sourcesRequest.getSources(this);

        sourcesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                NewsSource clickedItem = (NewsSource) parent.getItemAtPosition(position);

                Intent intent = new Intent(getActivity(), SourcesFeed.class);
                intent.putExtra("clickedItem", clickedItem);
                startActivity(intent);
            }
        });

        return view;
    }

    public void gotSources(ArrayList<NewsSource> sources) {
        ArrayAdapter<NewsSource> adapter = new SourcesLayout(getContext(), R.layout.layout_sources, sources);
        sourcesGridView.setAdapter(adapter);
    }

    public void gotSourcesError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }


}
