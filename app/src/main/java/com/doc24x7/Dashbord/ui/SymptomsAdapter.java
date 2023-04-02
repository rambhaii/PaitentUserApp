package com.doc24x7.Dashbord.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.Dashbord.dto.SymptomData;
import com.doc24x7.R;

import java.util.List;

public class SymptomsAdapter extends RecyclerView.Adapter<SymptomsAdapter.mSymtomlist>  {
    List<SymptomData> mSymtomlist;
    Context mContext;

    public SymptomsAdapter(List<SymptomData> mSymtomlist, Context mContext) {
        this.mSymtomlist = mSymtomlist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public SymptomsAdapter.mSymtomlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(parent.getContext()).inflate(R.layout.symptomsview,parent,false);
        mSymtomlist symtomlist=new mSymtomlist(view);
        return symtomlist;
    }

    @Override
    public void onBindViewHolder(@NonNull SymptomsAdapter.mSymtomlist holder, int position) {
        final SymptomData currentitem=mSymtomlist.get(position);
             holder.symptom.setText(currentitem.getSymptoms());
             holder.mobile.setText(currentitem.getSysmobile());
             holder.date.setText(currentitem.getSydate());
    }

    @Override
    public int getItemCount() {
        return mSymtomlist.size();

    }

    public class mSymtomlist extends RecyclerView.ViewHolder{
        TextView symptom,mobile,date;
        public mSymtomlist(@NonNull View itemView) {
            super(itemView);
            symptom=itemView.findViewById(R.id.tv_symptom);
            mobile=itemView.findViewById(R.id.tv_mobile);
            date=itemView.findViewById(R.id.tv_date);
        }
    }
}