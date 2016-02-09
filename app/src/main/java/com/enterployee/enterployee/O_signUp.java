package com.enterployee.enterployee;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Base64;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
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
public class O_signUp extends Fragment implements View.OnClickListener {
    Spinner signupas;
    Spinner plan;

    public static int NO_OPTIONS=0;
    private String SHAHash;

    EditText O_name,O_aFirst,O_aLast,O_email,O_pass,O_reenterpass,O_contact;
    String name,Afirst,Alast,email,pass,renterpass,contact;
    Button O_Buttonsignup;
    String ChoosedPlan;

    String ipaddress,date;

    //getting Ip
    GettingIP gettingIP;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.signuporganization,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //Spinners
        signupas = (Spinner) getActivity().findViewById(R.id.SignUps);
        plan = (Spinner) getActivity().findViewById(R.id.Plan);
        ArrayAdapter<CharSequence> adapter_sihnup=ArrayAdapter.createFromResource(getActivity(), R.array.Signup,android.R.layout.simple_spinner_item);
        adapter_sihnup.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        ArrayAdapter<CharSequence> adapter_plan=ArrayAdapter.createFromResource(getActivity(),R.array.Plan,android.R.layout.simple_spinner_item);
        adapter_plan.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        signupas.setAdapter(adapter_sihnup);
        plan.setAdapter(adapter_plan);
        signupas.setOnItemSelectedListener(new signupas());
        plan.setOnItemSelectedListener(new plan());
        // Spinner

        //Getting the Input Fields
        O_name = (EditText) getActivity().findViewById(R.id.input_organizationName);
        O_aFirst = (EditText) getActivity().findViewById(R.id.input_OrganisationAdminName);
        O_aLast = (EditText) getActivity().findViewById(R.id.input_OrganisationAdminLastName);
        O_email = (EditText) getActivity().findViewById(R.id.input_OrganisationEmailid);
        O_pass = (EditText) getActivity().findViewById(R.id.input_OrganisationAdminPassword);
        O_reenterpass = (EditText) getActivity().findViewById(R.id.input_OrganisationAdminReEnterPassword);
        O_contact = (EditText) getActivity().findViewById(R.id.input_OrganisationContact);
        O_Buttonsignup = (Button) getActivity().findViewById(R.id.btn_OrganizationSignup);
        O_Buttonsignup.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        organisation_signup(v);
    }

    class plan implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TextView t = (TextView) view;
          //  Toast.makeText(view.getContext(), "you have selected" + t.getText().toString(), Toast.LENGTH_SHORT).show();
            ChoosedPlan = t.getText().toString();
        }
        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    }

    class signupas implements AdapterView.OnItemSelectedListener{

        @Override
        public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            TextView t = (TextView) view;
            //Toast.makeText(view.getContext(),"you have selected"+t.getText().toString(),Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {

        }
    }

    public void organisation_signup(View view)
    {

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
        String method="O_register";
        backgroundTask.execute(method, name,Afirst,Alast,email,SHAHash,contact,ipaddress,date,ChoosedPlan);

        getActivity().getSupportFragmentManager().popBackStack();
        getActivity().getSupportFragmentManager().popBackStack();
    }
    public void onSignupFailed() {
        Toast.makeText(getContext(), "Registration failed", Toast.LENGTH_LONG).show();

        O_Buttonsignup.setEnabled(true);
    }


    public boolean validate() {
        boolean valid = true;

        name = O_name.getText().toString();
        Afirst = O_aFirst.getText().toString();
        Alast = O_aLast.getText().toString();
        pass = O_pass.getText().toString();
        renterpass = O_reenterpass.getText().toString();
        contact = O_contact.getText().toString();
        email = O_email.getText().toString();


        if (name.isEmpty() || name.length() < 3) {
            O_name.setError("at least 3 characters");
            valid = false;
        } else {
            O_name.setError(null);
        }

        if (Afirst.isEmpty() || Alast.length() < 3) {
            O_aFirst.setError("at least 3 characters");
            valid = false;
        } else {
            O_aFirst.setError(null);
        }

        if (Alast.isEmpty() || Alast.length() < 3) {
            O_aLast.setError("at least 3 characters");
            valid = false;
        } else {
            O_aLast.setError(null);
        }

        if (email.isEmpty() || !Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            O_email.setError("enter a valid email address");
            valid = false;
        } else {
            O_email.setError(null);
        }

        if (pass.isEmpty() || pass.length() < 4 || pass.length() > 10) {
            O_pass.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            O_pass.setError(null);
        }

        if(renterpass.isEmpty() || (renterpass.equals(pass)==false) )
        {
            O_reenterpass.setError("password does not match");
            valid = false;
        } else {
            O_reenterpass.setError(null);
        }

        if (contact.isEmpty() || contact.length() < 10 || contact.startsWith("1")==true) {
            O_contact.setError("Not Valid Number");
            valid = false;
        } else {
            O_contact.setError(null);
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
