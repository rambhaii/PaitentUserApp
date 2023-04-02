package com.doc24x7.Dashbord.ui;

import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Dashbord.dto.GalleryListResponse;
import com.doc24x7.R;
import com.doc24x7.Utils.FragmentActivityMessage;
import com.doc24x7.Utils.GlobalBus;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.gson.Gson;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

public class SearchDoctorActivity extends AppCompatActivity implements View.OnClickListener {

    EditText searchghazal;

     StaggeredGridLayoutManager mLayoutManager;
     Loader loader;


    RecyclerView recycler_view;

    GalleryListResponse transactions;
    ArrayList<Datum> transactionsObjects = new ArrayList<>() ;
    DoctorListDashboadAdapter mAdapter;
    ImageView imagedodeta;
    private GpsTracker gpsTracker;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);


        Gertid();

        try {
            if (ContextCompat.checkSelfPermission(getApplicationContext(), android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED ) {
                ActivityCompat.requestPermissions(this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 101);


                getLocation();
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        getLocation();


    }

    public void getLocation(){
        gpsTracker = new GpsTracker(SearchDoctorActivity.this);
        if(gpsTracker.canGetLocation()){
            double latitude = gpsTracker.getLatitude();
            double longitude = gpsTracker.getLongitude();

            HitAPi(String.valueOf(latitude),String.valueOf(longitude));


          //  Toast.makeText(this, ""+ String.valueOf(latitude), Toast.LENGTH_SHORT).show();

          //  tvLatitude.setText(String.valueOf(latitude));
           // tvLongitude.setText(String.valueOf(longitude));

        }else{
            gpsTracker.showSettingsAlert();
        }
    }


    private void Gertid() {

        imagedodeta=findViewById(R.id.imagedodeta);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Doctor Search");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });



        recycler_view=findViewById(R.id.recycler_view);

        searchghazal=findViewById(R.id.searchghazal);
        searchghazal.setOnClickListener(this);

        recycler_view.setVisibility(View.GONE);
        imagedodeta.setVisibility(View.VISIBLE);

       // HitAPi();

      //  UtilMethods.INSTANCE.FetchDrByName(SearchDoctorActivity.this,searchghazal.getText().toString().trim());


        searchghazal.addTextChangedListener(new TextWatcher() {

            @Override
            public void afterTextChanged(Editable s) {}

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {


            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if(s.length() != 0){

                    Log.e("Createghazalpage",""+ s +"   start   :  "+ start+"   before   :  "+ before+"   count   :  "+ count);

                    String newText=""+s;

                    Log.e("query",newText);
                    newText=newText.toLowerCase();
                    ArrayList<Datum> newlist=new ArrayList<>();
                    for(Datum op:transactionsObjects)
                    {
                        String getName = null;
                        getName =""+op.getName().toLowerCase()  ;


                        if(getName.contains(newText)){
                            recycler_view.setVisibility(View.VISIBLE);
                            newlist.add(op);



                        }
                    }
                    mAdapter.filter(newlist);




                  //  UtilMethods.INSTANCE.FetchDrByName(SearchDoctorActivity.this,searchghazal.getText().toString().trim(),"","");


                }else {


                    imagedodeta.setVisibility(View.GONE);
                }

            }
        });

    }

    private void HitAPi(String latitude,String longitude) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(SearchDoctorActivity.this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.FetchDrByName(SearchDoctorActivity.this,searchghazal.getText().toString().trim(),latitude,longitude,loader);

        } else {

            UtilMethods.INSTANCE.NetworkError(SearchDoctorActivity.this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }



    }


    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getFrom().equalsIgnoreCase("createghazal")) {

            dataParse(activityFragmentMessage.getMessage());

        }
    }

    public void dataParse(String response) {

        Gson gson = new Gson();
        transactions = gson.fromJson(response, GalleryListResponse.class);
        transactionsObjects = transactions.getData();

        if (transactionsObjects.size() > 0) {
            mAdapter = new DoctorListDashboadAdapter(transactionsObjects, this);
            mLayoutManager = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
            recycler_view.setLayoutManager(mLayoutManager);
            recycler_view.setItemAnimator(new DefaultItemAnimator());
            recycler_view.setAdapter(mAdapter);
            recycler_view.setVisibility(View.VISIBLE);
             imagedodeta.setVisibility(View.GONE);
        } else {
            recycler_view.setVisibility(View.GONE);
         }

    }

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }




    @Override
    public void onClick(View view) {


    }



}