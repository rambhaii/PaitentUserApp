package com.doc24x7;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.doc24x7.Dashbord.dto.Datum;

import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.gson.Gson;


import org.json.JSONException;
import org.json.JSONObject;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;


public class DoctorPatientAdapter extends RecyclerView.Adapter<DoctorPatientAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Activity mContext;
    int status;
    private int mYear, mMonth, mDay, mHour, mMinute;
    JSONObject object= null;
    String Age = "";
    String Gender = "";
    String Symptoms = "";
    String gender = "";
    String PatientName="";
    String PatientMobile="";
    String Type="";
    String Weight="";
    String Height="";
    String Bp="";
    String Sugar="";
    String  ReportUrl="";
    Loader loader;

    public void filter(ArrayList<Datum> newlist) {
        transactionsList=new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title,appointment_id,tv_discuraption,craetedate,enddate,View_Preciption,book_date,consult_date,Patientdetails,emergency_time;
        public ImageView chat,reUploadReport;
        RecyclerView recycler_view_Preciption;



        public MyViewHolder(View view) {
            super(view);
            tv_title =  view.findViewById(R.id.tv_title);
            tv_discuraption =  view.findViewById(R.id.tv_discuraption);
            craetedate =  view.findViewById(R.id.craetedate);
            enddate =  view.findViewById(R.id.enddate);
            tv_discuraption =  view.findViewById(R.id.tv_discuraption);
            recycler_view_Preciption =  view.findViewById(R.id.recycler_view_Preciption);
            View_Preciption =  view.findViewById(R.id.View_Preciption);
            book_date =  view.findViewById(R.id.book_date);
            appointment_id =  view.findViewById(R.id.appointment_id);
            consult_date =  view.findViewById(R.id.consult_date);
            Patientdetails=view.findViewById(R.id.patientdetails);
            emergency_time=view.findViewById(R.id.emergency_time);
            chat=view.findViewById(R.id.chat);
           reUploadReport=view.findViewById(R.id.reUploadReport);
        }
    }

    public DoctorPatientAdapter(ArrayList<Datum> transactionsList, Activity mContext,int status) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.status=status;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType)
    {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.patiend_booking_adapter, parent, false);

        return new MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);
        holder.appointment_id.setText("Appointment ID : "+operator.getAppointment_id());
        if(operator.getNoDays()!=null)
        {
            Integer doc_duration=Integer.parseInt(operator.getFees_duration());
            Integer noOfday=Integer.parseInt(operator.getNoDays());
            if (noOfday <= doc_duration) {
                holder.chat.setVisibility(View.VISIBLE);
                holder.reUploadReport.setVisibility(View.VISIBLE);
            }
        }
        else
        {
            holder.chat.setVisibility(View.GONE);
            holder.reUploadReport.setVisibility(View.GONE);
        }
        try {
            object = new JSONObject(operator.getPatient_details());
            Age=object.getString("PatientAge");
            Gender=object.getString("Gender");
            Type=object.getString("Type");
            String  ReportUrl=object.getString("ReportUrl");
            PatientName=object.getString("PatientName");
            PatientMobile=object.getString("PatientMobile");
            Weight=object.getString("Weight");
            Height=object.getString("Height");
            // Toast.makeText(mContext, ""+Height, Toast.LENGTH_SHORT).show();
            Bp=object.getString("Bp");
            Sugar=object.getString("Sugar");
        } catch (JSONException e) {
            e.printStackTrace();
        }
       // Toast.makeText(mContext, ""+status, Toast.LENGTH_SHORT).show();
        if(status==1)
        {
            holder.View_Preciption.setVisibility(View.VISIBLE);
        }
        if(status==2)
        {
            holder.View_Preciption.setVisibility(View.GONE);
        }
        Log.d("dddddddddd ",operator.getFees_duration()+"noDay  "+operator.getNoDays());
        holder.tv_title.setText("Dr.  " + operator.getDoctor_name() );
        holder.enddate.setText("" + operator.getEnd_time());
        holder.craetedate.setText("" + operator.getStart_time());

        holder.tv_discuraption.setText("" + operator.getExperience());
       // holder.book_date.setText(mContext.getResources().getString(R.string.booking_date)+" " + operator.getBook_date());
       holder.consult_date.setText(mContext.getResources().getString(R.string.consult_date)+" " + operator.getConsult_date());




        String strdate=operator.getBook_date().split(" ")[0];
        if(operator.getEnd_time().equals("Emergency"))
        {
           // holder.emergency_time.setVisibility(View.VISIBLE);
          holder.emergency_time.setText("Emergency Time : "+operator.getBook_date().split(" ")[1]);
        }

        holder.book_date.setText(mContext.getResources().getString(R.string.booking_date)+" "+operator.getBook_date());
       //Toast.makeText(mContext, ""+strdate, Toast.LENGTH_SHORT).show();
      //  SimpleDateFormat dateFormat=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

