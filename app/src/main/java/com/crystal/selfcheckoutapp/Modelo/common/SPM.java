package com.crystal.selfcheckoutapp.Modelo.common;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import javax.crypto.SecretKey;

public class SPM {
    private static final String APP_SETTINGS_FILE = "CRYSTAL_SELFCHECKOUT";
    private static final String LOG_PREF = "AppLog";
    private static final String LOG_KEY = "logData";
    private static final Gson gson = new Gson();

    private SPM() {}

    public static <T> void addLogStep(String title, T content) {
        String currentLog = getSharedPreferences().getString(LOG_KEY, "[]");
        JsonArray logSteps = gson.fromJson(currentLog, JsonArray.class);

        JsonObject step = new JsonObject();
        step.addProperty("title", title);
        step.add("content", gson.toJsonTree(content));

        logSteps.add(step);

        // Guardar el log actualizado
        getSharedPreferences().edit().putString(LOG_KEY, gson.toJson(logSteps)).apply();
    }

    public static String getLog() {
        return getSharedPreferences().getString(LOG_KEY, "[]");
    }

    public static void clearLog() {
        getSharedPreferences().edit().remove(LOG_KEY).apply();
    }

    private static SharedPreferences getSharedPreferences() {
        return MyApp.getContext()
                .getSharedPreferences(APP_SETTINGS_FILE, Context.MODE_PRIVATE);
    }

    public static void setString(String dataLabel, String dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putString(dataLabel, dataValue);
        editor.apply();
    }

    public static String getString(String dataLabel) {
        return  getSharedPreferences().getString(dataLabel, null);
    }

    @SuppressLint({"ApplySharedPref"})
    public static void setObject(String key, Object obj) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        Gson gson = new Gson();
        String json = gson.toJson(obj);

        editor.putString(key, json);
        editor.apply();
    }

    public static Object getObject(String key, Class<?> clazz) {
        String json = getSharedPreferences().getString(key, null);

        Gson gson = new Gson();
        return gson.fromJson(json, clazz);
    }

    @SuppressLint({"ApplySharedPref"})
    public static void setObjectList(String key, List<?> objList) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();

        Gson gson = new Gson();
        String json = gson.toJson(objList);

        editor.putString(key, json);
        editor.apply();
    }

    public static <T> List<T> getObjectList(String key, Class<T> clazz) {
        String json = getSharedPreferences().getString(key, null);

        if (json == null) {
            return new ArrayList<>();
        }

        Gson gson = new Gson();
        Type type = TypeToken.getParameterized(List.class, clazz).getType();
        return gson.fromJson(json, type);
    }


    public static void setFloat(String dataLabel, float dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putFloat(dataLabel, dataValue);
        editor.apply();
    }

    public static float getFloat(String dataLabel) {
        return getSharedPreferences().getFloat(dataLabel,0);
    }

    public static void setBoolean(String dataLabel, boolean dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putBoolean(dataLabel, dataValue);
        editor.apply();
    }

    public static boolean getBoolean(String dataLabel) {
        return getSharedPreferences().getBoolean(dataLabel,false);
    }

    public static void setInt(String dataLabel, int dataValue) {
        SharedPreferences.Editor editor = getSharedPreferences().edit();
        editor.putInt(dataLabel, dataValue);
        editor.apply();
    }

    public static int getInt(String dataLabel) {
        return getSharedPreferences().getInt(dataLabel,0);
    }
}