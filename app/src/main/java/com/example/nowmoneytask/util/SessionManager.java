package com.example.nowmoneytask.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.example.nowmoneytask.ui.MainActivity;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private Context mContext;
    private int PRIVATE_MODE = 0;

    private final String PREF_NAME = "TaskPref";

    private final String IS_LOGIN = "isLogged";

    public static final String KEY_TOKEN = "token";


    public SessionManager(Context context) {
        this.mContext = context;
        pref = mContext.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }


    public void createLoginSession(String tok) {
        editor.putBoolean(IS_LOGIN, true);
        editor.putString(KEY_TOKEN, tok);

        editor.commit();
    }

    public HashMap<String, String> getUSerDetails() {
        HashMap<String, String> user = new HashMap<>();

        user.put(KEY_TOKEN, pref.getString(KEY_TOKEN, null));

        return user;

    }

    /**
     * Clear session details
     * */
    public void logoutUser(Activity activity){
        // Clearing all data from Shared Preferences
        editor.clear();
        editor.commit();

        // After logout redirect user to Loing Activity
        Intent i = new Intent(activity, MainActivity.class);
        // Closing all the Activities
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        // Add new Flag to start new Activity
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        // Staring Login Activity
        activity.startActivity(i);
        activity.finish();

    }

    public boolean isLoggedIn() {
        return pref.getBoolean(IS_LOGIN, false);
    }


}
