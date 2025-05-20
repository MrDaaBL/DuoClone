package com.example.duoclone.activities;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;
import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import com.example.duoclone.R;
import com.example.duoclone.models.User;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.firestore.FirebaseFirestore;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "GoogleSignIn";
    private FirebaseAuth mAuth;
    private GoogleSignInClient googleSignInClient;

    // Актуальный метод обработки результата (замена startActivityForResult)
    private final ActivityResultLauncher<Intent> signInLauncher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> handleSignInResult(result.getData())
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        // Инициализация Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        // Настройка Google Sign-In с актуальными параметрами
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id)) // Убедитесь, что client_id добавлен в strings.xml
                .requestEmail()
                .build();

        googleSignInClient = GoogleSignIn.getClient(this, gso);

        // Обработчик кнопки Google Sign-In
        findViewById(R.id.google_sign_in_button).setOnClickListener(v -> signIn());
    }

    private void signIn() {
        Intent signInIntent = googleSignInClient.getSignInIntent();
        signInLauncher.launch(signInIntent); // Актуальный метод запуска
    }

    private void handleSignInResult(Intent data) {
        try {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            GoogleSignInAccount account = task.getResult(ApiException.class);
            if (account != null) {
                firebaseAuthWithGoogle(account.getIdToken());
            }
        } catch (ApiException e) {
            Log.w(TAG, "Ошибка входа: " + e.getStatusCode(), e);
            Toast.makeText(this, "Ошибка входа: " + e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void firebaseAuthWithGoogle(String idToken) {
        AuthCredential credential = GoogleAuthProvider.getCredential(idToken, null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        FirebaseUser firebaseUser = mAuth.getCurrentUser();
                        if (firebaseUser != null) {
                            saveUserToFirestore(firebaseUser); // Сохранение данных в Firestore
                            startActivity(new Intent(this, MainActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(this, "Ошибка аутентификации", Toast.LENGTH_SHORT).show();
                    }
                });
    }

    // Сохранение пользователя в Firestore
    private void saveUserToFirestore(FirebaseUser firebaseUser) {
        User user = new User(
                firebaseUser.getUid(),
                firebaseUser.getDisplayName(),
                firebaseUser.getEmail(),
                0, // Начальный прогресс
                0  // Начальный XP
        );

        FirebaseFirestore.getInstance().collection("users")
                .document(firebaseUser.getUid())
                .set(user)
                .addOnSuccessListener(aVoid -> Log.d(TAG, "Пользователь сохранен"))
                .addOnFailureListener(e -> Log.w(TAG, "Ошибка сохранения", e));
    }
}