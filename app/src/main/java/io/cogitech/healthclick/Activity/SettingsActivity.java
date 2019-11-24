package io.cogitech.healthclick.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import io.cogitech.healthclick.MonOrm.CRUDImp;
import io.cogitech.healthclick.R;

public class SettingsActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private CRUDImp repository = new CRUDImp();

    private Integer ageVal = 0;
    private String sexeVal = "male";
    private String langueVal = "fr-fr";

    private String[] sexeList = {"male", "female"};
    private String[] sexeListVal = {"masculin", "feminin"};
    private String[] langueList = {"fr-fr", "en-gb", "es-es", "de-ch"};
    private String[] langueListval = {"francais", "anglais", "espagnol", "allemand"};
    private TextView defaultValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings_activity);

        defaultValue = findViewById(R.id.default_values);

        defaultValue.setText("age = " + repository.find(this, "age") + " , sexe = " + repository.find(this, "sexe") + " , langue = " + repository.find(this, "langue"));
        final Button save = findViewById(R.id.save);
        save.setEnabled(false);

        final Spinner sexe = findViewById(R.id.sexe);
        sexe.setOnItemSelectedListener(SettingsActivity.this);
        ArrayAdapter sexeArrayAdapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_spinner_item, sexeListVal);
        sexeArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
        sexe.setAdapter(sexeArrayAdapter);

        final Spinner langue = findViewById(R.id.langeu);
        langue.setOnItemSelectedListener(SettingsActivity.this);
        ArrayAdapter langueArrayAdapter = new ArrayAdapter(SettingsActivity.this, android.R.layout.simple_spinner_item, langueListval);
        langueArrayAdapter.setDropDownViewResource(R.layout.spinner_item);

        langue.setAdapter(langueArrayAdapter);

        sexe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                sexeVal = sexeList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
/*
                sexeVal = sexeList[0] ;
*/
            }
        });


        langue.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                langueVal = langueList[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
/*
                langueVal = langueList[0] ;
*/
            }
        });


        EditText age = findViewById(R.id.age);
        age.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() <= 0) {
                    save.setEnabled(false);
                } else {
                    ageVal = Integer.parseInt(s.toString());
                    save.setEnabled(true);

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }

        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                repository.save(SettingsActivity.this, "age", "" + ageVal);
                repository.save(SettingsActivity.this, "sexe", sexeVal);
                repository.save(SettingsActivity.this, "langue", langueVal);

                Toast.makeText(SettingsActivity.this, "enregistre", Toast.LENGTH_SHORT).show();

                defaultValue.setText("age = " + repository.find(SettingsActivity.this, "age") + " , sexe = " + repository.find(SettingsActivity.this, "sexe") + " , langue = " + repository.find(SettingsActivity.this, "langue"));

                startActivity(new Intent(SettingsActivity.this, Menu.class));
                finish();
            }
        });

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingsActivity.this, Menu.class));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }
}