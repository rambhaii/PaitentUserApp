package com.doc24x7.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.R;

public class DoctorDetailActivity extends AppCompatActivity {

    String id="";
    String name="";
    String image="";
    String address="";
    String service_name="";
    TextView namedoc,service_namedoc,addressdoc;
    ImageView doctorimafe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_detail);



        namedoc=findViewById(R.id.name);
        service_namedoc=findViewById(R.id.service_name);
        addressdoc=findViewById(R.id.address);
        doctorimafe=findViewById(R.id.doctorimafe);





        id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        image=getIntent().getStringExtra("image");
        address=getIntent().getStringExtra("address");
        service_name=getIntent().getStringExtra("service_name");

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("View Profile");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        namedoc.setText(""+name);
        service_namedoc.setText(""+service_name);
        addressdoc.setText(""+address);


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.noavailable);



        Glide.with(this)
                .load( image)
                .apply(requestOptions)
                .into(doctorimafe);


    }
}