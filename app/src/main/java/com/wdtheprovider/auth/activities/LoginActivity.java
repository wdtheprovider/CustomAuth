package com.wdtheprovider.auth.activities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.wdtheprovider.auth.R;
import com.wdtheprovider.auth.utils.Constants;
import com.wdtheprovider.auth.utils.Prefs;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginActivity extends AppCompatActivity {

    Button btn_register, btn_login;

    private Prefs MyApp;

    String strEmail, strPassword, strMessage, strName, strPassengerId, strImage;
    EditText edt_password, edt_email;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        btn_register = findViewById(R.id.btn_register);
        btn_login = findViewById(R.id.btn_login);
        edt_password = findViewById(R.id.edt_password);
        edt_email = findViewById(R.id.edt_email);

        btn_login.setOnClickListener(v -> {

            if (edt_email.getText().toString().isEmpty()) {
                edt_email.setError(Constants.REQUIRED_FIELD);
            } else if (edt_password.getText().toString().isEmpty()) {
                edt_password.setError(Constants.REQUIRED_FIELD);
            } else {
                new MyTaskLoginNormal().execute(Constants.getLoginLink(edt_email.getText().toString(), edt_password.getText().toString()));
            }
        });


        btn_register.setOnClickListener(v -> {
            startActivity(new Intent(this, RegisterActivity.class));
            finish();
        });
    }


    private class MyTaskLoginNormal extends AsyncTask<String, Void, String> {

        ProgressDialog progressDialog;

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(LoginActivity.this);
            progressDialog.setTitle(getResources().getString(R.string.title_please_wait));
            progressDialog.setMessage(getResources().getString(R.string.login_process));
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            return Constants.getJSONString(params[0]);
        }

        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            if (null == result || result.length() == 0) {

            } else {

                try {
                    JSONObject mainJson = new JSONObject(result);
                    JSONArray jsonArray = mainJson.getJSONArray(Constants.RESULT);
                    JSONObject objJson = null;
                    for (int i = 0; i < jsonArray.length(); i++) {
                        objJson = jsonArray.getJSONObject(i);
                        if (objJson.has(Constants.MSG)) {
                            strMessage = objJson.getString(Constants.MSG);
                            Constants.GET_SUCCESS_MSG = objJson.getInt(Constants.SUCCESS);
                        } else {
                            Constants.GET_SUCCESS_MSG = objJson.getInt(Constants.SUCCESS);
                            strName = objJson.getString(Constants.USER_NAME);
                            strPassengerId = objJson.getString(Constants.USER_ID);
                        }
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


                new Handler().postDelayed(() -> {
                    if (null != progressDialog && progressDialog.isShowing()) {
                        progressDialog.dismiss();
                    }
                    setResult();
                }, 2000);
            }

        }
    }

    public void setResult() {

        if (Constants.GET_SUCCESS_MSG == 0) {
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.whops);
            dialog.setMessage(R.string.login_failed);
            dialog.setPositiveButton(R.string.dialog_ok, null);
            dialog.setCancelable(false);
            dialog.show();

        } else {
            Prefs.saveIsLogin(getApplicationContext(), true);
            Prefs.saveLogin(getApplicationContext(), strPassengerId, strName, strEmail);
            AlertDialog.Builder dialog = new AlertDialog.Builder(this);
            dialog.setTitle(R.string.login_title);
            dialog.setMessage(R.string.login_success);
            dialog.setPositiveButton(R.string.dialog_ok, (dialogInterface, i) -> {
                Intent intent = new Intent(this, DashboardActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);
                finish();
            });
            dialog.setCancelable(false);
            dialog.show();

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        startActivity(new Intent(this, MainActivity.class));
        finish();
    }
}