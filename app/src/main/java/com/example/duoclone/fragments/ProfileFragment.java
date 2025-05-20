package com.example.duoclone.fragments;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.duoclone.R;
import com.example.duoclone.activities.LoginActivity;
import com.example.duoclone.adapters.AchievementsAdapter;
import com.example.duoclone.utils.FirestoreManager;
import com.google.firebase.auth.FirebaseAuth;

public class ProfileFragment extends Fragment {
    private FirestoreManager firestoreManager;
    private RecyclerView achievementsRecyclerView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        achievementsRecyclerView = view.findViewById(R.id.achievements_recycler_view);
        achievementsRecyclerView.setLayoutManager(new GridLayoutManager(getContext(), 2));

        // Получение ID текущего пользователя
        String userId = FirebaseAuth.getInstance().getCurrentUser().getUid();
        if (userId == null) {
            // Пользователь не авторизован
            startActivity(new Intent(getActivity(), LoginActivity.class));
            getActivity().finish();
        }

        return view;
    }
}