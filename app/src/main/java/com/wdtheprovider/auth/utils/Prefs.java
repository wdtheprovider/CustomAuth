package com.wdtheprovider.auth.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class Prefs extends Application {

    public SharedPreferences preferences;
    public String prefName = "DB";
    private final SharedPreferences.Editor editor;


    @SuppressLint("CommitPrefEdits")
    public Prefs(Context context) {
        preferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE);
        editor = preferences.edit();
    }

    public static void saveIsLogin(Context context,boolean flag) {
       setBooleanPreference(context,"IsLoggedIn",flag);
    }

    public static boolean getIsLogin(Context context) {
        return getBooleanPreference(context,"IsLoggedIn",false);
    }

    public static void saveLogin(Context context, String user_id, String user_name, String email) {
        setStringPreference(context,"user_id",user_id);
        setStringPreference(context,"user_name", user_name);
        setStringPreference(context,"email", email);
    }

    public static String getUserId(Context context) {
        return getStringPreference(context,"user_id","");
    }

    public static String getUserName(Context context) {
        return getStringPreference(context,"user_name","");
    }

    public static String getUserEmail(Context context) {
        return getStringPreference(context,"email","");
    }

    public static String getType(Context context) {

        return getStringPreference(context,"type","");
    }

    public static  void saveType(Context context,String type) {
        setStringPreference(context,"type",type);
    }


    /**
     * Adds String values
     */
    public static void setStringPreference(Context context, String key, String value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, value).apply();
    }

    /**
     * Sets boolean values
     */
    public static void setBooleanPreference(Context context, String key, boolean value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putBoolean(key, value).apply();
    }

    /**
     * Sets long values
     */
    private static void setLongPreference(Context context, String key, long value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putLong(key, value).apply();
    }

    /**
     * Sets int values
     */
    public static void setIntegerPreference(Context context, String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putInt(key, value).apply();
    }


    private static void setFloatPreference(Context context, String key, int value) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putFloat(key, value).apply();
    }

    /**
     * This deletes the entire SharedPreference file
     */
    private static void removePreference(Context context, String key) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).apply();
    }




    /**
     * Gets String values
     */
    public static String getStringPreference(Context context, String key, String defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getString(key, defaultValue);
    }

    /**
     * Gets boolean values
     */
    public static boolean getBooleanPreference(Context context, String key, boolean defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getBoolean(key, defaultValue);
    }

    /**
     * Gets long values
     */
    private static long getLongPreference(Context context, String key, long defaultValue) {
        return PreferenceManager.getDefaultSharedPreferences(context).getLong(key, defaultValue);
    }

    /**
     * Gets int values
     */
    private static int getIntegerPreference(Context context, String key, Integer def) {
        SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
        return prefs.getInt(key, def);
    }


}
