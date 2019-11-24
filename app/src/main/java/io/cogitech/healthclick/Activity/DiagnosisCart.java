package io.cogitech.healthclick.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.treebo.internetavailabilitychecker.InternetAvailabilityChecker;
import com.treebo.internetavailabilitychecker.InternetConnectivityListener;

import java.util.ArrayList;
import java.util.List;

import io.cogitech.healthclick.Activity.Manager.SpecialisationListViewAdapter;
import io.cogitech.healthclick.Model.Diagnosis;
import io.cogitech.healthclick.Model.Specialisation;
import io.cogitech.healthclick.R;

public class DiagnosisCart extends AppCompatActivity implements SearchView.OnQueryTextListener, InternetConnectivityListener {

    public List<Specialisation> specialisations = new ArrayList<Specialisation>();
    Intent home;
    private ListView list;
    private SpecialisationListViewAdapter adapter;
    private SearchView editsearch;
    private Diagnosis diagnosis = new Diagnosis();
    private Boolean connect = false;
    private InternetAvailabilityChecker mInternetAvailabilityChecker;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_diagnosis_cart);

        LinearLayout detail = findViewById(R.id.details);
        detail.setVisibility(View.INVISIBLE);
        TextView summury = findViewById(R.id.summary);

        home = new Intent(this, Menu.class);

        findViewById(R.id.home).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(home);
            }
        });

        diagnosis = getIntent().getExtras().getParcelable("diagnosis");
        Log.e("recive", "onItemClick: " + diagnosis.getIssue().getIcdName());

        if (diagnosis != null) {
            if (diagnosis.getIssue() != null) {
                specialisations = diagnosis.getSpecialisation();
                detail.setVisibility(View.VISIBLE);
                summury.setText(" Name :" + diagnosis.getIssue().getName() + "\n Prof Name : " + diagnosis.getIssue().getProfName() + "\n Icd : " + diagnosis.getIssue().getIcdName() + "\n Accuracy : " + diagnosis.getIssue().getAccuracy());
            }

        } else {
            startActivity(new Intent(this, Error.class));
        }

        detail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (connect == true) {
                    Intent maladie = new Intent(DiagnosisCart.this, Maladies.class);
                    maladie.putExtra("maladie", (int) diagnosis.getIssue().getID());
                    startActivity(maladie);
                } else {
                    Toast.makeText(DiagnosisCart.this, "Veillez verifier votre connection internet", Toast.LENGTH_SHORT).show();
                }

            }
        });

        // Locate the ListView in listview_main.xml
        list = findViewById(R.id.list);

        // Pass results to ListViewAdapter Class
        adapter = new SpecialisationListViewAdapter(this, specialisations);

        // Binds the Adapter to the ListView
        list.setAdapter(adapter);

        // Locate the EditText in listview_main.xml
        editsearch = findViewById(R.id.search);
        editsearch.setOnQueryTextListener(this);

        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            }
        });


        editsearch.clearFocus();

        InternetAvailabilityChecker.init(this);
        mInternetAvailabilityChecker = InternetAvailabilityChecker.getInstance();
        mInternetAvailabilityChecker.addInternetConnectivityListener(this);
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
    public void onInternetConnectivityChanged(boolean isConnected) {
        this.connect = isConnected;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mInternetAvailabilityChecker != null) {
            mInternetAvailabilityChecker.removeInternetConnectivityChangeListener(this);
        }
    }
}
