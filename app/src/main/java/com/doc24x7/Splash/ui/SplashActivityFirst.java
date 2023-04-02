package com.doc24x7.Splash.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;

import androidx.appcompat.app.AppCompatActivity;

import com.doc24x7.Dashbord.ui.DashboardMainNew;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;


public class SplashActivityFirst extends AppCompatActivity {
    String Email="";




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }
        setContentView(R.layout.activity_splash_first);

        Selectpagr();


    }

    private void Selectpagr() {

        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = ""+myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
        Email = ""+balanceResponse;

        Log.e("Email","  Email"+   Email +"    "+  Email.length() );

        if ( Email.equalsIgnoreCase("1")){

            Dashboardpage();

        }else{

            loginpage();

        }

    }





    public void Dashboardpage() {

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(2000);
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
       // Intent intent = new Intent(SplashActivityFirst.this, MainActivity.class);
        Intent intent = new Intent(SplashActivityFirst.this, DashboardMainNew.class);
        startActivity(intent);
        finish();
    }

    public void loginpage() {

        Thread timerThread = new Thread(){
            public void run(){
                try{
                    sleep(3000);
                }catch(InterruptedException e){
                    e.printStackTrace();
                }finally{

                    startLogin();
                }
            }
        };
        timerThread.start();
    }

    public void startLogin() {
        Intent intent = new Intent(SplashActivityFirst.this, Splash.class);
        //  Intent intent = new Intent(Splash.this, RegisterActivity.class);
        startActivity(intent);
        finish();
    }



}