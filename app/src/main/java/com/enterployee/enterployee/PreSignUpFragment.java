package com.enterployee.enterployee;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import javax.xml.transform.Templates;

/**
 * Created by Pushpender on 03-02-2016.
 */
public class PreSignUpFragment extends Fragment implements View.OnClickListener {
    Communicator communicator;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.activity_pre_signup,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        communicator = (Communicator) getActivity();
        ImageView imageView1 = (ImageView) getActivity().findViewById(R.id.Pre_OrganizationImage);
        imageView1.setOnClickListener(this);
        ImageView imageView2 = (ImageView) getActivity().findViewById(R.id.Pre_IndividualImage);
        imageView2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.Pre_OrganizationImage) {
            communicator.fragmentdecide("O_SignUp");
        }
        else if(v.getId()==R.id.Pre_IndividualImage) {
            communicator.fragmentdecide("I_signUp");
        }
    }
}
