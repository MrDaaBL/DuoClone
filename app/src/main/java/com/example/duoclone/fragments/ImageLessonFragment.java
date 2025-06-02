package com.example.duoclone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.duoclone.R;

public class ImageLessonFragment extends Fragment {
    private ImageView imageView;
    private TextView textWord;
    private String[] imageNames = {"food1", "food2", "animal1"};
    private String[] words = {"Apple", "Bread", "Cat"};

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_image_lesson, container, false);
        imageView = view.findViewById(R.id.image_lesson);
        textWord = view.findViewById(R.id.text_word);

        loadImage(0);
        return view;
    }

    private void loadImage(int index) {
        if (index >= 0 && index < imageNames.length) {
            int resId = requireContext().getResources().getIdentifier(
                    imageNames[index],
                    "drawable",
                    requireContext().getPackageName()
            );
            imageView.setImageResource(resId);
            textWord.setText(words[index]);
        }
    }
}