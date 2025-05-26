package com.example.duoclone.utils;

import com.example.duoclone.models.Lesson;
import com.example.duoclone.models.QuizQuestion;
import java.util.Arrays;
import java.util.List;

public class LessonLoader {
    public List<Lesson> loadLessons(String language) {
        QuizQuestion quizQuestion = new QuizQuestion(
                "q1",
                getQuestionText(language),
                Arrays.asList(getOption1(language), getOption2(language)),
                0,
                10
        );

        Lesson lesson = new Lesson(
                "lesson1",
                getLessonTitle(language),
                "quiz",
                1,
                0,
                quizQuestion
        );

        return Arrays.asList(lesson);
    }

    private String getLessonTitle(String lang) {
        return lang.equals("kk") ? "Негізгі тест" : "Basic Test";
    }

    private String getQuestionText(String lang) {
        return lang.equals("kk") ? "Сәлем нешеу?" : "What is 'Hello'?";
    }

    private String getOption1(String lang) {
        return lang.equals("kk") ? "Сәлем" : "Hello";
    }

    private String getOption2(String lang) {
        return lang.equals("kk") ? "Сау бол" : "Goodbye";
    }
}