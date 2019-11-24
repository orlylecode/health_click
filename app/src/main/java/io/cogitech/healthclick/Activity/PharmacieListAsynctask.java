package io.cogitech.healthclick.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import io.cogitech.healthclick.Model.Pharmacie;
import io.cogitech.healthclick.Model.PharmacieGarde;
import io.cogitech.healthclick.R;

public class PharmacieListAsynctask extends AsyncTask {
    ProgressDialog progressDialog;
    private Context context;
    private GetResult getResult;
    private Exception exception;


    private ArrayList<Pharmacie> pharmacies = new ArrayList<Pharmacie>();
    private ArrayList<PharmacieGarde> pharmaciesGardes = new ArrayList<PharmacieGarde>();
    private Gson gson = new Gson();

    public PharmacieListAsynctask(Context context, GetResult getResult) {
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
            pharmacies = gson.fromJson(inputStreamToString(this.context.getResources().openRawResource(R.raw.pharmacies)), new TypeToken<ArrayList<Pharmacie>>() {
            }.getType());
            pharmaciesGardes = gson.fromJson(inputStreamToString(this.context.getResources().openRawResource(R.raw.pharmacie_garde)), new TypeToken<ArrayList<PharmacieGarde>>() {
            }.getType());

            for (int j = 0; j < pharmacies.size(); j++) {
                for (int i = 0; i < pharmaciesGardes.size(); i++) {
                    for (int i1 = 0; i1 < pharmaciesGardes.get(i).getC().size(); i1++) {
                        if (pharmaciesGardes.get(i).getC().get(i1).toLowerCase().equals(pharmacies.get(j).getB().toLowerCase())) {
                            pharmacies.get(j).setDateGarde(pharmaciesGardes.get(i).getA());
                            pharmacies.get(j).setGroupeGarde(pharmaciesGardes.get(i).getB());
                        }
                    }
                }
            }
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
                getResult.data(this.pharmacies);
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
        void data(ArrayList<Pharmacie> pharmacies);

        void getResultErreur(Exception exception);
    }
}