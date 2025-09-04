package com.unidad.gymapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;

public class HomeActivity extends AppCompatActivity {

    private final int[] btnIds = {
            R.id.btnMonday,  R.id.btnTuesday,   R.id.btnWednesday,
            R.id.btnThursday, R.id.btnFriday,   R.id.btnSaturday,
            R.id.btnSunday
    };

    private final Class<?>[] targets = {
            MondayActivity.class, TuesdayActivity.class, WednesdayActivity.class,
            ThursdayActivity.class, FridayActivity.class, SaturdayActivity.class,
            SundayActivity.class
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Navegación por días
        for (int i = 0; i < btnIds.length; i++) {
            MaterialButton btn = findViewById(btnIds[i]);
            final Class<?> dest = targets[i];
            btn.setOnClickListener(v ->
                    startActivity(new Intent(HomeActivity.this, dest))
            );
        }

        // Botón de chat con el profesor (WhatsApp)
        MaterialButton btnChat = findViewById(R.id.btnChat);
        btnChat.setOnClickListener(v -> {
            String phone = "+56912345678"; // ← tu número real para pruebas
            String message = "Hola profesor, tengo una consulta sobre mi rutina.";
            String url = "https://wa.me/" + phone.replace("+", "") + "?text=" + Uri.encode(message);
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        // Botón de cerrar sesión
        MaterialButton btnLogout = findViewById(R.id.btnLogout);
        btnLogout.setOnClickListener(v -> {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
            startActivity(intent);
        });
    }
}
