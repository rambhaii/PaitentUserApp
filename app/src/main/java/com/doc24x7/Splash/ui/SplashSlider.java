package com.doc24x7.Splash.ui;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.doc24x7.Login.ui.LoginActivity;
import com.doc24x7.R;


public class SplashSlider extends AppCompatActivity implements View.OnClickListener{

    SplashSliderPagerAdapter mCustomPagerAdapter;
    ViewPager mViewPager;
    Handler handler;
    LinearLayout dotsCount;
    Integer mDotsCount;
    public static TextView mDotsText[];
    private LinearLayout llCategory;


     TextView getstartt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.splash_slider);

        getId();

    }

    public void getId()
    {

        getstartt=(TextView)findViewById(R.id.getstartt);

        mCustomPagerAdapter = new SplashSliderPagerAdapter(this);
         handler=new Handler();
        dotsCount   = (LinearLayout)findViewById(R.id.image_count);
        mViewPager = (ViewPager)findViewById(R.id.pager);
        mViewPager.setAdapter(mCustomPagerAdapter);

        mDotsCount = mViewPager.getAdapter().getCount();

        mDotsText = new TextView[mDotsCount];

        //here we set the dots
        for(int i = 0; i < mDotsCount; i++) {
            mDotsText[i] = new TextView(this);
            mDotsText[i].setText(".");
            mDotsText[i].setTextSize(45);
            mDotsText[i].setTypeface(null, Typeface.BOLD);
            mDotsText[i].setTextColor(android.graphics.Color.GRAY);
            dotsCount.addView(mDotsText[i]);
        }

        mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                for (int i = 0; i < mDotsCount; i++)
                {
                    mDotsText[i].setTextColor(getResources().getColor(R.color.colorPrimary));
                }
                mDotsText[position].setTextColor(getResources().getColor(R.color.colorBackground));
            }

            @Override
            public void onPageSelected(int position) {

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

        postDelayedScrollNext();

        setListners();
    }

    private void setListners() {

        getstartt.setOnClickListener(this);
    }

    private void postDelayedScrollNext() {
        handler.postDelayed(new Runnable() {
            public void run() {

                if (mViewPager.getCurrentItem() == mViewPager.getAdapter().getCount() - 1) {
                    mViewPager.setCurrentItem(0);
                    postDelayedScrollNext();
                    return;
                }
                mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
                // postDelayedScrollNext(position+1);
                postDelayedScrollNext();

                // onKeyDown(KeyEvent.KEYCODE_DPAD_RIGHT, null);
            }
        }, 3000);

    }

    @Override
    public void onClick(View v) {
         if(v==getstartt)
        {


startActivity(new Intent(SplashSlider.this, LoginActivity.class));

finish();



        }
    }
}
