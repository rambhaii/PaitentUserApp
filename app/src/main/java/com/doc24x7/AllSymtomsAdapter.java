package com.doc24x7;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.Dashbord.dto.Datum;

import java.util.ArrayList;


public class AllSymtomsAdapter extends RecyclerView.Adapter<AllSymtomsAdapter.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;
    private int row_index;



    public void filter(ArrayList<Datum> newlist) {
        transactionsList=new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }



    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title,tv_discuraption;
        LinearLayout idCardView;
        CheckBox select_symtom;


        public MyViewHolder(View view) {
            super(view);
            tv_title =  view.findViewById(R.id.tv_title);
            tv_discuraption =  view.findViewById(R.id.tv_discuraption);
             idCardView =  view.findViewById(R.id.idCardView);
            select_symtom =  view.findViewById(R.id.select_symtom);

        }
    }

    public AllSymtomsAdapter(ArrayList<Datum> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public AllSymtomsAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.allsymtoms_adapter, parent, false);

        return new AllSymtomsAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final AllSymtomsAdapter.MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);

        holder.tv_title.setText("" + operator.getSymtom());
        holder.tv_discuraption.setText("" + operator.getDescription());





        holder.select_symtom.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

               // row_index = position;
               //  notifyDataSetChanged();
               // holder.select_symtom.setChecked(true);




                ((AllSymtomsActivity) mContext).ItemClick("" + operator.getId() ,""+ operator.getSymtom());


                /*

                Loader loader;
                loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);


                if (UtilMethods.INSTANCE.isNetworkAvialable( mContext)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);


                     UtilMethods.INSTANCE.AllSymtimsDoctors(mContext, operator.getId(), loader);



                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                            mContext.getResources().getString(R.string.network_error_message));
                }*/


            }
        });


       /* if (row_index == position) {
            holder.select_symtom.setChecked(true);


        } else {
            holder.select_symtom.setChecked(false);
        }*/


    }

    @Override
    public int getItemViewType(int position) {

       // return transactionsList.size();
        /*this returns the view type of each element in the list*/
        return position;
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}