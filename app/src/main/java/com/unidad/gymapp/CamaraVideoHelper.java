package com.unidad.gymapp;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.provider.MediaStore;

import androidx.activity.result.ActivityResultLauncher;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class CamaraVideoHelper {

    private static final int REQUEST_CAMERA_PERMISSION = 1001;

    private final Activity context;
    private final CameraCallback callback;
    private final ActivityResultLauncher<Intent> launcher;
    private Uri tempVideoUri;

    public interface CameraCallback {
        void onVideoCaptured(Uri videoUri);
        void onPermissionDenied();
    }

    public CamaraVideoHelper(Activity context, CameraCallback callback, ActivityResultLauncher<Intent> launcher) {
        this.context = context;
        this.callback = callback;
        this.launcher = launcher;
    }

    public void requestCameraPermissionAndLaunch() {
        if (ContextCompat.checkSelfPermission(context, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.CAMERA}, REQUEST_CAMERA_PERMISSION);
        } else {
            launchCamera();
        }
    }

    public void handlePermissionResult(int requestCode, int[] grantResults) {
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                launchCamera();
            } else {
                callback.onPermissionDenied();
            }
        }
    }

    private void launchCamera() {
        Intent intent = new Intent(MediaStore.ACTION_VIDEO_CAPTURE);
        launcher.launch(intent);
    }

    public void handleActivityResult(Intent data) {
        tempVideoUri = data.getData();
        if (tempVideoUri != null) {
            callback.onVideoCaptured(tempVideoUri);
        }
    }

    /**
     * Copia el video grabado a almacenamiento interno y devuelve un Uri persistente.
     * @param sourceUri Uri temporal del video
     * @param filename Nombre del archivo final (ej. "shoulders_video.mp4")
     * @return Uri persistente del video guardado
     */
    public Uri saveVideoPermanently(Uri sourceUri, String filename) {
        File destFile = new File(context.getFilesDir(), filename);
        try (InputStream in = context.getContentResolver().openInputStream(sourceUri);
             OutputStream out = new FileOutputStream(destFile)) {

            byte[] buffer = new byte[4096];
            int bytesRead;
            while ((bytesRead = in.read(buffer)) != -1) {
                out.write(buffer, 0, bytesRead);
            }
            return Uri.fromFile(destFile);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
