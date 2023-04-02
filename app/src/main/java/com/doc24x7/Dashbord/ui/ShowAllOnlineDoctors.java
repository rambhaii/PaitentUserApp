package com.doc24x7.Dashbord.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;

import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Dashbord.dto.GalleryListResponse;
import com.doc24x7.DocterList.CustomAdapter;

import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.FragmentActivityMessage;
import com.doc24x7.Utils.GlobalBus;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ShowAllOnlineDoctors extends AppCompatActivity implements View.OnClickListener {
    ArrayList<Datum> DataListspeciality = new ArrayList<>();
    ArrayList<Datum> DataList = new ArrayList<>();
    GalleryListResponse sliderImage;
    OnlineDoctorAdapter onlineDoctorAdapter;
    soup.neumorphism.NeumorphCardView neowidget,neospeciality;
    ImageView filter,searchic,cunsultimg,callimg,iv_clear;
    RecyclerView recycler_onlinedoctorscategory;
    Loader loader;
    String response="";
    EditText serchtext;
    Spinner spspeciality;
    ArrayList<String> SpecialityList = new ArrayList<String>();
    ArrayList<String> SpecialityId = new ArrayList<String>();
    ArrayList<String> SpecialityImageList = new ArrayList<String>();
    String specialityId;
    Button btnsort;
    ImageView book_img,language_img,shownotification;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_online_doctors);
        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
        iv_clear=findViewById(R.id.iv_clear);
        searchic=findViewById(R.id.searchicon);
        neowidget=findViewById(R.id.neowidget);
        neospeciality=findViewById(R.id.neospeciality);
        serchtext =findViewById(R.id.serchtext);
        book_img=findViewById(R.id.book_img);
        language_img=findViewById(R.id.language_img);
        shownotification=findViewById(R.id.shownotification);
        language_img.setOnClickListener(this);
        shownotification.setOnClickListener(this);
        book_img.setOnClickListener(this);

        searchic.setOnClickListener(this);
        iv_clear.setOnClickListener(this);
        response=getIntent().getStringExtra("response");
        recycler_onlinedoctorscategory=findViewById(R.id.recycler_onlinedoctors);
        spspeciality=findViewById(R.id.spspecility);
        recycler_onlinedoctorscategory=findViewById(R.id.recycler_onlinedoctors);
        SharedPreferences sharedPreferences=getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, this.MODE_PRIVATE);
        if(sharedPreferences!=null)
        {
            dataParse(sharedPreferences.getString(ApplicationConstant.INSTANCE.allCategoryDoctor,"").toString());
        }
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        DataList = sliderImage.getData();
        if (DataList.size() > 0) {
            onlineDoctorAdapter = new OnlineDoctorAdapter(DataList,this);
            recycler_onlinedoctorscategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
            recycler_onlinedoctorscategory.setItemAnimator(new DefaultItemAnimator());
             recycler_onlinedoctorscategory.setAdapter(onlineDoctorAdapter);
            recycler_onlinedoctorscategory.setVisibility(View.VISIBLE);
        } else {
            recycler_onlinedoctorscategory.setVisibility(View.GONE);
        }
        serchtext.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Toast.makeText(getContext(), "beforeTextChanged"+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                filters(s.toString());
                //  Toast.makeText(getContext(), "onTextChanged"+s, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {

                //   Toast.makeText(getContext(), "afterTextChanged"+s, Toast.LENGTH_SHORT).show();
            }
        });

    }
    

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {

    }
    private void dataParseonline(String response) {


    }
    public void dataParse(String response) {
        SpecialityList.add("All");
        SpecialityId.add("0");
        SpecialityImageList.add("noimage");
        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        DataListspeciality = sliderImage.getData();

        if (DataListspeciality.size() > 0) {
            if (DataListspeciality != null && DataListspeciality.size() > 0) {
                for (int i = 0; i < DataListspeciality.size(); i++) {
                    SpecialityList.add(DataListspeciality.get(i).getName());
                    SpecialityId.add(DataListspeciality.get(i).getId());
                    SpecialityImageList.add(DataListspeciality.get(i).getIcon());

                }
            }
            spspeciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("spinner", "  position   " + position + "  ,  id  " + id);
//                    if (parent.getItemAtPosition(position).toString().equals("All")) {
//                        //getLocationdddd();
//                        filters("");
//                    } else {
//                        specialityId = SpecialityId.get(position);
//                        filters(SpecialityList.get(position));
//                       // UtilMethods.INSTANCE.Doctors(ShowAllDoctors.this,specialityId,loader);
//                    }
                    if (SpecialityList.get(position).equals("All")) {
                      //  getLocationdddd();
                        filters("");
                    } else {
                        specialityId = SpecialityId.get(position);
                        filters(SpecialityList.get(position));
                       // UtilMethods.INSTANCE.Doctors(ShowAllOnlineDoctors.this,specialityId,loader);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            CustomAdapter customAdapter=new CustomAdapter(ShowAllOnlineDoctors.this,SpecialityImageList,SpecialityList);
            spspeciality.setAdapter(customAdapter);

        }

//
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sortlist, menu);
        return true;

    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        switch (id)
        {
            case R.id.low:
                Collections.sort(DataList, new Comparator<Datum>() {
                    @Override
                    public int compare(Datum o1, Datum o2) {
                        // return o1.getDoctor_fees().compareTo(o2.getDoctor_fees());
                        return Integer.valueOf(o1.getDoctor_fees()).compareTo(Integer.valueOf(o2.getDoctor_fees()));
                    }
                });
                onlineDoctorAdapter.notifyDataSetChanged();
                return true;
            case R.id.high:
                Collections.sort(DataList, new Comparator<Datum>() {
                    @Override
                    public int compare(Datum o1, Datum o2) {
                        // return o1.getDoctor_fees().compareTo(o2.getDoctor_fees());
                        return Integer.valueOf(o2.getDoctor_fees()).compareTo(Integer.valueOf(o1.getDoctor_fees()));
                    }
                });
                onlineDoctorAdapter.notifyDataSetChanged();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onResume() {
        super.onResume();


    }


    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    public void onClick(View v) {

          if (v==book_img)
          {
              loader.show();
              loader.setCancelable(false);
              loader.setCanceledOnTouchOutside(false);
              UtilMethods.INSTANCE.allReciept(ShowAllOnlineDoctors.this,loader);
          }
        if (v==language_img)
        {
            UtilMethods.INSTANCE.chooseLanguage(ShowAllOnlineDoctors.this,getIntent());
        }
        if (v==shownotification)
        {
            UtilMethods.INSTANCE.notification(ShowAllOnlineDoctors.this);
        }


        if (v == searchic) {
            neowidget.setVisibility(View.VISIBLE);
            neospeciality.setVisibility(View.GONE);
        }
        if (v == iv_clear) {
            neowidget.setVisibility(View.GONE);
            neospeciality.setVisibility(View.VISIBLE);
            serchtext.setText("");
            filters("");
        }
    }
    public  void filters(String txt){
        ArrayList<Datum> filtterdata=new ArrayList<>();
        for(Datum item: DataList)
        {
            if(item.getName().toLowerCase().contains(txt.toLowerCase())||item.getId().toLowerCase().contains(txt.toLowerCase())||item.getTypeName().toLowerCase().contains(txt.toLowerCase()))
            {
                filtterdata.add(item);
            }
        }
        onlineDoctorAdapter.filterss(filtterdata);
    }
}