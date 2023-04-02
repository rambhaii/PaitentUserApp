package com.doc24x7.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.Dashbord.dto.DETAIL;
import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Dashbord.dto.GalleryListResponse;
import com.doc24x7.Dashbord.dto.MeicineModel;
import com.doc24x7.Dashbord.dto.PatientDetailModel;
import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class PreciptionActivity extends AppCompatActivity
{

    RecyclerView recycler_view_Preciption;

    String response="";
    String doctorDetail="";
    TextView clinic_name,name,mobile,email;
  //  Data operator;
    ImageView Preciption_img,share,signature;
    TextView Preciption_description,Patinetmobile,bookdate,Patientname;
    LinearLayout title;
    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        saveBitmap(rootView.getDrawingCache());
        return rootView.getDrawingCache();
    }
    File imagePath;
    public void saveBitmap(Bitmap bitmap)
    {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        String v=imagePath.getAbsolutePath();
        Log.e("vvvvvv",v);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            shareIt();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }
    }
    private void shareIt()
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "Doc24x7 App priscription";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Doc24x7");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preciption);

        Preciption_description=findViewById(R.id.Preciption_description);

         Preciption_img=findViewById(R.id.Preciption_img);
        signature=findViewById(R.id.signature);

        clinic_name=findViewById(R.id.clinic_name);
        title=findViewById(R.id.title);
        share = findViewById(R.id.share);
        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap = takeScreenshot();

            }
        });
        name=findViewById(R.id.name);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        Patinetmobile=findViewById(R.id.Patinetmobile);
        Patientname=findViewById(R.id.Patientname);
        bookdate=findViewById(R.id.bookdate);

        response=getIntent().getStringExtra("response");

        doctorDetail=getIntent().getStringExtra("doctorDetail");

        Data balanceCheckResponse = new Gson().fromJson(response, Data.class);

        clinic_name.setText(""+balanceCheckResponse.getClinic_name() );
        name.setText("Dr. "+balanceCheckResponse.getName() );
        mobile.setText("Phone : "+balanceCheckResponse.getMobile() );
        email.setText("Email : "+balanceCheckResponse.getEmail() );
        Log.d("skdjghkdjgh",""+balanceCheckResponse.getPreciption_description());
        Preciption_description.setText("Description : "+balanceCheckResponse.getPreciption_description() );

        try {
            String pacientDatail=balanceCheckResponse.getPatient_details();
            PatientDetailModel patientDetailModel = new Gson().fromJson(pacientDatail, PatientDetailModel.class);
            Patientname = findViewById(R.id.Patientname);
            Patinetmobile = findViewById(R.id.Patinetmobile);
            bookdate = findViewById(R.id.bookdate);
            Patientname.setText("Patient Name: "+patientDetailModel.getPatientName()+" Age: "+patientDetailModel.getPatientAge()+" years");
            Patinetmobile.setText("Mobile Number: "+patientDetailModel.getPatientMobile());
            bookdate.setText("Booking Date: "+getIntent().getStringExtra("book_date")    );
        } catch (Exception e) {
            SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
            Data balanceCheckResponse2 = new Gson().fromJson(balanceResponse, Data.class);
            Patientname.setText("Patient Name: "+balanceCheckResponse2.getName());
            Patinetmobile.setText("Mobile Number: "+balanceCheckResponse2.getMobile());
            e.printStackTrace();
        }

       // customerimage

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);

        Log.e("Preciption_img",""+ balanceCheckResponse.getPreciption_img() );
        Log.e("signature",""+ balanceCheckResponse.getDigital_signature_img() );


if(!balanceCheckResponse.getPreciption_img().contains("no-image.png"))
{
    Preciption_img.setVisibility(View.VISIBLE);
    Glide.with(this)
            .load(balanceCheckResponse.getPreciption_img())
            .apply(requestOptions)
            .into(Preciption_img);
}
else
{
    Preciption_img.setVisibility(View.GONE);
}

        if(balanceCheckResponse.getDigital_signature_img()!=null)
        {
            signature.setVisibility(View.VISIBLE);
            Glide.with(this)
                    .load(balanceCheckResponse.getDigital_signature_img())
                    .apply(requestOptions)
                    .into(signature);
        }else{
            signature.setVisibility(View.GONE);
        }

        recycler_view_Preciption=findViewById(R.id.recycler_view_Preciption);
        String str=balanceCheckResponse.getMedicine_details();
        str.replace("\\\\","");
        str.replace("\"","");
        MeicineModel meicineModel = new Gson().fromJson(str, MeicineModel.class);
if(meicineModel!=null)
{
    ArrayList<DETAIL> arrayList = meicineModel.getMedicineMode()==null?new ArrayList<>():meicineModel.getMedicineMode();

    if (arrayList.size() > 0)
    {
        MedicineListAdapter  medicinelistadapter = new MedicineListAdapter(arrayList, this);
        LinearLayoutManager  mLayoutManager = new LinearLayoutManager(this);
        recycler_view_Preciption.setLayoutManager(mLayoutManager);
        recycler_view_Preciption.setItemAnimator(new DefaultItemAnimator());
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(recycler_view_Preciption.getContext(),
                mLayoutManager.getOrientation());
        recycler_view_Preciption.addItemDecoration(dividerItemDecoration);
        recycler_view_Preciption.setAdapter(medicinelistadapter);
        recycler_view_Preciption.setVisibility(View.VISIBLE);
    } else {
        recycler_view_Preciption.setVisibility(View.GONE);
    }
}else
{
    recycler_view_Preciption.setVisibility(View.GONE);
    title.setVisibility(View.GONE);
}


      //UtilMethods.INSTANCE.GetMedicineById(this,balanceCheckResponse.getMedicine_id(),null,recycler_view_Preciption);




      Preciption_img.setOnClickListener(new View.OnClickListener() {
          @Override
          public void onClick(View view) {


              LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
              View viewpopup = inflater.inflate(R.layout.popupfullimage, null);


              Button okButton = (Button) viewpopup.findViewById(R.id.okButton);
              ImageView popPreciption_img = (ImageView) viewpopup.findViewById(R.id.popPreciption_img);

              final Dialog dialog = new Dialog(PreciptionActivity.this);

              dialog.setCancelable(false);
              dialog.setContentView(viewpopup);
              dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


              Glide.with(PreciptionActivity.this)
                      .load(balanceCheckResponse.getPreciption_img())
                      .apply(requestOptions)
                      .into(popPreciption_img);


              okButton.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View v) {


                      dialog.dismiss();



                  }
              });



              dialog.show();






            /*  Preciption_img.getLayoutParams().height = ViewGroup.LayoutParams.MATCH_PARENT;
              Preciption_img.getLayoutParams().width = ViewGroup.LayoutParams.MATCH_PARENT;
              Preciption_img.setAdjustViewBounds(false);
              Preciption_img.setScaleType(ImageView.ScaleType.FIT_XY);*/







          }
      });


    }

    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>();
    MedicineListAdapter medicinelistadapter;
    LinearLayoutManager mLayoutManager;



 }