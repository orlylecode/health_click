package io.cogitech.healthclick.asynctask;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.util.Log;

public class get extends AsyncTask {

    ProgressDialog progressDialog;
    private Exception exception;
    private GetResult getResult;
    private String url;
    private String terminal;
    private String jsonBody;
    private Context context;
    private String method;
    private String user;
    private String password;

    public get(String method, String url, String jsonBody, String user, String password, GetResult getResult, Context context) {
        this.method = method;
        this.jsonBody = jsonBody;
        this.context = context;
        this.url = url;
        this.getResult = getResult;
        progressDialog = new ProgressDialog(context);
        this.user = user;
        this.password = password;
    }

/*    public Get(String url, View.OnClickListener onClickListener) {

    }
*/

    @Override
    protected void onPreExecute() {
        this.progressDialog = new ProgressDialog(context);
        this.progressDialog.setMessage("Loading..."); // Setting Message
        this.progressDialog.setTitle("please wait "); // Setting Title
        this.progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER); // Progress Dialog Style Spinner
        this.progressDialog.show(); // Display Progress Dialog
        this.progressDialog.setCancelable(false);
    }


    @Override
    protected Object doInBackground(Object[] objects) {
        try {
            terminal = WSUtils.get(method, url, jsonBody, user, password);
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
                Log.i(url, exception.getLocalizedMessage());
            } else {
                getResult.get(terminal);
                if (this.progressDialog.isShowing()) {
                    this.progressDialog.dismiss();
                }
            }
        }


    }

    public interface GetResult {
        void get(String terminal);

        void getResultErreur(Exception exception);
    }
}
