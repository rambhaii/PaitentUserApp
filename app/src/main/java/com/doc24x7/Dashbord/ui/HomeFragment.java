package com.doc24x7.Dashbord.ui;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.SearchView;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;
import androidx.viewpager.widget.ViewPager;
import androidx.viewpager2.widget.CompositePageTransformer;
import androidx.viewpager2.widget.MarginPageTransformer;
import androidx.viewpager2.widget.ViewPager2;

import android.os.Handler;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doc24x7.AllSymtomsActivity;
import com.doc24x7.BuildConfig;
import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Dashbord.dto.GalleryListResponse;
import com.doc24x7.Dashbord.dto.SymptomData;
import com.doc24x7.DocterList.CustomAdapter;
import com.doc24x7.DocterList.ShowAllDoctors;
import com.doc24x7.FinadDoctorActivity;
import com.doc24x7.Login.dto.Data;
import com.doc24x7.Utils.ApplicationConstant;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.gson.Gson;
import com.doc24x7.R;
import com.doc24x7.Utils.FragmentActivityMessage;
import com.doc24x7.Utils.GlobalBus;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.content.Context.MODE_PRIVATE;

public class HomeFragment extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    private static final int REQUEST_EXTERNAL_STORAGE = 1;
    private static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
    };

   //String res="";
    CircleImageView userpic;
    EditText serchtext;
    soup.neumorphism.NeumorphCardView neowidget,neospeciality;
    TextView name, contentnumber,tvonlinedoctors,speciality;
    RecyclerView recycler_view, recycler_view_doctor,recycler_selected_doctor, recycler_notavilable,recycler_onlinedoctors,recycler_onlinedoctorscategory;
    DoctorListDashboadAdapter doctormAdapter;
    Loader loader;
    Button whatsappbtn;
    ImageView filter,searchic,cunsultimg,callimg,iv_clear;
      SearchView searchiv;
    private GpsTracker gpsTracker;
      ListView mylist;
      ArrayList<String> list;
      ArrayAdapter<String> adapter;
    LinearLayout errordeta;
    TextView balanceamount, Logout_tv,cunsulttxt,booktxt;
   ViewPager2 viewPager2;
    ViewPager mViewPager;
    Spinner spspeciality;
    LinearLayout dotsCount;
    Handler handler;
    private Handler sliderHandler=new Handler();
    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>();
    CustomPagerAdapter mCustomPagerAdapter;
    Integer mDotsCount;
    public static TextView mDotsText[];
    SpecialitiesAdapter mAdapter;
    OnlinedoctorsspecialitiesAdapter mOnlineAdapter;
    ArrayList<String> SpecialityList = new ArrayList<String>();
    ArrayList<String> SpecialityId = new ArrayList<String>();
    ArrayList<String> SpecialityImageList = new ArrayList<String>();
    String specialityId;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    GalleryListResponse transactions;
    ArrayList<Datum> transactionsObjects = new ArrayList<>() ;
    OnlineDoctorAdapter onlineDoctorAdapter;
    LinearLayoutManager mLayoutManager;
    LinearLayout alltab;

    LinearLayout search, find_doctor, doctorsearch, Consultdoctor, AllSymtoms;

    /////LOcatin///

    private Location mLastLocation;

    private GoogleApiClient mGoogleApiClient;

    public static double latitude;
    public static double longitude;
    LocationRequest mLocationRequest;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    TextView location, AddressLine,viewall;
    LinearLayout chatButton,chatButtonConsult;
    RelativeLayout share, notification;
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.fragment_home, container, false);

        //Log.d("dbkhbvhsdbvhsbdshvb: ",sharedPreferences.getString(ApplicationConstant.INSTANCE.allCategoryDoctor,""));
        //        SharedPreferences.Editor editor = sharedPreferences.edit();
