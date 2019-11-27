package io.cogitech.healthclick.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.Gson;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Scanner;

import io.cogitech.healthclick.Activity.Manager.PharmacieListViewAdapter;
import io.cogitech.healthclick.Model.Pharmacie;
import io.cogitech.healthclick.Model.PharmacieGarde;
import io.cogitech.healthclick.R;


public class Contacts extends AppCompatActivity implements SearchView.OnQueryTextListener, PharmacieListAsynctask.GetResult {

    private SearchView editsearch;
    private ListView list;
    private PharmacieListViewAdapter adapter;
    private ArrayList<Pharmacie> pharmacies = new ArrayList<Pharmacie>();
    private ArrayList<PharmacieGarde> pharmaciesGardes = new ArrayList<PharmacieGarde>();
    private Gson gson = new Gson();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Contacts.this, Menu.class));
                finish();
            }
        });
        new PharmacieListAsynctask(this, this).execute();

        list = findViewById(R.id.pharmacies);
        adapter = new PharmacieListViewAdapter(this, pharmacies);
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        editsearch.clearFocus();

    }

    public String inputStreamToString(InputStream inputStream) {
        Scanner sc = new Scanner(inputStream);
        StringBuffer sb = new StringBuffer();
        while (sc.hasNext()) {
            sb.append(sc.nextLine());
        }
        return sb.toString();
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        String text = query;
        adapter.filter(text);
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    @Override
    public void data(ArrayList<Pharmacie> pharmacies) {
        adapter = new PharmacieListViewAdapter(this, pharmacies);
        list.setAdapter(adapter);
    }

    @Override
    public void getResultErreur(Exception exception) {

    }
}
