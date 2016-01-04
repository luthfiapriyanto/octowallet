package com.example.luthf.projectqroctowallet.app;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.util.Log;

import com.example.luthf.projectqroctowallet.LoginActivity;

import java.util.HashMap;

public class SessionManager {
    // LogCat tag
    private static String TAG = SessionManager.class.getSimpleName();

    // Shared Preferences
    SharedPreferences pref;

    Editor editor;
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "MPos Login";

    private static final String KEY_IS_LOGGEDIN = "isLoggedIn";

    //ID (make variable public to access from outside)
    public static final String KEY_ID = "cid";

    public static final String KEY_ITEM = "item";

    // Password (make variable public to access from outside)
    public static final String KEY_PASSWORD = "pass";

    // history (make variable public to access from outside)
    public static final String KEY_CID = "cid";

    public SessionManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    //For Session Login
    public void createLoginSession(String id, String pass, String cid){
        // Storing login value as TRUE
        editor.putBoolean(KEY_IS_LOGGEDIN, true);

        // Storing name in pref
        editor.putString(KEY_ID, id);

        editor.putString(KEY_CID, cid);

        // Storing email in pref
        editor.putString(KEY_PASSWORD, pass);

        // commit changes
        editor.commit();
    }

    //FOR CHECK LOGIN

    public void checkLogin(){
        // Check login status
        if(!this.isLoggedIn()){
            // user is not logged in redirect him to Login Activity
            Intent i = new Intent(_context, LoginActivity.class);
            // Closing all the Activities
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            // Add new Flag to start new Activity
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

            // Staring Login Activity
            _context.startActivity(i);
        }

    }

    public void setLogin(boolean isLoggedIn) {

        editor.putBoolean(KEY_IS_LOGGEDIN, isLoggedIn);

        // commit changes
        editor.commit();

        Log.d(TAG, "User login session modified!");
    }

    //Get stored session data just name
    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        // user name
        user.put(KEY_CID, pref.getString(KEY_CID, null));

        // return user
        return user;
    }

    //FOR LOGOUT USER AND REMOVE SESSION

    public void logoutUser(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Log.d(TAG, "User logout!");
        // After logout redirect user to Loing Activity
        Intent i;
        i = new Intent(_context, LoginActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        _context.startActivity(i);

    }

    public void logoutUser_L(){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();
        Log.d(TAG, "User logout!");
        // After logout redirect user to Loing Activity
    }

    public boolean isLoggedIn(){
        return pref.getBoolean(KEY_IS_LOGGEDIN, false);
    }
}