package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class ThursdayActivity extends AppCompatActivity {

    private EditText etWarmup, etHip, etCalf, etQuad, etNutrition, etTiming;
    private Button btnBackHome;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thursday);

        // Toolbar y botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbarthursday);
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

        // SharedPreferences para contenido editable
        prefs = getSharedPreferences("thursdayPrefs", MODE_PRIVATE);

        // Cargar datos guardados o valores por defecto
        etWarmup.setText(prefs.getString("warmup", getDefaultWarmup()));
        etHip.setText(prefs.getString("hip", getDefaultHip()));
        etCalf.setText(prefs.getString("calf", getDefaultCalf()));
        etQuad.setText(prefs.getString("quad", getDefaultQuad()));
        etNutrition.setText(prefs.getString("nutrition", getDefaultNutrition()));
        etTiming.setText(prefs.getString("timing", getDefaultTiming()));

        // ✅ Registrar progreso del sábado como completado
        SharedPreferences progressPrefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        progressPrefs.edit().putBoolean("Jueves_completado", true).apply();

        // Guardar contenido editable y volver al Home
        btnBackHome.setOnClickListener(v -> {
            prefs.edit()
                    .putString("warmup", etWarmup.getText().toString())
                    .putString("hip", etHip.getText().toString())
                    .putString("calf", etCalf.getText().toString())
                    .putString("quad", etQuad.getText().toString())
                    .putString("nutrition", etNutrition.getText().toString())
                    .putString("timing", etTiming.getText().toString())
                    .apply();

            Intent intent = new Intent(ThursdayActivity.this, HomeActivity.class);
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

        return "∙ Caminata + Elevación 10.00";
    }
    private String getDefaultHip() {
        return "Peso Muerto\n4×8–10 · Desc 70s";
    }

    private String getDefaultCalf() {

        return "Patada de burro\n3–4×10–12 · Desc 60–90s";
    }

    private String getDefaultQuad() {

        return "Estocada\n6×4 · Desc 50s";
    }

    private String getDefaultNutrition() {
        return "• Económica: Pan integral + huevo duro + leche\n" +
                "• Proteína: Pavo + arroz + batido post entreno\n" +
                "• Vegetariana: Tofu + ensalada fresca + avena";
    }

    private String getDefaultTiming() {
        return "Duración: 50–65 min\nDescanso entre series: 45–90 s";
    }
}
