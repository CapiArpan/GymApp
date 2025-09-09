package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MondayActivity extends AppCompatActivity {

    private Button btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_monday);

        btnBackToMenu = findViewById(R.id.btnBackToMenu);
        btnBackToMenu.setOnClickListener(v -> navigateHome());

        // Configurar los textos según el día lunes
        setupMondayContent();

        // Registrar que el lunes fue completado
        SharedPreferences prefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        prefs.edit().putBoolean("lunes_completado", true).apply();
    }

    private void setupMondayContent() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvSubtitle = findViewById(R.id.tvSubtitle);
        TextView tvWarmupTitle = findViewById(R.id.tvWarmupTitle);
        TextView tvWarmupDesc = findViewById(R.id.tvWarmupDesc);
        TextView tvChestTitle = findViewById(R.id.tvChestTitle);
        TextView tvChestExercise = findViewById(R.id.tvChestExercise);
        TextView tvChestSets = findViewById(R.id.tvChestSets);
        TextView tvBackTitle = findViewById(R.id.tvBackTitle);
        TextView tvBackExercise = findViewById(R.id.tvBackExercise);
        TextView tvBackSets = findViewById(R.id.tvBackSets);
        TextView tvShouldersTitle = findViewById(R.id.tvShouldersTitle);
        TextView tvShouldersExercise = findViewById(R.id.tvShouldersExercise);
        TextView tvShouldersSets = findViewById(R.id.tvShouldersSets);
        TextView tvExtrasTitle = findViewById(R.id.tvExtrasTitle);
        TextView tvExtrasDesc = findViewById(R.id.tvExtrasDesc);
        TextView tvNutritionTitle = findViewById(R.id.tvNutritionTitle);
        TextView tvEcoTitle = findViewById(R.id.tvEcoTitle);
        TextView tvEcoDesc = findViewById(R.id.tvEcoDesc);
        TextView tvMuscleTitle = findViewById(R.id.tvMuscleTitle);
        TextView tvMuscleDesc = findViewById(R.id.tvMuscleDesc);
        TextView tvVegTitle = findViewById(R.id.tvVegTitle);
        TextView tvVegDesc = findViewById(R.id.tvVegDesc);
        TextView tvDuration = findViewById(R.id.tvDuration);

        tvTitle.setText("Lunes · Upper");
        tvSubtitle.setText("Pecho · Espalda · Hombros · 50-65 min");
        tvWarmupTitle.setText("Calentamiento (5 min)");
        tvWarmupDesc.setText("Trote en maquina + 20 jumping jacks");
        tvChestTitle.setText("Pecho");
        tvChestExercise.setText("Press de banca");
        tvChestSets.setText("4×8-10 · Desc 90s");
        tvBackTitle.setText("Espalda");
        tvBackExercise.setText("Dominadas o jalón al pecho");
        tvBackSets.setText("4×8-10 · Desc 90s");
        tvShouldersTitle.setText("Hombros");
        tvShouldersExercise.setText("Press militar");
        tvShouldersSets.setText("3×10-12 · Desc 60s");
        tvExtrasTitle.setText("Extras");
        tvExtrasDesc.setText("Elevaciones laterales 3×15 · Face pulls 3×15");
        tvNutritionTitle.setText("Alimentacion Recomendada");
        tvEcoTitle.setText("Económica");
        tvEcoDesc.setText("Pan integral + huevo duro · leche");
        tvMuscleTitle.setText("Muscle");
        tvMuscleDesc.setText("Pavo · arroz integral · batido post entreno");
        tvVegTitle.setText("Vegetariana");
        tvVegDesc.setText("Tofu salteado · quinoa · ensalada fresca");
        tvDuration.setText("Duración ≈ 50-65 min\nDescansos 45-90s");
    }

    private void navigateHome() {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        startActivity(intent);
        finish();
    }
}
