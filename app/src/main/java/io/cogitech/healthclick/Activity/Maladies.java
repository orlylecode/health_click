package io.cogitech.healthclick.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.google.gson.Gson;

import io.cogitech.healthclick.Model.IssueInfo;
import io.cogitech.healthclick.MonOrm.CRUDImp;
import io.cogitech.healthclick.R;
import io.cogitech.healthclick.asynctask.CONFIG;
import io.cogitech.healthclick.asynctask.METHOD;
import io.cogitech.healthclick.asynctask.get;

public class Maladies extends AppCompatActivity implements get.GetResult {
    private static final Gson gson = new Gson();
    private TextView nom;
    private TextView description;
    private TextView condition;
    private TextView symptome;
    private TextView traitement;
    private IssueInfo issueInfo;
    private Intent home;
    private String url = "";
    private CRUDImp repository = new CRUDImp();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maladies);

        int issuId = getIntent().getIntExtra("maladie", 0);
        issueInfo = new IssueInfo();

        url = CONFIG.URL + "issues/" + issuId + "/info?token=" + repository.find(this, "token") + "&format=json&language=" + repository.find(this, "langue");

        nom = findViewById(R.id.name_content);
        description = findViewById(R.id.description_content);
        condition = findViewById(R.id.condition_content);
        symptome = findViewById(R.id.symptome_content);
        traitement = findViewById(R.id.traitement_content);

        new get(METHOD.HTTPS_GET, url, "", "", "", this, this).execute();

        home = new Intent(this, Menu.class);


        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(home);
                finish();
            }
        });
    }

    @Override
    public void get(String terminal) {
        if (terminal == null) {
            startActivity(new Intent(this, Error.class));

        } else {
            Log.e("TAG", "get: " + terminal);
            issueInfo = gson.fromJson(terminal, IssueInfo.class);

            nom.setText(" Nom Scientifique : " + issueInfo.getProfName() + ".\n Nom courant : " + issueInfo.getName() + ".\n Synonyms : " + issueInfo.getSynonyms());
            description.setText(issueInfo.getDescriptionShort() + "\n\n\n." + issueInfo.getDescription());
            condition.setText(issueInfo.getMedicalCondition());
            symptome.setText(issueInfo.getSynonyms());
            traitement.setText(issueInfo.getTreatmentDescription());
        }

    }

    @Override
    public void getResultErreur(Exception exception) {
        startActivity(new Intent(this, Error.class), ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }
}
