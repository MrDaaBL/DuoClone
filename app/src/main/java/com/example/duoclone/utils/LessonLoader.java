package com.example.duoclone.utils;

import com.example.duoclone.models.QuizQuestion;
import java.util.Arrays;
import java.util.List;

public class LessonLoader {
    public List<QuizQuestion> loadExtendedLessons(String language) {
        return Arrays.asList(
                // 10 примерных вопросов на казахском
                new QuizQuestion("q1", "Сәлем нешеу?",
                        Arrays.asList("Сәлем", "Сау бол"), 0, 1),
                new QuizQuestion("q2", "Қалайсың?",
                        Arrays.asList("Жақсы", "Жаман"), 0, 1),
                new QuizQuestion("q3", "Сенің атың кім?",
                        Arrays.asList("Менің атым...", "Рақмет"), 0, 1),
                new QuizQuestion("q4", "'Кітап' сөзінің ағылшынша аудармасы?",
                        Arrays.asList("Book", "Pen"), 0, 1),
                new QuizQuestion("q5", "Үй деген не?",
                        Arrays.asList("House", "Car"), 0, 1),
                new QuizQuestion("q6", "Алма түсі?",
                        Arrays.asList("Қызыл", "Көк"), 0, 1),
                new QuizQuestion("q7", "2+2 неше?",
                        Arrays.asList("4", "5"), 0, 1),
                new QuizQuestion("q8", "Астана қаласы",
                        Arrays.asList("Қазақстан", "Ресей"), 0, 1),
                new QuizQuestion("q9", "Қыс мезгілі",
                        Arrays.asList("Winter", "Summer"), 0, 1),
                new QuizQuestion("q10", "Суретте не бар? (яблоко)",
                        Arrays.asList("Алма", "Алмұрт"), 0, 1)
        );
    }
}