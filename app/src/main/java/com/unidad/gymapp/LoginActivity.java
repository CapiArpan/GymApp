package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class LoginActivity extends AppCompatActivity {

    private TextInputEditText etUser, etPassword;
    private MaterialButton btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etUser     = findViewById(R.id.etUser);
        etPassword = findViewById(R.id.etPassword);
        btnLogin   = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(v -> {
            String user = etUser.getText() != null ? etUser.getText().toString().trim() : "";
            String pass = etPassword.getText() != null ? etPassword.getText().toString() : "";

            if (user.equals("profesor") && pass.equals("buentrabajo7")) {
                SharedPreferences prefs = getSharedPreferences("LoginPrefs", MODE_PRIVATE);
                prefs.edit().putBoolean("isLoggedIn", true).apply();

                startActivity(new Intent(this, HomeActivity.class));
                finish();
                return;
            } else {
                Toast.makeText(this,
                        "Usuario o contraseña incorrectos",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}
