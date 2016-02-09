package com.enterployee.enterployee;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;

/**
 * Created by Pushpender on 23-01-2016.
 */
public class BackgroundTask extends AsyncTask<String,Void,String> {

    Context ctx;
    AlertDialog alertDialog;
    String emaildata;

    public BackgroundTask(Context ctx) {
        this.ctx = ctx;
    }

    @Override
    protected void onPreExecute() {
        alertDialog = new AlertDialog.Builder(ctx).create();
        alertDialog.setTitle("Login Info");
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected String doInBackground(String... params) {
        String method = params[0];
        String url_registrationI = "http://192.168.219.1/testenter/I_register.php";
        String url_login = "http://192.168.219.1/testenter/reterieve.php";
        String url_registrationO = "http://192.168.219.1/testenter/O_register.php";
        //if (isInternetAvailable()) {
          //  Log.d("Vishnu", "connected");
            if (method.equals("I_register")) {
                String fname = params[1];
                String lname = params[2];
                String email = params[3];
                String pass = params[4];
                String contact = params[5];
                String ipaddress = params[6];
                String date = params[7];
                try {
                    URL url = new URL(url_registrationI);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();

                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("fname", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8") + "&" +
                            URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(lname, "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8") + "&" +
                            URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8") + "&" +
                            URLEncoder.encode("jlogin", "UTF-8") + "=" + URLEncoder.encode(ipaddress, "UTF-8") + "&" +
                            URLEncoder.encode("jdate", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();
                    httpURLConnection.disconnect();
                    return "Registration Success....";

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else if (method.equals("login")) {
                emaildata = params[1];
                String pass = params[2];
                String ipaddress = params[3];
                String date = params[4];
                try {
                    URL url = new URL(url_login);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    httpURLConnection.setDoInput(true);
                    OutputStream outputStream = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    String data = URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(emaildata, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(pass, "UTF-8")+"&"+
                            URLEncoder.encode("lip", "UTF-8") + "=" + URLEncoder.encode(ipaddress, "UTF-8")+"&"+
                            URLEncoder.encode("ldate", "UTF-8") + "=" + URLEncoder.encode(date, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    outputStream.close();

                    InputStream inputStream = httpURLConnection.getInputStream();
                    BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "iso-8859-1"));
                    String response = "";
                    String line = "";
                    while ((line = bufferedReader.readLine()) != null) {
                        response += line;
                    }
                    bufferedReader.close();
                    inputStream.close();
                    httpURLConnection.disconnect();
                    return response;
                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Log.d("Vishnu","lauchded");
            } else if (method.equals("O_register")) {
                String name = params[1];
                String fname = params[2];
                String lname = params[3];
                String email = params[4];
                String password = params[5];
                String contact = params[6];
                String jlogin = params[7];
                String jdate = params[8];
                String plan = params[9];
                try {
                    URL url = new URL(url_registrationO);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
                    httpURLConnection.setRequestMethod("POST");
                    httpURLConnection.setDoOutput(true);
                    OutputStream OS = httpURLConnection.getOutputStream();
                    BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(OS, "UTF-8"));
                    String data = URLEncoder.encode("Oname", "UTF-8") + "=" + URLEncoder.encode(name, "UTF-8") + "&" +
                            URLEncoder.encode("fname", "UTF-8") + "=" + URLEncoder.encode(fname, "UTF-8") + "&" +
                            URLEncoder.encode("lname", "UTF-8") + "=" + URLEncoder.encode(lname, "UTF-8") + "&" +
                            URLEncoder.encode("email", "UTF-8") + "=" + URLEncoder.encode(email, "UTF-8") + "&" +
                            URLEncoder.encode("password", "UTF-8") + "=" + URLEncoder.encode(password, "UTF-8") + "&" +
                            URLEncoder.encode("jdate", "UTF-8") + "=" + URLEncoder.encode(jdate, "UTF-8") + "&" +
                            URLEncoder.encode("jlogin", "UTF-8") + "=" + URLEncoder.encode(jlogin, "UTF-8")+ "&" +
                            URLEncoder.encode("contact", "UTF-8") + "=" + URLEncoder.encode(contact, "UTF-8")+ "&" +
                            URLEncoder.encode("plan", "UTF-8") + "=" + URLEncoder.encode(plan, "UTF-8");
                    bufferedWriter.write(data);
                    bufferedWriter.flush();
                    bufferedWriter.close();
                    OS.close();
                    InputStream IS = httpURLConnection.getInputStream();
                    IS.close();
                    httpURLConnection.disconnect();
                    return "Registration Success....";

                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        return null;
        }
        //return "No Internet";
    //}
    public boolean isInternetAvailable() {
        try {
            InetAddress ipAddr = InetAddress.getByName("google.com");

            if (ipAddr.equals("")) {
                return false;
            } else {
                return true;
            }

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        if(result!=null) {
            if (result.equals("Registration Success....")) {
                Toast.makeText(ctx, result, Toast.LENGTH_SHORT).show();
            } else if(result.equals("No Internet")){
                alertDialog.setMessage(result);
                alertDialog.show();
            }
            else {
                if(result.equals("failed"))
                {
                    String r ="Username or Password doesnot match";
                    alertDialog.setMessage(r);
                    alertDialog.show();
                }
                else {
                    data d = new data();
                    d.setter(emaildata,result);
                    Intent i = new Intent(ctx,Main2Activity.class);
                    ctx.startActivity(i);
                }
            }
        }
    }
}
