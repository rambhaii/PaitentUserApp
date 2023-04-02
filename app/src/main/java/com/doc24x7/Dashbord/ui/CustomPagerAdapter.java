package com.doc24x7.Dashbord.ui;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.bumptech.glide.Glide;
import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.R;

import java.util.ArrayList;

/**
 * it display the list of Images at start of app
 */


public class CustomPagerAdapter extends PagerAdapter {

    private ArrayList<Datum> ImageList;
    Context mContext;
    LayoutInflater mLayoutInflater;

    public CustomPagerAdapter(ArrayList<Datum> ImageList, Context context) {
        this.ImageList = ImageList;
        this.mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return ImageList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pagerbanner_item, container, false);

        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);

        Log.e("Image", ImageList.get(position).getBanner_img());
        Glide.with(mContext)
                .load( ImageList.get(position).getBanner_img())
                .into(imageView);

        container.addView(itemView);

        return itemView;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}