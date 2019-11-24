package io.cogitech.healthclick.asynctask;

import android.util.Log;

import com.squareup.okhttp.Response;

import java.io.InputStreamReader;
import java.net.HttpURLConnection;


public class WSUtils {

    public static String get(String method, String url, String jsonBody, String user, String password) throws Exception {

        Response response = OkHttpUtils.sendGetOkHttpRequest(method, url, jsonBody, user, password);

        if (response.code() != HttpURLConnection.HTTP_OK) {
            throw new Exception("reponse du service incorrect :" + response.code());
        } else {

            InputStreamReader inputStreamReader = new InputStreamReader(response.body().byteStream());

            String s = response.body().string();

            Log.e("reponses : ", s);
            //Log.v("TAG",inputStreamReader.toString());
            inputStreamReader.close();

            return s;
        }
    }
}
