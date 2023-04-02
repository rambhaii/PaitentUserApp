package com.doc24x7.Dashbord.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.appcompat.widget.AppCompatTextView;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.doc24x7.R;

public class SupportPage extends Fragment implements View.OnClickListener {

    AppCompatTextView webside,mailSupport;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_helpcenter, container, false);

        webside=v.findViewById(R.id.webside);
        webside.setOnClickListener(this);


        mailSupport=v.findViewById(R.id.mailSupport);
        mailSupport.setOnClickListener(this);

        return v;

    }

    @Override
    public void onClick(View view) {


        if(view==webside){

            Intent i=new Intent(getActivity(),WebViewActivity.class);
            i.putExtra("url","http://cureu.in/");
            i.putExtra("name","webside");
            startActivity(i);
        }

   if(view==mailSupport) {

       String[] TO = {"xyz@gmail.com"};
       String[] CC = {"test@gamil.com"};
       Intent emailIntent = new Intent(Intent.ACTION_SEND);

       emailIntent.setData(Uri.parse("mailto:"));
       emailIntent.setType("text/plain");
       emailIntent.putExtra(Intent.EXTRA_EMAIL, TO);
       emailIntent.putExtra(Intent.EXTRA_CC, CC);
       emailIntent.putExtra(Intent.EXTRA_SUBJECT, "Your subject");
       emailIntent.putExtra(Intent.EXTRA_TEXT, "Email message goes here");


       try {
           startActivity(Intent.createChooser(emailIntent, "Send mail..."));
        } catch (android.content.ActivityNotFoundException ex) {
           Toast.makeText(getActivity(), "There is no email client installed.", Toast.LENGTH_SHORT).show();
       }

   }

    }

}