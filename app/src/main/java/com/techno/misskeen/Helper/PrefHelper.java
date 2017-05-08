package com.techno.misskeen.Helper;

import android.content.Context;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import com.google.gson.Gson;

/**
 * Created by Paulina on 5/8/2017.
 */
public class PrefHelper {
    public static void saveToPref(Context context, String key, String val) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().putString(key, val).commit();
    }

    public static void clearAll(Context context) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().clear().commit();

    }

    public static void remove(Context context, String key) {
        PreferenceManager.getDefaultSharedPreferences(context).edit().remove(key).commit();

    }

    public static String getPref(Context context, String key) {
        if (PreferenceManager.getDefaultSharedPreferences(context).contains(key)) {
            return PreferenceManager.getDefaultSharedPreferences(context).getString(key, null);
        } else {
            return null;
        }
    }

//    public static void saveJson(Context context, Object object, String params) { //params : VarConstant.xxx
//        Gson gson = new Gson();
//        String json = gson.toJson(object).toString();
//        PrefHelper.saveToPref(context, params, varcons.wrap(json).trim());
//    }
//
//    public static <T> T getJson(Context context, Class<T> classOfT, String params) { //params : VarConstant.xxx
//        String data = PrefHelper.getPref(context, params);
//        if (!TextUtils.isEmpty(data)) {
//            data = varcons.unwrap(data).trim();
//            Gson gson = new Gson();
//            return gson.fromJson(data, classOfT);
//        }
//        return null;
//    }

}
