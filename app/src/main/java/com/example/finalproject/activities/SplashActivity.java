package com.example.finalproject.activities;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.finalproject.R;

public class SplashActivity extends AppCompatActivity {

    private static final int SPLASH_DURATION = 2500; // 2.5 seconds

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ImageView splashLogo = findViewById(R.id.splashLogo);
        TextView splashAppName = findViewById(R.id.splashAppName);
        TextView splashTagline = findViewById(R.id.splashTagline);

        // Make logo circular
        if (splashLogo != null) {
            splashLogo.setOutlineProvider(new android.view.ViewOutlineProvider() {
                @Override
                public void getOutline(android.view.View view, android.graphics.Outline outline) {
                    outline.setOval(0, 0, view.getWidth(), view.getHeight());
                }
            });
            splashLogo.setClipToOutline(true);
        }

        // Animate logo with scale and fade
        if (splashLogo != null) {
            Animation logoAnimation = AnimationUtils.loadAnimation(this, R.anim.scale_in);
            splashLogo.startAnimation(logoAnimation);
        }

        // Animate app name with fade in and delay
        if (splashAppName != null) {
            splashAppName.setAlpha(0f);
            splashAppName.animate()
                    .alpha(1f)
                    .setStartDelay(600)
                    .setDuration(800)
                    .start();
        }

        // Animate tagline with slide up and delay
        if (splashTagline != null) {
            Animation taglineAnimation = AnimationUtils.loadAnimation(this, R.anim.slide_up);
            taglineAnimation.setStartOffset(1000);
            splashTagline.startAnimation(taglineAnimation);
        }

        // Delay navigation to HomeActivity
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            Intent intent = new Intent(SplashActivity.this, HomeActivity.class);
            startActivity(intent);
            finish();
        }, SPLASH_DURATION);
    }
}

