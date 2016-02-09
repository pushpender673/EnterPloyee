package com.enterployee.enterployee;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Pushpender on 03-02-2016.
 */
public class I_signUp extends Fragment implements View.OnClickListener {
    Button E_SignupButton;
    EditText E_firstname, E_lastname, E_email, E_pass, E_renterpass,E_contact;
    String firstname, lastname, email, pass, reenterpass,contact;

    String ipaddress,date;

    public static int NO_OPTIONS=0;
    private String SHAHash;

    //getting Ip
    GettingIP gettingIP;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.employeesignup, container, false);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        E_SignupButton = (Button) getActivity().findViewById(R.id.btn_EmployeeSignup);
        E_SignupButton.setOnClickListener(this);

        E_firstname = (EditText) getActivity().findViewById(R.id.input_EmployeeFirstName);
        E_lastname = (EditText) getActivity().findViewById(R.id.input_EmployeeLastName);
        E_email = (EditText) getActivity().findViewById(R.id.input_EmployeeEmailid);
        E_pass = (EditText) getActivity().findViewById(R.id.input_EmployeePassword);
        E_renterpass = (EditText) getActivity().findViewById(R.id.input_EmployeeReenterPassword);
        E_contact = (EditText) getActivity().findViewById(R.id.input_EmployeeContact);

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.btn_EmployeeSignup) {
            Employeesignup(v);
        }
    }

    public void Employeesignup(View v){

        if (!validate()) {
            onSignupFailed();
            return;
        }

        //getting ip
        ipaddress = gettingIP.getIPAddress(true);

        //Compute Hash
        computeSHAHash(pass);
        Log.i("Vishnu", " Sha Generated : " + SHAHash);

        // Getting Date
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss:SS");
        Date myDate = new Date();
        date = timeStampFormat.format(myDate);
        Log.d("Vishnu", "Got date : " + date);

        //Handling Input on Server
        BackgroundTask backgroundTask = new BackgroundTask(getContext());
        String method="I_register";
        backgroundTask.execute(method, firstname, lastname, email, SHAHash,contact,ipaddress,date);

        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().popBackStack();

    }

    public void onSignupFailed() {
        Toast.makeText(getContext(), "Registration Failed", Toast.LENGTH_LONG).show();

        E_SignupButton.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        firstname = E_firstname.getText().toString();
        lastname = E_lastname.getText().toString();
        email = E_email.getText().toString();
        pass = E_pass.getText().toString();
        reenterpass = E_renterpass.getText().toString();
        contact = E_contact.getText().toString();


        if (firstname.isEmpty() || firstname.length() < 3) {
            E_firstname.setError("at least 3 characters");
            valid = false;
        } else {
            E_firstname.setError(null);
        }

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            E_email.setError("enter a valid email address");
            valid = false;
        } else {
            E_email.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            E_pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            E_pass.setError(null);
        }

        if(reenterpass.isEmpty() || (reenterpass.equals(pass)==false) )
        {
            E_renterpass.setError("password does not match");
            valid = false;
        } else {
            E_renterpass.setError(null);
        }

        if (contact.isEmpty() || contact.length() < 10 || contact.startsWith("1")==true) {
            E_contact.setError("Not Valid Number");
            valid = false;
        } else {
            E_contact.setError(null);
        }

        return valid;
    }


    public void computeSHAHash(String password)
    {
        Log.d("Vishnu", "Called Function  " + " pass " + password);
        MessageDigest mdSha1 = null;
        try
        {
            mdSha1 = MessageDigest.getInstance("SHA-1");
        } catch (NoSuchAlgorithmException e1) {
            Log.e("Vishnu", "Error initializing SHA1 message digest");
        }
        try {
            mdSha1.update(password.getBytes("ASCII"));
        } catch (UnsupportedEncodingException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        byte[] data = mdSha1.digest();
        try {
            SHAHash=convertToHex(data);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    private static String convertToHex(byte[] data) throws java.io.IOException
    {
        StringBuffer sb = new StringBuffer();
        String hex=null;

        hex= Base64.encodeToString(data, 0, data.length, NO_OPTIONS);

        sb.append(hex);

        return sb.toString();
    }
}
