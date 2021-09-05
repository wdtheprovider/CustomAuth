package com.wdtheprovider.auth.utils;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Constants {
    public static int GET_SUCCESS_MSG;
    public static String MSG = "msg";
    public static String RESULT = "result";
    public static String SUCCESS = "success";
    public static String REQUIRED_FIELD = "This field is required";
    public static final String USER_NAME = "name";
    public static final String USER_ID = "user_id";

    public static String TOP_URL_API = "https://dingi.icu/UniAPIs";

    public static String getRegisterLink(String name, String email, String password) {
        return TOP_URL_API+ "/api/user_register/?user_type=normal&name=" + name + "&email=" + email + "&password=" + password;
    }

    public static String getLoginLink(String email, String password){
        return TOP_URL_API + "/api/get_user_login/?email="+email+"&password="+password;
    }



    public static String getJSONString(String url) {
        String jsonString = null;
        HttpURLConnection linkConnection = null;
        try {
            URL linkurl = new URL(url);
            linkConnection = (HttpURLConnection) linkurl.openConnection();
            int responseCode = linkConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                InputStream linkinStream = linkConnection.getInputStream();
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                int j = 0;
                while ((j = linkinStream.read()) != -1) {
                    baos.write(j);
                }


                byte[] data = baos.toByteArray();
                jsonString = new String(data);

            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (linkConnection != null) {
                linkConnection.disconnect();
            }
        }

        return jsonString;
    }

}
