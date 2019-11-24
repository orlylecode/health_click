package io.cogitech.healthclick.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import io.cogitech.healthclick.Activity.Manager.MaladiesListViewAdapter;
import io.cogitech.healthclick.Model.Issue;
import io.cogitech.healthclick.MonOrm.CRUDImp;
import io.cogitech.healthclick.R;
import io.cogitech.healthclick.asynctask.CONFIG;
import io.cogitech.healthclick.asynctask.METHOD;
import io.cogitech.healthclick.asynctask.get;

public class MaladieList extends AppCompatActivity implements SearchView.OnQueryTextListener, get.GetResult {
    private static final Gson gson = new Gson();
    public ArrayList<Issue> maladies_list = new ArrayList<Issue>();
    Intent home;
    private CRUDImp repository = new CRUDImp();
    private String url = "";
    private ListView list;
    private MaladiesListViewAdapter adapter;
    private SearchView editsearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maladie_list);
        url = CONFIG.URL + "issues?token=" + repository.find(this, "token") + "&format=json&language=" + repository.find(this, "langue");

        new get(METHOD.HTTPS_GET, url, "", "", "", this, this).execute();
        home = new Intent(this, Menu.class);

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(home);
                finish();
            }
        });

/*        Issue issue = new Issue();
        issue.setID(1);
        issue.setName("No name");

        maladies_list.add(issue);*/

        // Locate the ListView in listview_main.xml
        list = findViewById(R.id.maladies_list);

        // Pass results to ListViewAdapter Class
        adapter = new MaladiesListViewAdapter(this, maladies_list);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent maladie = new Intent(MaladieList.this, Maladies.class);
                maladie.putExtra("maladie", (int) maladies_list.get(position).getID());
                startActivity(maladie);
            }
        });

        editsearch.clearFocus();

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        String text = newText;
        adapter.filter(text);
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    @Override
    public void get(String terminal) {
        if (terminal == null) {
            startActivity(new Intent(this, Error.class));

        } else {
            Log.e("TAG", "get: " + terminal);
            maladies_list = gson.fromJson(terminal, new TypeToken<ArrayList<Issue>>() {
            }.getType());
            // Pass results to ListViewAdapter Class
            adapter = new MaladiesListViewAdapter(this, maladies_list);
            // Binds the Adapter to the ListView
            list.setAdapter(adapter);
        }

    }

    @Override
    public void getResultErreur(Exception exception) {
        startActivity(new Intent(this, Error.class), ActivityOptionsCompat.makeSceneTransitionAnimation(this).toBundle());
    }
}
