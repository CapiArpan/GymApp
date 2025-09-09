package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SaturdayActivity extends AppCompatActivity {

    private EditText etWarmup, etHip, etCalf, etQuad, etNutrition, etTiming;
    private Button btnBackHome;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saturday);

        // Toolbar y botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbarSaturday);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Referencias a los campos
        etWarmup    = findViewById(R.id.etWarmup);
        etHip       = findViewById(R.id.etHip);
        etCalf      = findViewById(R.id.etCalf);
        etQuad      = findViewById(R.id.etQuad);
        etNutrition = findViewById(R.id.etNutrition);
        etTiming    = findViewById(R.id.etTiming);
        btnBackHome = findViewById(R.id.btnBackHome);

        // SharedPreferences
        prefs = getSharedPreferences("SaturdayPrefs", MODE_PRIVATE);

        // Cargar datos guardados o valores por defecto
        etWarmup.setText(prefs.getString("warmup", getDefaultWarmup()));
        etHip.setText(prefs.getString("hip", getDefaultHip()));
        etCalf.setText(prefs.getString("calf", getDefaultCalf()));
        etQuad.setText(prefs.getString("quad", getDefaultQuad()));
        etNutrition.setText(prefs.getString("nutrition", getDefaultNutrition()));
        etTiming.setText(prefs.getString("timing", getDefaultTiming()));

        // Guardar y volver al Home
        btnBackHome.setOnClickListener(v -> {
            prefs.edit()
                    .putString("warmup", etWarmup.getText().toString())
                    .putString("hip", etHip.getText().toString())
                    .putString("calf", etCalf.getText().toString())
                    .putString("quad", etQuad.getText().toString())
                    .putString("nutrition", etNutrition.getText().toString())
                    .putString("timing", etTiming.getText().toString())
                    .apply();

            Intent intent = new Intent(SaturdayActivity.this, HomeActivity.class);
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
        return "∙ Trote en máquina + 20 jumping jacks";
    }

    private String getDefaultHip() {
        return "Abducción en máquina\n4×8–10 · Desc 90s";
    }

    private String getDefaultCalf() {
        return "Elevación de talones\n3–4×10–12 · Desc 60–90s";
    }

    private String getDefaultQuad() {
        return "Extensión de cuádriceps\n4×8 · Desc 45s";
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
