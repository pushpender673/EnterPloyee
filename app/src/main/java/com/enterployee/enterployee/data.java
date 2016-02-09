package com.enterployee.enterployee;

import android.util.Log;

import java.security.acl.LastOwnerException;

/**
 * Created by Pushpender on 09-02-2016.
 */
public class data {
    public static String emaildata,result;
    public void setter(String e,String r){
        Log.d("Vishnu"," "+e+" "+r);
        this.emaildata = e;
        this.result = r;
    }
}
