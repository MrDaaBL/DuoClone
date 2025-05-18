package com.example.duoclone.activities;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.example.duoclone.R;
import com.example.duoclone.models.Language;
import java.util.ArrayList;
import java.util.List;

public class LanguageSelectionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_language_selection);

        // Создаем список языков
        List<Language> languages = new ArrayList<>();
        languages.add(new Language("English", R.drawable.flag_usa));
        languages.add(new Language("Русский", R.drawable.flag_russia));
        languages.add(new Language("Español", R.drawable.flag_spain));
        languages.add(new Language("Français", R.drawable.flag_france));
        languages.add(new Language("Deutsch", R.drawable.flag_germany));
        languages.add(new Language("中文", R.drawable.flag_china));
        languages.add(new Language("日本語", R.drawable.flag_japan));

        // Находим контейнер для языков
        LinearLayout container = findViewById(R.id.languagesContainer);

        // Динамически добавляем элементы
        for (Language lang : languages) {
            // Создаем элемент языка
            View languageItem = getLayoutInflater().inflate(R.layout.item_language, container, false);

            // Находим элементы внутри item_language
            ImageView flag = languageItem.findViewById(R.id.flag);
            TextView name = languageItem.findViewById(R.id.languageName);

            // Устанавливаем данные
            flag.setImageResource(lang.getFlagResourceId());
            name.setText(lang.getName());

            // Обработчик клика
            languageItem.setOnClickListener(v -> {
                // Переход на главный экран
                Intent intent = new Intent(LanguageSelectionActivity.this, MainActivity.class);
                startActivity(intent);
            });

            // Добавляем в контейнер
            container.addView(languageItem);
        }
    }
}