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

public class SaturdayActivity extends AppCompatActivity {

    private EditText etWarmup, etHip, etCalf, etQuad, etNutrition, etTiming;
    private Button btnBackHome, btnRecordHip, btnRecordCalf, btnRecordQuad;
    private Button btnDeleteHip, btnDeleteCalf, btnDeleteQuad;
    private SharedPreferences prefs;

    private LottieAnimationView lottieHip, lottieCalf, lottieQuad;
    private VideoView videoHip, videoCalf, videoQuad;
    private LottieZoomHelper zoomHelper;
    private CamaraVideoHelper camaraHelper;

    private Uri hipVideoUri, calfVideoUri, quadVideoUri;

    private final ActivityResultLauncher<Intent> videoLauncher =
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(), result -> {
                if (result.getResultCode() == RESULT_OK && result.getData() != null) {
                    camaraHelper.handleActivityResult(result.getData());
                }
            });

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saturday);

        // Toolbar y botón de retroceso
        Toolbar toolbar = findViewById(R.id.toolbarSaturday);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        // Inicializar helper de zoom
        zoomHelper = new LottieZoomHelper(this, R.id.zoomOverlay);

        // Referencias a los campos
        etWarmup    = findViewById(R.id.etWarmup);
        etHip       = findViewById(R.id.etHip);
        etCalf      = findViewById(R.id.etCalf);
        etQuad      = findViewById(R.id.etQuad);
        etNutrition = findViewById(R.id.etNutrition);
        etTiming    = findViewById(R.id.etTiming);
        btnBackHome = findViewById(R.id.btnBackHome);

        // Referencias a los Lotties y Videos
        lottieHip  = findViewById(R.id.lottieHip);
        lottieCalf = findViewById(R.id.lottieCalf);
        lottieQuad = findViewById(R.id.lottieQuad);

        videoHip  = findViewById(R.id.videoHip);
        videoCalf = findViewById(R.id.videoCalf);
        videoQuad = findViewById(R.id.videoQuad);

        // Botones de grabar y eliminar
        btnRecordHip  = findViewById(R.id.btnRecordHip);
        btnRecordCalf = findViewById(R.id.btnRecordCalf);
        btnRecordQuad = findViewById(R.id.btnRecordQuad);

        btnDeleteHip  = findViewById(R.id.btnDeleteHip);
        btnDeleteCalf = findViewById(R.id.btnDeleteCalf);
        btnDeleteQuad = findViewById(R.id.btnDeleteQuad);

        // Activar zoom modular
        lottieHip.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));
        lottieCalf.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));
        lottieQuad.setOnClickListener(v -> zoomHelper.showZoom(R.raw.rest_day_animation));

        videoHip.setOnClickListener(v -> {
            if (hipVideoUri != null) zoomHelper.showZoom(hipVideoUri);
        });
        videoCalf.setOnClickListener(v -> {
            if (calfVideoUri != null) zoomHelper.showZoom(calfVideoUri);
        });
        videoQuad.setOnClickListener(v -> {
            if (quadVideoUri != null) zoomHelper.showZoom(quadVideoUri);
        });

        // SharedPreferences
        prefs = getSharedPreferences("SaturdayPrefs", MODE_PRIVATE);

        // Cargar datos guardados o valores por defecto
        etWarmup.setText(prefs.getString("warmup", getDefaultWarmup()));
        etHip.setText(prefs.getString("hip", getDefaultHip()));
        etCalf.setText(prefs.getString("calf", getDefaultCalf()));
        etQuad.setText(prefs.getString("quad", getDefaultQuad()));
        etNutrition.setText(prefs.getString("nutrition", getDefaultNutrition()));
        etTiming.setText(prefs.getString("timing", getDefaultTiming()));

        // Restaurar videos si existen
        restoreVideo("video_hip_uri", "hip_video.mp4", R.id.lottieHip, R.id.videoHip);
        restoreVideo("video_calf_uri", "calf_video.mp4", R.id.lottieCalf, R.id.videoCalf);
        restoreVideo("video_quad_uri", "quad_video.mp4", R.id.lottieQuad, R.id.videoQuad);

        // Eliminar videos
        btnDeleteHip.setOnClickListener(v -> deleteVideo("video_hip_uri", "hip_video.mp4", videoHip, lottieHip));
        btnDeleteCalf.setOnClickListener(v -> deleteVideo("video_calf_uri", "calf_video.mp4", videoCalf, lottieCalf));
        btnDeleteQuad.setOnClickListener(v -> deleteVideo("video_quad_uri", "quad_video.mp4", videoQuad, lottieQuad));

        // Registrar progreso del sábado como completado
        SharedPreferences progressPrefs = getSharedPreferences("ProgressPrefs", MODE_PRIVATE);
        progressPrefs.edit().putBoolean("sabado_completado", true).apply();

        // Guardar contenido editable y volver al Home
        btnBackHome.setOnClickListener(v -> {
            prefs.edit()
                    .putString("warmup", etWarmup.getText().toString())
                    .putString("hip", etHip.getText().toString())
                    .putString("calf", etCalf.getText().toString())
                    .putString("quad", etQuad.getText().toString())
                    .putString("nutrition", etNutrition.getText().toString())
                    .putString("timing", etTiming.getText().toString())
                    .apply();

            Intent intent = new Intent(SaturdayActivity.this, HomeActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
            startActivity(intent);
            finish();
        });

        // Grabar videos
        btnRecordHip.setOnClickListener(v -> recordVideo("hip_video.mp4", "video_hip_uri", R.id.lottieHip, R.id.videoHip));
        btnRecordCalf.setOnClickListener(v -> recordVideo("calf_video.mp4", "video_calf_uri", R.id.lottieCalf, R.id.videoCalf));
        btnRecordQuad.setOnClickListener(v -> recordVideo("quad_video.mp4", "video_quad_uri", R.id.lottieQuad, R.id.videoQuad));
    }

    private void restoreVideo(String key, String filename, int lottieId, int videoId) {
        String path = prefs.getString(key, null);
        if (path != null) {
            File file = new File(path);
            if (file.exists()) {
                Uri uri = Uri.fromFile(file);
                if (filename.contains("hip")) hipVideoUri = uri;
                else if (filename.contains("calf")) calfVideoUri = uri;
                else if (filename.contains("quad")) quadVideoUri = uri;
                replaceLottieWithVideo(lottieId, videoId, uri);
            } else {
                Toast.makeText(this, "El video fue eliminado o no se encuentra", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void deleteVideo(String key, String filename, VideoView video, LottieAnimationView lottie) {
        File file = new File(getFilesDir(), filename);
        if (file.exists()) file.delete();
        prefs.edit().remove(key).apply();
        video.setVisibility(VideoView.GONE);
        lottie.setVisibility(LottieAnimationView.VISIBLE);
    }

    private void recordVideo(String filename, String key, int lottieId, int videoId) {
        camaraHelper = new CamaraVideoHelper(this, new CamaraVideoHelper.CameraCallback() {
            @Override
            public void onVideoCaptured(Uri videoUri) {
                Uri savedUri = camaraHelper.saveVideoPermanently(videoUri, filename);
                if (filename.contains("hip")) hipVideoUri = savedUri;
                else if (filename.contains("calf")) calfVideoUri = savedUri;
                else if (filename.contains("quad")) quadVideoUri = savedUri;
                replaceLottieWithVideo(lottieId, videoId, savedUri);
                prefs.edit().putString(key, savedUri.getPath()).apply();
            }

            @Override
            public void onPermissionDenied() {
                Toast.makeText(SaturdayActivity.this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
            }
        }, videoLauncher);
        camaraHelper.requestCameraPermissionAndLaunch();
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
        // video.start();
    }

    // Valores por defecto
    private String getDefaultWarmup() {
        return "∙ Sentadillas con peso corporal – 2 rondas\n20 jumping jacks + 10 lunges por pierna";
    }

    private String getDefaultHip() {
        return "Elevaciones de pierna + estiramiento dinámico\n3×15 · Desc 45s";
    }

    private String getDefaultCalf() {
        return "Elevaciones de talón en escalón\n4×20 · Desc 30s";
    }

    private String getDefaultQuad() {
        return "Sentadilla frontal con barra\n4×8 · Desc 60s";
    }

    private String getDefaultNutrition() {
        return "• Económica: Avena + plátano + leche\n" +
                "• Proteína: Pollo + batata + ensalada\n" +
                "• Vegetariana: Lentejas + arroz integral + palta";
    }

    private String getDefaultTiming() {
        return "Duración: 55–70 min\nDescanso entre series: 45–90 s";
    }
}
