package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

public class SundayActivity extends AppCompatActivity {

    private EditText etTips, etNutrition;
    private Button btnStartWeek;
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sunday);

        // Toolbar y back
        Toolbar toolbar = findViewById(R.id.toolbarSunday);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        etTips       = findViewById(R.id.etTips);
        etNutrition  = findViewById(R.id.etNutrition);
        btnStartWeek = findViewById(R.id.btnStartWeek);

        // SharedPreferences para contenido editable
        prefs = getSharedPreferences("SundayPrefs", MODE_PRIVATE);

        // Cargar guardado o defecto
        etTips.setText(prefs.getString("tips", getDefaultTips()));
        etNutrition.setText(prefs.getString("nutri", getDefaultNutri()));

        // ✅ Registrar progreso del domingo como completado
        SharedPreferences progressPrefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        progressPrefs.edit().putBoolean("domingo_completado", true).apply();

        btnStartWeek.setOnClickListener(v -> {
            // Guardar cambios
            prefs.edit()
                    .putString("tips", etTips.getText().toString())
                    .putString("nutri", etNutrition.getText().toString())
                    .apply();

            // Volver al HomeActivity
            Intent i = new Intent(SundayActivity.this, HomeActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(i);
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

    private String getDefaultTips() {
        return "• Mantén hidratación constante (2.5L mínimo).\n" +
                "• Estiramientos ligeros o yoga para movilidad.\n" +
                "• Duerme 8-9 horas para recuperación.\n" +
                "• Planifica entrenos y comidas de la semana.";
    }

    private String getDefaultNutri() {
        return "Desayuno: Batido de proteínas + avena con frutas.\n" +
                "Almuerzo: Ensalada con pollo y palta.\n" +
                "Merienda: Yogur griego + semillas de chía.\n" +
                "Cena: Pescado a la plancha con verduras al vapor.";
    }
}
