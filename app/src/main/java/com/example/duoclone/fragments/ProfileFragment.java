package com.example.duoclone.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import android.content.IntentSender;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.IntentSenderRequest;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.duoclone.R;
import com.example.duoclone.adapters.AchievementAdapter;
import com.example.duoclone.models.Achievement;
import com.google.android.gms.auth.api.identity.BeginSignInRequest;
import com.google.android.gms.auth.api.identity.Identity;
import com.google.android.gms.auth.api.identity.SignInClient;
import com.google.android.gms.auth.api.identity.SignInCredential;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class ProfileFragment extends Fragment {

    // UI элементы
    private TextView usernameTextView, lessonTextView, levelTextView;
    private RecyclerView achievementsRecyclerView;
    private AchievementAdapter achievementAdapter;
    private List<Achievement> achievementList;
    private Button loginButton, registerButton, googleSignInButton, buttonLogout;
    private EditText editTextEmail, editTextPassword;
    private LinearLayout authButtonsLayout, profileDataLayout;

    // Firebase
    private FirebaseUser user;
    private FirebaseFirestore db;
    private FirebaseAuth mAuth;

    // Google Sign-In
    private SignInClient oneTapClient;
    private BeginSignInRequest signInRequest;
    private final ActivityResultLauncher<IntentSenderRequest> googleSignInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartIntentSenderForResult(),
            result -> {
                try {
                    SignInCredential credential = oneTapClient.getSignInCredentialFromIntent(result.getData());
                    String idToken = credential.getGoogleIdToken();
                    if (idToken != null) {
                        firebaseAuthWithGoogle(idToken);
                    }
                } catch (Exception e) {
                    Log.e("GoogleSignIn", "Error signing in with Google", e);
                }
            });

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Инициализация Firebase
        mAuth = FirebaseAuth.getInstance();
        db = FirebaseFirestore.getInstance();

        // Инициализация Google Sign-In
        oneTapClient = Identity.getSignInClient(requireActivity());
        signInRequest = BeginSignInRequest.builder()
                .setGoogleIdTokenRequestOptions(BeginSignInRequest.GoogleIdTokenRequestOptions.builder()
                        .setSupported(true)
                        .setServerClientId(getString(R.string.default_web_client_id))
                        .setFilterByAuthorizedAccounts(true)
                        .build())
                .build();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // Инициализация UI элементов
        initViews(view);

        // Настройка RecyclerView
        setupRecyclerView();

        // Установка обработчиков кликов
        setupClickListeners();

        // Обновление UI
        updateUI(mAuth.getCurrentUser() != null);
    }

    private void initViews(View view) {
        usernameTextView = view.findViewById(R.id.text_username);
        lessonTextView = view.findViewById(R.id.text_lessons_completed);
        levelTextView = view.findViewById(R.id.text_level);
        achievementsRecyclerView = view.findViewById(R.id.recycler_achievements);

        loginButton = view.findViewById(R.id.loginButton);
        registerButton = view.findViewById(R.id.registerButton);
        googleSignInButton = view.findViewById(R.id.googleSignInButton);
        buttonLogout = view.findViewById(R.id.buttonLogout);

        editTextEmail = view.findViewById(R.id.editTextEmail);
        editTextPassword = view.findViewById(R.id.editTextPassword);

        authButtonsLayout = view.findViewById(R.id.authButtonsLayout);
        profileDataLayout = view.findViewById(R.id.profileDataLayout);
    }

    private void setupRecyclerView() {
        achievementList = new ArrayList<>();
        achievementAdapter = new AchievementAdapter(achievementList);
        achievementsRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        achievementsRecyclerView.setAdapter(achievementAdapter);
    }

    private void setupClickListeners() {
        loginButton.setOnClickListener(v -> handleLogin());
        registerButton.setOnClickListener(v -> handleRegistration());
        googleSignInButton.setOnClickListener(v -> handleGoogleSignIn());
        buttonLogout.setOnClickListener(v -> handleLogout());
    }

    private void updateUI(boolean isSignedIn) {
        authButtonsLayout.setVisibility(isSignedIn ? View.GONE : View.VISIBLE);
        profileDataLayout.setVisibility(isSignedIn ? View.VISIBLE : View.GONE);

        if (isSignedIn) {
            loadUserProfile();
            loadAchievements();
        } else {
            editTextEmail.setText("");
            editTextPassword.setText("");
        }
    }

    private void handleLogin() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            return;
        }

        mAuth.signInWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        updateUI(true);
                    } else {
                        Log.e("Login", "Authentication failed", task.getException());
                    }
                });
    }

    private void handleLogout() {
        // Выход из Firebase
        FirebaseAuth.getInstance().signOut();

        // Выход из Google Sign-In
        oneTapClient.signOut().addOnCompleteListener(task -> {
            updateUI(false);
            Toast.makeText(getContext(), "Сіз жүйеден шықтыңыз", Toast.LENGTH_SHORT).show();
        });
    }

    private void handleRegistration() {
        String email = editTextEmail.getText().toString();
        String password = editTextPassword.getText().toString();

        if (email.isEmpty() || password.isEmpty()) {
            return;
        }

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        saveUserToFirestore(mAuth.getCurrentUser());
                        updateUI(true);
                    } else {
                        Log.e("Register", "Registration failed", task.getException());
                    }
                });
    }

    private void handleGoogleSignIn() {
        oneTapClient.beginSignIn(signInRequest)
                .addOnSuccessListener(requireActivity(), result -> {
                    try {
                        // Создаем IntentSenderRequest правильно
                        IntentSenderRequest intentSenderRequest = new IntentSenderRequest.Builder(
                                result.getPendingIntent().getIntentSender()
                        ).build();

                        googleSignInLauncher.launch(intentSenderRequest);
                    } catch (Exception e) {
                        Log.e("GoogleSignIn", "Couldn't start Sign In: " + e.getLocalizedMessage());
                        Toast.makeText(getContext(), "Error starting Google Sign-In", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("GoogleSignIn", "Error starting Google sign-in", e);
                    Toast.makeText(getContext(), "Google Sign-In failed", Toast.LENGTH_SHORT).show();
                });
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(requireActivity(), task -> {
                    if (task.isSuccessful()) {
                        saveUserToFirestore(mAuth.getCurrentUser());
                        updateUI(true);
                    } else {
                        Log.w("GoogleSignIn", "signInWithCredential:failure", task.getException());
                        updateUI(false);
                    }
                });
    }

    private void saveUserToFirestore(FirebaseUser user) {
        if (user == null) return;

        com.example.duoclone.models.User firestoreUser = new com.example.duoclone.models.User(
                user.getUid(),
                user.getDisplayName(),
                user.getEmail(),
                user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : ""
        );

        db.collection("users")
                .document(user.getUid())
                .set(firestoreUser)
                .addOnSuccessListener(aVoid -> Log.d("Firestore", "User data saved"))
                .addOnFailureListener(e -> Log.w("Firestore", "Error saving user", e));
    }

    private void loadUserProfile() {
        if (user == null) return;

        db.collection("users").document(user.getUid())
                .get()
                .addOnSuccessListener(documentSnapshot -> {
                    if (documentSnapshot.exists()) {
                        String displayName = documentSnapshot.getString("displayName");
                        Long lessonProgress = documentSnapshot.getLong("lessonProgress");
                        Long level = documentSnapshot.getLong("level");

                        usernameTextView.setText("Аты: " + (displayName != null ? displayName : "Анықталмаған"));
                        lessonTextView.setText("Сабақтар: " + (lessonProgress != null ? lessonProgress : 0));
                        levelTextView.setText("Деңгей: " + (level != null ? level : 1));
                    }
                });
    }

    private void loadAchievements() {
        if (user == null) return;

        CollectionReference achievementsRef = db.collection("users")
                .document(user.getUid())
                .collection("achievements");

        achievementsRef.get().addOnSuccessListener(queryDocumentSnapshots -> {
            achievementList.clear();
            for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                Achievement achievement = doc.toObject(Achievement.class);
                achievementList.add(achievement);
            }
            achievementAdapter.notifyDataSetChanged();
        });
    }
}