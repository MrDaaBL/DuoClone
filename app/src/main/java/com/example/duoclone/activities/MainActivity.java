package com.example.duoclone.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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
    private NavigationView navigationView; // Используем поле класса

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Инициализация NavigationView (без дублирования)
        navigationView = findViewById(R.id.nav_view);
        DrawerLayout drawer = findViewById(R.id.drawer_layout);

        // Настройка NavController
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        mAppBarConfiguration = new AppBarConfiguration.Builder(
                R.id.nav_lessons,
                R.id.nav_profile,
                R.id.nav_progress
        ).setOpenableLayout(drawer).build();

        NavigationUI.setupActionBarWithNavController(this, navController, mAppBarConfiguration);
        NavigationUI.setupWithNavController(navigationView, navController);

        // Управление музыкой
        preferences = getSharedPreferences("AppSettings", MODE_PRIVATE);
        isMusicEnabled = preferences.getBoolean("music_enabled", true);
        if (isMusicEnabled) {
            startService(new Intent(this, BackgroundMusicService.class));
        }

        // Обработчик навигации (удаляем дублирующийся вызов setNavigationItemSelectedListener)
        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_music_toggle) {
                isMusicEnabled = !isMusicEnabled;
                toggleMusic(isMusicEnabled);
                updateMusicIcon();
            }
            // Закрываем Drawer и передаем управление NavigationUI
            drawer.closeDrawer(GravityCompat.START);
            return NavigationUI.onNavDestinationSelected(item, navController);
        });

        // Обновляем иконку при старте
        updateMusicIcon();
    }

    private void toggleMusic(boolean enable) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean("music_enabled", enable).apply();
        Intent intent = new Intent(this, BackgroundMusicService.class);
        if (enable) startService(intent); else stopService(intent);
    }

    private void updateMusicIcon() {
        MenuItem musicItem = navigationView.getMenu().findItem(R.id.nav_music_toggle);
        if (musicItem != null) {
            musicItem.setIcon(isMusicEnabled ? R.drawable.ic_music_on : R.drawable.ic_music_off);
            musicItem.setChecked(isMusicEnabled);
        }

        Log.d("MusicDebug", "Icon resource: " + (isMusicEnabled ? R.drawable.ic_music_on : R.drawable.ic_music_off));
    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
        return NavigationUI.navigateUp(navController, mAppBarConfiguration) || super.onSupportNavigateUp();
    }
}