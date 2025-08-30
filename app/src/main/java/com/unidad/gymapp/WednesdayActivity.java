package com.unidad.gymapp;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class WednesdayActivity extends AppCompatActivity {

    private Button btnBackToMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wednesday);

        btnBackToMenu = findViewById(R.id.btnBackToMenu);

        btnBackToMenu.setOnClickListener(v -> navigateHome());
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