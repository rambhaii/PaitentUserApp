package com.doc24x7.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.google.gson.Gson;

public class ProfileDetail extends AppCompatActivity implements View.OnClickListener {

    TextView name,email,patientcontect;
    ImageView edit_profile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail);


        GetId();
    }

    private void GetId() {

        edit_profile=findViewById(R.id.edit_profile);
        edit_profile.setOnClickListener(this);

        name=findViewById(R.id.name);
        email=findViewById(R.id.email);
        patientcontect=findViewById(R.id.patientcontect);

         SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);

        name.setText(""+balanceCheckResponse.getName() );
        patientcontect.setText(""+balanceCheckResponse.getMobile() );
        email.setText(""+balanceCheckResponse.getEmail() );


    }

    @Override
    public void onClick(View view) {


        if(view==edit_profile){




            startActivity(new Intent(this, ProfileDetailEdit.class));
            finish();





        }


    }
}