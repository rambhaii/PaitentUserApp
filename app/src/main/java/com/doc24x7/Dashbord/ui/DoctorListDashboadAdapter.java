package com.doc24x7.Dashbord.ui;

import static android.content.Context.MODE_PRIVATE;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.DOctoeByIdActivityOffline;
import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.NewDashboard.Doctor_details;
import com.doc24x7.R;
import com.doc24x7.Splash.ui.SelectAccountActivity;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.UtilMethods;
import com.google.gson.Gson;

import java.util.ArrayList;

import eightbitlab.com.blurview.BlurView;


public class DoctorListDashboadAdapter extends RecyclerView.Adapter<DoctorListDashboadAdapter.MyViewHolder> {
    private ArrayList<Datum> transactionsList;
    private Activity mContext;
    public void filter(ArrayList<Datum> newlist) {
        transactionsList=new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, specialities, address, qualification,expwrence,consultation,clinic_fees,doctorid;
        ImageView opImage;
        CardView idCardView;

        TextView daynext,docid;

        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
           // specialities = view.findViewById(R.id.specialities);
            qualification = view.findViewById(R.id.qualification);
            address = view.findViewById(R.id.address);
            //clinic_fees = view.findViewById(R.id.clinic_fees);
            opImage = view.findViewById(R.id.opImage);
           idCardView = view.findViewById(R.id.ic_card_ontap);
//            id_chatnow = view.findViewById(R.id.id_chatnow);
            expwrence = view.findViewById(R.id.expwrence);
            consultation = view.findViewById(R.id.consultation);
//            doctorid=view.findViewById(R.id.doctorsid);
//            daynext=view.findViewById(R.id.daynext);
            docid=view.findViewById(R.id.docid);
        }
    }

    public DoctorListDashboadAdapter(ArrayList<Datum> transactionsList, Activity mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public DoctorListDashboadAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.doctor_dash_view, parent, false);

        return new DoctorListDashboadAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final DoctorListDashboadAdapter.MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);

        holder.name.setText("Dr. " + operator.getName());
        holder.qualification.setText("" + operator.getTypeName());
       holder.address.setText("" + operator.getAddress());
       // holder.specialities.setText("" + operator.getTypeName());
       holder.expwrence.setText("" + operator.getExperience()+" "+mContext.getResources().getString(R.string.year_experiance));
        holder.consultation.setText("\u20B9 "+ operator.getDoctor_fees()+" "+"FEES" );
//        holder.clinic_fees.setText("\u20B9 "+ operator.getClinic_fees()+" "+"Clinic Fees");
        RequestOptions requestOptions = new RequestOptions();
       requestOptions.placeholder(R.drawable.customer_support);
//          String str=operator.getAvailableSlot().get(0).getDate();
//        //  SimpleDateFormat sm=new SimpleDateFormat("yyyy-MM-dd");
//        Date c = Calendar.getInstance().getTime();
//       // Date d1=Calendar.getInstance();
//        Date dt = new Date();
//        Calendar c2 = Calendar.getInstance();
//        c2.setTime(dt);
//        c2.add(Calendar.DATE, 1);
//        dt = c2.getTime();
//
//        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
//        String formattedDate = df.format(c);
//          String nextdate=df.format(dt);
//
//
//        if(formattedDate.equals(operator.getAvailableSlot().get(0).getDate()))
//        {
//          //  Toast.makeText(mContext, ""+formattedDate, Toast.LENGTH_SHORT).show();
//            holder.daynext.setText("Today Available");
//        }
//        else if(nextdate.equals(operator.getAvailableSlot().get(0).getDate()))
//        {
//            holder.daynext.setText("Next Day Available");
//        }
//        else
//        {
//            holder.daynext.setText("Available "+operator.getAvailableSlot().get(0).getDate()+"");
//        }
//        else if(formattedDate.equals(operator.getAvailableSlot().get(0).getDate()))
//        {
//
//        }

