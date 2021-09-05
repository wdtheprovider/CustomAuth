package com.wdtheprovider.auth.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.wdtheprovider.auth.R;
import com.wdtheprovider.auth.utils.Prefs;

public class MainActivity extends AppCompatActivity {

    Button btnRegister, btn_login;
    TextView logged_in;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btnRegister = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        logged_in = findViewById(R.id.logged_in);


        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle("Main");
        setSupportActionBar(toolbar);


        if (Prefs.getIsLogin(getApplicationContext())) {
            logged_in.setText("Congratulation!, You are logged in with the following email\n"+Prefs.getUserEmail(getApplicationContext()));
        } else {
            logged_in.setText("Please Register to continue");
        }

        btn_login.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });


        btnRegister.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }


    public void btnAction(View view) {


    }


}