package com.doc24x7.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.util.Log;
import android.view.View;

import com.doc24x7.Dashbord.dto.AllRecieptAdapter;
import com.doc24x7.Dashbord.dto.ResponseReciept;
import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class AllRecieptActivity extends AppCompatActivity
{
   RecyclerView recyclerview;
  String data="";
  ResponseReciept responseReciept;
  ArrayList<Data> dataModel;
    File imagePath;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_reciept);
        recyclerview=findViewById(R.id.recyclerview);
        data=getIntent().getStringExtra("data");

        parseData(data);
    }
  void  parseData(String data)
  {
      responseReciept=new Gson().fromJson(data, ResponseReciept.class);
      dataModel=responseReciept.getData();
      if (dataModel.size()>0)
      {
          AllRecieptAdapter adapter = new AllRecieptAdapter(AllRecieptActivity.this, dataModel);
          recyclerview.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, true));
          recyclerview.setAdapter(adapter);
      }
      else {
          recyclerview.setVisibility(View.GONE);
      }

  }

    public Bitmap takeScreenshot() {
        View rootView = findViewById(android.R.id.content).getRootView();
        rootView.setDrawingCacheEnabled(true);
        saveBitmap(rootView.getDrawingCache());
        return rootView.getDrawingCache();
    }
    public void saveBitmap(Bitmap bitmap)
    {
        imagePath = new File(Environment.getExternalStorageDirectory() + "/screenshot.png");
        String v=imagePath.getAbsolutePath();
        Log.e("vvvvvv",v);
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(imagePath.getAbsolutePath());
            bitmap.compress(Bitmap.CompressFormat.JPEG, 100, fos);
            fos.flush();
            fos.close();
            shareIt();
        } catch (FileNotFoundException e) {
            Log.e("GREC", e.getMessage(), e);
        } catch (IOException e) {
            Log.e("GREC", e.getMessage(), e);
        }




    }

    private void shareIt()
    {
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Uri uri = Uri.fromFile(imagePath);
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("image/*");
        String shareBody = "Doc24x7 App priscription";
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Doc24x7");
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
        sharingIntent.putExtra(Intent.EXTRA_STREAM, uri);
        startActivity(Intent.createChooser(sharingIntent, "Share via"));
    }



}