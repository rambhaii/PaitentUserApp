package com.doc24x7.Splash.ui;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;

import com.doc24x7.R;


/**
 * it display the list of Images at start of app
 */


public class SplashSliderPagerAdapter extends PagerAdapter {

    int[] mResources = {R.drawable.pingone, R.drawable.pingtwo, R.drawable.pinthree};


 String[] imagetext = {"Book an appointment with right \n doctor","Too Busy To See a Doctor Chat Online Instead","Get Medication delivered to your Doorstep" };



    Context mContext;
    LayoutInflater mLayoutInflater;
    TextView txt3;

    public SplashSliderPagerAdapter(Context context) {
        mContext = context;
        mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return mResources.length;
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == ((LinearLayout) object);
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);


        ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
        imageView.setImageResource(mResources[position]);


        TextView textdeta = (TextView) itemView.findViewById(R.id.textdeta);
        textdeta.setText(imagetext[position]);


        container.addView(itemView);

        return itemView;


    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView((LinearLayout) object);
    }
}