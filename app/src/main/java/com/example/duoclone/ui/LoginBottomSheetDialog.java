package com.example.duoclone.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.example.duoclone.R;
import com.example.duoclone.models.Achievement;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomsheet.BottomSheetDialogFragment;
import com.google.android.gms.auth.api.signin.*;
import com.google.firebase.auth.*;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.SetOptions;

import java.util.HashMap;
import java.util.Map;

public class LoginBottomSheetDialog extends BottomSheetDialogFragment {

    private static final int RC_SIGN_IN = 9001;
    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    @Override
    public int getTheme() {
        return R.style.BottomSheetDialogTheme;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mAuth = FirebaseAuth.getInstance();


        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(requireContext(), gso);
    }

    @Override
    public View onCreateView(@NonNull android.view.LayoutInflater inflater, android.view.ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_login, container, false);

        view.findViewById(R.id.google_sign_in_button).setOnClickListener(v -> signIn());

        return view;
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if (user != null) {
            FirebaseFirestore db = FirebaseFirestore.getInstance();

            Map<String, Object> userData = new HashMap<>();
            userData.put("displayName", user.getDisplayName());
            userData.put("lessonProgress", 1);
            userData.put("level", 1);

            db.collection("users")
                    .document(user.getUid())
                    .set(userData, SetOptions.merge());

            // Добавим достижения (метод ниже)
            checkAndGrantAchievements(user.getUid(), 1, 1);
        }

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(getContext(), "Google sign in failed", Toast.LENGTH_SHORT).show();
            }
        }
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();

        if (currentUser != null) {
            DocumentReference userRef = db.collection("users").document(currentUser.getUid());

            // Пример достижения — только при первом входе
            userRef.collection("achievements")
                    .document("lesson_1_complete")
                    .set(new Achievement("Бірінші сабақты аяқтадыңыз", "Сіз бірінші сабақты сәтті аяқтадыңыз!"));
        }

        Map<String, Object> userData = new HashMap<>();
        userData.put("displayName", user.getDisplayName());
        userData.put("lessonProgress", 1);
        userData.put("level", 1);

        db.collection("users").document(user.getUid())
                .set(userData, SetOptions.merge());

        checkAndGrantAchievements(user.getUid(), 1, 1);

    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(requireActivity(), task -> {
            if (task.isSuccessful()) {
                Toast.makeText(getContext(), "Login successful", Toast.LENGTH_SHORT).show();
                dismiss(); // закрыть диалог
                if (getActivity() != null) {
                    getActivity().recreate(); // обновим UI
                }
            } else {
                Toast.makeText(getContext(), "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void checkAndGrantAchievements(String uid, int progress, int level) {
        FirebaseFirestore db = FirebaseFirestore.getInstance();
        CollectionReference achRef = db.collection("users").document(uid).collection("achievements");

        if (progress >= 1) {
            achRef.document("first_lesson").set(new Achievement(
                    "Бірінші сабақ аяқталды", "Сіз бірінші сабақты сәтті аяқтадыңыз!"));
        }

        if (progress >= 5) {
            achRef.document("five_lessons").set(new Achievement(
                    "5 сабақ аяқталды", "Сіз 5 сабақты аяқтадыңыз!"));
        }

        if (level >= 2) {
            achRef.document("level_2").set(new Achievement(
                    "2-деңгейге жеттіңіз", "Сіз 2-деңгейге жеттіңіз! Керемет!"));
        }
    }

}