//        if(operator.getAvailableSlot().get(0).getAvailableDay().equals("nextDay"))
//        {
//
//            holder.daynext.setText(" Next Day Available");
//        }
//        else {
//            holder.daynext.setText("Today Available");
//        }
        holder.docid.setText("ID : DR"+operator.getId());

    //    Log.e("datesdsdsds  : ",formattedDate);
      // Toast.makeText(mContext, operator.getAvailableSlot().get(0).getDate(), Toast.LENGTH_SHORT).show();
       // holder.doctorid.setText(operator.getAvailableSlot().get(0).getStart_time()+" - "+operator.getAvailableSlot().get(0).getEnd_time());
        requestOptions.error(R.drawable.doctordemo);
        Glide.with(mContext)
                .load(operator.getProfilePic())
                .apply(requestOptions)
                .into(holder.opImage);
           holder.idCardView.setOnClickListener(new View.OnClickListener() {
             @Override
            public void onClick(View view) {


                 AlertDialog alertDialog = new AlertDialog.Builder(mContext,R.style.MyTransparentDialog)
                        .setView(R.layout.choosebooking) .show();
                 TextView btnonline=alertDialog.findViewById(R.id.online);
                 alertDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
                 BlurView blurView= alertDialog.findViewById(R.id.blurView);
                 View decorView =mContext.getWindow().getDecorView();
                 UtilMethods.INSTANCE.blur(mContext,blurView,decorView);
                   btnonline.setText("ONLINE CONSULT FEES : "+operator.getDoctor_fees());
                ImageView cancle=alertDialog.findViewById(R.id.cancel);
                cancle.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        alertDialog.dismiss();
                    }
                });
                TextView btnoffline=alertDialog.findViewById(R.id.offline);
                btnoffline.setText("ON CLINIC CONSULT FEES : "+operator.getClinic_fees());
                btnonline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        alertDialog.dismiss();

                        SharedPreferences myPreferences =  mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
                        String pref = balanceResponse;

                        Log.e("prefffff","As : Online  "+ pref);


                        if(pref==null||pref.equalsIgnoreCase(""))
                        {
                            Intent intent = new Intent(mContext, SelectAccountActivity.class);
                            mContext.startActivity(intent);
                        }
                        else {
                            Intent intent = new Intent(mContext, Doctor_details.class);
                            intent.putExtra("response", "" + new Gson().toJson(operator));
                            intent.putExtra("type", "1");
                            intent.putExtra("doctorDetail", new Gson().toJson(operator));
                            intent.putExtra("bookstatus","1");
                            intent.putExtra("online_fee",operator.getDoctor_fees());
                            mContext.startActivity(intent);
                        }


                    }
                });
                btnoffline.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {


                        alertDialog.dismiss();
                        SharedPreferences myPreferences =  mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
                        String pref = ""+balanceResponse;

                        Log.e("prefffff","As : offine   "+ pref);


                        if(pref==null||pref.equalsIgnoreCase(""))
                        {
                            Intent intent = new Intent(mContext, SelectAccountActivity.class);
                            mContext.startActivity(intent);
                        }
                        else {
                            Intent i = new Intent(mContext, DOctoeByIdActivityOffline.class);
                            i.putExtra("response", "" + new Gson().toJson(operator));
                            i.putExtra("type", "1");
                            i.putExtra("doctorDetail", new Gson().toJson(operator));
                            i.putExtra("bookstatus", "2");
                            i.putExtra("offline_fee", operator.getClinic_fees());
                            mContext.startActivity(i);
                        }

                    }
                });
//                        .setPositiveButton("Submit", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//
//
//
//                            }
//                      .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                dialog.dismiss();
//                            }
//                        }).show();




                ///////////////////////
//


             }
        });


//        holder.id_chatnow.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//
//                Log.e("getMobile", "" + operator.getMobile());
//
//                String url = "https://api.whatsapp.com/send?phone=" + "+91" + operator.getMobile();
//                Intent i = new Intent(Intent.ACTION_VIEW);
//                i.setData(Uri.parse(url));
//                mContext.startActivity(i);
//
//            }
//        });

//        holder.idCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(mContext, Doctor_details.class);
//                        intent.putExtra("response", "" + new Gson().toJson(operator));
//                        intent.putExtra("doctorDetail", new Gson().toJson(operator));
//                        intent.putExtra("online_fee",operator.getDoctor_fees());
//                        mContext.startActivity(intent);
//            }
//        });
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}