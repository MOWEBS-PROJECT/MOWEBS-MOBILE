package com.example.mowebs;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Preference {

    private static final String USER_ID = "USERID";
    private static final String USERNAME = "USERNAME";

    private static SharedPreferences getSharedPreference(Context context) {
        return PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static void setUserId(Context context, String userID) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(USER_ID, userID);
        editor.apply();
    }

    public static String getUserId(Context context) {
        return getSharedPreference(context).getString(USER_ID, "");
    }

    public static void setUsername(Context context, String username) {
        SharedPreferences.Editor editor = getSharedPreference(context).edit();
        editor.putString(USERNAME, username);
        editor.apply();
    }

    public static String getUsername(Context context) {
        return getSharedPreference(context).getString(USERNAME, "");
    }

}
