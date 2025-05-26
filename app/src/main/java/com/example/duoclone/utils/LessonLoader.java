package com.example.duoclone.utils;

import android.content.res.AssetManager;
import com.example.duoclone.models.Lesson;
import com.example.duoclone.models.QuizQuestion;
import com.example.duoclone.models.VocabularyCard;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class LessonLoader {
    public static List<Lesson> loadLessons(AssetManager assets, String languageCode) {
        List<Lesson> lessons = new ArrayList<>();
        try {
            InputStream is = assets.open("lessons/lessons_" + languageCode + ".json");
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
                int total = lessonJson.getInt("totalExercises");
                int completed = lessonJson.getInt("completedExercises");
                JSONObject content = lessonJson.getJSONObject("content");

                Object lessonContent = null;
                if (type.equals("vocabulary")) {
                    // Исправление: ручное преобразование JSONArray в List<String>
                    JSONArray optionsArray = content.getJSONArray("options");
                    List<String> options = new ArrayList<>();
                    for (int j = 0; j < optionsArray.length(); j++) {
                        options.add(optionsArray.getString(j));
                    }

                    lessonContent = new VocabularyCard(
                            content.getString("word"),
                            content.getString("translation"),
                            options // Передаем готовый список
                    );
                } else if (type.equals("quiz")) {
                    // Аналогичное исправление для QuizQuestion
                    JSONArray optionsArray = content.getJSONArray("options");
                    List<String> options = new ArrayList<>();
                    for (int j = 0; j < optionsArray.length(); j++) {
                        options.add(optionsArray.getString(j));
                    }

                    lessonContent = new QuizQuestion(
                            content.getString("question"),
                            options, // Готовый список
                            content.getInt("correctAnswerIndex")
                    );
                }

                lessons.add(new Lesson(id, title, type, total, completed, lessonContent));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lessons;
    }
}