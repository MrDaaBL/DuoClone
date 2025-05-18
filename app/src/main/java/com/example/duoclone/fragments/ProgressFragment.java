package com.example.duoclone.fragments;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.example.duoclone.R;

public class ProgressFragment extends Fragment {

    public ProgressFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Инфлейтим макет фрагмента
        return inflater.inflate(R.layout.fragment_progress, container, false);
    }
}