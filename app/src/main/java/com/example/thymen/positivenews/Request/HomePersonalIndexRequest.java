/*
    HomePersonalIndexRequest calls the firebase to get the preferences.
    Based on the preferences the index from the article list is created.
    Higher preference for a category leads to a higher change to get an index
    from that category.
 */

package com.example.thymen.positivenews.Request;

import android.content.Context;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;

public class HomePersonalIndexRequest {
    public Callback activity;
    public Context context;
    private ArrayList<Integer> lengthsList;
    private ArrayList<Integer> indexList;
    private static int LISTSIZE = 60;

    // preference value from firebase for every category set by the user
    private float businessPreference, entertainmentPreference, healthPreference, sciencePreference, sportsPreference, technologyPreference, totalPreference;
    // converted to float value between 0 and 1
    private float businessScore, entertainmentScore, healthScore, scienceScore, sportsScore, technologyScore;

    // listLenghts to calculate the indexrange of a category.
    // 1 = business, 2 = entertainment, 3 = health, 4 = science, 5= sports, 6 = technology;
    private int listLength1 = 1, listLength2 = 1, listLength3 = 1, listLength4 = 1, listLength5 = 1, listLength6 = 1;

    public interface Callback {
        void gotPersonalIndex(ArrayList<Integer> indexListUsed);
        void gotPersonalIndexError(String message);
    }

    public HomePersonalIndexRequest(Context context) {
        this.context = context;
    }

    public void getPersonalIndex(final Callback activity, final ArrayList<Integer> lengthsList) {
        this.activity = activity;
        this.lengthsList = lengthsList;

        initializeVariables();

        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference();
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String userId = user.getUid();
        Query updatePreferenceScore = databaseReference.child("users").child(userId).child("preferences");
        updatePreferenceScore.addListenerForSingleValueEvent(new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                businessPreference = dataSnapshot.child("business").getValue(Float.class);
                entertainmentPreference = (dataSnapshot.child("entertainment").getValue(Float.class) + businessPreference);
                healthPreference = (dataSnapshot.child("health").getValue(Float.class) + entertainmentPreference);
                sciencePreference = (dataSnapshot.child("science").getValue(Float.class) + healthPreference);
                sportsPreference = (dataSnapshot.child("sports").getValue(Float.class) + sciencePreference);
                technologyPreference = (dataSnapshot.child("technology").getValue(Float.class) + sportsPreference);
                totalPreference = technologyPreference;

                businessScore = businessPreference / totalPreference;
                entertainmentScore = entertainmentPreference / totalPreference;
                healthScore = healthPreference / totalPreference;
                scienceScore = sciencePreference / totalPreference;
                sportsScore = sportsPreference / totalPreference;
                technologyScore = technologyPreference / totalPreference;

                // creates list with all indexes for one specific class (business)
                // takes random amount(based on preferencesScore) indexes from the list
                // adds it to the total index list
                ArrayList<Integer> list1 = new ArrayList<>();
                for (int i = 0; i < listLength1; i++) {
                    list1.add(new Integer(i));
                }
                Collections.shuffle(list1);
                for (int i = 0; i < Math.round(LISTSIZE * businessScore); i++) {
                    if (i > listLength1 - 1) {
                        break;
                    }
                    indexList.add(list1.get(i));
                }

                ArrayList<Integer> list2 = new ArrayList<>();
                for (int i = 0; i < listLength2 - listLength1; i++) {
                    list2.add(new Integer(i + listLength1));
                }
                Collections.shuffle(list2);
                for (int i = 0; i < Math.round(LISTSIZE * entertainmentScore); i++) {
                    if (i > listLength2 - listLength1 - 1) {
                        break;
                    }
                    indexList.add(list2.get(i));
                }

                ArrayList<Integer> list3 = new ArrayList<>();
                for (int i = 0; i < listLength3 - listLength2; i++) {
                    list3.add(new Integer(i + listLength2));
                }
                Collections.shuffle(list3);
                for (int i = 0; i < Math.round(LISTSIZE * healthScore); i++) {
                    if (i > listLength3 - listLength2 - 1) {
                        break;
                    }
                    indexList.add(list3.get(i));
                }

                ArrayList<Integer> list4 = new ArrayList<>();
                for (int i = 0; i < listLength4 - listLength3; i++) {
                    list4.add(new Integer(i + listLength3));
                }
                Collections.shuffle(list4);
                for (int i = 0; i < Math.round(LISTSIZE * scienceScore); i++) {
                    if (i > listLength4 - listLength3 - 1) {
                        break;
                    }
                    indexList.add(list4.get(i));
                }

                ArrayList<Integer> list5 = new ArrayList<>();
                for (int i = 0; i < listLength5 - listLength4; i++) {
                    list5.add(new Integer(i + listLength4));
                }
                Collections.shuffle(list5);
                for (int i = 0; i < Math.round(LISTSIZE * sportsScore); i++) {
                    if (i > listLength5 - listLength4 - 1) {
                        break;
                    }
                    indexList.add(list5.get(i));
                }

                ArrayList<Integer> list6 = new ArrayList<>();
                for (int i = 0; i < listLength6 - listLength5; i++) {
                    list6.add(new Integer(i + listLength5));
                }
                Collections.shuffle(list6);
                for (int i = 0; i < Math.round(LISTSIZE * technologyScore); i++) {
                    if (i > listLength6 - listLength5 - 1) {
                        break;
                    }
                    indexList.add(list6.get(i));
                }
                Collections.shuffle(indexList);

                activity.gotPersonalIndex(indexList);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                String message = databaseError.getMessage();
                activity.gotPersonalIndexError(message);
            }
        });
    }

    public void initializeVariables() {
        indexList = new ArrayList<>();
        listLength1 = lengthsList.get(0);
        listLength2 = lengthsList.get(1) + listLength1;
        listLength3 = lengthsList.get(2) + listLength2;
        listLength4 = lengthsList.get(3) + listLength3;
        listLength5 = lengthsList.get(4) + listLength4;
        listLength6 = lengthsList.get(5) + listLength5;
    }

}