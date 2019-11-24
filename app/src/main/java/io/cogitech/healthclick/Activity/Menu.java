package io.cogitech.healthclick.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.google.gson.Gson;
import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import Decoder.BASE64Encoder;
import io.cogitech.healthclick.Activity.Manager.PrefManager;
import io.cogitech.healthclick.Model.AccessToken;
import io.cogitech.healthclick.MonOrm.CRUDImp;
import io.cogitech.healthclick.R;
import io.cogitech.healthclick.asynctask.CONFIG;
import io.cogitech.healthclick.asynctask.METHOD;
import io.cogitech.healthclick.asynctask.get;

public class Menu extends AppCompatActivity implements get.GetResult, InternetConnectivityListener {

    private PrefManager prefManager;
    private boolean connect = false;
    private Gson gson = new Gson();
    private AccessToken accessToken = new AccessToken();
    private CRUDImp repository = new CRUDImp();
    private InternetAvailabilityChecker mInternetAvailabilityChecker;
    private RelativeLayout layout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        prefManager = new PrefManager(this);
        if (prefManager.isFirstTimeLaunch()) {
            startActivity(new Intent(this, Welcome.class), ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
        }

        InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);
        try {
            new get(METHOD.HTTPS_BEARER_AUT_POST, CONFIG.URL_LOGIN, "", CONFIG.USERNAME, hash(CONFIG.PASSWORD), this, this).execute();
        } catch (Exception e) {
            e.printStackTrace();
        }

        findViewById(R.id.aide).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Menu.this, Welcome.class), ActivityOptionsCompat.makeSceneTransitionAnimation(Menu.this).toBundle());
                    }
                });

        findViewById(R.id.diagnosis).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!connect) {
                            Toast.makeText(Menu.this, "Veillez vous connecter a internet", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(Menu.this, DiagnosisForm.class), ActivityOptionsCompat.makeSceneTransitionAnimation(Menu.this).toBundle());
                        }
                    }
                });

        findViewById(R.id.maladies).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!connect) {
                            Toast.makeText(Menu.this, "Veillez vous connecter a internet", Toast.LENGTH_SHORT).show();
                        } else {
                            startActivity(new Intent(Menu.this, MaladieList.class), ActivityOptionsCompat.makeSceneTransitionAnimation(Menu.this).toBundle());
                        }
                    }
                });

        findViewById(R.id.parametre).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Menu.this, SettingsActivity.class), ActivityOptionsCompat.makeSceneTransitionAnimation(Menu.this).toBundle());

                    }
                });

        findViewById(R.id.remedes).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Menu.this, RemedesList.class), ActivityOptionsCompat.makeSceneTransitionAnimation(Menu.this).toBundle());

                    }
                });

        findViewById(R.id.contact).
                setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startActivity(new Intent(Menu.this, Contacts.class), ActivityOptionsCompat.makeSceneTransitionAnimation(Menu.this).toBundle());
                    }
                });

        layout = findViewById(R.id.orly);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }

    @Override
    public void get(String terminal) {
        if (terminal != null) {
            accessToken = gson.fromJson(terminal, AccessToken.class);
            repository.save(Menu.this, "token", accessToken.Token);
        } else {
            Toast.makeText(this, "Veillez verifier votre connexion internet.", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void getResultErreur(Exception exception) {
/*
        startActivity(new Intent( this , Error.class) , ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle() );
*/
    }

    @Override
    public void onInternetConnectivityChanged(boolean isConnected) {
        this.connect = isConnected;
        if (isConnected) {
            try {
                layout.setBackgroundColor(Color.argb(100, 255, 255, 255));
            } catch (Exception e) {

            }
        } else {
            try {
                layout.setBackgroundColor(Color.argb(100, 203, 250, 166));
            } catch (Exception e) {

            }
        }
    }

    public String hash(String password) throws Exception {
        SecretKeySpec keySpec = new SecretKeySpec(
                password.getBytes(),
                "HmacMD5");

        String computedHashString = "";
        try {
            Mac mac = Mac.getInstance("HmacMD5");
            mac.init(keySpec);
            byte[] result = mac.doFinal(CONFIG.URL_LOGIN.getBytes());

            BASE64Encoder encoder = new BASE64Encoder();
            computedHashString = encoder.encode(result);

        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("Can not create token (NoSuchAlgorithmException)");
        } catch (InvalidKeyException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
            throw new Exception("Can not create token (InvalidKeyException)");
        }
        Log.e("test", "hash: " + computedHashString);
        return computedHashString;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInternetAvailabilityChecker != null) {
            mInternetAvailabilityChecker.removeInternetConnectivityChangeListener(this);
        }
    }
}
