package com.doc24x7;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;

public class PayoutActivity extends AppCompatActivity {

    //declaration

    TextView confirmed,docname,docspec,docadd,reasonvisit,datetime,
             appointmentfor,patientname,gender,age,symptoms,weight;
    ImageView link,docimage;

    RadioButton self,someelse,male,female;
    Button back,done,feedback,videochat,chaticon;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payout);

        //initialization

        confirmed=findViewById(R.id.confirmed);




    }
}