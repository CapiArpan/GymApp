package com.unidad.gymapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.airbnb.lottie.LottieAnimationView;
import com.google.android.material.appbar.CollapsingToolbarLayout;
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

        // Progreso Semanal
        progressWeekly = findViewById(R.id.progressWeekly);
        progressWeekly.setMax(100);
        progressWeekly.setProgress(45, true); // Ejemplo: 45%

        // RecyclerView de días
        rvDays = findViewById(R.id.rvDays);
        rvDays.setLayoutManager(new LinearLayoutManager(this));
        rvDays.setAdapter(new DayAdapter(this, getDayList()));

        // FAB de chat
        fabChat = findViewById(R.id.fabChat);
        fabChat.setOnClickListener(v -> {
            String phone = "+56977193187";
            String msg = "Hola profe, necesito ayuda con mi rutina.";
            String url = "https://wa.me/"
                    + phone.replace("+", "")
                    + "?text=" + Uri.encode(msg);
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(url)));
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
}
