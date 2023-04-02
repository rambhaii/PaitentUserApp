package com.doc24x7.Dashbord.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.R;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;

import java.util.ArrayList;


public class SpecialitiesAdapterSecend extends RecyclerView.Adapter<SpecialitiesAdapterSecend.MyViewHolder> {

    private ArrayList<Datum> transactionsList;
    private Context mContext;

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        LinearLayout idCardView;
        ImageView opImage;


        public MyViewHolder(View view) {
            super(view);
            tv_title =  view.findViewById(R.id.tv_title);
             opImage =  view.findViewById(R.id.opImage);
            idCardView =  view.findViewById(R.id.idCardView);

        }
    }

    public SpecialitiesAdapterSecend(ArrayList<Datum> transactionsList, Context mContext) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
    }

    @Override
    public SpecialitiesAdapterSecend.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.specialities_product_secand_adapter, parent, false);

        return new SpecialitiesAdapterSecend.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final SpecialitiesAdapterSecend.MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);

        holder.tv_title.setText("" + operator.getName());



        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.noavailable);



        Glide.with(mContext)
                .load( operator.getIcon())
                .apply(requestOptions)
                .into(holder.opImage);


        holder.idCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Loader loader;
                loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);


                if (UtilMethods.INSTANCE.isNetworkAvialable( mContext)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);


                     UtilMethods.INSTANCE.Doctors(mContext, operator.getId(), loader);



                } else {
                    UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                            mContext.getResources().getString(R.string.network_error_message));
                }




            }
        });
    }
    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}