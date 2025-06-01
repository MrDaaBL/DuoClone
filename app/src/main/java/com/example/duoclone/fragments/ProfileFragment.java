package com.example.duoclone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.duoclone.R;
import com.example.duoclone.adapters.AchievementsAdapter;
import com.example.duoclone.models.Achievement;
import com.example.duoclone.ui.LoginBottomSheetDialog;
import com.example.duoclone.utils.FirestoreManager;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.List;

public class ProfileFragment extends Fragment {
    private RecyclerView achievementsRecyclerView;
    private TextView userNameText;
    private FirestoreManager firestoreManager;
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;

    private LinearLayout authSection, profileContent;
    private EditText emailField, passwordField;
    private Button loginButton, registerButton, logoutButton;
    private SignInButton googleButton;
    private TextView emailDisplay;
    private final int RC_GOOGLE_SIGN_IN = 9001;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);
        mAuth = FirebaseAuth.getInstance();

        authSection = view.findViewById(R.id.auth_section);
        profileContent = view.findViewById(R.id.profile_content);

        emailField = view.findViewById(R.id.editTextEmail);
        passwordField = view.findViewById(R.id.editTextPassword);
        loginButton = view.findViewById(R.id.buttonLogin);
        registerButton = view.findViewById(R.id.buttonRegister);
        googleButton = view.findViewById(R.id.buttonGoogle);
        logoutButton = view.findViewById(R.id.buttonLogout);
        emailDisplay = view.findViewById(R.id.textViewUserEmail);


        loginButton.setOnClickListener(v -> signInEmail());
        registerButton.setOnClickListener(v -> registerEmail());
        logoutButton.setOnClickListener(v -> logout());
        googleButton.setOnClickListener(v -> signInWithGoogle());

        if (FirebaseAuth.getInstance().getCurrentUser() == null) {
            new LoginBottomSheetDialog().show(getChildFragmentManager(), "LoginDialog");
        } else {
            // показать достижения и т.д.
            loadUserProfile();
        }

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // 👈 из google-services.json
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso);

        updateUI();

        return view;
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        if (user == null) {
            new LoginBottomSheetDialog().show(getChildFragmentManager(), "LoginDialog");
        } else {
            loadUserProfile();
        }
    }

    private void updateUI() {
        FirebaseUser user = mAuth.getCurrentUser();
        if (user != null) {
            authSection.setVisibility(View.GONE);
            profileContent.setVisibility(View.VISIBLE);
            emailDisplay.setText("Email: " + user.getEmail());
        } else {
            authSection.setVisibility(View.VISIBLE);
            profileContent.setVisibility(View.GONE);
        }
    }

    private void signInEmail() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> updateUI())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Қате: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    private void registerEmail() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnSuccessListener(authResult -> updateUI())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Тіркелу қатесі: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }
    private void logout() {
        mAuth.signOut();
        googleSignInClient.signOut();
        updateUI();
    }
    private void loadUserProfile() {
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            TextView usernameTextView = requireView().findViewById(R.id.username_text);
            TextView emailTextView = requireView().findViewById(R.id.email_text);
            ImageView avatarImage = requireView().findViewById(R.id.avatar_image);

            usernameTextView.setText(user.getDisplayName());
            emailTextView.setText(user.getEmail());

            // Используем Glide для загрузки аватарки, если она есть
            Glide.with(this)
                    .load(user.getPhotoUrl())
                    .placeholder(R.drawable.ic_person)
                    .into(avatarImage);

            // Показать UI (например, RecyclerView с достижениями и т.д.)
            View achievementsSection = requireView().findViewById(R.id.achievements_container);
            achievementsSection.setVisibility(View.VISIBLE);

            View logoutButton = requireView().findViewById(R.id.logout_button);
            logoutButton.setVisibility(View.VISIBLE);
            logoutButton.setOnClickListener(v -> {
                FirebaseAuth.getInstance().signOut();
                getActivity().recreate(); // Перезапускаем фрагмент
            });
        }
    }
    private void signInWithGoogle() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_GOOGLE_SIGN_IN);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_GOOGLE_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Google кіру қатесі", Toast.LENGTH_SHORT).show();
            }
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnSuccessListener(authResult -> updateUI())
                .addOnFailureListener(e -> Toast.makeText(getContext(), "Қате: " + e.getMessage(), Toast.LENGTH_SHORT).show());
    }

}