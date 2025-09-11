package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class ThursdayActivity extends AppCompatActivity {

    private Button btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thursday);

        btnBackToMenu = findViewById(R.id.btnBackToMenu);
        btnBackToMenu.setOnClickListener(v -> navigateHome());

        // ✅ Registrar progreso del jueves como completado
        SharedPreferences progressPrefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        progressPrefs.edit().putBoolean("jueves_completado", true).apply();
    }

    private void navigateHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(
                Intent.FLAG_ACTIVITY_CLEAR_TOP |
                        Intent.FLAG_ACTIVITY_SINGLE_TOP
        );
        startActivity(intent);
        finish();
    }
}
