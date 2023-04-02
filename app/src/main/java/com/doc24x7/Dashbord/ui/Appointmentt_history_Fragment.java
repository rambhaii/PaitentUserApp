package com.doc24x7.Dashbord.ui;

import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.doc24x7.R;


public class Appointmentt_history_Fragment extends Fragment {

    RecyclerView recycler_view;





    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_appointmentt_history_, container, false);

        recycler_view=v.findViewById(R.id.recycler_view);


        Toolbar toolbar = (Toolbar) v.findViewById(R.id.toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_reporbook);
        toolbar.setTitle("Appointment History");
        toolbar.setTitleTextColor(Color.WHITE);




        return v;

    }


}