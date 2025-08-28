package com.unidad.gymapp;

import android.content.Intent;
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

        for (int i = 0; i < btnIds.length; i++) {
            MaterialButton btn = findViewById(btnIds[i]);
            final Class<?> dest = targets[i];
            btn.setOnClickListener(v ->
                    startActivity(new Intent(HomeActivity.this, dest))
            );
        }
    }
}
