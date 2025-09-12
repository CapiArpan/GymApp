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

public class FridayActivity extends AppCompatActivity {

    private EditText etWarmup, etShoulders, etTriceps, etCore, etNutrition, etTiming;
    private Button btnBackHome, btnRecordShoulders, btnRecordTriceps, btnRecordCore;
    private Button btnDeleteShoulders, btnDeleteTriceps, btnDeleteCore;
    private SharedPreferences prefs;

    private LottieAnimationView lottieShoulders, lottieTriceps, lottieCore;
    private VideoView videoShoulders, videoTriceps, videoCore;
    private LottieZoomHelper zoomHelper;
    private CamaraVideoHelper camaraHelper;

    private Uri shouldersVideoUri, tricepsVideoUri, coreVideoUri;

    private final ActivityResultLauncher<Intent> videoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    camaraHelper.handleActivityResult(result.getData());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friday);

        Toolbar toolbar = findViewById(R.id.toolbarFriday);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        zoomHelper = new LottieZoomHelper(this, R.id.zoomOverlay);

        etWarmup    = findViewById(R.id.etWarmup);
        etShoulders = findViewById(R.id.etShoulders);
        etTriceps   = findViewById(R.id.etTriceps);
        etCore      = findViewById(R.id.etCore);
        etNutrition = findViewById(R.id.etNutrition);
        etTiming    = findViewById(R.id.etTiming);
        btnBackHome = findViewById(R.id.btnBackHome);

        lottieShoulders = findViewById(R.id.lottieShoulders);
        lottieTriceps   = findViewById(R.id.lottieTriceps);
        lottieCore      = findViewById(R.id.lottieCore);

        videoShoulders = findViewById(R.id.videoShoulders);
        videoTriceps   = findViewById(R.id.videoTriceps);
        videoCore      = findViewById(R.id.videoCore);

        btnRecordShoulders = findViewById(R.id.btnRecordShoulders);
        btnRecordTriceps   = findViewById(R.id.btnRecordTriceps);
        btnRecordCore      = findViewById(R.id.btnRecordCore);

        btnDeleteShoulders = findViewById(R.id.btnDeleteShoulders);
        btnDeleteTriceps   = findViewById(R.id.btnDeleteTriceps);
        btnDeleteCore      = findViewById(R.id.btnDeleteCore);

        lottieShoulders.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));
        lottieTriceps.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));
        lottieCore.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));

        videoShoulders.setOnClickListener(v -> {
            if (shouldersVideoUri != null) zoomHelper.showZoom(shouldersVideoUri);
        });
        videoTriceps.setOnClickListener(v -> {
            if (tricepsVideoUri != null) zoomHelper.showZoom(tricepsVideoUri);
        });
        videoCore.setOnClickListener(v -> {
            if (coreVideoUri != null) zoomHelper.showZoom(coreVideoUri);
        });

        prefs = getSharedPreferences("FridayPrefs", MODE_PRIVATE);

        etWarmup.setText(prefs.getString("warmup", getDefaultWarmup()));
        etShoulders.setText(prefs.getString("shoulders", getDefaultShoulders()));
        etTriceps.setText(prefs.getString("triceps", getDefaultTriceps()));
        etCore.setText(prefs.getString("core", getDefaultCore()));
        etNutrition.setText(prefs.getString("nutrition", getDefaultNutrition()));
        etTiming.setText(prefs.getString("timing", getDefaultTiming()));

        String shouldersPath = prefs.getString("video_shoulders_uri", null);
        if (shouldersPath != null) {
            File file = new File(shouldersPath);
            if (file.exists()) {
                shouldersVideoUri = Uri.fromFile(file);
                replaceLottieWithVideo(R.id.lottieShoulders, R.id.videoShoulders, shouldersVideoUri);
            } else {
                Toast.makeText(this, "El video de hombros fue eliminado o no se encuentra", Toast.LENGTH_SHORT).show();
            }
        }

        String tricepsPath = prefs.getString("video_triceps_uri", null);
        if (tricepsPath != null) {
            File file = new File(tricepsPath);
            if (file.exists()) {
                tricepsVideoUri = Uri.fromFile(file);
                replaceLottieWithVideo(R.id.lottieTriceps, R.id.videoTriceps, tricepsVideoUri);
            } else {
                Toast.makeText(this, "El video de tríceps fue eliminado o no se encuentra", Toast.LENGTH_SHORT).show();
            }
        }

        String corePath = prefs.getString("video_core_uri", null);
        if (corePath != null) {
            File file = new File(corePath);
            if (file.exists()) {
                coreVideoUri = Uri.fromFile(file);
                replaceLottieWithVideo(R.id.lottieCore, R.id.videoCore, coreVideoUri);
            } else {
                Toast.makeText(this, "El video de core fue eliminado o no se encuentra", Toast.LENGTH_SHORT).show();
            }
        }

        btnDeleteShoulders.setOnClickListener(v -> {
            File file = new File(getFilesDir(), "shoulders_video.mp4");
            if (file.exists()) file.delete();
            prefs.edit().remove("video_shoulders_uri").apply();
            shouldersVideoUri = null;
            videoShoulders.setVisibility(VideoView.GONE);
            lottieShoulders.setVisibility(LottieAnimationView.VISIBLE);
        });

        btnDeleteTriceps.setOnClickListener(v -> {
            File file = new File(getFilesDir(), "triceps_video.mp4");
            if (file.exists()) file.delete();
            prefs.edit().remove("video_triceps_uri").apply();
            tricepsVideoUri = null;
            videoTriceps.setVisibility(VideoView.GONE);
            lottieTriceps.setVisibility(LottieAnimationView.VISIBLE);
        });

        btnDeleteCore.setOnClickListener(v -> {
            File file = new File(getFilesDir(), "core_video.mp4");
            if (file.exists()) file.delete();
            prefs.edit().remove("video_core_uri").apply();
            coreVideoUri = null;
            videoCore.setVisibility(VideoView.GONE);
            lottieCore.setVisibility(LottieAnimationView.VISIBLE);
        });

        SharedPreferences progressPrefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        progressPrefs.edit().putBoolean("viernes_completado", true).apply();

        btnBackHome.setOnClickListener(v -> {
            prefs.edit()
                    .putString("warmup", etWarmup.getText().toString())
                    .putString("shoulders", etShoulders.getText().toString())
                    .putString("triceps", etTriceps.getText().toString())
                    .putString("core", etCore.getText().toString())
                    .putString("nutrition", etNutrition.getText().toString())
                    .putString("timing", etTiming.getText().toString())
                    .apply();

            Intent intent = new Intent(FridayActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        btnRecordShoulders.setOnClickListener(v -> {
            camaraHelper = new CamaraVideoHelper(this, new CamaraVideoHelper.CameraCallback() {
                @Override
                public void onVideoCaptured(Uri videoUri) {
                    Uri savedUri = camaraHelper.saveVideoPermanently(videoUri, "shoulders_video.mp4");
                    shouldersVideoUri = savedUri;
                    replaceLottieWithVideo(R.id.lottieShoulders, R.id.videoShoulders, savedUri);
                    prefs.edit().putString("video_shoulders_uri", savedUri.getPath()).apply();
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(FridayActivity.this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
            }, videoLauncher);
            camaraHelper.requestCameraPermissionAndLaunch();
        });

        btnRecordTriceps.setOnClickListener(v -> {
            camaraHelper = new CamaraVideoHelper(this, new CamaraVideoHelper.CameraCallback() {
                @Override
                public void onVideoCaptured(Uri videoUri) {
                    Uri savedUri = camaraHelper.saveVideoPermanently(videoUri, "triceps_video.mp4");
                    tricepsVideoUri = savedUri;
                    replaceLottieWithVideo(R.id.lottieTriceps, R.id.videoTriceps, savedUri);
                    prefs.edit().putString("video_triceps_uri", savedUri.getPath()).apply();
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(FridayActivity.this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
            }, videoLauncher);
            camaraHelper.requestCameraPermissionAndLaunch();
        });

        btnRecordCore.setOnClickListener(v -> {
            camaraHelper = new CamaraVideoHelper(this, new CamaraVideoHelper.CameraCallback() {
                @Override
                public void onVideoCaptured(Uri videoUri) {
                    Uri savedUri = camaraHelper.saveVideoPermanently(videoUri, "core_video.mp4");
                    coreVideoUri = savedUri;
                    replaceLottieWithVideo(R.id.lottieCore, R.id.videoCore, savedUri);
                    prefs.edit().putString("video_core_uri", savedUri.getPath()).apply();
                }

                @Override
                public void onPermissionDenied() {
                    Toast.makeText(FridayActivity.this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
                }
            }, videoLauncher);
            camaraHelper.requestCameraPermissionAndLaunch();
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
        // video.start(); ← desactivado para evitar reproducción automática
    }

    private String getDefaultWarmup() {
        return "∙ Rotación hombros con banda – 2 rondas\n10 flexiones rodillas + 20 jumping jacks";
    }

    private String getDefaultShoulders() {
        return "Press militar (barra o mancuernas)\n4×8–10 · Desc 60s";
    }

    private String getDefaultTriceps() {
        return "Fondos en banco o paralelas\n3×10–12 · Desc 45s";
    }

    private String getDefaultCore() {
        return "Plancha + Mountain climbers\n3×30s + 20 · Desc 45s";
    }

    private String getDefaultNutrition() {
        return "• Económica: Pan integral + huevo + leche\n" +
                "• Proteína: Pavo + arroz + entrenar\n" +
                "• Vegetariana: Tofu + ensalada fresca + quinoa";
    }

    private String getDefaultTiming() {
        return "Duración: 50–65 min\nDescanso entre series: 45–90 s";
    }
}

