package com.unidad.gymapp;

import android.app.Activity;
import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.RawRes;

import com.airbnb.lottie.LottieAnimationView;
import com.airbnb.lottie.LottieDrawable;

public class LottieZoomHelper {

    private final FrameLayout zoomOverlay;

    public LottieZoomHelper(Activity activity, int overlayId) {
        zoomOverlay = activity.findViewById(overlayId);
    }

    /**
     * Muestra una animación Lottie centrada y ampliada en pantalla.
     * @param animationResId ID del recurso raw (ej. R.raw.rest_day_animation)
     */
    public void showZoom(@RawRes int animationResId) {
        if (zoomOverlay == null) return;

        zoomOverlay.removeAllViews();
        zoomOverlay.setVisibility(View.VISIBLE);

        LottieAnimationView zoomedLottie = new LottieAnimationView(zoomOverlay.getContext());
        zoomedLottie.setAnimation(animationResId);
        zoomedLottie.setRepeatCount(LottieDrawable.INFINITE);
        zoomedLottie.setScaleX(2.5f);
        zoomedLottie.setScaleY(2.5f);
        zoomedLottie.playAnimation();

        FrameLayout.LayoutParams params = new FrameLayout.LayoutParams(
                FrameLayout.LayoutParams.WRAP_CONTENT,
                FrameLayout.LayoutParams.WRAP_CONTENT
        );
        params.gravity = Gravity.CENTER;

        zoomOverlay.addView(zoomedLottie, params);

        // Cerrar al hacer clic fuera
        zoomOverlay.setOnClickListener(v -> {
            zoomOverlay.setVisibility(View.GONE);
            zoomOverlay.removeAllViews();
        });
    }
}
