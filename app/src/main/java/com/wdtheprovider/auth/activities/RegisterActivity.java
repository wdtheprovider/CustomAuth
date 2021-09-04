package com.wdtheprovider.auth.activities;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.wdtheprovider.auth.R;
import com.wdtheprovider.auth.utils.Constants;

import org.json.JSONArray;
import org.json.JSONObject;

import static com.wdtheprovider.auth.utils.Constants.getJSONString;

public class RegisterActivity extends AppCompatActivity {

    AlertDialog.Builder emailTakenDialog, alertDialog;
    EditText email, password, name;
    String finalNamel;
    String strMessage;
    Button btn_register;

    TextView txt_terms;

    Button btn_login;


    private String TAG = "wdregister";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        initComponents();

        btn_register.setOnClickListener(v -> {

            /**
             * Register button is click
             * The if statements below, i need to validate the inputs. (Check if the name,password and email inputs are not empty)
             * Then if everything enter met the requirements, we can then call our RegisterTask asyncTask.
             *
             **/


            if (name.getText().toString().isEmpty()) {
                name.setError(Constants.REQUIRED_FIELD);
            } else if (email.getText().toString().isEmpty()) {
                email.setError(Constants.REQUIRED_FIELD);
            } else if (password.getText().toString().isEmpty()) {
                password.setError(Constants.REQUIRED_FIELD);
            } else {
                finalNamel = name.getText().toString().replace(" ", "%20");
                Log.d(TAG, "Name: " + finalNamel);
                new registerTask().execute(Constants.getRegisterLink(finalNamel, email.getText().toString(), password.getText().toString()));
            }

        });

        btn_login.setOnClickListener(v -> {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        });


    }

    private void initComponents() {
        name = findViewById(R.id.edt_user);
        email = findViewById(R.id.edt_email);
        password = findViewById(R.id.edt_password);
        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        txt_terms = findViewById(R.id.txt_terms);
    }


    @SuppressLint("StaticFieldLeak")
    public class registerTask extends AsyncTask<String, String, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            Log.d(TAG, "Registering Account");

            progressDialog = new ProgressDialog(RegisterActivity.this);
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

        alertDialog = new AlertDialog.Builder(RegisterActivity.this);
        emailTakenDialog = new AlertDialog.Builder(RegisterActivity.this);


        /**
         * When our json file returns 0
         * It means our account that email provided is already in the database, the use should login.
         */

        if (Constants.GET_SUCCESS_MSG == 0) {
            emailTakenDialog.setTitle("Account Notice");
            emailTakenDialog.setMessage("Oops, Email already exist. Please login or reset your password.");
            //alertDialog.setCancelable(false);
            emailTakenDialog.setPositiveButton("Login", (dialog, which) -> {
                Intent intent = new Intent(this, LoginActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            });
            emailTakenDialog.show();

            email.setText("");
            password.setText("");
            email.requestFocus();


            /**
             * When our json file returns 1
             * It means our account has been created successfully and records are added in our database.
             *
             */
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

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}