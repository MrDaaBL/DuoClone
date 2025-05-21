package com.example.duoclone.utils;

import android.content.res.AssetManager;
import com.example.duoclone.models.Lesson;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LessonLoader {
    public static List<Lesson> loadLessons(AssetManager assets, String languageCode) {
        List<Lesson> lessons = new ArrayList<>();
        try {
            InputStream is = assets.open("lessons/" + languageCode + "/basic.json");
            byte[] buffer = new byte[is.available()];
            is.read(buffer);
            is.close();
            String json = new String(buffer, "UTF-8");

            JSONObject root = new JSONObject(json);
            JSONArray lessonsArray = root.getJSONArray("lessons");
            for (int i = 0; i < lessonsArray.length(); i++) {
                JSONObject lessonJson = lessonsArray.getJSONObject(i);
                String id = lessonJson.getString("id");
                String title = lessonJson.getString("title");
                String type = lessonJson.getString("type");
                JSONArray content = lessonJson.getJSONArray("content");

                lessons.add(new Lesson(
                        id,
                        title,
                        type,
                        content.length(),
                        0,
                        false
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lessons;
    }
}