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

    private String TAG = "wdregister";
    EditText email, password;
    String strMessage;
    RelativeLayout btn,btn_login;


    AlertDialog.Builder emailTakenDialog, alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        btn = findViewById(R.id.btn);
        btn_login = findViewById(R.id.btnlogin);
        email = findViewById(R.id.email);
        password = findViewById(R.id.password);

        btn_login.setOnClickListener(v -> {
            startActivity(new Intent(this,RegisterActivity.class));
        });



        btn.setOnClickListener(v -> {
            if (email.getText().toString().isEmpty()) {
                email.setError(Constants.REQUIRED_FIELD);
            } else if (password.getText().toString().isEmpty()) {
                password.setError(Constants.REQUIRED_FIELD);
            } else {
                new registerTask().execute(Constants.getRegisterLink("Dingaan", email.getText().toString(), password.getText().toString()));
            }
        });
    }


    @SuppressLint("StaticFieldLeak")
    public class registerTask extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "Registering Account");

            progressDialog = new ProgressDialog(MainActivity.this);
            progressDialog.setTitle("Registering Account");
            progressDialog.setMessage("Please wait, we are setting up your account.");
            progressDialog.setCancelable(false);
            progressDialog.show();


        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);

            if (s == null || s.length() == 0) {
                showToast("No Data Found");
                Log.d(TAG, strMessage + " No Data Found");
            } else {
                try {

                    JSONObject mainJSON = new JSONObject(s);
                    JSONArray jsonArray = mainJSON.getJSONArray(Constants.RESULT);
                    JSONObject jsonObject = null;

                    for (int i = 0; i < jsonArray.length(); i++) {
                        jsonObject = jsonArray.getJSONObject(i);
                        strMessage = jsonObject.getString(Constants.MSG);
                        Constants.GET_SUCCESS_MSG = jsonObject.getInt(Constants.SUCCESS);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

                new Handler().postDelayed(() -> {
                    if (null != progressDialog && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }

                    login();
                }, 2000);

            }

            Log.d(TAG, strMessage + " GET_SUCCESS_MSG - " + Constants.GET_SUCCESS_MSG);
            Log.d(TAG, s + "");


        }

        @Override
        protected String doInBackground(String... strings) {
            return getJSONString(strings[0]);

        }
    }

    private void login() {

        alertDialog = new AlertDialog.Builder(MainActivity.this);
        emailTakenDialog = new AlertDialog.Builder(MainActivity.this);


        if (Constants.GET_SUCCESS_MSG == 0) {
            emailTakenDialog.setTitle("Account Notice");
            emailTakenDialog.setMessage("Oops, Email already exist. Please login or reset your password.");
            //alertDialog.setCancelable(false);
            emailTakenDialog.setPositiveButton("Login", (dialog, which) -> {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            });            emailTakenDialog.show();

            email.setText("");
            password.setText("");
            email.requestFocus();


        } else if (Constants.GET_SUCCESS_MSG == 1) {
            alertDialog.setTitle("Account Created");
            alertDialog.setMessage("Account registered successfully.");
            alertDialog.setPositiveButton("OK", (dialog, which) -> {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            });
            alertDialog.setCancelable(false);
            alertDialog.show();
        }


    }


    public void showToast(String message) {
        Toast.makeText(getApplicationContext(), message, Toast.LENGTH_SHORT).show();
    }


}