package com.doc24x7.Dashbord.ui;

//public class CancleappointmentAdapter {
//}

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.DOctoeByIdActivity;
import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Dashbord.dto.SymptomData;
import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.google.gson.Gson;

import java.util.List;

import static android.content.Context.MODE_PRIVATE;

public class CancleappointmentAdapter extends RecyclerView.Adapter<CancleappointmentAdapter.mSymtomlist> {
    List<SymptomData> mSymtomlist;
    Context mContext;

    public CancleappointmentAdapter(List<SymptomData> mSymtomlist, Context mContext) {
        this.mSymtomlist = mSymtomlist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public CancleappointmentAdapter.mSymtomlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cancelappointmview, parent, false);
        mSymtomlist symtomlist = new mSymtomlist(view);
        return symtomlist;
    }

    @Override
    public void onBindViewHolder(@NonNull CancleappointmentAdapter.mSymtomlist holder, int position) {
        final SymptomData currentitem = mSymtomlist.get(position);
        holder.tv_title.setText("DR." + currentitem.getDoctor_name());
        holder.starttime.setText(currentitem.getStart_time());
        holder.endtime.setText(currentitem.getEnd_time());
        holder.book_date.setText(currentitem.getBook_date());
        SharedPreferences myPreferences = mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        holder.tv_discuraption.setText(mContext.getResources().getString(R.string.message_cancle_appointment));
//        name.setText(""+balanceCheckResponse.getName() );


        holder.reschedule.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Datum datum = new Datum();
                datum.setName(currentitem.getDoctor_name());
                datum.setDoctor_id(currentitem.getDoctor_id());
                datum.setId(currentitem.getDoctor_id());
                datum.setTypeName("");
                datum.setExperience(currentitem.getExperience());
                Intent intent = new Intent(mContext, DOctoeByIdActivity.class);
                intent.putExtra("response", new Gson().toJson(datum));
                intent.putExtra("type", "1");
                intent.putExtra("paymentStatus", "1");
                intent.putExtra("payment_id", currentitem.getPayment_id()+"");
                intent.putExtra("doctorDetail", new Gson().toJson(datum));
                intent.putExtra("appointment_id", currentitem.getAppointment_id());
                mContext.startActivity(intent);

            }
        });
    }

    @Override
    public int getItemCount() {
        return mSymtomlist.size();

    }

    public class mSymtomlist extends RecyclerView.ViewHolder {
        TextView tv_title, starttime, endtime, book_date, reschedule, tv_discuraption;

        public mSymtomlist(@NonNull View itemView) {
            super(itemView);
            tv_title = itemView.findViewById(R.id.tv_title);
            starttime = itemView.findViewById(R.id.craetedate);
            endtime = itemView.findViewById(R.id.enddate);
            book_date = itemView.findViewById(R.id.book_date);
            reschedule = itemView.findViewById(R.id.reschedule);
            tv_discuraption = itemView.findViewById(R.id.tv_discuraption);

        }
    }
}