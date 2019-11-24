package io.cogitech.healthclick.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import io.cogitech.healthclick.Model.Remede;
import io.cogitech.healthclick.R;

public class RemedeListAsynctask extends AsyncTask {
    public ArrayList<io.cogitech.healthclick.Model.Remede> remedes_list;
    ProgressDialog progressDialog;
    private Context context;
    private GetResult getResult;
    private Exception exception;
    private Gson gson = new Gson();

    public RemedeListAsynctask(Context context, GetResult getResult) {
        progressDialog = new ProgressDialog(context);
        this.context = context;
        this.getResult = getResult;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setMessage("Loading data ..."); // Setting Message
        this.progressDialog.setTitle("please wait "); // Setting Title
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        this.progressDialog.show(); // Display Progress Dialog
        this.progressDialog.setCancelable(false);
    }

    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            this.remedes_list = this.gson.fromJson(inputStreamToString(this.context.getResources().openRawResource(R.raw.drugs)), new TypeToken<ArrayList<Remede>>() {
            }.getType());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Object o) {
        super.onPostExecute(o);
        if (getResult != null) {
            if (exception != null) {
                getResult.getResultErreur(exception);
            } else {
                getResult.data(remedes_list);
                if (this.progressDialog.isShowing()) {
                    this.progressDialog.dismiss();
                }
            }
        }
    }

    public String inputStreamToString(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        StringBuffer sb = new StringBuffer();
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
        }
        return sb.toString();
    }

    public interface GetResult {
        void data(ArrayList<io.cogitech.healthclick.Model.Remede> remedes_list);

        void getResultErreur(Exception exception);
    }
}