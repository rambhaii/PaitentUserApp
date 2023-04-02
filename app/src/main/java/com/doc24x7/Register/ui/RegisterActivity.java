package com.doc24x7.Register.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.doc24x7.R;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;

public class RegisterActivity extends AppCompatActivity implements View.OnClickListener {

      Button btregister;
     Loader loader;
     AutoCompleteTextView name;
     EditText contact,email;
 String number;
 String patient_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        GetId();

    }

    private void GetId() {

        number=getIntent().getStringExtra("number");
        patient_id=getIntent().getStringExtra("patient_id");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back_black_24dp);
        toolbar.setTitle("Register");
        toolbar.setTitleTextColor(Color.WHITE);

        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);


        name=findViewById(R.id.name);
        contact=findViewById(R.id.contact);
        email=findViewById(R.id.email);

        btregister=findViewById(R.id.bt_register);
        btregister.setOnClickListener(this);

        contact.setText(""+ number);

    }




    @Override
    public void onClick(View view) {



        if(view==btregister) {


            if (name.getText().toString().trim().isEmpty() ) {

                Toast.makeText(this, "please enter name", Toast.LENGTH_SHORT).show();


            }  else if (UtilMethods.INSTANCE.isNetworkAvialable(RegisterActivity.this)) {

                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.updatePatient(RegisterActivity.this, name.getText().toString().trim(),email.getText().toString().trim(),patient_id
                    ,number,loader,this);


            } else {
                UtilMethods.INSTANCE.NetworkError(RegisterActivity.this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }






    }

    }



}
