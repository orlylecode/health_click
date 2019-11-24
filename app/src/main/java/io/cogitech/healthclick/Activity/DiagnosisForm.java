package io.cogitech.healthclick.Activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.ArrayList;

import io.cogitech.healthclick.Activity.Manager.DiagnosisListViewAdapter;
import io.cogitech.healthclick.Activity.Manager.PrefManager;
import io.cogitech.healthclick.Activity.Manager.SymptomListViewAdapter;
import io.cogitech.healthclick.Model.Diagnosis;
import io.cogitech.healthclick.Model.Info;
import io.cogitech.healthclick.Model.Symptom;
import io.cogitech.healthclick.MonOrm.CRUDImp;
import io.cogitech.healthclick.R;
import io.cogitech.healthclick.asynctask.CONFIG;
import io.cogitech.healthclick.asynctask.METHOD;
import io.cogitech.healthclick.asynctask.get;

public class DiagnosisForm extends AppCompatActivity implements SearchView.OnQueryTextListener, AdapterView.OnItemSelectedListener, get.GetResult {
    private static final Gson gson = new Gson();
    public SymptomListViewAdapter adapter;
    public SymptomListViewAdapter adapter1;
    public DiagnosisListViewAdapter adapter2;
    public ArrayList<Symptom> symptomes = new ArrayList<Symptom>();
    public ArrayList<Symptom> symptomes1 = new ArrayList<Symptom>();
    public Integer ageVal = 19;
    public String sexeVal = "male";
    public String langueVal = "fr-fr";
    public Info info;
    public EditText age;
    Intent home;
    private ViewPager viewPager;
    private MyViewPagerAdapter myViewPagerAdapter;
    private LinearLayout dotsLayout;
    private TextView[] dots;
    private int[] layouts;
    private Button btnSkip, btnNext;
    private PrefManager prefManager;
    private ListView list;
    private ListView list1;
    private ListView list2;
    private SearchView editsearch;
    private SearchView editsearch1;
    private SearchView editsearch2;
    private ArrayList<Diagnosis> diagnosis = new ArrayList<Diagnosis>();
    private String[] sexeList = {"male", "female"};
    private String[] sexeListVal = {"masculin", "feminin"};
    private String[] langueList = {"fr-fr", "en-gb", "es-es", "de-ch"};
    private String[] langueListval = {"francais", "anglais", "espagnol", "allemand"};
    private CRUDImp repository = new CRUDImp();
    private String text = "";
    private ArrayList<Integer> selectedSymptomes = new ArrayList<>();
    private int page = 0;
    //  viewpager change listener
    ViewPager.OnPageChangeListener viewPagerPageChangeListener = new ViewPager.OnPageChangeListener() {

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);

            if (position == 0) {
                page = position;

                final Spinner sexe = findViewById(R.id.sexe);
                sexe.setOnItemSelectedListener(DiagnosisForm.this);
                ArrayAdapter sexeArrayAdapter = new ArrayAdapter(viewPager.getContext(), android.R.layout.simple_spinner_item, sexeListVal);
                sexeArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                sexe.setAdapter(sexeArrayAdapter);

                final Spinner langue = findViewById(R.id.langeu);
                langue.setOnItemSelectedListener(DiagnosisForm.this);
                ArrayAdapter langueArrayAdapter = new ArrayAdapter(viewPager.getContext(), android.R.layout.simple_spinner_item, langueListval);
                langueArrayAdapter.setDropDownViewResource(R.layout.spinner_item);
                langue.setAdapter(langueArrayAdapter);

                sexe.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        sexeVal = sexeList[position];
                        repository.save(viewPager.getContext(), "sexe", sexeVal);
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
                        repository.save(viewPager.getContext(), "langue", langueVal);
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
                        ageVal = Integer.parseInt(s.toString());
                        repository.save(viewPager.getContext(), "age", "" + ageVal);
                    }

