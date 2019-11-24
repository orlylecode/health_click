package io.cogitech.healthclick.asynctask;

import android.util.Log;

import com.squareup.okhttp.Credentials;
import com.squareup.okhttp.MediaType;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Protocol;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.RequestBody;
import com.squareup.okhttp.Response;

import java.util.Arrays;


public class OkHttpUtils {

    public static Response sendGetOkHttpRequest(String method, String url, String jsonBody, String user, String password) throws Exception {

        Log.v("TAG_URL", url);
        //Creation de la requette
        MediaType JSON = MediaType.parse("application/json; charset=utf-8");

        RequestBody body = RequestBody.create(JSON, jsonBody);

        OkHttpClient client = new OkHttpClient();

        Request request;

        switch (method.toUpperCase()) {

            case METHOD.HTTPS_DELETE:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .url(url)
                        .delete(body) //PUT
                        .build();
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();

            case METHOD.HTTPS_POST:
                request = new Request.Builder()
                        .addHeader("User-Agent", "OkHttp Bot")
                        .url(url)
                        .post(body) //PUT
                        .addHeader("Content-Type", "application/json;")
                        .build();
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();

            case METHOD.HTTPS_PUT:
                request = new Request.Builder()
                        .addHeader("User-Agent", "OkHttp Bot")
                        .url(url)
                        .put(body) //PUT
                        .addHeader("Content-Type", "application/json;")
                        .build();
                Log.e("TAG", "sendGetOkHttpRequest: " + request.headers());
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();

            case METHOD.HTTPS_GET:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .url(url)
                        .build();
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();

            case METHOD.HTTPS_AUT_POST:
                request = new Request.Builder()
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", Credentials.basic(user, password))
                        .url(url)
                        .post(body) //POST
                        .addHeader("Content-Type", "application/json;")
                        .build();
                Log.e("TAG", "sendGetOkHttpRequest: " + request.headers());
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();

            case METHOD.HTTPS_AUT_PUT:
                request = new Request.Builder()
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", Credentials.basic(user, password))
                        .url(url)
                        .put(body) //PUT
                        .addHeader("Content-Type", "application/json;")
                        .build();
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();

            case METHOD.HTTPS_AUT_GET:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", Credentials.basic(user, password))
                        .url(url)
                        .build();
                Log.e("TAG", "sendGetOkHttpRequest: " + request.headers());
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();

            case METHOD.HTTPS_AUT_DELETE:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", Credentials.basic(user, password))
                        .url(url)
                        .delete(body) //PUT
                        .build();
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();


            ///////////////////////////////////////////////////////////////////////////////////////////////
            case METHOD.DELETE:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .url(url)
                        .delete(body) //PUT
                        .build();
                return client.newCall(request).execute();

            case METHOD.POST:
                request = new Request.Builder()
                        .addHeader("User-Agent", "OkHttp Bot")
                        .url(url)
                        .post(body) //PUT
                        .addHeader("Content-Type", "application/json;")
                        .build();
                return client.newCall(request).execute();

            case METHOD.PUT:
                request = new Request.Builder()
                        .addHeader("User-Agent", "OkHttp Bot")
                        .url(url)
                        .put(body) //PUT
                        .addHeader("Content-Type", "application/json;")
                        .build();
                return client.newCall(request).execute();

            case METHOD.GET:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .url(url)
                        .build();
                return client.newCall(request).execute();

            case METHOD.AUT_POST:
                request = new Request.Builder()
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", Credentials.basic(user, password))
                        .url(url)
                        .post(body) //POST
                        .addHeader("Content-Type", "application/json;")
                        .build();
                Log.e("TAG", "sendGetOkHttpRequest: " + request.headers());
                return client.newCall(request).execute();

            case METHOD.AUT_PUT:
                request = new Request.Builder()
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", Credentials.basic(user, password))
                        .url(url)
                        .put(body) //PUT
                        .addHeader("Content-Type", "application/json;")
                        .build();
                return client.newCall(request).execute();

            case METHOD.AUT_GET:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", Credentials.basic(user, password))
                        .url(url)
                        .build();
                Log.e("TAG", "sendGetOkHttpRequest: " + request.headers());
                return client.newCall(request).execute();

            case METHOD.AUT_DELETE:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", Credentials.basic(user, password))
                        .url(url)
                        .delete(body) //PUT
                        .build();
                return client.newCall(request).execute();


//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////

            case METHOD.HTTPS_BEARER_AUT_POST:
                request = new Request.Builder()
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", "Bearer " + user + ":" + password)
                        .url(url)
                        .post(body) //POST
                        .addHeader("Content-Type", "application/json;")
                        .build();
                Log.e("TAG", "sendGetOkHttpRequest: " + request.headers());
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();

            case METHOD.HTTPS_BEARER_AUT_PUT:
                request = new Request.Builder()
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", "Bearer " + user + ":" + password)
                        .url(url)
                        .put(body) //PUT
                        .addHeader("Content-Type", "application/json;")
                        .build();
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();

            case METHOD.HTTPS_BEARER_AUT_GET:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", "Bearer " + user + ":" + password)
                        .url(url)
                        .build();
                Log.e("TAG", "sendGetOkHttpRequest: " + request.headers());
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();

            case METHOD.HTTPS_BEARER_AUT_DELETE:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .addHeader("Authorization", "Bearer " + user + ":" + password)
                        .url(url)
                        .delete(body) //PUT
                        .build();
                client.setProtocols(Arrays.asList(Protocol.HTTP_1_1));
                return client.newCall(request).execute();


            default:
                request = new Request.Builder()
                        .addHeader("Content-Type", "application/json;")
                        .addHeader("User-Agent", "OkHttp Bot")
                        .url(url)
                        .build();
                Log.e("TAG", "sendGetOkHttpRequest: " + request.headers());
                return client.newCall(request).execute();
        }
    }

}
