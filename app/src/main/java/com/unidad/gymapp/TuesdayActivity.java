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

public class TuesdayActivity extends AppCompatActivity {

    private EditText etWarmup, etLegs, etGlutes, etCardio, etNutrition, etTiming;
    private Button btnBackHome, btnRecordLegs, btnRecordGlutes, btnRecordCardio;
    private Button btnDeleteLegs, btnDeleteGlutes, btnDeleteCardio;
    private SharedPreferences prefs;

    private LottieAnimationView lottieLegs, lottieGlutes, lottieCardio;
    private VideoView videoLegs, videoGlutes, videoCardio;
    private LottieZoomHelper zoomHelper;
    private CamaraVideoHelper camaraHelper;

    private Uri legsVideoUri, glutesVideoUri, cardioVideoUri;

    private final ActivityResultLauncher<Intent> videoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    camaraHelper.handleActivityResult(result.getData());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuesday);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbarTuesday);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Zoom helper
        zoomHelper = new LottieZoomHelper(this, R.id.zoomOverlay);

        // EditTexts y Buttons
        etWarmup    = findViewById(R.id.etWarmup);
        etLegs      = findViewById(R.id.etLegs);
        etGlutes    = findViewById(R.id.etGlutes);
        etCardio    = findViewById(R.id.etCardio);
        etNutrition = findViewById(R.id.etNutrition);
        etTiming    = findViewById(R.id.etTiming);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Lottie y VideoViews
        lottieLegs   = findViewById(R.id.lottieLegs);
        lottieGlutes = findViewById(R.id.lottieGlutes);
        lottieCardio = findViewById(R.id.lottieCardio);

        videoLegs   = findViewById(R.id.videoLegs);
        videoGlutes = findViewById(R.id.videoGlutes);
        videoCardio = findViewById(R.id.videoCardio);

        // Botones de grabar y borrar
        btnRecordLegs   = findViewById(R.id.btnRecordLegs);
        btnRecordGlutes = findViewById(R.id.btnRecordGlutes);
        btnRecordCardio = findViewById(R.id.btnRecordCardio);

        btnDeleteLegs   = findViewById(R.id.btnDeleteLegs);
        btnDeleteGlutes = findViewById(R.id.btnDeleteGlutes);
        btnDeleteCardio = findViewById(R.id.btnDeleteCardio);

        // Click en Lottie para zoom
        lottieLegs.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));
        lottieGlutes.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));
        lottieCardio.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));

        // Click en VideoView para zoom
        videoLegs.setOnClickListener(v -> {
            if (legsVideoUri != null) zoomHelper.showZoom(legsVideoUri);
        });
        videoGlutes.setOnClickListener(v -> {
            if (glutesVideoUri != null) zoomHelper.showZoom(glutesVideoUri);
        });
        videoCardio.setOnClickListener(v -> {
            if (cardioVideoUri != null) zoomHelper.showZoom(cardioVideoUri);
        });

        // SharedPreferences
        prefs = getSharedPreferences("TuesdayPrefs", MODE_PRIVATE);

        etWarmup.setText(prefs.getString("warmup", getDefaultWarmup()));
        etLegs.setText(prefs.getString("legs", getDefaultLegs()));
        etGlutes.setText(prefs.getString("glutes", getDefaultGlutes()));
        etCardio.setText(prefs.getString("cardio", getDefaultCardio()));
        etNutrition.setText(prefs.getString("nutrition", getDefaultNutrition()));
        etTiming.setText(prefs.getString("timing", getDefaultTiming()));

        // Cargar videos existentes
        loadVideo("legs_video.mp4", R.id.lottieLegs, R.id.videoLegs, "video_legs_uri");
        loadVideo("glutes_video.mp4", R.id.lottieGlutes, R.id.videoGlutes, "video_glutes_uri");
        loadVideo("cardio_video.mp4", R.id.lottieCardio, R.id.videoCardio, "video_cardio_uri");

        // Guardar progreso
        SharedPreferences progressPrefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        progressPrefs.edit().putBoolean("martes_completado", true).apply();

        // Botón regresar Home
        btnBackHome.setOnClickListener(v -> {
            prefs.edit()
                    .putString("warmup", etWarmup.getText().toString())
                    .putString("legs", etLegs.getText().toString())
                    .putString("glutes", etGlutes.getText().toString())
                    .putString("cardio", etCardio.getText().toString())
                    .putString("nutrition", etNutrition.getText().toString())
                    .putString("timing", etTiming.getText().toString())
                    .apply();

            Intent intent = new Intent(TuesdayActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Grabación de videos
        setupRecordButton(btnRecordLegs, "legs_video.mp4", R.id.lottieLegs, R.id.videoLegs, "video_legs_uri");
        setupRecordButton(btnRecordGlutes, "glutes_video.mp4", R.id.lottieGlutes, R.id.videoGlutes, "video_glutes_uri");
        setupRecordButton(btnRecordCardio, "cardio_video.mp4", R.id.lottieCardio, R.id.videoCardio, "video_cardio_uri");

        // Borrado de videos
        setupDeleteButton(btnDeleteLegs, "legs_video.mp4", R.id.lottieLegs, R.id.videoLegs, "video_legs_uri");
        setupDeleteButton(btnDeleteGlutes, "glutes_video.mp4", R.id.lottieGlutes, R.id.videoGlutes, "video_glutes_uri");
        setupDeleteButton(btnDeleteCardio, "cardio_video.mp4", R.id.lottieCardio, R.id.videoCardio, "video_cardio_uri");
    }

    // Métodos auxiliares para no repetir código
    private void loadVideo(String fileName, int lottieId, int videoId, String prefKey) {
        String path = prefs.getString(prefKey, null);
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                Uri uri = Uri.fromFile(file);
                replaceLottieWithVideo(lottieId, videoId, uri);
                if (fileName.contains("legs")) legsVideoUri = uri;
                if (fileName.contains("glutes")) glutesVideoUri = uri;
                if (fileName.contains("cardio")) cardioVideoUri = uri;
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
                    if (fileName.contains("legs")) legsVideoUri = savedUri;
                    if (fileName.contains("glutes")) glutesVideoUri = savedUri;
                    if (fileName.contains("cardio")) cardioVideoUri = savedUri;
                    replaceLottieWithVideo(lottieId, videoId, savedUri);
                    prefs.edit().putString(prefKey, savedUri.getPath()).apply();
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(TuesdayActivity.this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
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
            if (fileName.contains("legs")) legsVideoUri = null;
            if (fileName.contains("glutes")) glutesVideoUri = null;
            if (fileName.contains("cardio")) cardioVideoUri = null;
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
        // video.start(); ← desactivado
    }

    private String getDefaultWarmup() { return "∙ Caminata rápida 3 min\n∙ Movilidad cadera + 10 sentadillas aire"; }
    private String getDefaultLegs() { return "Sentadillas (con barra o peso corporal)\n4×10–12 · Desc 60s"; }
    private String getDefaultGlutes() { return "Hip thrust (con barra o banda)\n3×12–15 · Desc 45s"; }
    private String getDefaultCardio() { return "Bicicleta estática o elíptica\n20–30 min · Intensidad moderada"; }
    private String getDefaultNutrition() {
        return "• Económica: Avena + plátano + huevo\n" +
                "• Proteína: Pollo + batata + vegetales\n" +
                "• Vegetariana: Lentejas + quinoa + aguacate";
    }
    private String getDefaultTiming() { return "Duración: 60–75 min\nDescanso entre series: 60–90 s"; }
}