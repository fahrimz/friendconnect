package com.fahrimz.friendconnect;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.HashMap;

public class PrefManager {
    // Shared Preferences
    SharedPreferences pref;

    // Editor for Shared preferences
    SharedPreferences.Editor editor;

    // Context
    Context _context;

    // Shared pref mode
    int PRIVATE_MODE = 0;

    // Shared preferences file name
    private static final String PREF_NAME = "FRIEND_CONNECT_PREF";

    // All Shared Preferences Keys
    public static final String KEY_IS_LOGGED_IN = "isLoggedIn";
    public static final String KEY_ACCESS_TOKEN = "accessToken";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_ID_USER = "idUser";

    public PrefManager(Context context) {
        this._context = context;
        pref = _context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = pref.edit();
    }

    public void setIdUser(int idUser) {
        editor.putInt(KEY_ID_USER, idUser);
        editor.commit();
    }

    public int getIdUser() {
        return pref.getInt(KEY_ID_USER, 0);
    }

    public void setUsername(String username) {
        editor.putString(KEY_USERNAME, username);
        editor.commit();
    }

    public String getUsername() {
        return pref.getString(KEY_USERNAME, null);
    }

    public void setAccessToken(String accessToken) {
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.commit();
    }

    public String getAccessToken() {
        return pref.getString(KEY_ACCESS_TOKEN, null);
    }

    public void createLogin(int idUser, String accessToken, String username) {
        editor.putInt(KEY_ID_USER, idUser);
        editor.putString(KEY_USERNAME, username);
        editor.putString(KEY_ACCESS_TOKEN, accessToken);
        editor.putBoolean(KEY_IS_LOGGED_IN, true);
        editor.commit();
    }

    public boolean isLoggedIn() {
        return pref.getBoolean(KEY_IS_LOGGED_IN, false);
    }

    public void clearSession() {
        editor.clear();
        editor.commit();
    }

    public HashMap<String, String> getUserDetails() {
        HashMap<String, String> profile = new HashMap<>();
        profile.put("username", pref.getString(KEY_USERNAME, null));
        profile.put("accessToken", pref.getString(KEY_ACCESS_TOKEN, null));
        return profile;
    }
}
