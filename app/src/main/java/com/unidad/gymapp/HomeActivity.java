package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.progressindicator.LinearProgressIndicator;

import java.util.ArrayList;
import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private CollapsingToolbarLayout collapsingToolbar;
    private LottieAnimationView animationView;
    private RecyclerView rvDays;
    private FloatingActionButton fabChat;
    private LinearProgressIndicator progressWeekly;
    private MaterialButton btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Toolbar + Collapsing title
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        collapsingToolbar = findViewById(R.id.collapseToolbar);

        animationView = findViewById(R.id.homeAnimation);
        animationView.setSpeed(1.2f);

        // Progreso Semanal real
        progressWeekly = findViewById(R.id.progressWeekly);
        progressWeekly.setMax(100);
        progressWeekly.setProgress(calcularProgreso(), true);

        // RecyclerView de días
        rvDays = findViewById(R.id.rvDays);
        rvDays.setLayoutManager(new LinearLayoutManager(this));
        rvDays.setAdapter(new DayAdapter(this, getDayList()));

        // FAB de chat
        fabChat = findViewById(R.id.fabChat);
        fabChat.setOnClickListener(v -> {
            String phone = "+56977193187";
            String msg = "Hola profe, necesito ayuda con mi rutina.";
            String url = "https://wa.me/" + phone.replace("+", "") + "?text=" + Uri.encode(msg);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
        });

        // Botón de cerrar sesión
        btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            // Borrar sesión y progreso
            getSharedPreferences("LoginPrefs", MODE_PRIVATE).edit().clear().apply();
            getSharedPreferences("ProgressPrefs", MODE_PRIVATE).edit().clear().apply();

            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();

            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        });
    }

    private List<Day> getDayList() {
        List<Day> days = new ArrayList<>();
        days.add(new Day("Lunes", MondayActivity.class));
        days.add(new Day("Martes", TuesdayActivity.class));
        days.add(new Day("Miércoles", WednesdayActivity.class));
        days.add(new Day("Jueves", ThursdayActivity.class));
        days.add(new Day("Viernes", FridayActivity.class));
        days.add(new Day("Sábado", SaturdayActivity.class));
        days.add(new Day("Domingo", SundayActivity.class));
        return days;
    }

    private int calcularProgreso() {
        SharedPreferences prefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        int completados = 0;

        if (prefs.getBoolean("lunes_completado", false)) completados++;
        if (prefs.getBoolean("martes_completado", false)) completados++;
        if (prefs.getBoolean("miércoles_completado", false)) completados++;
        if (prefs.getBoolean("jueves_completado", false)) completados++;
        if (prefs.getBoolean("viernes_completado", false)) completados++;
        if (prefs.getBoolean("sábado_completado", false)) completados++;
        if (prefs.getBoolean("domingo_completado", false)) completados++;

        return (int) ((completados / 7.0) * 100);
    }
}
