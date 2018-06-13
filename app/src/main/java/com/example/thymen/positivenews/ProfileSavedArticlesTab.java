package com.example.thymen.positivenews;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ProfileSavedArticlesTab extends Fragment {
    private View view;
    private ListView listView;
    private ArrayAdapter<NewsArticle> arrayAdapter;
    private ArrayList<NewsArticle> savedArticlesList;
    private DatabaseReference databaseReference;

    public ProfileSavedArticlesTab() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_profile_saved_articles, container, false);

        listView = view.findViewById(R.id.savedArticleListView);

        ValueEventListener postListener = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                String userId = user.getUid();
                savedArticlesList = dataSnapshot.child("users").child(userId).child("savedArticles").getValue(ArrayList.class);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.d("TAG", "something went wrong", databaseError.toException());
            }
        };
        databaseReference.addValueEventListener(postListener);

        arrayAdapter = new SavedArticlesListLayout(getContext(), R.layout.layout_saved_articles_list, savedArticlesList);
        listView.setAdapter(arrayAdapter);
        return view;
    }
}
