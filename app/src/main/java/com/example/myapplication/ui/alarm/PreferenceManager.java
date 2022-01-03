package com.example.myapplication.ui.alarm;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class PreferenceManager {
    public static final String PREFERENCES_NAME = "rebuild_preference";

    private static final String DEFAULT_VALUE_STRING = "";
    private static final boolean DEFAULT_VALUE_BOOLEAN = false;
    private static final int DEFAULT_VALUE_INT = -1;
    private static final long DEFAULT_VALUE_LONG = -1L;
    private static final float DEFAULT_VALUE_FLOAT = -1F;

    private static SharedPreferences getPreferences(Context context) {
        return context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
    }

    public static void setAlarms(Context context, String key, ArrayList<Alarm> alarms) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(alarms);

        editor.putString(key, json);
        editor.commit();
    }
    public static ArrayList<Alarm> getAlarms(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String resp = prefs.getString(key, "");
        ArrayList<Alarm> alarms;

        Gson gson = new Gson();
        alarms = gson.fromJson(resp, new TypeToken<ArrayList<Alarm>>(){}.getType());

        if (alarms == null) {
            alarms = new ArrayList<Alarm>();
        }

        return alarms;
    }

    public static void setAlarm(Context context, String key, Alarm alarm) {
        SharedPreferences prefs = getPreferences(context);
        SharedPreferences.Editor editor = prefs.edit();

        Gson gson = new Gson();
        String json = gson.toJson(alarm);

        editor.putString(key, json);
        editor.commit();
    }
    public static Alarm getAlarm(Context context, String key) {
        SharedPreferences prefs = getPreferences(context);
        String resp = prefs.getString(key, "");

        Gson gson = new Gson();
        Alarm alarm = gson.fromJson(resp, Alarm.class);

        return alarm;
    }
}
