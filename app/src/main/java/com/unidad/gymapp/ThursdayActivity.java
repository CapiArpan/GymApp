package com.unidad.gymapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.VideoView;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.airbnb.lottie.LottieAnimationView;

import java.io.File;

public class ThursdayActivity extends AppCompatActivity {

    private EditText etWarmup, etChest, etBack, etLegs, etNutrition, etTiming;
    private Button btnBackHome, btnRecordChest, btnRecordBack, btnRecordLegs;
    private Button btnDeleteChest, btnDeleteBack, btnDeleteLegs;
    private SharedPreferences prefs;

    private LottieAnimationView lottieChest, lottieBack, lottieLegs;
    private VideoView videoChest, videoBack, videoLegs;
    private LottieZoomHelper zoomHelper;
    private CamaraVideoHelper camaraHelper;

    private Uri chestVideoUri, backVideoUri, legsVideoUri;

    private final ActivityResultLauncher<Intent> videoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    camaraHelper.handleActivityResult(result.getData());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thursday);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarThursday);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Zoom helper
        zoomHelper = new LottieZoomHelper(this, R.id.zoomOverlay);

        // EditTexts y Buttons
        etWarmup    = findViewById(R.id.etWarmup);
        etChest     = findViewById(R.id.etChest);
        etBack      = findViewById(R.id.etBack);
        etLegs      = findViewById(R.id.etLegs);
        etNutrition = findViewById(R.id.etNutrition);
        etTiming    = findViewById(R.id.etTiming);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Lottie y VideoViews
        lottieChest = findViewById(R.id.lottieChest);
        lottieBack  = findViewById(R.id.lottieBack);
        lottieLegs  = findViewById(R.id.lottieLegs);

        videoChest = findViewById(R.id.videoChest);
        videoBack  = findViewById(R.id.videoBack);
        videoLegs  = findViewById(R.id.videoLegs);

        // Botones de grabar y borrar
        btnRecordChest = findViewById(R.id.btnRecordChest);
        btnRecordBack  = findViewById(R.id.btnRecordBack);
        btnRecordLegs  = findViewById(R.id.btnRecordLegs);

        btnDeleteChest = findViewById(R.id.btnDeleteChest);
        btnDeleteBack  = findViewById(R.id.btnDeleteBack);
        btnDeleteLegs  = findViewById(R.id.btnDeleteLegs);

        // Click en Lottie para zoom
        lottieChest.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));
        lottieBack.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));
        lottieLegs.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));

        // Click en VideoView para zoom
        videoChest.setOnClickListener(v -> {
            if (chestVideoUri != null) zoomHelper.showZoom(chestVideoUri);
        });
        videoBack.setOnClickListener(v -> {
            if (backVideoUri != null) zoomHelper.showZoom(backVideoUri);
        });
        videoLegs.setOnClickListener(v -> {
            if (legsVideoUri != null) zoomHelper.showZoom(legsVideoUri);
        });

        // SharedPreferences
        prefs = getSharedPreferences("ThursdayPrefs", MODE_PRIVATE);

        etWarmup.setText(prefs.getString("warmup", getDefaultWarmup()));
        etChest.setText(prefs.getString("chest", getDefaultChest()));
        etBack.setText(prefs.getString("back", getDefaultBack()));
        etLegs.setText(prefs.getString("legs", getDefaultLegs()));
        etNutrition.setText(prefs.getString("nutrition", getDefaultNutrition()));
        etTiming.setText(prefs.getString("timing", getDefaultTiming()));

        // Cargar videos existentes
        loadVideo("chest_video_thu.mp4", R.id.lottieChest, R.id.videoChest, "video_chest_uri");
        loadVideo("back_video_thu.mp4", R.id.lottieBack, R.id.videoBack, "video_back_uri");
        loadVideo("legs_video_thu.mp4", R.id.lottieLegs, R.id.videoLegs, "video_legs_uri");

        // Guardar progreso
        SharedPreferences progressPrefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        progressPrefs.edit().putBoolean("jueves_completado", true).apply();

        // Botón regresar Home
        btnBackHome.setOnClickListener(v -> {
            prefs.edit()
                    .putString("warmup", etWarmup.getText().toString())
                    .putString("chest", etChest.getText().toString())
                    .putString("back", etBack.getText().toString())
                    .putString("legs", etLegs.getText().toString())
                    .putString("nutrition", etNutrition.getText().toString())
                    .putString("timing", etTiming.getText().toString())
                    .apply();

            Intent intent = new Intent(ThursdayActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Grabación de videos
        setupRecordButton(btnRecordChest, "chest_video_thu.mp4", R.id.lottieChest, R.id.videoChest, "video_chest_uri");
        setupRecordButton(btnRecordBack, "back_video_thu.mp4", R.id.lottieBack, R.id.videoBack, "video_back_uri");
        setupRecordButton(btnRecordLegs, "legs_video_thu.mp4", R.id.lottieLegs, R.id.videoLegs, "video_legs_uri");

        // Borrado de videos
        setupDeleteButton(btnDeleteChest, "chest_video_thu.mp4", R.id.lottieChest, R.id.videoChest, "video_chest_uri");
        setupDeleteButton(btnDeleteBack, "back_video_thu.mp4", R.id.lottieBack, R.id.videoBack, "video_back_uri");
        setupDeleteButton(btnDeleteLegs, "legs_video_thu.mp4", R.id.lottieLegs, R.id.videoLegs, "video_legs_uri");
    }

    // Métodos auxiliares para no repetir código
    private void loadVideo(String fileName, int lottieId, int videoId, String prefKey) {
        String path = prefs.getString(prefKey, null);
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                Uri uri = Uri.fromFile(file);
                replaceLottieWithVideo(lottieId, videoId, uri);
                if (fileName.contains("chest")) chestVideoUri = uri;
                if (fileName.contains("back")) backVideoUri = uri;
                if (fileName.contains("legs")) legsVideoUri = uri;
            } else {
                Toast.makeText(this, "El video " + fileName + " no se encuentra", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void setupRecordButton(Button btn, String fileName, int lottieId, int videoId, String prefKey) {
        btn.setOnClickListener(v -> {
            camaraHelper = new CamaraVideoHelper(this, new CamaraVideoHelper.CameraCallback() {
                @Override
                public void onVideoCaptured(Uri videoUri) {
                    Uri savedUri = camaraHelper.saveVideoPermanently(videoUri, fileName);
                    if (fileName.contains("chest")) chestVideoUri = savedUri;
                    if (fileName.contains("back")) backVideoUri = savedUri;
                    if (fileName.contains("legs")) legsVideoUri = savedUri;
                    replaceLottieWithVideo(lottieId, videoId, savedUri);
                    prefs.edit().putString(prefKey, savedUri.getPath()).apply();
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(ThursdayActivity.this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
            }, videoLauncher);
            camaraHelper.requestCameraPermissionAndLaunch();
        });
    }

    private void setupDeleteButton(Button btn, String fileName, int lottieId, int videoId, String prefKey) {
        btn.setOnClickListener(v -> {
            File file = new File(getFilesDir(), fileName);
            if (file.exists()) file.delete();
            prefs.edit().remove(prefKey).apply();
            if (fileName.contains("chest")) chestVideoUri = null;
            if (fileName.contains("back")) backVideoUri = null;
            if (fileName.contains("legs")) legsVideoUri = null;
            findViewById(videoId).setVisibility(VideoView.GONE);
            findViewById(lottieId).setVisibility(LottieAnimationView.VISIBLE);
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

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (camaraHelper != null) {
            camaraHelper.handlePermissionResult(requestCode, grantResults);
        }
    }

    private void replaceLottieWithVideo(int lottieId, int videoViewId, Uri videoUri) {
        LottieAnimationView lottie = findViewById(lottieId);
        VideoView video = findViewById(videoViewId);
        lottie.setVisibility(android.view.View.GONE);
        video.setVisibility(android.view.View.VISIBLE);
        video.setVideoURI(videoUri);
    }

    // Rutina por defecto del jueves
    private String getDefaultWarmup() {
        return "∙ Estiramiento dinámico completo\n∙ 5 min cuerda + movilidad hombros";
    }
    private String getDefaultChest() {
        return "Fondos en paralelas\n4×10–12 · Desc 60s";
    }
    private String getDefaultBack() {
        return "Remo con barra\n4×8–10 · Desc 90s";
    }
    private String getDefaultLegs() {
        return "Peso muerto rumano\n4×8–10 · Desc 90s";
    }
    private String getDefaultNutrition() {
        return "• Económica: Lentejas + arroz + ensalada\n" +
                "• Proteína: Pechuga de pollo + camote + espinacas\n" +
                "• Vegetariana: Garbanzos + couscous + verduras";
    }
    private String getDefaultTiming() {
        return "Duración: 75–90 min\nDescanso entre series: 60–120 s";
    }
}
