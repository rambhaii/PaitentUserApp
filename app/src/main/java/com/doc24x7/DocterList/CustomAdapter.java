package com.doc24x7.DocterList;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.R;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {
    Context context;
    ArrayList<String>  images;
    ArrayList<String>  Speciality;
    LayoutInflater inflter;

    public CustomAdapter(Context applicationContext,   ArrayList<String> images,   ArrayList<String> Speciality) {
        this.context = applicationContext;
        this.images = images;
        this.Speciality = Speciality;
        inflter = (LayoutInflater.from(applicationContext));
    }

    @Override
    public int getCount() {
        return Speciality.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        view = inflter.inflate(R.layout.spinner_custom_layout, null);
        ImageView icon = (ImageView) view.findViewById(R.id.imageView);
        TextView names = (TextView) view.findViewById(R.id.textView);
       // icon.setImageResource(images.indexOf(i));
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.doctordemo);
        Glide.with(context)
                .load(images.get(i))
                .apply(requestOptions)
                .into(icon);
        names.setText(Speciality.get(i));
        return view;
    }
}