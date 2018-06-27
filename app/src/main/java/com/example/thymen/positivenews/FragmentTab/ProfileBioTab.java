/*
    ProfileBio shows the email and name of the user.
 */

package com.example.thymen.positivenews.FragmentTab;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.thymen.positivenews.Activity.LoginActivity;
import com.example.thymen.positivenews.Request.ProfileBioRequest;
import com.example.thymen.positivenews.R;

public class ProfileBioTab extends Fragment implements ProfileBioRequest.Callback{
    private TextView bioText;

    public ProfileBioTab() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.tab_profile_bio, container, false);

        initializeVariables(view);

        ProfileBioRequest profileBioRequest = new ProfileBioRequest(getContext());
        profileBioRequest.getBio(this);

        goToLogin(view);

        return view;
    }

    public void gotBio(String name, String email) {
        bioText.setText("Welcome to your profile bio " + name + ". I hope you are having a nice day! You are logged in with the email " + email + ".");
    }

    public void gotBioError(String message) {
        Toast.makeText(getContext(), message,
                Toast.LENGTH_LONG).show();
    }

    public void initializeVariables(View view) {
        bioText = view.findViewById(R.id.bioText);
    }

    public void goToLogin(View view) {
        Button logOut = view.findViewById(R.id.startLogout);
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);

            }
        });
    }
}
