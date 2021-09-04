package com.wdtheprovider.auth.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wdtheprovider.auth.R;
import com.wdtheprovider.auth.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.wdtheprovider.auth.utils.Constants.getJSONString;

public class MainActivity extends AppCompatActivity {

    RelativeLayout btnRegister,btn_login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegister = findViewById(R.id.btnRegister);
        btn_login = findViewById(R.id.btnlogin);

        btn_login.setOnClickListener(v -> {
            startActivity(new Intent(this,LoginActivity.class));
            finish();
        });


        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this,RegisterActivity.class));
            finish();
        });
    }


}