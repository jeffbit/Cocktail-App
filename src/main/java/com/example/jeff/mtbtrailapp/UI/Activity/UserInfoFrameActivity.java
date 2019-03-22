package com.example.jeff.mtbtrailapp.UI.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.jeff.mtbtrailapp.R;
import com.example.jeff.mtbtrailapp.UI.Fragment.EditUserInfoFragment;
import com.example.jeff.mtbtrailapp.UI.Fragment.ViewUserInfoFragment;

public class UserInfoFrameActivity extends AppCompatActivity implements ViewUserInfoFragment.OnFragmentInteractionListener, EditUserInfoFragment.OnFragmentInteractionListener {

    public static final ViewUserInfoFragment sViewUserInfoFragment = new ViewUserInfoFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user);

        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();
        ft.replace(R.id.userActivityFrame, sViewUserInfoFragment);
        ft.commit();
    }

    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}
