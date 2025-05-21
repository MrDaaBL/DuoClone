package com.example.duoclone.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import androidx.fragment.app.Fragment;
import com.example.duoclone.R;
import com.example.duoclone.network.NetworkMonitor;
import androidx.navigation.Navigation;

public class NoInternetFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_no_internet, container, false);

        Button retryButton = view.findViewById(R.id.btn_retry);
        retryButton.setOnClickListener(v -> {
            if (NetworkMonitor.isOnline(requireContext())) {
                Navigation.findNavController(v).navigateUp();
            }
        });

        return view;
    }
}