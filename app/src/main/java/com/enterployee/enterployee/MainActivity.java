package com.enterployee.enterployee;

import android.app.AlertDialog;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.os.Bundle;
import android.util.Log;

public class MainActivity extends FragmentActivity implements Communicator{

    public int SETTTING = 1;
    android.support.v4.app.FragmentManager fragmentManager;
    String Choice="login";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        setter(Choice);
    }


    public void setter(String set){
        if(set=="login")
        {
            login login_fragment = new login();
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.add(R.id.frame,login_fragment);
            fragmentTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.commit();
        }
        else if(set =="PreSignUp"){
            PreSignUpFragment preSignup = new PreSignUpFragment();
            android.support.v4.app.FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame,preSignup);
            fragmentTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
        else  if(set =="I_signUp"){
            I_signUp i_signUp = new I_signUp();
            android.support.v4.app.FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame,i_signUp);
            fragmentTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();

        } else  if(set =="O_SignUp"){
            O_signUp o_signUp = new O_signUp();
            android.support.v4.app.FragmentTransaction fragmentTransaction =fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.frame,o_signUp);
            fragmentTransaction.setTransition(android.support.v4.app.FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
            fragmentTransaction.addToBackStack(null);
            fragmentTransaction.commit();
        }
    }

    @Override
    public void fragmentdecide(String frag) {
        setter(frag);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }
}
