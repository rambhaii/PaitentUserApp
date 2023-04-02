package com.doc24x7.Splash.ui;

import android.Manifest;
import android.content.Intent;
import android.content.IntentSender;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.doc24x7.BuildConfig;
import com.doc24x7.Dashbord.ui.DashboardMainNew;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.GooglePlayStoreAppVersionNameLoader;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GoogleApiAvailability;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.PendingResult;
import com.google.android.gms.common.api.ResultCallback;
import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.location.LocationSettingsRequest;
import com.google.android.gms.location.LocationSettingsResult;
import com.google.android.gms.location.LocationSettingsStatusCodes;
import com.google.android.gms.location.places.Places;
import com.google.android.material.snackbar.Snackbar;

public class Splash extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    String Email="";
   TextView version;

    private static final String TAG = "HomeActivity";
    private final static int PLAY_SERVICES_REQUEST = 1000;
    private final static int REQUEST_CHECK_SETTINGS = 1000;
    // Google client to interact with Google API
    private GoogleApiClient mGoogleApiClient;
    private static final int ASK_MULTIPLE_PERMISSION_REQUEST_CODE = 0;

    private static String key="2";



    @Override
    protected void onPause() {
        super.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);


        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.splash_screen);
        version=findViewById(R.id.version);
        version.setText("Version : "+BuildConfig.VERSION_NAME+"");
        Loader  loader = new Loader(Splash.this, android.R.style.Theme_Translucent_NoTitleBar);
        if (UtilMethods.INSTANCE.isNetworkAvialable(Splash.this)) {

            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            // UtilMethods.INSTANCE.getbanner(getActivity(), loader);
            UtilMethods.INSTANCE.doctorlist(Splash.this, loader);


        } else {
            UtilMethods.INSTANCE.NetworkError(Splash.this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }
        //UtilMethods.INSTANCE.Doctors(Splash.this,"",loader);
        new GooglePlayStoreAppVersionNameLoader().execute();
        if (checkPlayServices()) {

            buildGoogleApiClient();
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            } else {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
            }
        }

    }

    private void select()
    {
        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
        Email = ""+balanceResponse;

        // Log.e("Email","  Email"+   Email +"    "+  Email.length() );

        if ( Email.equalsIgnoreCase("1"))
        {
            Dashboardpage();
        }else{

           // Dashboardpage();
            loginpage();


        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(Splash.this)
                .addOnConnectionFailedListener(Splash.this)
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

                        SharedPreferences myPrefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                        key = ""+myPrefs.getString(ApplicationConstant.INSTANCE.locationreposeval, null);

                        if(key.equalsIgnoreCase("1"))
                        {
                            select();
                        }
                        else
                        {


                            // Toast.makeText(Splash.this, "permition allow ", // Toast.LENGTH_SHORT).show();
                        }




                        break;
                    case LocationSettingsStatusCodes.RESOLUTION_REQUIRED:
                        try {

                            // // Toast.makeText(Splash.this, "RESOLUTION_REQUIRED", // // Toast.LENGTH_SHORT).show();

                            status.startResolutionForResult(Splash.this, REQUEST_CHECK_SETTINGS);

                        } catch (IntentSender.SendIntentException e)
                        {

                        }
                        break;
                    case LocationSettingsStatusCodes.SETTINGS_CHANGE_UNAVAILABLE:
                        break;
                }
            }
        });


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode==REQUEST_CHECK_SETTINGS){
            buildGoogleApiClient();
        }
    }

    private boolean checkPlayServices() {

        GoogleApiAvailability googleApiAvailability = GoogleApiAvailability.getInstance();

        int resultCode = googleApiAvailability.isGooglePlayServicesAvailable(this);

        if (resultCode != ConnectionResult.SUCCESS) {
            if (googleApiAvailability.isUserResolvableError(resultCode)) {
                googleApiAvailability.getErrorDialog(this, resultCode, PLAY_SERVICES_REQUEST).show();
            }
            return false;
        }
        return true;
    }


    public void loginpage()
    {

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    startLogin();
                }
            }
        };
        timerThread.start();
    }

    public void Dashboardpage() {

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(1000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    DashboardLogin();
                }
            }
        };
        timerThread.start();
    }

    public void DashboardLogin() {


        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String SetType = ""+myPreferences.getString(ApplicationConstant.INSTANCE.SetType, null);

        if(SetType.equalsIgnoreCase("1")){

            Intent intent = new Intent(Splash.this, DashboardMainNew.class);
            startActivity(intent);

            finish();
        }else  if(SetType.equalsIgnoreCase("2")){

            Intent intent = new Intent(Splash.this, DashboardMainNew.class);
            startActivity(intent);

            finish();
        }else {
            Intent intent = new Intent(Splash.this, DashboardMainNew.class);
            startActivity(intent);

            finish();

        }




    }

    public void startLogin()
    {

        Intent intent = new Intent(Splash.this, SelectAccountActivity.class);
        startActivity(intent);
        //finish();

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
    public void onRequestPermissionsResult(int requestCode, @NonNull String permissions[], @NonNull int[] grantResults) {
        switch (requestCode) {
            case ASK_MULTIPLE_PERMISSION_REQUEST_CODE:
                if (grantResults.length > 0) {
                    boolean FINE_LOCATIONPermission = grantResults[1] == PackageManager.PERMISSION_GRANTED;
                    boolean COARSE_LOCATIONPermission = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                    if (FINE_LOCATIONPermission && COARSE_LOCATIONPermission) {
                        UtilMethods.INSTANCE.locationreposeval(this,"1");
                        select();
                      }
                    else {

                        Snackbar.make(this.findViewById(android.R.id.content),
                                "Please Grant Permissions to start service",
                                Snackbar.LENGTH_INDEFINITE).setAction("ENABLE",
                                new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                        ActivityCompat.requestPermissions(Splash.this, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);

                                    }
                                }).show();
                    }
                }
                break;
        }
    }


}

