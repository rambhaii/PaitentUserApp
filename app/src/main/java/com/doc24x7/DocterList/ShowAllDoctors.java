package com.doc24x7.DocterList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Dashbord.dto.GalleryListResponse;
import com.doc24x7.Dashbord.ui.DashboardMainNew;
import com.doc24x7.Dashbord.ui.DoctorListDashboadAdapter;
import com.doc24x7.Dashbord.ui.GpsTracker;
import com.doc24x7.Dashbord.ui.LanguageManager;

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

public class ShowAllDoctors extends AppCompatActivity implements View.OnClickListener {
    Spinner spspeciality;

    Loader loader;
    EditText serchtext;
    soup.neumorphism.NeumorphCardView neowidget,neospeciality;
    ImageView filter,searchic,cunsultimg,callimg,iv_clear;
    ArrayList<String> SpecialityList = new ArrayList<String>();
    ArrayList<String> SpecialityId = new ArrayList<String>();
    ArrayList<String> SpecialityImageList = new ArrayList<String>();
    String specialityId;
    LinearLayout alltab;
    private GpsTracker gpsTracker;
    LinearLayoutManager mLayoutManager;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    RecyclerView recycler_view, recycler_view_doctor,recycler_selected_doctor, recycler_notavilable,recycler_onlinedoctors,recycler_onlinedoctorscategory;
    DoctorListDashboadAdapter doctormAdapter;
    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>();
    LanguageManager languageManager;

    ImageView book_img,language_img,shownotification;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_all_doctors);
        spspeciality=findViewById(R.id.spspecility);
        book_img=findViewById(R.id.book_img);
        book_img.setOnClickListener(this);
        language_img=findViewById(R.id.language_img);
        shownotification=findViewById(R.id.shownotification);
        language_img.setOnClickListener(this);
        shownotification.setOnClickListener(this);
        language_img.setOnClickListener(this);

        searchic=findViewById(R.id.searchicon);
        iv_clear=findViewById(R.id.iv_clear);
        searchic.setOnClickListener(this);
        neowidget=findViewById(R.id.neowidget);
        neospeciality=findViewById(R.id.neospeciality);
        recycler_view_doctor = findViewById(R.id.recycler_view_doctor);
        recycler_selected_doctor=findViewById(R.id.recyclerselecteddoctor);
        languageManager=new LanguageManager(this);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        iv_clear.setOnClickListener(this);
        serchtext =findViewById(R.id.serchtext);
        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
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
        SharedPreferences sharedPreferences=getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, this.MODE_PRIVATE);
        if(sharedPreferences!=null)
        {
            dataParse(sharedPreferences.getString(ApplicationConstant.INSTANCE.allCategoryDoctor,"").toString());
        }
    }
    public  void filters(String txt){
        ArrayList<Datum> filtterdata=new ArrayList<>();
        for(Datum item: sliderLists)
        {
            if(item.getName().toLowerCase().contains(txt.toLowerCase())||item.getId().toLowerCase().contains(txt.toLowerCase()))
            {
                filtterdata.add(item);
            }
        }
        doctormAdapter.filter(filtterdata);
    }
    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("alldoctorsondash")) {
            dataParsedoctoronBook(activityFragmentMessage.getFrom());

        }

        else if (activityFragmentMessage.getMessage().equalsIgnoreCase("doctorlist")) {

            dataParsedoctor(activityFragmentMessage.getFrom());

        }

    }

    public void dataParse(String response) {
        SpecialityList.add("All");
        SpecialityId.add("0");
        SpecialityImageList.add("noimage");
        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            if (sliderLists != null && sliderLists.size() > 0) {
                for (int i = 0; i < sliderLists.size(); i++) {
                    SpecialityList.add(sliderLists.get(i).getName());
                    SpecialityId.add(sliderLists.get(i).getId());
                    SpecialityImageList.add(sliderLists.get(i).getIcon());

                }
            }
            spspeciality.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                    Log.e("spinner", "  position   " + position + "  ,  id  " + id);
//                    if (parent.getItemAtPosition(position).toString().equals("All")) {
//                        getLocationdddd();
//                    } else {
//                        specialityId = SpecialityId.get(position);
//                        UtilMethods.INSTANCE.Doctors(ShowAllDoctors.this,specialityId,loader);
//                    }
                    if (SpecialityList.get(position).equals("All")) {
                        getLocationdddd();
                    } else {
                        specialityId = SpecialityId.get(position);
                        UtilMethods.INSTANCE.Doctors(ShowAllDoctors.this,specialityId,loader);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            CustomAdapter customAdapter=new CustomAdapter(ShowAllDoctors.this,SpecialityImageList,SpecialityList);
            spspeciality.setAdapter(customAdapter);

        }

//
    }
    public void dataParsedoctoronBook(String response) {
        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            doctormAdapter = new DoctorListDashboadAdapter(sliderLists, this);
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
            recycler_selected_doctor.setLayoutManager(staggeredGridLayoutManager);
            recycler_selected_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_selected_doctor.setAdapter(doctormAdapter);
            recycler_selected_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_selected_doctor.setVisibility(View.GONE);
        }

    }
    public void dataParsedoctor(String response) {


        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            doctormAdapter = new DoctorListDashboadAdapter(sliderLists, this);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view_doctor.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
            recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_view_doctor.setAdapter(doctormAdapter);

            recycler_view_doctor.setVisibility(View.GONE);
            //  recycler_view_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_view_doctor.setVisibility(View.GONE);
        }

    }
    public void getLocationdddd(){
        gpsTracker = new GpsTracker(getApplicationContext());
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();
            HitAPi(String.valueOf(latitude),String.valueOf(longitude));
        }else{
            gpsTracker.showSettingsAlert();
        }
    }

    private void HitAPi(String latitude,String longitude) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.FetchDrByName(this,"",latitude,longitude,loader);

        } else {

            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }



    }

    @Override
    public void onClick(View v) {
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

        if (v==book_img)
        {
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.allReciept(ShowAllDoctors.this,loader);
        }
        if (v==language_img)
        {
            UtilMethods.INSTANCE.chooseLanguage(ShowAllDoctors.this,getIntent());
        }
        if (v==shownotification)
        {
            UtilMethods.INSTANCE.notification(ShowAllDoctors.this);
        }




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
}