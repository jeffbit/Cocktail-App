package com.example.jeff.mtbtrailapp.UI.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;

import com.example.jeff.mtbtrailapp.R;
import com.example.jeff.mtbtrailapp.UI.Fragment.AboutAppFragment;
import com.example.jeff.mtbtrailapp.UI.Fragment.LoginFragment;
import com.example.jeff.mtbtrailapp.UI.Fragment.UserRegistrationFragment;

/**
 * A login screen that offers login via email/password.
 */
public class MainActivity extends AppCompatActivity implements LoginFragment.OnFragmentInteractionListener,
        UserRegistrationFragment.OnFragmentInteractionListener,
        AboutAppFragment.OnFragmentInteractionListener {
    public static final LoginFragment loginFragment = new LoginFragment();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.activityMainFrame, loginFragment);
        fragmentTransaction.commit();


    }


    @Override
    public void onFragmentInteraction(Uri uri) {

    }
}







