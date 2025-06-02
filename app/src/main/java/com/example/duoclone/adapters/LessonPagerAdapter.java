package com.example.duoclone.adapters;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.example.duoclone.fragments.LessonsFragment;
import com.example.duoclone.fragments.ImageLessonFragment;

public class LessonPagerAdapter extends FragmentPagerAdapter {
    private int currentLevel = 1;

    public LessonPagerAdapter(FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

    public void setLevel(int level) {
        this.currentLevel = level;
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: return QuizFragment.newInstance(currentLevel);
            case 1: return ImageLessonFragment.newInstance(currentLevel);
            default: return QuizFragment.newInstance(currentLevel);
        }
    }

    @Override
    public int getCount() {
        return 2; // Количество вкладок
    }
}