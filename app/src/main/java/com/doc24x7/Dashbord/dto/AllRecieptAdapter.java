package com.doc24x7.Dashbord.dto;



import static android.content.Context.MODE_PRIVATE;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.Dashbord.ui.AllRecieptActivity;
import com.doc24x7.Dashbord.ui.MedicineListAdapter;
import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AllRecieptAdapter extends RecyclerView.Adapter<AllRecieptAdapter.ViewHolder>
{   Context context;
  ArrayList<Data> list;
    public AllRecieptAdapter(Context context, ArrayList<Data> list) {
        this.context = context;
        this.list = list;
    }

    File imagePath;
    @NonNull
    @Override
    public AllRecieptAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType)
    {
        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.all_reciept,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllRecieptAdapter.ViewHolder holder, int position)
    {
         Data data=list.get(position);
         holder.name.setText("Dr. "+data.getName());
         holder.mobile.setText("Phone : "+data.getMobile());
         holder.email.setText("Email : "+data.getEmail());
         holder.clinic_name.setText(""+data.getClinic_name());
         holder.Patientname.setText("Name: "+data.getPatient_details());
         holder.Preciption_description.setText("Name: "+data.getPreciption_description());

        holder.share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Bitmap bitmap   = ((AllRecieptActivity)context).takeScreenshot();
            }
        });
        String str=data.getMedicine_details();
        MeicineModel meicineModel = new Gson().fromJson(str, MeicineModel.class);
        if(meicineModel!=null)
        {
            ArrayList<DETAIL> arrayList = meicineModel.getMedicineMode()==null?new ArrayList<>():meicineModel.getMedicineMode();

            if (arrayList.size() > 0)
            {
                MedicineListAdapter medicinelistadapter = new MedicineListAdapter(arrayList, context);
                LinearLayoutManager mLayoutManager = new LinearLayoutManager(context);
               holder. recycler_view_Preciption.setLayoutManager(mLayoutManager);
                holder.recycler_view_Preciption.setItemAnimator(new DefaultItemAnimator());
                DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(holder.recycler_view_Preciption.getContext(),
                        mLayoutManager.getOrientation());
                holder.recycler_view_Preciption.addItemDecoration(dividerItemDecoration);
                holder.recycler_view_Preciption.setAdapter(medicinelistadapter);
                holder.recycler_view_Preciption.setVisibility(View.VISIBLE);
            } else {
                holder.recycler_view_Preciption.setVisibility(View.GONE);
            }
        }else{
            holder.recycler_view_Preciption.setVisibility(View.GONE);
            holder.title.setVisibility(View.GONE);
        }


        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);

        if(data.getDigital_signature_img()!=null){
            holder.signature.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(data.getDigital_signature_img())
                    .apply(requestOptions)
                    .into(holder.signature);
        }else{
            holder.signature.setVisibility(View.GONE);
        }



        if(data.getPreciption_img()!=null)
        {
            Log.d("dfjkgh",data.getPreciption_img());
            holder.Preciption_img.setVisibility(View.VISIBLE);
            Glide.with(context)
                    .load(data.getPreciption_img())
                    .apply(requestOptions)
                    .into(holder.Preciption_img);
        }
        else
        {
            holder.Preciption_img.setVisibility(View.GONE);
        }

        try {
            String pacientDatail=data.getPatient_details();
            PatientDetailModel patientDetailModel = new Gson().fromJson(pacientDatail, PatientDetailModel.class);

            holder.Patientname.setText("Patient Name: "+patientDetailModel.getPatientName()+" Age: "+patientDetailModel.getPatientAge()+" years");
            holder.Patinetmobile.setText("Mobile Number: "+patientDetailModel.getPatientMobile());
          //  holder.bookdate.setText("Booking Date: "+context.getStringExtra("book_date")    );
        } catch (Exception e) {
            SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
            Data balanceCheckResponse2 = new Gson().fromJson(balanceResponse, Data.class);
            holder.Patientname.setText("Patient Name: "+balanceCheckResponse2.getName());
            holder.Patinetmobile.setText("Mobile Number: "+balanceCheckResponse2.getMobile());
            e.printStackTrace();
        }






         //  Bitmap bitmap =holder. takeScreenshot();
    }

    @Override
    public int getItemCount()
    {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder
    {
        RecyclerView recycler_view_Preciption;

        String response="";
        String doctorDetail="";
        TextView clinic_name,name,mobile,email;
        //  Data operator;
        ImageView Preciption_img,share,signature;
        TextView Preciption_description,Patinetmobile,bookdate,Patientname;
        LinearLayout title;
        View rootView;

        public ViewHolder(@NonNull View v)
        {
            super(v);
            Preciption_description=v.findViewById(R.id.Preciption_description);

            Preciption_img=v.findViewById(R.id.Preciption_img);
            signature=v.findViewById(R.id.signature);

            clinic_name=v.findViewById(R.id.clinic_name);
            title=v.findViewById(R.id.title);
            share =v.findViewById(R.id.share);
        //    rootView =v.findViewById(android.R.id.content).getRootView();
            name=v.findViewById(R.id.name);
            mobile=v.findViewById(R.id.mobile);
            email=v.findViewById(R.id.email);
            Patinetmobile=v.findViewById(R.id.Patinetmobile);
            Patientname=v.findViewById(R.id.Patientname);
            bookdate=v.findViewById(R.id.bookdate);
            recycler_view_Preciption=v.findViewById(R.id.recycler_view_Preciption);





        }

     /*   public  Bitmap takeScreenshot()
        {
            rootView.setDrawingCacheEnabled(true);
            saveBitmap(rootView.getDrawingCache());
            return rootView.getDrawingCache();
        }
*/

    }

/*

    public   void saveBitmap(Bitmap bitmap)
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
        context.startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }*/

}