                    @Override
                    public void afterTextChanged(Editable s) {

                    }

                });
                Log.e("test", "onPageSelected: " + ageVal + " " + sexeVal + " " + langueVal);
            }


            if (position == 1) {
                text = "";
                ////////////////////////////////////////////Affichages/////////////////////////
                page = position;

                String url = CONFIG.URL + "symptoms?token=" + repository.find(viewPager.getContext(), "token") + "&format=json&language=" + repository.find(viewPager.getContext(), "langue");

                new get(METHOD.HTTPS_GET, url, "", "", "", DiagnosisForm.this, viewPager.getContext()).execute();

                adapter = null;
                list = null;
                editsearch = null;

                // Locate the ListView in listview_main.xml
                list = findViewById(R.id.list1);

                // Pass results to ListViewAdapter Class
                adapter = new SymptomListViewAdapter(viewPager.getContext(), symptomes);

                // Binds the Adapter to the ListView
                list.setAdapter(adapter);

                // Locate the EditText in listview_main.xml
                editsearch = findViewById(R.id.search1);
                editsearch.setOnQueryTextListener(DiagnosisForm.this);

                list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (selectedSymptomes.contains(symptomes.get(position).getID())) {
                            selectedSymptomes.remove(symptomes.get(position).getID());
                            Toast.makeText(DiagnosisForm.this, "Symptom removed", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedSymptomes.add(symptomes.get(position).getID());
                            Toast.makeText(DiagnosisForm.this, "Symptom added", Toast.LENGTH_SHORT).show();

                        }
                        adapter.setAddedList(symptomes.get(position).getID(), text);
                    }
                });

                editsearch.clearFocus();

                ////////////////////////////////////////////Affichages/////////////////////////
            }
            if (position == 2) {
                text = "";
                page = position;
                ////////////////////////////////////////////Affichages/////////////////////////
                String url = CONFIG.URL + "symptoms/proposed?symptoms=" + selectedSymptomes.toString() + "&gender=" + info.getGender() + "&year_of_birth=" + info.getYear_of_birth() + "&token=" + repository.find(viewPager.getContext(), "token") + "&format=json&language=" + info.getLanguage();

                new get(METHOD.HTTPS_GET, url, "", "", "", DiagnosisForm.this, viewPager.getContext()).execute();

                adapter1 = null;
                list1 = null;
                editsearch1 = null;
                // Locate the ListView in listview_main.xml
                list1 = findViewById(R.id.list2);

                // Pass results to ListViewAdapter Class
                adapter1 = new SymptomListViewAdapter(viewPager.getContext(), symptomes1);

                // Binds the Adapter to the ListView
                list1.setAdapter(adapter1);

                // Locate the EditText in listview_main.xml
                editsearch1 = findViewById(R.id.search2);
                editsearch1.setOnQueryTextListener(DiagnosisForm.this);

                list1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        if (selectedSymptomes.contains(symptomes1.get(position).getID())) {
                            selectedSymptomes.remove(symptomes1.get(position).getID());
                            Toast.makeText(DiagnosisForm.this, "Symptom removed", Toast.LENGTH_SHORT).show();
                        } else {
                            selectedSymptomes.add(symptomes1.get(position).getID());
                            Toast.makeText(DiagnosisForm.this, "Symptom added", Toast.LENGTH_SHORT).show();

                        }
                        adapter1.setAddedList(symptomes1.get(position).getID(), text);
                    }
                });

                editsearch1.clearFocus();

                ////////////////////////////////////////////Affichages/////////////////////////
            }


            if (position == 3) {
                text = "";
                page = position;
                ////////////////////////////////////////////Affichages/////////////////////////
                String url = CONFIG.URL + "diagnosis?symptoms=" + selectedSymptomes.toString() + "&gender=" + info.getGender() + "&year_of_birth=" + info.getYear_of_birth() + "&token=" + repository.find(viewPager.getContext(), "token") + "&format=json&language=" + info.getLanguage();

                new get(METHOD.HTTPS_GET, url, "", "", "", DiagnosisForm.this, viewPager.getContext()).execute();

                adapter2 = null;
                list2 = null;
                editsearch2 = null;
                // Locate the ListView in listview_main.xml
                list2 = findViewById(R.id.list3);

                // Pass results to ListViewAdapter Class
                adapter2 = new DiagnosisListViewAdapter(viewPager.getContext(), diagnosis);

                // Binds the Adapter to the ListView
                list2.setAdapter(adapter2);

                // Locate the EditText in listview_main.xml
                editsearch2 = findViewById(R.id.search3);
                editsearch2.setOnQueryTextListener(DiagnosisForm.this);

                list2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent cart = new Intent(DiagnosisForm.this, DiagnosisCart.class);
                        cart.putExtra("diagnosis", diagnosis.get(position));
                        Log.e("send", "onItemClick: " + diagnosis.get(position).getIssue().getIcdName());
                        startActivity(cart);
                    }
                });

                editsearch2.clearFocus();

                ////////////////////////////////////////////Affichages/////////////////////////
            }
            // changing the next button text 'NEXT' / 'GOT IT'
            switch (position) {

                case 0:
                    page = position;
                    // still pages are left
                    btnNext.setText(getString(R.string.next));
                    btnSkip.setVisibility(View.VISIBLE);
                    break;

                case 1:
                    page = position;
                    // still pages are left
                    btnNext.setText(getString(R.string.next));
                    btnSkip.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    page = position;
                    // still pages are left
                    btnNext.setText("DIAGNOSIS");
                    btnSkip.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    page = position;
                    info.setSymptoms(selectedSymptomes);
                    // last page. make button text to GOT IT
                    btnNext.setText("HOME");
                    btnSkip.setVisibility(View.GONE);
                    break;
                default:// still pages are left
                    page = -1;
                    btnNext.setText(getString(R.string.next));
                    btnSkip.setVisibility(View.VISIBLE);
            }

        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
            info.setGender(sexeVal);
            info.setLanguage(langueVal);
            info.setYear_of_birth(ageVal);

            Log.e("test", "onPageSelected: " + info.getYear_of_birth() + info.getGender() + info.getLanguage());
            Log.e("test", "onPageScrolled: ");
        }

        @Override
        public void onPageScrollStateChanged(int arg0) {
            Log.e("test", "onPageScrollStateChanged: ");
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_diagnosis_form);

        prefManager = new PrefManager(this);
        info = new Info();

        if (repository.find(this, "age") != "") {
            ageVal = Integer.parseInt(repository.find(this, "age"));
            sexeVal = repository.find(this, "sexe");
            langueVal = repository.find(this, "langue");
        }

        final Intent home = new Intent(this, Menu.class);

        viewPager = findViewById(R.id.view_pager);
        dotsLayout = findViewById(R.id.layoutDots);
        btnSkip = findViewById(R.id.btn_skip);
        btnNext = findViewById(R.id.btn_next);


        // layouts of all welcome sliders
        // add few more layouts if you want
        layouts = new int[]{
                R.layout.form1,
                R.layout.form2,
                R.layout.form3,
                R.layout.form4};

        // adding bottom dots
        addBottomDots(0);

        // making notification bar transparent
        changeStatusBarColor();

        myViewPagerAdapter = new MyViewPagerAdapter();
        viewPager.setAdapter(myViewPagerAdapter);
        viewPager.addOnPageChangeListener(viewPagerPageChangeListener);

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchHomeScreen();
            }
        });

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // checking for last page
                // if last page home screen will be launched
                int current = getItem(+1);
                if (current < layouts.length) {
                    // move to next screen
                    viewPager.setCurrentItem(current);

                } else {
                    launchHomeScreen();
                }
            }
        });
    }

    private void addBottomDots(int currentPage) {
        dots = new TextView[layouts.length];

        int[] colorsActive = getResources().getIntArray(R.array.array_dot_active);
        int[] colorsInactive = getResources().getIntArray(R.array.array_dot_inactive);

        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorsInactive[currentPage]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[currentPage].setTextColor(colorsActive[currentPage]);
    }

    private int getItem(int i) {
        return viewPager.getCurrentItem() + i;
    }

    private void launchHomeScreen() {
        prefManager.setFirstTimeLaunch(false);

        startActivity(new Intent(DiagnosisForm.this, Menu.class));
        finish();
    }

    /**
     * Making notification bar transparent
     */
    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    @Override
    public void get(String terminal) {
        if (terminal == null) {
            startActivity(new Intent(this, Error.class));
        } else {
            switch (page) {
                case 0:
                    break;
                case 1:
                    symptomes = gson.fromJson(terminal, new TypeToken<ArrayList<Symptom>>() {
                    }.getType());
                    adapter = new SymptomListViewAdapter(viewPager.getContext(), symptomes);
                    list.setAdapter(adapter);
                    break;
                case 2:
                    symptomes1 = gson.fromJson(terminal, new TypeToken<ArrayList<Symptom>>() {
                    }.getType());
                    adapter1 = new SymptomListViewAdapter(viewPager.getContext(), symptomes1);
                    list1.setAdapter(adapter1);
                    break;
                case 3:
                    diagnosis = gson.fromJson(terminal, new TypeToken<ArrayList<Diagnosis>>() {
                    }.getType());
                    adapter2 = new DiagnosisListViewAdapter(viewPager.getContext(), diagnosis);
                    list2.setAdapter(adapter2);
                    break;

                default:
                    break;

            }
        }

    }

    @Override
    public void getResultErreur(Exception exception) {
        startActivity(new Intent(this, Error.class));
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        text = newText;
        if (adapter != null) {
            adapter.filter(text);
        }
        if (adapter1 != null) {
            adapter1.filter(text);
        }
        if (adapter2 != null) {
            adapter2.filter(text);
        }
        return false;
    }

    @Override
    public void onPointerCaptureChanged(boolean hasCapture) {

    }

    /**
     * View pager adapter
     */
    private class MyViewPagerAdapter extends PagerAdapter {
        private LayoutInflater layoutInflater;

        public MyViewPagerAdapter() {
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);

            View view = layoutInflater.inflate(layouts[position], container, false);
            container.addView(view);

            return view;
        }

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public boolean isViewFromObject(View view, Object obj) {
            return view == obj;
        }


        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View view = (View) object;
            container.removeView(view);
        }
    }
}