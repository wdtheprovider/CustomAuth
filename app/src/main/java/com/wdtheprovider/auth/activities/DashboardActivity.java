package com.wdtheprovider.auth.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wdtheprovider.auth.R;
import com.wdtheprovider.auth.utils.Prefs;

public class DashboardActivity extends AppCompatActivity {

    Button btn_logout;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        btn_logout = findViewById(R.id.btn_logout);

        btn_logout.setOnClickListener(v -> {

            AlertDialog.Builder builder = new AlertDialog.Builder(DashboardActivity.this);
            builder.setTitle("Log out");
            builder.setMessage("Are you sure want to log out?");
            builder.setPositiveButton(R.string.dialog_yes, (di, i) -> {

                progressDialog = new ProgressDialog(DashboardActivity.this);
                progressDialog.setTitle(getResources().getString(R.string.title_please_wait));
                progressDialog.setMessage("Processing to log you out");
                progressDialog.setCancelable(false);
                progressDialog.show();

                Prefs.saveIsLogin(getApplicationContext(), false);

                new Handler().postDelayed(() -> {
                    progressDialog.dismiss();
                    AlertDialog.Builder builder1 = new AlertDialog.Builder(DashboardActivity.this);
                    builder1.setMessage("You successfully logged out");
                    builder1.setPositiveButton(R.string.dialog_ok, (dialogInterface, i1) -> {
                        Intent intent = new Intent(this, MainActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        finish();
                    });

                    builder1.setCancelable(false);
                    builder1.show();

                }, 2000);

            });
            builder.setNegativeButton(R.string.dialog_cancel, null);
            builder.show();

        });
    }
}