//        editor.putString(ApplicationConstant.INSTANCE.allCategoryDoctor, new Gson().toJson(response.body()).toString());
//        editor.commit();

        Getid(v);
        getsliderid(v);
        UtilMethods.INSTANCE.requestStoragePermission(getActivity(),ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

        if (checkPlayServices()) {
            buildGoogleApiClient();
        }
        if (shouldAskPermissions()) {
            askPermissions();
        }
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, getActivity().MODE_PRIVATE);
         if(sharedPreferences!=null)
         {
             dataParse(sharedPreferences.getString(ApplicationConstant.INSTANCE.allCategoryDoctor,"").toString());
         }
        return v;

    }

    private void getsliderid(View v) {
        viewPager2=v.findViewById(R.id.pageview);
        //Toast.makeText(this, "running app", Toast.LENGTH_SHORT).show();
        List<Slideritem> slideritems=new ArrayList<>();
        slideritems.add(new Slideritem(R.drawable.slider1));
        slideritems.add(new Slideritem(R.drawable.slider2));
        slideritems.add(new Slideritem(R.drawable.slider3));
        slideritems.add(new Slideritem(R.drawable.slider4));
        slideritems.add(new Slideritem(R.drawable.slider5));
        viewPager2.setAdapter(new SliderAdapter(slideritems,viewPager2));
        viewPager2.setClipToPadding(false);
        viewPager2.setClipChildren(false);
        viewPager2.setOffscreenPageLimit(3);
        viewPager2.getChildAt(0).setOverScrollMode(RecyclerView.OVER_SCROLL_NEVER);
        CompositePageTransformer compositePageTransformer=new CompositePageTransformer();
        compositePageTransformer.addTransformer(new MarginPageTransformer(20));
        compositePageTransformer.addTransformer(new ViewPager2.PageTransformer() {
            @Override
            public void transformPage(@NonNull View page, float position) {
                float r=1 - Math.abs(position);
                page.setScaleY((float) (0.85f + r * 0.15));
            }
        });
        viewPager2.setPageTransformer(compositePageTransformer);
        viewPager2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);
                sliderHandler.removeCallbacks(sliderrunnable);
                sliderHandler.postDelayed(sliderrunnable,3000);//slide duration
            }
        });
    }
    private Runnable sliderrunnable=new Runnable() {
        @Override
        public void run() {
            viewPager2.setCurrentItem(viewPager2.getCurrentItem()+1);
        }
    };

    @TargetApi(23)
    protected void askPermissions() {
        String[] permissions = {
                "android.permission.READ_EXTERNAL_STORAGE",
                "android.permission.WRITE_EXTERNAL_STORAGE"
        };
        int requestCode = 200;
        requestPermissions(permissions, requestCode);
    }


    protected boolean shouldAskPermissions() {

        return (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP_MR1);

    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(getActivity())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API).build();

        mGoogleApiClient.connect();

        LocationRequest mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(10000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);

        LocationSettingsRequest.Builder builder = new LocationSettingsRequest.Builder()
                .addLocationRequest(mLocationRequest);

        PendingResult<LocationSettingsResult> result =
                LocationServices.SettingsApi.checkLocationSettings(mGoogleApiClient, builder.build());

        result.setResultCallback(new ResultCallback<LocationSettingsResult>() {
            @Override
            public void onResult(LocationSettingsResult locationSettingsResult) {

                final Status status = locationSettingsResult.getStatus();

                switch (status.getStatusCode()) {
                    case LocationSettingsStatusCodes.SUCCESS:
                        // All location settings are satisfied. The client can initialize location requests here
                        getLocation();
                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {
                            // Show the dialog by calling startResolutionForResult(),
                            // and check the result in onActivityResult().
                            status.startResolutionForResult(getActivity(), REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {
                            // Ignore the error.
                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });

    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(getActivity());

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(getActivity(), resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getActivity(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                // finish();
            }
            return false;
        }
        return true;
    }


    private void getLocation() {
        try {
            mLastLocation = LocationServices.FusedLocationApi.getLastLocation(mGoogleApiClient);
        } catch (SecurityException e) {
            e.printStackTrace();
        }
        if (mLastLocation == null) {
            mLocationRequest = new LocationRequest();
            mLocationRequest = new LocationRequest();
            mLocationRequest.setInterval(1000);
            mLocationRequest.setFastestInterval(1000);
            mLocationRequest.setPriority(LocationRequest.PRIORITY_BALANCED_POWER_ACCURACY);
            if (ContextCompat.checkSelfPermission(getActivity(),
                    Manifest.permission.ACCESS_FINE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED) {

                try {
                    LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, (LocationListener) this);
                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        } else {
            latitude = mLastLocation.getLatitude();
            longitude = mLastLocation.getLongitude();
            GlobalData.latitude = mLastLocation.getLatitude();
            GlobalData.longitude = mLastLocation.getLongitude();


            // Log.e("GlobalData.latitude3", "" + GlobalData.latitude);
            // Log.e("GlobalData.longitude3 ", "" + GlobalData.longitude);

            GetLOcation();
        }

    }

    private void GetLOcation() {

        // GetLocation Location = new GetLocation(this);
        //  latitude = Location.getLatitude();
        // longitude = Location.getLongitude();


        try {
            Geocoder geocoder = new Geocoder(getActivity(), Locale.getDefault());
            List<Address> addresses = null;
            try {
                addresses = geocoder.getFromLocation(GlobalData.latitude, GlobalData.longitude, 1);
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (addresses.get(0).getAddressLine(0) == null) {


            }

            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getCountryName();
            String countryName = addresses.get(0).getLocality();

            Log.e("countryName", "   cityName  " + cityName + "    stateName   " + stateName + "   countryName   " + countryName);


            location.setText("" + countryName);
            AddressLine.setText("" + cityName);
        } catch (Exception e) {
            e.printStackTrace();
        }


    }


    private void Getid(View v) {

        notification = v.findViewById(R.id.notification);
        share = v.findViewById(R.id.share);
//        filter=v.findViewById(R.id.fillteriv);
     //   searchiv=v.findViewById(R.id.searchiv);
       // searchiv.setVisibility(View.GONE);
       // mylist=v.findViewById(R.id.mylist);
       // mylist.setVisibility(View.INVISIBLE);
        neospeciality=v.findViewById(R.id.neospeciality);
       searchic=v.findViewById(R.id.searchicon);
        neowidget=v.findViewById(R.id.neowidget);
        iv_clear=v.findViewById(R.id.iv_clear);
       // searchic.setVisibility(View.VISIBLE);
        speciality=v.findViewById(R.id.speciality);
       // searchic.setOnClickListener(this);

        notification.setOnClickListener(this);
        searchic.setOnClickListener(this);
        iv_clear.setOnClickListener(this);
        share.setOnClickListener(this);

       cunsulttxt=v.findViewById(R.id.cunsulttxt);
       booktxt=v.findViewById(R.id.booktxt);
       cunsultimg=v.findViewById(R.id.cunsulticon);
       callimg=v.findViewById(R.id.callimg);
        location = v.findViewById(R.id.location);
        AddressLine = v.findViewById(R.id.AddressLine);
        chatButton = v.findViewById(R.id.chatButton);
        viewall = v.findViewById(R.id.viewall);
        chatButtonConsult = v.findViewById(R.id.chatButtonConsult);
        serchtext = v.findViewById(R.id.serchtext);
        tvonlinedoctors = v.findViewById(R.id.tvonlinedoctors);

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

        search = v.findViewById(R.id.search);
        search.setOnClickListener(this);
       chatButton.setOnClickListener(this);
        viewall.setOnClickListener(this);
        chatButtonConsult.setOnClickListener(this);

        find_doctor = v.findViewById(R.id.find_doctor);
        find_doctor.setOnClickListener(this);
        recycler_selected_doctor=v.findViewById(R.id.recyclerselecteddoctor);
        doctorsearch = v.findViewById(R.id.doctorsearch);
        doctorsearch.setOnClickListener(this);

        Consultdoctor = v.findViewById(R.id.Consultdoctor);
        Consultdoctor.setOnClickListener(this);


        AllSymtoms = v.findViewById(R.id.AllSymtoms);
        AllSymtoms.setOnClickListener(this);

       // mViewPager = (ViewPager) v.findViewById(R.id.pager);
        handler = new Handler();
        dotsCount = (LinearLayout) v.findViewById(R.id.image_count);


        balanceamount = v.findViewById(R.id.balanceamount);
        Logout_tv = v.findViewById(R.id.Logout_tv);
        Logout_tv.setOnClickListener(this);

        errordeta = v.findViewById(R.id.errordeta);
        contentnumber = v.findViewById(R.id.contentnumber);

        name = v.findViewById(R.id.name);
        userpic = v.findViewById(R.id.userpic);
        spspeciality=v.findViewById(R.id.spspecility);

        loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);

        recycler_view = v.findViewById(R.id.recycler_view);
        recycler_view_doctor = v.findViewById(R.id.recycler_view_doctor);
        recycler_notavilable = v.findViewById(R.id.recycler_notavilable);
       // recycler_onlinedoctors = v.findViewById(R.id.recycler_onlinedoctors);
        recycler_onlinedoctorscategory = v.findViewById(R.id.recycler_onlinedoctorscategory);

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
        recycler_notavilable.setLayoutManager(staggeredGridLayoutManager);
        HitApi();

     //   UtilMethods.INSTANCE.FetchDrByName(getActivity(),"",latitude,longitude,loader);

//        UtilMethods.INSTANCE.Doctors(getActivity(),"1",loader);
              //  getOnlineDoctor();
              //  getcancelappointment();
        userpic.setOnClickListener(this);


    }






    private void getcancelappointment() {
        ArrayList<SymptomData> doctorlist = new ArrayList<>();
        SharedPreferences myPreferences = getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String userId = balanceCheckResponse.getPatient_id();
        //  Toast.makeText(getContext(), ""+userId, Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(Request.Method.GET, "http://patient.globalforex.in/api/Appointment/GetPatientCanceledAppointment/?userId=" + userId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                // Toast.makeText(getContext(), ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    for (int i = 0; i < jsonArray.length(); i++) {
                        JSONObject object = jsonArray.getJSONObject(i);
                        SymptomData data = new SymptomData();
                        data.setDoctor_name(object.getString("doctor_name"));
                        data.setStart_time(object.getString("start_time"));
                        data.setEnd_time(object.getString("end_time"));
                        data.setDoctor_id(object.getString("doctor_id"));
                        data.setDoctor_id(object.getString("doctor_id"));
                        data.setBook_date(object.getString("book_date"));
                        data.setExperience(object.getString("experience"));
                        data.setPayment_id(object.getInt("payment_id"));
                        data.setAppointment_id(object.getString("appointment_id"));
                        doctorlist.add(data);
                    }
                    CancleappointmentAdapter cancleappointmentAdapter = new CancleappointmentAdapter(doctorlist, getActivity());
                    recycler_notavilable.setAdapter(cancleappointmentAdapter);

//                    symptomsAdapter=new SymptomsAdapter(symtomlist,ShowSymptom.this);
//                    recyclerView.setAdapter(symptomsAdapter);
                    loader.dismiss();

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ShowSymptom.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                loader.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("x-api-key", "apikey@animationmedia.org");
                return header;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(request);
    }


//    private void GalleryList(String response) {
//
//        sliderImage = new Gson().fromJson(response, GalleryListResponse.class);
//        sliderLists = sliderImage.getData();
//
//
//        // For Inflate Banner Images...
//        if (sliderLists != null && sliderLists.size() > 0) {
//            mCustomPagerAdapter = new CustomPagerAdapter(sliderLists, getActivity());
//            mViewPager.setAdapter(mCustomPagerAdapter);
//
//            mDotsCount = mViewPager.getAdapter().getCount();
//
//            mDotsText = new TextView[mDotsCount];
//
//            //here we set the dots
//            for (int i = 0; i < mDotsCount; i++) {
//                mDotsText[i] = new TextView(getActivity());
//                mDotsText[i].setText(".");
//                mDotsText[i].setTextSize(40);
//                mDotsText[i].setTypeface(null, Typeface.BOLD);
//                mDotsText[i].setTextColor(android.graphics.Color.GRAY);
//                dotsCount.addView(mDotsText[i]);
//            }
//
//            mViewPager.setOnTouchListener(new View.OnTouchListener() {
//                @Override
//                public boolean onTouch(View v, MotionEvent event) {
//                    return false;
//                }
//            });
//
//            mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//                @Override
//                public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//                    for (int i = 0; i < mDotsCount; i++) {
//                        mDotsText[i].setTextColor(getResources().getColor(R.color.colorBackground));
//                    }
//                    mDotsText[position].setTextColor(getResources().getColor(R.color.black));
//                }
//
//                @Override
//                public void onPageSelected(int position) {
//
//                }
//
//                @Override
//                public void onPageScrollStateChanged(int state) {
//
//                }
//            });
//
//            postDelayedScrollNext();
//        }
//
//    }

//    private void postDelayedScrollNext() {
//        handler.postDelayed(new Runnable() {
//            public void run() {
//
//                try {
//                    if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
//                        mViewPager.setCurrentItem(0);
//                        postDelayedScrollNext();
//                        return;
//                    }
//                    mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
//                    // postDelayedScrollNext(position+1);
//                    postDelayedScrollNext();
//                } catch (Exception e) {
//
//                }
//                // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
//            }
//        }, 3000);
//
//    }

    private void HitApi() {

        loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);





    }


    @Override
    public void onClick(View v) {

        if (v == find_doctor) {

            //  startActivity(new Intent(getActivity(), FinadDoctorActivity.class));
            startActivity(new Intent(getActivity(), SearchDoctorActivity.class));


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


        if (v == doctorsearch) {


            startActivity(new Intent(getActivity(), SearchDoctorActivity.class));



        }
        if (v == chatButton)
        {
            chatButtonConsult.setBackground(getActivity().getDrawable(R.drawable.unselectedborder));
            chatButton.setBackground(getActivity().getDrawable(R.drawable.backblue));
            Intent i=new Intent(getActivity(), ShowAllDoctors.class);
            // i.putExtra("response",res);
            startActivity(i);
//            loader.show();
//            loader.setCancelable(false);
//            loader.setCanceledOnTouchOutside(false);
//            UtilMethods.INSTANCE.getOnlineDoctor(getActivity(), "1", loader);
        }
        if (v == chatButtonConsult)
        {
            chatButton.setBackground(getActivity().getDrawable(R.drawable.unselectedborder));
            chatButtonConsult.setBackground(getActivity().getDrawable(R.drawable.backblue));
//            Intent i=new Intent(getActivity(),SearchDoctorActivity.class);
//            // i.putExtra("response",res);
//            startActivity(i);
            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);
            UtilMethods.INSTANCE.getOnlineDoctor(getActivity(), "1", loader);

        }
       if (v == viewall) {

           Intent i=new Intent(getActivity(),ShowAllOnlineDoctors.class);
          // i.putExtra("response",res);
           startActivity(i);

          //  startActivity(new Intent(getActivity(), ShowAllOnlineDoctors.class));


        }

        if (v == Consultdoctor) {


            //   startActivity(new Intent(getActivity(), ConsultdoctorActivity.class));
            startActivity(new Intent(getActivity(), FinadDoctorActivity.class));


        }


        if (v == AllSymtoms) {
            startActivity(new Intent(getActivity(), AllSymtomsActivity.class));
        }


        if (v == notification) {


        }

        if (v == share) {

            try {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Cureu");
                String shareMessage = "\n";
                shareMessage = shareMessage + "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID + "\n\n";
                shareIntent.putExtra(Intent.EXTRA_TEXT, shareMessage);
                startActivity(Intent.createChooser(shareIntent, "choose one"));
            } catch (Exception e) {
                //e.toString();
            }

        }



        }

//       if(v==whatsappbtn)
//       {
//           boolean isWhatsappInstalled = whatsappInstalledOrNot("com.whatsapp");
//           if (isWhatsappInstalled) {
//               Uri uri = Uri.parse("https://wa.me/" + "+917235090909");
//               Intent sendIntent = new Intent(Intent.ACTION_VIEW, uri);
//               sendIntent.putExtra(Intent.EXTRA_TEXT, "Hai Good Morning");
//               sendIntent.setPackage("com.whatsapp");
//               startActivity(sendIntent);
//           } else {
//               Toast.makeText(getActivity(), "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
//               Uri uri = Uri.parse("market://details?id=com.whatsapp");
//               Intent goToMarket = new Intent(Intent.ACTION_VIEW, uri);
//               startActivity(goToMarket);
//
//           }
////           try {
////               // Check if whatsapp is installed
////               getActivity().getPackageManager().getPackageInfo("com.whatsapp", PackageManager.GET_META_DATA);
////               Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://wa.me/" +"917235090909" ));
////               startActivity(intent);
////           } catch (PackageManager.NameNotFoundException e) {
////               Toast.makeText(getContext(), "WhatsApp not Installed", Toast.LENGTH_SHORT).show();
////           }
//
//
//       }

    private boolean whatsappInstalledOrNot(String uri) {
        PackageManager pm = getActivity().getPackageManager();
        boolean app_installed = false;
        try {
            pm.getPackageInfo(uri, PackageManager.GET_ACTIVITIES);
            app_installed = true;
        } catch (PackageManager.NameNotFoundException e) {
            app_installed = false;
        }
        return app_installed;
    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("alldoctorsondash"))
        {


            dataParsedoctoronBook(activityFragmentMessage.getFrom());

        }
        else if (activityFragmentMessage.getMessage().equalsIgnoreCase("specialities")) {
//            Log.d( "specialitycsdabvdbv: ",activityFragmentMessage.getFrom());
//            dataParse(activityFragmentMessage.getFrom());
            //dataParseonlinecategory(activityFragmentMessage.getFrom());

        }
        else if (activityFragmentMessage.getMessage().equalsIgnoreCase("doctorlist")) {

         //   dataParsedoctor(activityFragmentMessage.getFrom());

        }

    }
    public void dataParsedoctoronBook(String response) {
        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            doctormAdapter = new DoctorListDashboadAdapter(sliderLists, getActivity());
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,LinearLayout.VERTICAL);
            recycler_selected_doctor.setLayoutManager(staggeredGridLayoutManager);
            recycler_selected_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_selected_doctor.setAdapter(doctormAdapter);
            recycler_selected_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_selected_doctor.setVisibility(View.GONE);
        }

    }


    private void dataParseonlinecategory(String response) {

        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            mOnlineAdapter = new OnlinedoctorsspecialitiesAdapter(sliderLists, getActivity());
            mLayoutManager = new LinearLayoutManager(getActivity());
                  // mLayoutManager = new GridLayoutManager(getActivity(),3);
            recycler_onlinedoctors.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
                  // recycler_view.setLayoutManager(mLayoutManager);
            recycler_onlinedoctors.setItemAnimator(new DefaultItemAnimator());
            recycler_onlinedoctors.setAdapter(mOnlineAdapter);
            recycler_onlinedoctors.setVisibility(View.VISIBLE);
        } else {
            recycler_onlinedoctors.setVisibility(View.GONE);
        }

//        if (transactionsObjects.size() > 0) {
////            onlineDoctorAdapter = new OnlineDoctorAdapter(transactionsObjects, getActivity());
////            staggeredGridLayoutManager = new StaggeredGridLayoutManager(1, LinearLayout.HORIZONTAL);
////            recycler_onlinedoctors.setLayoutManager(staggeredGridLayoutManager);
////            recycler_onlinedoctors.setItemAnimator(new DefaultItemAnimator());
////            recycler_onlinedoctors.setAdapter(onlineDoctorAdapter);
////            recycler_onlinedoctors.setVisibility(View.VISIBLE);
////             tvonlinedoctors.setVisibility(View.VISIBLE);
//        } else {
//            recycler_onlinedoctors.setVisibility(View.GONE);
//            tvonlinedoctors.setVisibility(View.GONE);
//
//        }


    }

    public void dataParse(String response) {
        SpecialityList.add("All");
        SpecialityId.add("0");
        SpecialityImageList.add("noimage");
        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0)
        {
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
                      if (SpecialityList.get(position).equals("All"))
                    {
                      UtilMethods.INSTANCE.Doctors(getActivity(),"",loader);
                       // getLocationdddd();
                    } else {
                        specialityId = SpecialityId.get(position);
                        UtilMethods.INSTANCE.Doctors(getActivity(),specialityId,loader);
                    }
                }

                @Override
                public void onNothingSelected(AdapterView<?> parent) {

                }
            });
            CustomAdapter customAdapter=new CustomAdapter(getActivity(),SpecialityImageList,SpecialityList);
            spspeciality.setAdapter(customAdapter);
 //           ArrayAdapter<String> specialityAdapter;
