package com.doc24x7.Dashbord.ui;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.doc24x7.R;
import com.makeramen.roundedimageview.RoundedImageView;

import java.util.List;

public class SliderAdapter extends RecyclerView.Adapter<SliderAdapter.SliderViewHolders> {
    private List<Slideritem> slideritems;
    private ViewPager2 viewPager2;
    public SliderAdapter(List<Slideritem> slideritems, ViewPager2 viewPager2) {
        this.slideritems = slideritems;
        this.viewPager2 = viewPager2;
    }


    @NonNull
    @Override
    public SliderViewHolders onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new SliderViewHolders(
                LayoutInflater.from(parent.getContext()).inflate(R.layout.slider_item_container,parent,false)
        );
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolders holder, int position) {
       holder.setImage(slideritems.get(position));
       if(position==slideritems.size()-2)
       {
           viewPager2.post(runnable);
       }
    }

    @Override
    public int getItemCount() {
        return slideritems.size();
    }

    class SliderViewHolders extends RecyclerView.ViewHolder{
        private RoundedImageView imageView;

        public SliderViewHolders(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.imageslider);

        }
        void setImage(Slideritem slideritem)
        {
            //if you want to display image from internet
            imageView.setImageResource(slideritem.getImage());
        }
    }
    private Runnable runnable=new Runnable() {
        @Override
        public void run() {
           slideritems.addAll(slideritems);
           notifyDataSetChanged();
        }
    };
}
