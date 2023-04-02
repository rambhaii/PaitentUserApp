package com.doc24x7.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.gson.Gson;

public class ProfileDetailEdit extends AppCompatActivity implements View.OnClickListener {

    EditText name,email,patientcontect;
    LinearLayout submit;
Loader loader;
String patient_id="";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile_detail_edit);

        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);


        patientcontect=findViewById(R.id.patientcontect);
        email=findViewById(R.id.email);
        name=findViewById(R.id.name);
        submit=findViewById(R.id.submit);
        submit.setOnClickListener(this);


        SetValue();
    }

    private void SetValue() {



        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);

        name.setText(""+balanceCheckResponse.getName() );
        patientcontect.setText(""+balanceCheckResponse.getMobile() );
        email.setText(""+balanceCheckResponse.getEmail() );
        patient_id=""+balanceCheckResponse.getPatient_id();

    }

    @Override
    public void onClick(View view) {

        if(view==submit){

          /*  SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
            Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
            String member_id = balanceCheckResponse.getPatient_id();

          */



            if (name.getText().toString().trim().isEmpty() ) {

                Toast.makeText(this, "please enter name", Toast.LENGTH_SHORT).show();


            } else  if (!UtilMethods.INSTANCE.isValidEmail(email.getText().toString().trim())){

                Toast.makeText(this, "please enter valid email id ", Toast.LENGTH_SHORT).show();


            } else if (UtilMethods.INSTANCE.isNetworkAvialable(ProfileDetailEdit.this)) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                UtilMethods.INSTANCE.updatePatient(ProfileDetailEdit.this, name.getText().toString().trim(),email.getText().toString().trim(),patient_id
                        ,patientcontect.getText().toString().trim(),loader,this);


            } else {
                UtilMethods.INSTANCE.NetworkError(ProfileDetailEdit.this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }



        }


    }
}