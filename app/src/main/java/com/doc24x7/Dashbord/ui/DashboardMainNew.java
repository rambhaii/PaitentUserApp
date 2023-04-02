package com.doc24x7.Dashbord.ui;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.net.Uri;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.BuildConfig;
import com.doc24x7.DOctoeByIdActivity;
import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.doc24x7.Splash.ui.SelectAccountActivity;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.ForceUpdateChecker;
import com.doc24x7.Utils.GooglePlayStoreAppVersionNameLoader;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
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
import com.google.android.gms.location.places.Places;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.rampo.updatechecker.UpdateChecker;

import java.io.IOException;
import java.util.List;
import java.util.Locale;

import cn.pedant.SweetAlert.SweetAlertDialog;
import eightbitlab.com.blurview.BlurView;

public class DashboardMainNew extends AppCompact implements NavigationView.OnNavigationItemSelectedListener,
        View.OnClickListener, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, ForceUpdateChecker.OnUpdateNeededListener {
    ImageView imgnotificatio,language_img,book_img;
    NavigationView navigationView;
    private GpsTracker gpsTracker;
    Context context;
    ActionBarDrawerToggle actionBarDrawerToggle;
    FrameLayout main_container;
    DrawerLayout drawerLayout;
    AlertDialog alertDialog;
    private static long back_pressed;
    public static int countBackstack = 0;
    private static final int TIME_DELAY = 2000;
    RelativeLayout call, favarate, card, editlocation, reference;
    TextView location, AddressLine, count;
    RecyclerView recyclerView;
    BlurView blurView;
    TextView ic_logout, Profile, support, Share, orderhistory, symptoms,viewprofile,username;
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 2000;
    private final static int AUTOCOMPLETE_REQUEST_CODE = 1;
    Loader loader;
    String version = "";
    String versionName = "";
    int versionCode;
    LanguageManager languageManager;
    private Location mLastLocation;
    private GoogleApiClient mGoogleApiClient;
    public static double latitude;
    public static double longitude;
    LocationRequest mLocationRequest;
    TextView tv_version;
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_dashboard);
        languageManager=new LanguageManager(this);

        ForceUpdateChecker.with(this).onUpdateNeeded(this).check();


        GetId();
        UtilMethods.INSTANCE.Doctors(DashboardMainNew.this,"",loader);
      //  getLocationdddd();
      //  imgnotificatiocount();
        version = GooglePlayStoreAppVersionNameLoader.newVersion;
        getVersionInfo();
       // PopUpdate();
        if (checkPlayServices()) {

            buildGoogleApiClient();
        }




    }
    public void getLocationdddd(){
        gpsTracker = new GpsTracker(DashboardMainNew.this);
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

        if (UtilMethods.INSTANCE.isNetworkAvialable(DashboardMainNew.this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.FetchDrByName(DashboardMainNew.this,"",latitude,longitude,loader);

        } else {

            UtilMethods.INSTANCE.NetworkError(DashboardMainNew.this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }



    }
    private void imgnotificatiocount() {
        SharedPreferences myPreferences = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String userId = balanceCheckResponse.getPatient_id()==null?"":balanceCheckResponse.getPatient_id();

        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);

        UtilMethods.INSTANCE.noticount(this, userId, loader, count);


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {

                    case RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;

                    case RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;


                    default:
                        break;
                }
                break;
        }


    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(DashboardMainNew.this)
                .addOnConnectionFailedListener(DashboardMainNew.this)
                .addApi(Places.GEO_DATA_API)
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

                        getLocation();




                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {


                            status.startResolutionForResult(DashboardMainNew.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e) {

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

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_REQUEST).show();


                // // Toast.makeText(getApplicationContext(), "PLAY_SERVICES_REQUEST", // // Toast.LENGTH_LONG).show();


            } else {
                // // Toast.makeText(getApplicationContext(), "This device is not supported.", // // Toast.LENGTH_LONG).show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case ASK_MULTIPLE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean FINE_LOCATIONPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean COARSE_LOCATIONPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (FINE_LOCATIONPermission && COARSE_LOCATIONPermission) {

                        // // Toast.makeText(Splash.this, "SUCCESS  Done ", // // Toast.LENGTH_SHORT).show();
                        UtilMethods.INSTANCE.locationreposeval(this, "1");
                        UtilMethods.INSTANCE.locationreposeval(this, "1");

                        getLocation();
                        // select();

                    } else {

                        Snackbar.make(this.findViewById(android.R.id.content),
                                "Please Grant Permissions to start service",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ActivityCompat.requestPermissions(DashboardMainNew.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

                                    }
                                }).show();
                    }
                }
                break;
        }
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
            if (ContextCompat.checkSelfPermission(this,
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


            Log.e("GlobalData.latitude3", "" + GlobalData.latitude);
            Log.e("GlobalData.longitude3 ", "" + GlobalData.longitude);


            GetLOcation();

        }

    }

    private void GetLOcation() {


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(GlobalData.latitude, GlobalData.longitude, 1);
            String cityName = addresses.get(0).getAddressLine(0);
            String stateName = addresses.get(0).getCountryName();
            String countryName = addresses.get(0).getLocality();
            location.setText("" + countryName);
            AddressLine.setText("" + cityName);
        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private void GetId() {

        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
        editlocation = findViewById(R.id.editlocation);
        tv_version = findViewById(R.id.tv_version);
        card = findViewById(R.id.card);
        call = findViewById(R.id.call);
        favarate = findViewById(R.id.favarate);
        reference = findViewById(R.id.reference);
        location = findViewById(R.id.location);
        AddressLine = findViewById(R.id.AddressLine);
        count = findViewById(R.id.noticount);
        editlocation.setOnClickListener(this);
        card.setOnClickListener(this);
        call.setOnClickListener(this);
        favarate.setOnClickListener(this);
        reference.setOnClickListener(this);
        location.setOnClickListener(this);
        AddressLine.setOnClickListener(this);
        language_img=findViewById(R.id.language_img);
        book_img=findViewById(R.id.book_img);
        language_img.setOnClickListener(this);
        book_img.setOnClickListener(this);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        main_container = (FrameLayout) findViewById(R.id.main_container);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimaryDark));

        setSupportActionBar(toolbar);
        imgnotificatio = findViewById(R.id.shownotification);
        imgnotificatio.setOnClickListener(this);
        imgnotificatio.setVisibility(View.VISIBLE);
        language_img.setVisibility(View.VISIBLE);
        book_img.setVisibility(View.VISIBLE);
        recyclerView = findViewById(R.id.recycler_view);
        ic_logout = findViewById(R.id.ic_logout);
        Profile = findViewById(R.id.Profile);
        viewprofile = findViewById(R.id.viewprofile);
        support = findViewById(R.id.support);
        symptoms = findViewById(R.id.ic_symptoms);
        Share = findViewById(R.id.Share);
        username = findViewById(R.id.userName);

        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);

        String pref = balanceResponse;

        if(pref==null||pref.equalsIgnoreCase(""))
        {

        }else{
            Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
            username.setText(""+balanceCheckResponse.getName());
        }

        orderhistory = findViewById(R.id.orderhistory);
        ic_logout.setOnClickListener(this);
        Profile.setOnClickListener(this);
        viewprofile.setOnClickListener(this);
        support.setOnClickListener(this);
        Share.setOnClickListener(this);
        orderhistory.setOnClickListener(this);
        symptoms.setOnClickListener(this);
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.drawer_open, R.string.drawer_close);
        drawerLayout.setDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.getDrawerArrowDrawable().setColor(getResources().getColor(R.color.colorPrimary));
        actionBarDrawerToggle.syncState();
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.navbar_icon);

        navigationView.setNavigationItemSelectedListener(this);

        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#2F84C7\">" + "" + "</font>")));

        changeFragment(new HomeFragment());
    }


    private void Gethint() {


        View one = findViewById(R.id.reference);
        int[] oneLocation = new int[2];
        one.getLocationInWindow(oneLocation);
        float oneX = oneLocation[0] + one.getWidth() / 2f;
        float oneY = oneLocation[1] + one.getHeight() / 2f;


    }


    public void OpenUpdateDialog() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.update_available_pop, null);

        TextView tvLater = (TextView) view.findViewById(R.id.tv_later);
        TextView tvOk = (TextView) view.findViewById(R.id.tv_ok);

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });
        tvOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goToMarket(DashboardMainNew.this);
                dialog.dismiss();
                }
        });
        dialog.show();
    }

    private static void goToMarket(Context mContext) {
        mContext.startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse(UpdateChecker.ROOT_PLAY_STORE_DEVICE + mContext.getPackageName())));
    }

    private void PopUpdate() {
        // Log.e("version","    versionName    "+versionName +"  version    "+version );
        if (version != null && !version.equalsIgnoreCase("")) {
            if (!versionName.equalsIgnoreCase(version)) {
                OpenUpdateDialog();
            }
        }
    }

    private void getVersionInfo() {

        try {
            PackageInfo packageInfo = getPackageManager().getPackageInfo(getPackageName(), 0);
            versionName = packageInfo.versionName;
            versionCode = packageInfo.versionCode;

            tv_version.setText(" Version  :  " + BuildConfig.VERSION_NAME);

            // Log.e("versionnn","   versionName   "+versionName+"   versionCode  "+  versionCode+"   version  "+  version);

        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


  /*  protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
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
                            status.startResolutionForResult(DashboardMainNew.this, REQUEST_CHECK_SETTINGS);

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

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode,
                        PLAY_SERVICES_REQUEST).show();
            } else {
                Toast.makeText(getApplicationContext(),
                        "This device is not supported.", Toast.LENGTH_LONG)
                        .show();
                finish();
            }
            return false;
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

         switch (requestCode) {

            case REQUEST_CHECK_SETTINGS:
                switch (resultCode) {

                    case Activity.RESULT_OK:
                        // All required changes were successfully made
                        getLocation();
                        break;

                    case Activity.RESULT_CANCELED:
                        // The user was asked to change settings, but chose not to
                        break;


                    default:
                        break;
                }
                break;
        }

       *//* if (requestCode == AUTOCOMPLETE_REQUEST_CODE) {
            if (resultCode == RESULT_OK) {
                Place place = Autocomplete.getPlaceFromIntent(data);

                location.setText(""+ place.getName());
                AddressLine.setText(""+place.getAddress());


            } else if (resultCode == AutocompleteActivity.RESULT_ERROR) {
                Status status = Autocomplete.getStatusFromIntent(data);
               // Log.i(TAG, status.getStatusMessage());
            } else if (resultCode == RESULT_CANCELED) {

                // The user canceled the operation.

            }
        }*//*
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
            if (ContextCompat.checkSelfPermission(this,
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


            Log.e("GlobalData.latitude3", "" + GlobalData.latitude);
            // Log.e("GlobalData.longitude3 ", "" + GlobalData.longitude);

            GetLOcation();
        }

    }

    private void GetLOcation() {

        // GetLocation Location = new GetLocation(this);
        //  latitude = Location.getLatitude();
        // longitude = Location.getLongitude();


        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        List<Address> addresses = null;
        try {
            addresses = geocoder.getFromLocation(GlobalData.latitude, GlobalData.longitude, 1);
        } catch (IOException e) {
            e.printStackTrace();
        }

        String cityName = addresses.get(0).getAddressLine(0);
        String stateName = addresses.get(0).getCountryName();
        String countryName = addresses.get(0).getLocality();

         Log.e("countryName","   cityName  "+  cityName  + "    stateName   "+ stateName  +"   countryName   "+countryName);


        location.setText(""+countryName);
        AddressLine.setText(""+cityName);


    }*/

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();

        {
            if (id == R.id.home) {

                changeFragment(new HomeFragment());


            } else {

                changeFragment(new HomeFragment());

            }

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private boolean changeFragment(Fragment fragment) {
        //switching fragment
        if (fragment != null) {
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, fragment)
                    .commit();
            return true;
        }
        return false;
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else if (countBackstack > 0) {
            countBackstack = 0;
//            fm.beginTransaction().replace(R.id.main_container, new ServiceFragment()).commit();

        } else if (back_pressed + TIME_DELAY > System.currentTimeMillis()) {
            super.onBackPressed();
        } else {
            Toast.makeText(getBaseContext(), "Press once again to exit!",
                    Toast.LENGTH_SHORT).show();
        }
        back_pressed = System.currentTimeMillis();
    }

    private void HitApi() {
        getVersionInfo();
       // PopUpdate();

      //  UtilMethods.INSTANCE.AllSymtoms(this, null);

        /*if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {

            UtilMethods.INSTANCE.Category(this,null);
            UtilMethods.INSTANCE.offer(this,null);

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.products(this, loader);

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }*/

    }

    @Override
    public void onClick(View view) {
        if(view==language_img)
        {
            UtilMethods.INSTANCE.chooseLanguage(DashboardMainNew.this,getIntent());
        }
        if (view==book_img)
        {
          if (UtilMethods.INSTANCE.isNetworkAvialable(DashboardMainNew.this))
          {

              loader.show();
              loader.setCancelable(false);
              loader.setCanceledOnTouchOutside(false);
              UtilMethods.INSTANCE.allReciept(DashboardMainNew.this,loader);
          }
        }

        if (view == reference) {


            HitApi();


        }

        if (view == favarate) {

            // new FavouriteFragment().show(getSupportFragmentManager(),"Dialog");

            //   startActivity(new Intent(this, FavouriteActivity.class));

        }

        if (view == card) {


        }


        if (view == Profile) {
         SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
            String pref = balanceResponse;
            if(pref==null||pref.equalsIgnoreCase(""))
            {
                Intent intent = new Intent(DashboardMainNew.this, SelectAccountActivity.class);
               startActivity(intent);
            }
            else {

                startActivity(new Intent(this, ProfileDetail.class));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
            }

        }
        if (view == viewprofile) {
            SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
            String pref = balanceResponse;
            if(pref==null||pref.equalsIgnoreCase(""))
            {
                Intent intent = new Intent(DashboardMainNew.this, SelectAccountActivity.class);
               startActivity(intent);
            }
            else {


                startActivity(new Intent(this, ProfileDetail.class));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        }

        if (view == imgnotificatio)
        {
           UtilMethods.INSTANCE.notification(DashboardMainNew.this);
        }

        if (view == support) {


            startActivity(new Intent(this, SupportActivity.class));

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }

        if (view == Share) {


            Intent shareIntent = new Intent(Intent.ACTION_SEND);
            shareIntent.setType("text/plain");
            shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Ping Doctor");
            String app_url = "https://play.google.com/store/apps/details?id=" + BuildConfig.APPLICATION_ID;
            shareIntent.putExtra(Intent.EXTRA_TEXT, app_url);
            startActivity(Intent.createChooser(shareIntent, "Share via"));
            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }

        if (view == orderhistory) {
            SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
            String pref = balanceResponse;
            if(pref==null||pref.equalsIgnoreCase(""))
            {
                Intent intent = new Intent(DashboardMainNew.this, SelectAccountActivity.class);
                startActivity(intent);
            }
            else {
                startActivity(new Intent(this, DOctoeByIdActivity.class).putExtra("type", "4"));
                DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);

            }
        }
        if (view == symptoms)
        {
            SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
            String pref = balanceResponse;

            Log.e("prefpref",""+ pref);

            if(pref==null||pref.equalsIgnoreCase(""))
            {

                 Intent intent = new Intent(DashboardMainNew.this, SelectAccountActivity.class);
                startActivity(intent);
            }
            else {


                Intent i = new Intent(DashboardMainNew.this, ShowSymptom.class);
                startActivity(i);
            }
        }



        if (view == ic_logout) {
            final SweetAlertDialog alertDialog = new SweetAlertDialog(DashboardMainNew.this);
            alertDialog.setTitle("Alert!");
            alertDialog.setContentText("Do you want to logout?");
            alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    UtilMethods.INSTANCE.logout(DashboardMainNew.this);


                }
            });

            alertDialog.show();

            DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
            drawer.closeDrawer(GravityCompat.START);

        }


        if (view == location) {
            /*  List<Place.Field> fields = Arrays.asList(Place.Field.ID, Place.Field.NAME,Place.Field.LAT_LNG,Place.Field.ADDRESS_COMPONENTS,Place.Field.TYPES,Place.Field.ADDRESS,Place.Field.PHONE_NUMBER);
           Intent intent = new Autocomplete.IntentBuilder(
                  AutocompleteActivityMode.FULLSCREEN, fields)
                  .build(DashboardMainNew.this);
          startActivityForResult(intent, AUTOCOMPLETE_REQUEST_CODE);*/
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


    @Override
    public void onResume() {
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
        String pref = balanceResponse;
        if(pref==null||pref.equalsIgnoreCase(""))
        {

        }
        else
        {

            HitApi();
        }

        super.onResume();

    }

    @Override
    public void onPause() {
        super.onPause();
    }

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu){
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.main, menu);
//        return true;
//     }


//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        int id = item.getItemId();
//        switch (id) {
//              case R.id.local_english:
//                languageManager.updateResource("en");
//                Intent intent = this.getIntent();
//                startActivity(intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
//                return true;
//              case R.id.local_hindi:
//                languageManager.updateResource("hi");
//                Intent inten = this.getIntent();
//                startActivity(inten.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
//                return true;
//              }
//              return super.onOptionsItemSelected(item);
//    }



    protected void reLaunchApp() {

        Intent intent = getIntent();
        finish();
        startActivity(intent);
    }


    @Override
    public void onUpdateNeeded(String updateUrl) {
        Log.e("updateUrl",""+ updateUrl);

        if (updateUrl.equals("")) {

        } else {
            AlertDialog dialog = new AlertDialog.Builder(this)
                    .setTitle("New version available")
                    .setCancelable(false)
                    .setMessage("Please, update our app to new version to continue for better experiance.")
                    .setPositiveButton("Update",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    redirectStore(updateUrl);
                                }
                            })
                    .create();
            dialog.show();
        }
    }

    private void redirectStore(String updateUrl) {

            final Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(updateUrl));
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);

    }

}
