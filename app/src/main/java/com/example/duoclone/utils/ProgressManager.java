package com.example.duoclone.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class ProgressManager {
    private static final String PREFS_NAME = "DuoClonePrefs";
    private static final String KEY_XP = "xp";
    private static final String KEY_COMPLETED = "completed";

    public static void saveProgress(Context context, int xp, int completedLessons) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        prefs.edit()
                .putInt(KEY_XP, xp)
                .putInt(KEY_COMPLETED, completedLessons)
                .apply();
    }

    public static int[] loadProgress(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
        return new int[] {
                prefs.getInt(KEY_XP, 0),
                prefs.getInt(KEY_COMPLETED, 0)
        };
    }
}