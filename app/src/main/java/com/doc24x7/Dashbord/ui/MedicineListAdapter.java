package com.doc24x7.Dashbord.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.Dashbord.dto.DETAIL;
import com.doc24x7.R;

import java.util.ArrayList;


public class MedicineListAdapter extends RecyclerView.Adapter<MedicineListAdapter.MyViewHolder> {

    private ArrayList<DETAIL> transactionsList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView medicineName,medicineFrequency,medicinedays,medicineHowtouse,a,count;
        LinearLayout idCardView;
        ImageView opImage;


        public MyViewHolder(View view) {
            super(view);
            medicineName =  view.findViewById(R.id.medicineName);
            medicineFrequency =  view.findViewById(R.id.medicineFrequency);
            medicinedays =  view.findViewById(R.id.medicinedays);
            medicineHowtouse =  view.findViewById(R.id.medicineHowtouse);
            a =  view.findViewById(R.id.a);
            count =  view.findViewById(R.id.count);


        }
    }

    public MedicineListAdapter(ArrayList<DETAIL> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public MedicineListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.medivine_list_adapter, parent, false);

        return new MedicineListAdapter.MyViewHolder(itemView);

    }



    @Override
    public void onBindViewHolder(final MedicineListAdapter.MyViewHolder holder, int position) {
        final DETAIL operator = transactionsList.get(position);

        int count =position+1;
        holder.count.setText(""+count +". ");
        holder.medicineName.setText(" " + operator.getMedicineName().replace("\"",""));
        holder.medicinedays.setText(" " + operator.getMedicimeDays().replace("\"",""));
        holder.medicineFrequency.setText(" " + operator.getMedicimeFreQuency().replace("\"",""));
        holder.medicineHowtouse.setText(" " + operator.getHowtouse().replace("\"",""));
        holder.a.setText(" " + operator.getWhentouse().replace("\"",""));


    }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}