//            specialityAdapter = new ArrayAdapter<String>(getActivity(), R.layout.spinner_item,R.id.tv_spname, SpecialityList);
//            spspeciality.setAdapter(specialityAdapter);

        }

//        if (sliderLists.size() > 0) {
//            mAdapter = new SpecialitiesAdapter(sliderLists, getActivity());
//            mLayoutManager = new LinearLayoutManager(getActivity());
//            // mLayoutManager = new GridLayoutManager(getActivity(),3);
//            recycler_view.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//            // recycler_view.setLayoutManager(mLayoutManager);
//            recycler_view.setItemAnimator(new DefaultItemAnimator());
//            recycler_view.setAdapter(mAdapter);
//
//            recycler_view.setVisibility(View.VISIBLE);
//        } else {
//            recycler_view.setVisibility(View.GONE);
//        }

    }


    public void dataParsedoctor(String response) {


        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            doctormAdapter = new DoctorListDashboadAdapter(sliderLists, getActivity());
            mLayoutManager = new LinearLayoutManager(getActivity());
            recycler_view_doctor.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
            recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_view_doctor.setAdapter(doctormAdapter);

            recycler_view_doctor.setVisibility(View.GONE);
            //  recycler_view_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_view_doctor.setVisibility(View.GONE);
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


    @Override
    public void onConnected(@Nullable Bundle bundle) {

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    public void getLocationdddd(){
        gpsTracker = new GpsTracker(getActivity());
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
    private void HitAPi(String latitude,String longitude) {

        if (UtilMethods.INSTANCE.isNetworkAvialable(getActivity())) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.FetchDrByName(getActivity(),"",latitude,longitude,loader);

        } else {

            UtilMethods.INSTANCE.NetworkError(getActivity(), getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }



    }
    public  void filters(String txt)
    {
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
}

