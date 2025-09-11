package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class FridayActivity extends AppCompatActivity {

    private EditText etWarmup, etShoulders, etTriceps, etCore, etNutrition, etTiming;
    private Button btnBackHome;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friday);

        // Toolbar y botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbarFriday);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Referencias a los campos
        etWarmup    = findViewById(R.id.etWarmup);
        etShoulders = findViewById(R.id.etShoulders);
        etTriceps   = findViewById(R.id.etTriceps);
        etCore      = findViewById(R.id.etCore);
        etNutrition = findViewById(R.id.etNutrition);
        etTiming    = findViewById(R.id.etTiming);
        btnBackHome = findViewById(R.id.btnBackHome);

        // SharedPreferences para contenido editable
        prefs = getSharedPreferences("FridayPrefs", MODE_PRIVATE);

        // Cargar datos guardados o valores por defecto
        etWarmup.setText(prefs.getString("warmup", getDefaultWarmup()));
        etShoulders.setText(prefs.getString("shoulders", getDefaultShoulders()));
        etTriceps.setText(prefs.getString("triceps", getDefaultTriceps()));
        etCore.setText(prefs.getString("core", getDefaultCore()));
        etNutrition.setText(prefs.getString("nutrition", getDefaultNutrition()));
        etTiming.setText(prefs.getString("timing", getDefaultTiming()));

        // ✅ Registrar progreso del viernes como completado
        SharedPreferences progressPrefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        progressPrefs.edit().putBoolean("viernes_completado", true).apply();

        // Guardar contenido editable y volver al Home
        btnBackHome.setOnClickListener(v -> {
            prefs.edit()
                    .putString("warmup", etWarmup.getText().toString())
                    .putString("shoulders", etShoulders.getText().toString())
                    .putString("triceps", etTriceps.getText().toString())
                    .putString("core", etCore.getText().toString())
                    .putString("nutrition", etNutrition.getText().toString())
                    .putString("timing", etTiming.getText().toString())
                    .apply();

            Intent intent = new Intent(FridayActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            startActivity(new Intent(this, HomeActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // Valores por defecto
    private String getDefaultWarmup() {
        return "∙ Rotación hombros con banda – 2 rondas\n10 flexiones rodillas + 20 jumping jacks";
    }

    private String getDefaultShoulders() {
        return "Press militar (barra o mancuernas)\n4×8–10 · Desc 60s";
    }

    private String getDefaultTriceps() {
        return "Fondos en banco o paralelas\n3×10–12 · Desc 45s";
    }

    private String getDefaultCore() {
        return "Plancha + Mountain climbers\n3×30s + 20 · Desc 45s";
    }

    private String getDefaultNutrition() {
        return "• Económica: Pan integral + huevo + leche\n" +
                "• Proteína: Pavo + arroz + entrenar\n" +
                "• Vegetariana: Tofu + ensalada fresca + quinoa";
    }

    private String getDefaultTiming() {
        return "Duración: 50–65 min\nDescanso entre series: 45–90 s";
    }
}
