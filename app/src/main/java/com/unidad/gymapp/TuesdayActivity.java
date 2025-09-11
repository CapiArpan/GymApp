package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class TuesdayActivity extends AppCompatActivity {

    private Button btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuesday);

        btnBackToMenu = findViewById(R.id.btnBackToMenu);
        btnBackToMenu.setOnClickListener(v -> navigateHome());

        // Configurar los textos según el día martes
        setupTuesdayContent();

        // ✅ Registrar progreso del martes como completado
        SharedPreferences progressPrefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        progressPrefs.edit().putBoolean("martes_completado", true).apply();
    }

    private void setupTuesdayContent() {
        TextView tvTitle = findViewById(R.id.tvTitle);
        TextView tvSubtitle = findViewById(R.id.tvSubtitle);
        TextView tvWarmupTitle = findViewById(R.id.tvWarmupTitle);
        TextView tvWarmupDesc = findViewById(R.id.tvWarmupDesc);
        TextView tvExercise1Title = findViewById(R.id.tvExercise1Title);
        TextView tvExercise1Name = findViewById(R.id.tvExercise1Name);
        TextView tvExercise1Sets = findViewById(R.id.tvExercise1Sets);
        TextView tvExercise2Title = findViewById(R.id.tvExercise2Title);
        TextView tvExercise2Name = findViewById(R.id.tvExercise2Name);
        TextView tvExercise2Sets = findViewById(R.id.tvExercise2Sets);
        TextView tvExercise3Title = findViewById(R.id.tvExercise3Title);
        TextView tvExercise3Name = findViewById(R.id.tvExercise3Name);
        TextView tvExercise3Sets = findViewById(R.id.tvExercise3Sets);
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

        tvTitle.setText("Martes · Active Rest");
        tvSubtitle.setText("Cardio · Movilidad · Core · 40-50 min");
        tvWarmupTitle.setText("Calentamiento (5 min)");
        tvWarmupDesc.setText("Caminata rápida + movilidad articular");
        tvExercise1Title.setText("Cardio");
        tvExercise1Name.setText("Bicicleta estática o elíptica");
        tvExercise1Sets.setText("30 min · Intensidad moderada");
        tvExercise2Title.setText("Movilidad");
        tvExercise2Name.setText("Estiramientos dinámicos");
        tvExercise2Sets.setText("2 series · 10 repeticiones cada ejercicio");
        tvExercise3Title.setText("Core");
        tvExercise3Name.setText("Plancha · Russian twists · Leg raises");
        tvExercise3Sets.setText("3×30s · 3×15 · 3×12 · Desc 30s");
        tvExtrasTitle.setText("Extras");
        tvExtrasDesc.setText("Movilidad de hombros 2×10 · Respiración diafragmática");
        tvNutritionTitle.setText("Alimentacion Recomendada");
        tvEcoTitle.setText("Económica");
        tvEcoDesc.setText("Avena con plátano · huevo revuelto · leche");
        tvMuscleTitle.setText("Muscle");
        tvMuscleDesc.setText("Pescado · camote · batido de proteínas");
        tvVegTitle.setText("Vegetariana");
        tvVegDesc.setText("Garbanzos · quinoa · ensalada de vegetales");
        tvDuration.setText("Duración ≈ 40-50 min\nDescansos 30-60s");
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
