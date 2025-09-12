package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;

public class SaturdayActivity extends AppCompatActivity {

    private EditText etWarmup, etHip, etCalf, etQuad, etNutrition, etTiming;
    private Button btnBackHome;
    private SharedPreferences prefs;

    private LottieAnimationView lottieHip, lottieCalf, lottieQuad;
    private LottieZoomHelper zoomHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saturday);

        // Toolbar y botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbarSaturday);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inicializar helper de zoom
        zoomHelper = new LottieZoomHelper(this, R.id.zoomOverlay);

        // Referencias a los campos
        etWarmup    = findViewById(R.id.etWarmup);
        etHip       = findViewById(R.id.etHip);
        etCalf      = findViewById(R.id.etCalf);
        etQuad      = findViewById(R.id.etQuad);
        etNutrition = findViewById(R.id.etNutrition);
        etTiming    = findViewById(R.id.etTiming);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Referencias a los Lotties
        lottieHip  = findViewById(R.id.lottieHip);
        lottieCalf = findViewById(R.id.lottieCalf);
        lottieQuad = findViewById(R.id.lottieQuad);

        // Activar zoom modular en cada Lottie
        lottieHip.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));
        lottieCalf.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));
        lottieQuad.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));

        // SharedPreferences para contenido editable
        prefs = getSharedPreferences("SaturdayPrefs", MODE_PRIVATE);

        // Cargar datos guardados o valores por defecto
        etWarmup.setText(prefs.getString("warmup", getDefaultWarmup()));
        etHip.setText(prefs.getString("hip", getDefaultHip()));
        etCalf.setText(prefs.getString("calf", getDefaultCalf()));
        etQuad.setText(prefs.getString("quad", getDefaultQuad()));
        etNutrition.setText(prefs.getString("nutrition", getDefaultNutrition()));
        etTiming.setText(prefs.getString("timing", getDefaultTiming()));

        // Registrar progreso del sábado como completado
        SharedPreferences progressPrefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        progressPrefs.edit().putBoolean("sabado_completado", true).apply();

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
        return "∙ Sentadillas con peso corporal – 2 rondas\n20 jumping jacks + 10 lunges por pierna";
    }

    private String getDefaultHip() {
        return "Elevaciones de pierna + estiramiento dinámico\n3×15 · Desc 45s";
    }

    private String getDefaultCalf() {
        return "Elevaciones de talón en escalón\n4×20 · Desc 30s";
    }

    private String getDefaultQuad() {
        return "Sentadilla frontal con barra\n4×8 · Desc 60s";
    }

    private String getDefaultNutrition() {
        return "• Económica: Avena + plátano + leche\n" +
                "• Proteína: Pollo + batata + ensalada\n" +
                "• Vegetariana: Lentejas + arroz integral + palta";
    }

    private String getDefaultTiming() {
        return "Duración: 55–70 min\nDescanso entre series: 45–90 s";
    }
}
