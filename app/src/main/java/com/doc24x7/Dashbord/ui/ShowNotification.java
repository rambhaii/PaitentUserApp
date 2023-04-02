package com.doc24x7.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doc24x7.Dashbord.dto.SymptomData;
import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShowNotification extends AppCompatActivity {
   RecyclerView recyclerView;
   RequestQueue requestQueue;
   Loader loader;
   NotificationAdapter adapter;
   List<SymptomData> notilist=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_notification);
        loader=new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        loader.show();
        loader.setCancelable(false);
        loader.setCanceledOnTouchOutside(false);

          recyclerView=findViewById(R.id.recycle_notification);

          StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
          recyclerView.setLayoutManager(staggeredGridLayoutManager);
        requestQueue= Volley.newRequestQueue(getApplicationContext());
        Apihit();
    }

    private void Apihit() {
        loader.isShowing();
        SharedPreferences myPreferences = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String userId = balanceCheckResponse.getPatient_id();
        Log.d("ksjfhdkjfghd",userId);
       // Toast.makeText(this, ""+userId, Toast.LENGTH_SHORT).show();
        StringRequest request = new StringRequest(Request.Method.GET, "http://www.yoursdoc.com/api/Notification/FetchPatientNotification?userId="+userId,
                new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
             // Toast.makeText(ShowNotification.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject = new JSONObject(response);

                    JSONArray jsonArray = jsonObject.getJSONArray("Data");
                    if (jsonArray.isNull(0))
                    {
                        loader.dismiss();
                        Toast.makeText(ShowNotification.this, "No notification", Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(ShowNotification.this, DashboardMainNew.class);
                        startActivity(i);
                        finish();

                    }

                    int i;
                    for (i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        SymptomData data = new SymptomData();
                           data.setNotidoctname(object.getString("doctor_name"));
                           data.setNotimessage(object.getString("message"));
                           data.setCreate_date(object.getString("created_date"));
                           //data.setActivityType(object.getString("ActivityType"));
                           data.setRoom(object.getString("room"));
                           data.setEncryption_type(object.getString("encryption_type"));
                           data.setToken_with_int_uid(object.getString("token_with_int_uid"));
                           data.setEncryption_key(object.getString("encryption_key"));
                           notilist.add(data);

                    }
                    adapter=new NotificationAdapter(notilist,ShowNotification.this);
                    adapter.notifyDataSetChanged();
                    recyclerView.setAdapter(adapter);
//
//                    symptomsAdapter=new SymptomsAdapter(symtomlist,ShowSymptom.this);
//                    recyclerView.setAdapter(symptomsAdapter);
                  loader.dismiss();

                } catch (JSONException e) {

                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                //Toast.makeText(ShowSymptom.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show();
                loader.dismiss();
            }
        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("x-api-key", "apikey@animationmedia.org");

                return header;
            }


        };
        requestQueue.add(request);
    }
}