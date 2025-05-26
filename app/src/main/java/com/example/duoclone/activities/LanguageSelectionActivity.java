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

        List<Language> languages = new ArrayList<>();
        languages.add(new Language("English", R.drawable.flag_usa));
        languages.add(new Language("Казақ тілі", R.drawable.ic_flag_kz));
        languages.add(new Language("Español", R.drawable.flag_spain));
        languages.add(new Language("Français", R.drawable.flag_france));
        languages.add(new Language("Deutsch", R.drawable.flag_germany));
        languages.add(new Language("中文", R.drawable.flag_china));
        languages.add(new Language("日本語", R.drawable.flag_japan));

        LinearLayout container = findViewById(R.id.languagesContainer);
        for (Language lang : languages) {
            View view = getLayoutInflater().inflate(R.layout.item_language, container, false);
            ImageView flag = view.findViewById(R.id.flag);
            TextView name = view.findViewById(R.id.languageName);

            flag.setImageResource(lang.getFlagResourceId());
            name.setText(lang.getName());

            view.setOnClickListener(v -> {
                // Запуск MainActivity БЕЗ закрытия текущей активности
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
            });
            container.addView(view);
        }
    }
}