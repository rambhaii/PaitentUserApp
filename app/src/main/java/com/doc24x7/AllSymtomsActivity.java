package com.doc24x7;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Dashbord.dto.GalleryListResponse;
import com.doc24x7.Dashbord.ui.ConsultdoctorActivity;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;
import com.google.gson.Gson;

import java.util.ArrayList;

public class AllSymtomsActivity extends AppCompatActivity implements View.OnClickListener {

    RecyclerView recycler_view;
    Loader loader;
    AllSymtomsAdapter mAdapter;

    GalleryListResponse sliderImage;
    ArrayList<Datum> operator = new ArrayList<>();
    android.widget.SearchView SearchView;
    TextView Final_Doctor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_searchnew);

        Final_Doctor=findViewById(R.id.Final_Doctor);
        Final_Doctor.setOnClickListener(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("");
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        getid();

    }

    private void getid() {

        recycler_view = findViewById(R.id.recycler_view);
        SearchView = findViewById(R.id.searchghazal);
        SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
        String str = prefs.getString(ApplicationConstant.INSTANCE.allsympton, "");
        getOperatorList(str);

    }

    public void getOperatorList(String response) {
        sliderImage = new Gson().fromJson(response, GalleryListResponse.class);
        operator = sliderImage.getData();

        if (operator != null && operator.size() > 0) {
            mAdapter = new AllSymtomsAdapter(operator, this);
            RecyclerView.LayoutManager mLayoutManager = new GridLayoutManager(this, 1);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
            SearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    if(operator.contains(query)){
                      String  newText = query.toLowerCase();
                        ArrayList<Datum> newlist = new ArrayList<>();
                        for (Datum op : operator) {
                            String getName = op.getSymtom().toLowerCase();
                            if (getName.contains(newText)) {
                                newlist.add(op);

                            }
                        }
                        mAdapter.filter(newlist);
                    }else{
                        Toast.makeText(AllSymtomsActivity.this, "No Match found",Toast.LENGTH_LONG).show();
                    }
                    return false;
                }
                @Override
                public boolean onQueryTextChange(String newText) {
                    newText = newText.toLowerCase();
                    ArrayList<Datum> newlist = new ArrayList<>();
                    for (Datum op : operator) {
                        String getName = op.getSymtom().toLowerCase();
                        if (getName.contains(newText)) {
                            newlist.add(op);

                        }
                    }
                    mAdapter.filter(newlist);
                    return false;
                }
            });
        } else {


        }


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.search_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onResume() {
        super.onResume();

    }

    ArrayList <String>Symtom_id = new ArrayList<String>();
    ArrayList <String>Symtom_text = new ArrayList<String>();

    public void ItemClick(String Id ,String Symtom) {


        Symtom_id.add(Id);

        Symtom_text.add(Symtom);


        // Toast.makeText(this, "  Id  :   "+Id+  "     Symtom  :    "+Symtom , Toast.LENGTH_SHORT).show();


    }

    @Override
    public void onClick(View view) {

        if(view==Final_Doctor){


            if(Symtom_id.size()!=0){

                Intent i=new Intent(this, ConsultdoctorActivity.class);
                i.putExtra("Symtom_id",""+Symtom_id);
                i.putExtra("Symtom_text",""+Symtom_text);
                startActivity(i);
            }else {

                Toast.makeText(this, "Select Symtom", Toast.LENGTH_SHORT).show();

            }



        }

    }
}