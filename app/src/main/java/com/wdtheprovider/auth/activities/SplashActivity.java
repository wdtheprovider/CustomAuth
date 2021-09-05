package com.wdtheprovider.auth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import com.wdtheprovider.auth.R;
import com.wdtheprovider.auth.utils.Prefs;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);


        new Handler().postDelayed(() -> {

            if (Prefs.getIsLogin(getApplicationContext())) {
                startActivity(new Intent(this, DashboardActivity.class));
                finish();
            } else {
                startActivity(new Intent(this, MainActivity.class));
                finish();
            }

        }, 2000);
    }
}