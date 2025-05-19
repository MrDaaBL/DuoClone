package com.example.duoclone.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ProgressManager {
    private static final String PREFS_NAME = "DuoClonePrefs";
    private static final String KEY_PROGRESS = "progress";

    public static void saveProgress(Context context, int progress) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit().putInt(KEY_PROGRESS, progress).apply();
    }

    public static int getProgress(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return prefs.getInt(KEY_PROGRESS, 0);
    }
}