//        try {
//
//          //  Date date1=dateFormat.parse(strdate);
//           // holder.book_date.setText(date1.getYear()+"-"+date1.getMonth()+"-"+date1.getDay());
//          // Toast.makeText(mContext, date1.getTime()+"", Toast.LENGTH_SHORT).show();
//        } catch (ParseException e) {
//            e.printStackTrace();
//            Log.e("timeee",e.toString());
//           // Toast.makeText(mContext, e.toString(), Toast.LENGTH_SHORT).show();
//        }
        if(!Gender.equals(""))
        {
            gender= String.valueOf(Gender.charAt(0));
        }
        else {
            //Toast.makeText(mContext, ""+Gender, Toast.LENGTH_SHORT).show();
            //  gender=" ";
        }
        holder.Patientdetails.setText(PatientName+"  "+Age+"/"+gender);
        Log.e( "modallllllll: ", operator.getPatient_details());

        Log.e("modelsssss: ",operator.getPatient_details() );

        if(operator.getPreciption_status()==null){
            holder.View_Preciption.setEnabled(false);
            holder.View_Preciption.setText(mContext.getResources().getString(R.string.no_prescription));
            holder.View_Preciption.setTextColor(mContext.getResources().getColor(R.color.red));
        }else{
            if(status==1) {
                holder.View_Preciption.setVisibility(View.VISIBLE);
            }
            if(status==2) {
                holder.View_Preciption.setVisibility(View.GONE);
            }
            holder.View_Preciption.setText(mContext.getResources().getString(R.string.view_prescription));
        }

     UtilMethods.INSTANCE.GetPatientPreciptionhide(mContext,operator.getAppointment_id(),holder.View_Preciption,status);

        holder.View_Preciption.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Loader loader;
                loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.GetPatientPreciption(mContext,operator.getAppointment_id(),holder.recycler_view_Preciption,loader,operator,operator.getBook_date());
}
        });

  holder.chat.setOnClickListener(new View.OnClickListener() {
                                     @Override
                                     public void onClick(View v) {
                                         Log.d( "idfffdffdfd ","id"+operator.getId()+"doc_id"+operator.getDoctor_id());
                                         loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                                         loader.show();
                                         loader.setCancelable(false);
                                         loader.setCanceledOnTouchOutside(false);
                                         UtilMethods.INSTANCE.CreateGetRTMAccessToken(mContext,operator.getDoctor_id()+"",operator.getDoctor_name(),loader);

                                     }
                                 });
        holder.reUploadReport.setOnClickListener(new View.OnClickListener() {
      @Override
      public void onClick(View v) {
          try {
             JSONObject object = new JSONObject(operator.getPatient_details());
              Age=object.getString("PatientAge");
              Gender=object.getString("Gender");
              Type=object.getString("Type");
              ReportUrl=object.getString("ReportUrl");
              Log.d("onClickhbhhghhgh: ",ReportUrl);
             // Toast.makeText(mContext, ""+ReportUrl, Toast.LENGTH_SHORT).show();
              PatientName=object.getString("PatientName");
              PatientMobile=object.getString("PatientMobile");
              Weight=object.getString("Weight");
              Height=object.getString("Height");
              // Toast.makeText(mContext, ""+Height, Toast.LENGTH_SHORT).show();
              Bp=object.getString("Bp");
              Sugar=object.getString("Sugar");
          } catch (JSONException e) {
              e.printStackTrace();
          }
          Intent i=new Intent(mContext,PatientDetail.class);
          i.putExtra("Doctordetail", new Gson().toJson(operator));
          i.putExtra("age", Age);
          i.putExtra("patientname",PatientName);
          i.putExtra("mobile",PatientMobile);
          i.putExtra("gender",Gender);
          i.putExtra("type","2");
          i.putExtra("height",Height);
          i.putExtra("weight",Weight);
          i.putExtra("bp",Bp);
          i.putExtra("sugar",Sugar);
          i.putExtra("ReportUrl",ReportUrl);
          i.putExtra("symptoms",Symptoms);
          i.putExtra("AppintmentDetail", new Gson().toJson(operator));
          i.putExtra("appointment_id", operator.getAppointment_id());
          i.putExtra("bookstatus","5");
          mContext.startActivity(i);
      }
  });
    }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}