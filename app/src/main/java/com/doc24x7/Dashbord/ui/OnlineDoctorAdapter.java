package com.doc24x7.Dashbord.ui;

//public class OnlineDoctorAdapter  {
//}


import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Login.dto.Data;

import com.doc24x7.R;
import com.doc24x7.Splash.ui.SelectAccountActivity;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import static android.content.Context.MODE_PRIVATE;


public class OnlineDoctorAdapter extends RecyclerView.Adapter<OnlineDoctorAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsListss;
    private Activity mContext;
    private String currentDay;
    private String currentDate;
    public void filterss(ArrayList<Datum> newlist) {
        transactionsListss=new ArrayList<>();
        transactionsListss.addAll(newlist);
        notifyDataSetChanged();
    }
    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView name, specialities, address, qualification, expwrence, consultation, consultfor,doctorsid,docid,experiance;
        ImageView opImage;
        Button idCardView, id_chatnow;
        CardView ic_card_ontap;
        ProgressBar Progress;
        public MyViewHolder(View view) {
            super(view);
            name = view.findViewById(R.id.name);
            specialities = view.findViewById(R.id.specialities);
            qualification = view.findViewById(R.id.qualification);
            address = view.findViewById(R.id.etaddress);
            experiance = view.findViewById(R.id.experiance);
            opImage = view.findViewById(R.id.opImage);
            idCardView = view.findViewById(R.id.idCardView);
            ic_card_ontap = view.findViewById(R.id.ic_card_ontap);
            Progress = view.findViewById(R.id.Progress);
            doctorsid=view.findViewById(R.id.docid);
            expwrence = view.findViewById(R.id.expwrence);
            consultation = view.findViewById(R.id.consultation);
         // consultfor = view.findViewById(R.id.consultfor);

        }
    }

    public OnlineDoctorAdapter(ArrayList<Datum> transactionsListss, Activity mContext) {
        this.transactionsListss = transactionsListss;
        this.mContext = mContext;


    }

    @Override
    public OnlineDoctorAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.onlinedoctorviewnew, parent, false);

        return new OnlineDoctorAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final OnlineDoctorAdapter.MyViewHolder holder, int position) {

        final Datum operator = transactionsListss.get(position);

        holder.name.setText("Dr. " + operator.getName());
        holder.address.setText("" + operator.getAddress());
        holder.qualification.setText("" + operator.getTypeName());
        holder.experiance.setText("" + operator.getExperience()+"  Year experiance");
        holder.doctorsid.setText("ID : DR"+operator.getId());

        //  holder.specialities.setText("" + operator.getTypeName()+operator.getConsult_for());
//        holder.expwrence.setText("" + operator.getExperience() + " " + mContext.getResources().getString(R.string.year_experiance));
     holder.consultation.setText("\u20B9 " + operator.getDoctor_fees() + " FEES" );
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.doctordemo);
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        SimpleDateFormat sdff = new SimpleDateFormat("yyyy-MM-dd");
        String currentDate = sdff.format(new Date());
        Date CurrentTime = null;
        try {
            CurrentTime = sdf.parse(sdf.format(new Date()));
            currentDay = CurrentTime.toString();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        Glide.with(mContext)
                .load(operator.getProfilePic())
                .apply(requestOptions)
                .into(holder.opImage);
     holder.ic_card_ontap.setOnClickListener(new View.OnClickListener() {
         @Override
         public void onClick(View v) {
             SharedPreferences myPreferences =  mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
             String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
             String pref = balanceResponse;
             if(pref==null||pref.equalsIgnoreCase(""))
             {
                 Intent intent = new Intent(mContext, SelectAccountActivity.class);
                 mContext.startActivity(intent);
             }
             else
             {
                 ShowDailog(operator);
             }

         }
     });
//        holder.idCardView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                SharedPreferences myPreferencess = mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
//                String balanceResponses = myPreferencess.getString(ApplicationConstant.INSTANCE.one, null);
//                String pref = ""+balanceResponses;
//                if(pref.equalsIgnoreCase(""))
//                {
//                    Intent intent = new Intent(mContext, SelectAccountActivity.class);
//                    mContext.startActivity(intent);
//                }
//                else {
//                    if (holder.idCardView.getText().equals(mContext.getResources().getString(R.string.consult_now))) {
//                        holder.Progress.setVisibility(View.VISIBLE);
//                        holder.idCardView.setText("Contacting doctor please wait");
//                        holder.idCardView.setEnabled(false);
//                        SharedPreferences myPreferences = mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
//                        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
//                        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
//                        String member_id = balanceCheckResponse.getPatient_id();
//                        HashMap<String, Object> taskData = new HashMap<String, Object>();
//                        taskData.put("userId", member_id);
//                        taskData.put("DoctorId", operator.getId());
//                        taskData.put("Status", false);
//                        FirebaseFirestore.getInstance().collection("OnlineRequest")
//                                .add(taskData)
//                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
//                                    @Override
//                                    public void onSuccess(DocumentReference documentReference) {
//                                        Log.e("saSa", documentReference.getId());
//                                        FirebaseFirestore.getInstance().collection("OnlineRequest").document(documentReference.getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
//                                            @Override
//                                            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
//                                                Log.e("saSa", documentSnapshot.get("Status").toString());
//                                                if (documentSnapshot.get("Status").toString().equals("false")) {
//                                                    UtilMethods.INSTANCE.request_to_doctor(mContext, operator.getId(), documentReference.getId(), "1", operator.getConsult_for(), operator.getDoctor_fees(), null);
//                                                    holder.Progress.setVisibility(View.VISIBLE);
//                                                    holder.idCardView.setText("Contacting doctor please wait");
//                                                    holder.idCardView.setEnabled(false);
//                                                    new Handler().postDelayed(new Runnable() {
//                                                        public void run() {
//
//                                                            try {
//                                                                UtilMethods.INSTANCE.request_to_doctor(mContext, operator.getId(), documentReference.getId(), "2", operator.getConsult_for(), operator.getDoctor_fees(), null);
//                                                                holder.Progress.setVisibility(View.GONE);
//                                                                holder.idCardView.setText("Doctor is busy please consult other doctors");
//                                                                holder.idCardView.setEnabled(true);
//                                                            } catch (Exception e) {
//
//                                                            }
//                                                            // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
//                                                        }
//                                                    }, 40000);
//                                                } else {
//                                                    if (documentSnapshot.get("DoctorId").toString().equals(operator.getId())) {
//                                                        holder.Progress.setVisibility(View.GONE);
//                                                        holder.idCardView.setText("Continue");
//                                                        holder.idCardView.setEnabled(true);
//                                                    } else {
//                                                        holder.Progress.setVisibility(View.GONE);
//                                                        holder.idCardView.setText("Doctor is busy please consult other doctors");
//                                                        holder.idCardView.setEnabled(false);
//                                                        Loader loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
//                                                        loader.show();
//                                                        loader.setCancelable(false);
//                                                        loader.setCanceledOnTouchOutside(false);
//                                                        UtilMethods.INSTANCE.GetDrProfileData(mContext, documentSnapshot.get("DoctorId").toString(), operator, currentDate, loader);
//                                                    }
//
//                                                }
//
//                                            }
//                                        });
//                                    }
//                                })
//                                .addOnFailureListener(new OnFailureListener() {
//                                    @Override
//                                    public void onFailure(@NonNull Exception e) {
//                                        Log.e("saSa", e.getMessage());
//                                    }
//                                });
//                    } else if (holder.idCardView.getText().equals("Continue")) {
//                        Loader loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
//                        loader.show();
//                        loader.setCancelable(false);
//                        loader.setCanceledOnTouchOutside(false);
//                        UtilMethods.INSTANCE.chkpayment(mContext, operator.getId(), operator, loader, null,
//                                "" + operator.getId(), "" + 0, "" + 0,
//                                "" + operator.getDoctor_fees(), operator, "" + " " + "" + currentDate, "" + currentDate, null, "1", "0");
//                    }
//                }
//            }
//        });
     }
   public  void ShowDailog(Datum operator) {
       AlertDialog alertDialog = new AlertDialog.Builder(mContext)
               .setView(R.layout.doctor_details_dailog) .show();
       TextView btnonline=alertDialog.findViewById(R.id.online);
       ProgressBar progress=alertDialog.findViewById(R.id.progress);
       ImageView cancle=alertDialog.findViewById(R.id.cancel);
       ImageView opImage=alertDialog.findViewById(R.id.opImage);
       TextView name=alertDialog.findViewById(R.id.name);
       TextView specialities=alertDialog.findViewById(R.id.specialities);
       TextView exprieance=alertDialog.findViewById(R.id.exprieance);
       TextView qualification=alertDialog.findViewById(R.id.qualification);
       TextView clinic_fees=alertDialog.findViewById(R.id.clinic_fees);
       TextView address=alertDialog.findViewById(R.id.address);
       TextView consultfor=alertDialog.findViewById(R.id.consultfor);
       Button bookappointment=alertDialog.findViewById(R.id.bookappointment);
       name.setText("Dr. "+operator.getName());
       qualification.setText(""+operator.getTypeName());
       clinic_fees.setText("\u20B9 " + operator.getDoctor_fees() + " FEES" );
       exprieance.setText(operator.getExperience()+"  Year experiance");
       address.setText(operator.getAddress()+"");
       consultfor.setText(operator.getConsult_for()+"");
       RequestOptions requestOptions = new RequestOptions();
       requestOptions.placeholder(R.drawable.customer_support);
       requestOptions.error(R.drawable.doctordemo);
       Glide.with(mContext)
               .load(operator.getProfilePic())
               .apply(requestOptions)
               .into(opImage);
       cancle.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               alertDialog.dismiss();
           }
       });
       bookappointment.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               SharedPreferences myPreferencesss =  mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
               String balanceResponsess = myPreferencesss.getString(ApplicationConstant.INSTANCE.one, null);
               String pref = ""+balanceResponsess;
               if(pref==null||pref.equalsIgnoreCase(""))
               {
                   Intent intent = new Intent(mContext, SelectAccountActivity.class);
                   mContext.startActivity(intent);
               }
                else {
                    if (bookappointment.getText().equals(mContext.getResources().getString(R.string.consult_now))) {
                        progress.setVisibility(View.VISIBLE);
                        bookappointment.setText("Contacting doctor please wait");
                        bookappointment.setEnabled(false);
                        SharedPreferences myPreferences = mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
                        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
                        String member_id = balanceCheckResponse.getPatient_id();
                        HashMap<String, Object> taskData = new HashMap<String, Object>();
                        taskData.put("userId", member_id);
                        taskData.put("DoctorId", operator.getId());
                        taskData.put("Status", false);
                        FirebaseFirestore.getInstance().collection("OnlineRequest")
                                .add(taskData)
                                .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    @Override
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.e("saSa", documentReference.getId());
                                        FirebaseFirestore.getInstance().collection("OnlineRequest").document(documentReference.getId()).addSnapshotListener(new EventListener<DocumentSnapshot>() {
                                            @Override
                                            public void onEvent(DocumentSnapshot documentSnapshot, FirebaseFirestoreException e) {
                                                Log.e("saSa", documentSnapshot.get("Status").toString());
                                                if (documentSnapshot.get("Status").toString().equals("false")) {
                                                    UtilMethods.INSTANCE.request_to_doctor(mContext, operator.getId(), documentReference.getId(), "1", operator.getConsult_for(), operator.getDoctor_fees(), null);
                                                    progress.setVisibility(View.VISIBLE);
                                                    bookappointment.setText("Contacting doctor please wait");
                                                    bookappointment.setEnabled(false);
                                                    new Handler().postDelayed(new Runnable() {
                                                        public void run() {

                                                            try {
                                                                UtilMethods.INSTANCE.request_to_doctor(mContext, operator.getId(), documentReference.getId(), "2", operator.getConsult_for(), operator.getDoctor_fees(), null);
                                                                progress.setVisibility(View.GONE);
                                                                bookappointment.setText("Doctor is busy please consult other doctors");
                                                                bookappointment.setEnabled(true);
                                                            } catch (Exception e) {

                                                            }
                                                            // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
                                                        }
                                                    }, 40000);
                                                } else {
                                                    if (documentSnapshot.get("DoctorId").toString().equals(operator.getId())) {
                                                        progress.setVisibility(View.GONE);
                                                        bookappointment.setText("Continue");
                                                        bookappointment.setEnabled(true);
                                                    } else {
                                                        progress.setVisibility(View.GONE);
                                                        bookappointment.setText("Doctor is busy please consult other doctors");
                                                        bookappointment.setEnabled(false);
                                                        Loader loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                                                        loader.show();
                                                        loader.setCancelable(false);
                                                        loader.setCanceledOnTouchOutside(false);
                                                        UtilMethods.INSTANCE.GetDrProfileData(mContext, documentSnapshot.get("DoctorId").toString(), operator, currentDate, loader);
                                                    }

                                                }

                                            }
                                        });
                                    }
                                })
                                .addOnFailureListener(new OnFailureListener() {
                                    @Override
                                    public void onFailure(@NonNull Exception e) {
                                        Log.e("saSa", e.getMessage());
                                    }
                                });
                    } else if (bookappointment.getText().equals("Continue")) {
                        Loader loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        UtilMethods.INSTANCE.chkpayment(mContext, operator.getId(), operator, loader, null,
                                "" + operator.getId(), "" + 0, "" + 0,
                                "" + operator.getDoctor_fees(), operator, "" + " " + "" + currentDate, "" + currentDate, null, "1", "0");
                    }
                }
            }

       });
    }
    @Override
    public int getItemCount() {
        return transactionsListss.size();
    }

}