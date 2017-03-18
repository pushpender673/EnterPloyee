package com.enterployee.enterployee;

import android.content.Intent;
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
import android.widget.TextView;
import android.widget.Toast;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;

/**
 * Created by Pushpender on 03-02-2016.
 */
public class login extends Fragment implements View.OnClickListener {
    Communicator communicator;
    Button login_button;
    String username,password,ipaddress,date;
    EditText ETLogin_username,ETLogin_password;
    //ComputeSha1 computeSha1;
    public static int NO_OPTIONS=0;
    private String SHAHash;


    // Getting IP
    GettingIP ipaddr;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login,container,false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        TextView textView = (TextView) getActivity().findViewById(R.id.link_signup);
        textView.setOnClickListener(this);
        communicator = (Communicator) getActivity();
        ETLogin_username = (EditText) getActivity().findViewById(R.id.input_loginemail);
        ETLogin_password = (EditText)getActivity().findViewById(R.id.input_loginpassword);
        login_button = (Button)getActivity().findViewById(R.id.btn_login);
        login_button.setOnClickListener(this);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.link_signup) {
            communicator.fragmentdecide("PreSignUp");
        }
        else if(v.getId()==R.id.btn_login)
        {
            SignIn(v);
        }
    }

    public void SignIn(View view){


        if (!validate()) {
            onLoginFailed();
            return;
        }

        username = ETLogin_username.getText().toString();
        password = ETLogin_password.getText().toString();

        Intent intent  = new Intent(getContext(),Main2Activity.class);
        startActivity(intent);

        //computing Sha
        computeSHAHash(password);
        Log.i("Vishnu", " Sha Generated : " + SHAHash);


        // Getting IP address
        ipaddress = ipaddr.getIPAddress(true);
        Log.d("Vishnu", "Got IP : " + ipaddress);

        // Getting Date
        SimpleDateFormat timeStampFormat = new SimpleDateFormat("yyyy/MM/dd/ HH:mm:ss:SS");
        Date myDate = new Date();
        date = timeStampFormat.format(myDate);
        Log.d("Vishnu", "Got date : " + date);


        //Handling Input on Server
        BackgroundTask backgroundTask = new BackgroundTask(getContext());
        String method="login";
        backgroundTask.execute(method, username, SHAHash, ipaddress, date);
    }



    public void onLoginFailed() {
        Toast.makeText(getContext(), "Login failed", Toast.LENGTH_LONG).show();

        login_button.setEnabled(true);
    }

    public boolean validate() {
        boolean valid = true;

        String email = ETLogin_username.getText().toString();
        String password = ETLogin_password.getText().toString();

        if (email.isEmpty() || !android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches()) {
            ETLogin_username.setError("enter a valid email address");
            valid = false;
        } else {
            ETLogin_password.setError(null);
        }

        if (password.isEmpty() || password.length() < 4 || password.length() > 10) {
            ETLogin_password.setError("between 4 and 10 alphanumeric characters");
            valid = false;
        } else {
            ETLogin_password.setError(null);
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


    public String getLocalIpAddress()
    {
        try {
            for (Enumeration<NetworkInterface> en = NetworkInterface.getNetworkInterfaces(); en.hasMoreElements();) {
                NetworkInterface intf = en.nextElement();
                for (Enumeration<InetAddress> enumIpAddr = intf.getInetAddresses(); enumIpAddr.hasMoreElements();) {
                    InetAddress inetAddress = enumIpAddr.nextElement();
                    if (!inetAddress.isLoopbackAddress()) {
                        return inetAddress.getHostAddress().toString();
                    }
                }
            }
        } catch (Exception ex) {
            Log.e("IP Address", ex.toString());
        }
        return null;
    }
}
