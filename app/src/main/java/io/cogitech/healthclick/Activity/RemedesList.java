package io.cogitech.healthclick.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.SearchView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;

import io.cogitech.healthclick.Activity.Manager.RemedesListViewAdapter;
import io.cogitech.healthclick.Model.Remede;
import io.cogitech.healthclick.Model.RemedeComparator;
import io.cogitech.healthclick.R;

public class RemedesList extends AppCompatActivity implements SearchView.OnQueryTextListener, RemedeListAsynctask.GetResult {
    public ArrayList<Remede> remedes_list = new ArrayList<Remede>();
    Intent home;
    private ListView list;
    private RemedesListViewAdapter adapter;
    private SearchView editsearch;
    private ProgressDialog mProgressDialog;

    /*
        private Gson gson = new Gson();
    */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_remedes_list);
        home = new Intent(this, Menu.class);

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(home);
                finish();
            }
        });

        new RemedeListAsynctask(this, this).execute();

        Collections.sort(remedes_list, new RemedeComparator());
        list = findViewById(R.id.remedes_list);
        adapter = new RemedesListViewAdapter(this, remedes_list);
        list.setAdapter(adapter);
        list.setTextFilterEnabled(true);

        // Locate the EditText in listview_main.xml
        editsearch = findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

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
/*

    public String inputStreamToString(InputStream inputStream){
        Scanner sc = new Scanner(inputStream);
        StringBuffer sb = new StringBuffer();
        while(sc.hasNext()){
            sb.append(sc.nextLine());
        }
        return sb.toString();
    }
*/

    @Override
    public void data(ArrayList<Remede> remedes_list) {
        Collections.sort(remedes_list, new RemedeComparator());
        adapter = new RemedesListViewAdapter(this, remedes_list);
        list.setAdapter(adapter);
    }

    @Override
    public void getResultErreur(Exception exception) {

    }
}
