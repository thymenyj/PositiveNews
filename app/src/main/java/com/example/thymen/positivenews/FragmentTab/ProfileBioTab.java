package com.example.thymen.positivenews.FragmentTab;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thymen.positivenews.Request.ProfileBioRequest;
import com.example.thymen.positivenews.R;

public class ProfileBioTab extends Fragment implements ProfileBioRequest.Callback{
    View view;
    private TextView bioText;

    public ProfileBioTab() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.tab_profile_bio, container, false);

        bioText = view.findViewById(R.id.bioText);

        ProfileBioRequest profileBioRequest = new ProfileBioRequest(getContext());
        profileBioRequest.getBio(this);


        return view;
    }

    public void gotBio(String name, String email) {
        bioText.setText("Welcome to your profile bio " + name + ". I hope you are having a nice day! You are logged in with the email " + email + ".");
    }

    public void gotBioError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }
}
