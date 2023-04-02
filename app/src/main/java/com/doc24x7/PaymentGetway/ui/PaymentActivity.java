package com.doc24x7.PaymentGetway.ui;

import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.gson.Gson;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;
import org.json.JSONObject;

public class PaymentActivity extends AppCompatActivity implements PaymentResultListener , View.OnClickListener {

    RelativeLayout button ;
    String amount="";
    String doctorDetail="";
    String AppintmentDetail="";
    String namepack="";
    String doctorid="";
    String Symtom_id="";
    String contectnumber="";
    String emailuser="";
    String parent_slot_id="";
    String paymentStatus="";
    TextView PackageName,tv_amount,tvcredit;
    Loader loader;
    TextView okButton;
    ImageView cancel;
    Datum operator;
    String bookstatus="";
    String tv_datetime;
    String DateTile;

    public TextView name, specialities, address, qualification,amount_pay,amount_buttom;
    ImageView opImage;
    String slot_id;
    private static final String TAG = PaymentActivity.class.getSimpleName();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment_getway);
        Getid();
    }

    private void Getid() {
        cancel=findViewById(R.id.cancel);
        okButton=findViewById(R.id.okButton);
        cancel.setOnClickListener(this);
        okButton.setOnClickListener(this);
        loader = new Loader(PaymentActivity.this, android.R.style.Theme_Translucent_NoTitleBar);
        PackageName=findViewById(R.id.PackageName);
        tv_amount=findViewById(R.id.amount);
        amount_pay=findViewById(R.id.amount_pay);
        amount_buttom=findViewById(R.id.amount_buttom);
        tvcredit=findViewById(R.id.tvcredit);
        name = findViewById(R.id.name);
        specialities = findViewById(R.id.specialities);
        qualification = findViewById(R.id.qualification);
        address = findViewById(R.id.address);
         opImage = findViewById(R.id.opImage);
         amount=getIntent().getStringExtra("amount");
        doctorDetail=getIntent().getStringExtra("doctorDetail");
        AppintmentDetail=getIntent().getStringExtra("AppintmentDetail");
        namepack=getIntent().getStringExtra("namepack");
        paymentStatus=getIntent().getStringExtra("paymentStatus");
        doctorid=getIntent().getStringExtra("doctorid");
        Symtom_id=getIntent().getStringExtra("Symtom_id");
        bookstatus=getIntent().getStringExtra("bookstatus");
        slot_id=getIntent().getStringExtra("slot_id");
        parent_slot_id=getIntent().getStringExtra("parent_slot_id");
        tv_datetime=getIntent().getStringExtra("tv_datetime");
        DateTile=getIntent().getStringExtra("DateTile");


        Gson gson = new Gson();
        operator = gson.fromJson(doctorDetail, Datum.class);
        name.setText("Dr. " + operator.getName());
        qualification.setText("" + operator.getTypeName());
        address.setText("" + operator.getAddress());
        specialities.setText("" + operator.getTypeName());

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.doctordemo);


        Glide.with(this)
                .load(operator.getProfilePic())
                .apply(requestOptions)
                .into(opImage);


        tv_amount.setText("\u20B9  "+  amount );
        amount_pay.setText("\u20B9  "+  amount );
        amount_buttom.setText("\u20B9  "+  amount );

        tvcredit.setVisibility(View.GONE);

        Checkout.preload(getApplicationContext());
        button =  findViewById(R.id.btn_pay);
        button.setOnClickListener(this);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Payment Activity");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);

        contectnumber = balanceCheckResponse.getMobile();
        emailuser = balanceCheckResponse.getEmail();


    }

    @Override
    public void onClick(View view) {

        if(view==cancel){


            finish();
        }

        if (view==okButton){


            int   amount_toPay = Integer.parseInt(amount);
           // Doctor is not Available Today
            
            
            if(amount_toPay==0){

                Toast.makeText(this, "Doctor Can not Book appointment", Toast.LENGTH_SHORT).show();
                
            }else {

                startPayment();


            }
             

        }
 
    }

    public void startPayment() {

        int   amount_toPay = Integer.parseInt(amount);
 
        final Activity activity = this;
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.doctorlogo);

          checkout.setKeyID("rzp_test_s2M7AwrgFAzpBN");// Test
         // checkout.setKeyID("rzp_live_Ql4mpXZv9VJmKw"); //Live
        float amountRupees = Float.valueOf(amount_toPay)*100;
        try {
            JSONObject options = new JSONObject();
            options.put("name", "Doc24x7");
            options.put("description", "   Doc24x7 " );
            options.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            options.put("currency", "INR");
            options.put("theme.color", "#00E5FF");
            options.put("amount", amountRupees);
            JSONObject preFill = new JSONObject();
            preFill.put("email", ""+emailuser);
            preFill.put("contact", ""+contectnumber);
            options.put("prefill", preFill);
            checkout.open(activity, options);

        } catch (Exception e) {
            Toast.makeText(activity, "Error in payment: " + e.getMessage(), Toast.LENGTH_SHORT)
                    .show();
            e.printStackTrace();
        }
    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentSuccess(String razorpayPaymentID) {
        try {
            Toast.makeText(this, "Payment Successful: " + razorpayPaymentID, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentSuccess", e);
        }

        HitApiSusses(razorpayPaymentID);
    }

    private void HitApiSusses(String razorpayPaymentID) {

        Datum patAppointmentDetail = new Gson().fromJson(getIntent().getStringExtra("AppintmentDetail"), Datum.class);


        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {


            loader.show();
            loader.setCancelable(false);
            loader.setCanceledOnTouchOutside(false);

            UtilMethods.INSTANCE.Paymentsusses(this, ""+doctorid,""+razorpayPaymentID, loader,amount,operator,
                    Symtom_id,slot_id,parent_slot_id,operator,""+tv_datetime,this,DateTile ,patAppointmentDetail,bookstatus,paymentStatus);


        } else {
            UtilMethods.INSTANCE.NetworkError(this, this.getResources().getString(R.string.network_error_title),
                    this.getResources().getString(R.string.network_error_message));
        }



    }


    @SuppressWarnings("unused")
    @Override
    public void onPaymentError(int code, String response) {
        try {
            Toast.makeText(this, "Payment failed: " + code + " " + response, Toast.LENGTH_SHORT).show();
        } catch (Exception e) {
            Log.e(TAG, "Exception in onPaymentError", e);
        }
    }


}
