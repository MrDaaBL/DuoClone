package com.example.duoclone.utils;

import com.example.duoclone.models.QuizQuestion;
import com.example.duoclone.models.Lesson;
import java.util.Arrays;
import java.util.List;

public class LessonLoader {
    public List<Lesson> loadLessonsForLanguage(String language) {
        return Arrays.asList(
                new Lesson(
                        "lesson1",
                        "Основные фразы",
                        "quiz",
                        10,
                        0,
                        new QuizQuestion(
                                "q1",
                                "Как сказать 'Привет' на " + language + "?",
                                Arrays.asList("Hello", "Hi"),
                                0, // Индекс правильного ответа
                                "multiple_choice",
                                5
                        )
                )
        );
    }
}