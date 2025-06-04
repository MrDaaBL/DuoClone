package com.example.duoclone.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SeekBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.duoclone.R;
import com.example.duoclone.services.BackgroundMusicService;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MainActivity extends AppCompatActivity {
    private AppBarConfiguration mAppBarConfiguration;
    private SharedPreferences preferences;
    private boolean isMusicEnabled;
    private NavigationView navigationView;
    private MenuItem musicMenuItem;

    @Override
    protected void onStart() {
        super.onStart();
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser == null) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация NavigationView
        navigationView = findViewById(R.id.nav_view);
        if (navigationView == null) {
            Log.e("MainActivity", "NavigationView not found!");
            return;
        }

        // Настройка Navigation
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);

        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_lessons,
                R.id.nav_profile,
                R.id.nav_progress
        ).setOpenableLayout(drawer).build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Настройка музыки
        preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        isMusicEnabled = preferences.getBoolean("music_enabled", true);

        // Инициализация меню
        setupNavigationMenu();

        // Запуск сервиса музыки
        if (isMusicEnabled) {
            startService(new Intent(this, BackgroundMusicService.class));
        }
    }

    private void setupNavigationMenu() {
        Menu menu = navigationView.getMenu();
        musicMenuItem = menu.findItem(R.id.nav_music_toggle);

        if (musicMenuItem == null) {
            Log.e("MainActivity", "Music menu item not found!");
            return;
        }

        // Установка начальной иконки
        updateMusicIcon();

        // Обработчик нажатий
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_music_toggle) {
                isMusicEnabled = !isMusicEnabled;
                toggleMusic(isMusicEnabled);
                updateMusicIcon();
                return true;
            }

            // Закрываем drawer после выбора
            DrawerLayout drawer = findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

            // Передаем обработку навигации NavigationUI
            return NavigationUI.onNavDestinationSelected(item,
                    Navigation.findNavController(this, R.id.nav_host_fragment));
        });
    }

    private void toggleMusic(boolean enable) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("music_enabled", enable).apply();

        Intent intent = new Intent(this, BackgroundMusicService.class);
        if (enable) {
            startService(intent);
        } else {
            stopService(intent);
        }
    }

    private void updateMusicIcon() {
        if (musicMenuItem != null) {
            musicMenuItem.setIcon(isMusicEnabled ?
                    R.drawable.ic_music_on : R.drawable.ic_music_off);
        }
    }



    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // Остановка сервиса при закрытии приложения (опционально)
        // stopService(new Intent(this, BackgroundMusicService.class));
    }
}