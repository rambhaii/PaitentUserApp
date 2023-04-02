package com.doc24x7.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import java.util.Map;

public class ShowSymptom extends AppCompatActivity {

    int i;
    RequestQueue requestQueue;
    RecyclerView recyclerView;
    ArrayList<SymptomData>  symtomlist=new ArrayList<>();
    SymptomsAdapter symptomsAdapter;
    Loader loader;
      @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_show_symptom);
        recyclerView=findViewById(R.id.recyclersymptom);
          loader=new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);
        StaggeredGridLayoutManager staggeredGridLayoutManager=new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL);
          recyclerView.setLayoutManager(staggeredGridLayoutManager);
          requestQueue= Volley.newRequestQueue(getApplicationContext());
          loader.show();
          loader.setCancelable(false);
          loader.setCanceledOnTouchOutside(false);
          Apihit();

    }

    private void Apihit() {
        loader.isShowing();
        SharedPreferences myPreferences = this.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String userId = balanceCheckResponse.getPatient_id();

        StringRequest request = new StringRequest(Request.Method.GET,"http://www.yoursdoc.com/api/Symtoms/GetUserRequest/?userId="+userId, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                //Toast.makeText(ShowSymptom.this, ""+response, Toast.LENGTH_SHORT).show();
                try {
                    JSONObject jsonObject=new JSONObject(response);

                    JSONArray jsonArray=jsonObject.getJSONArray("Data");
                    if(jsonArray.isNull(0)) {
                        loader.dismiss();
                        Toast.makeText(ShowSymptom.this, "No Symptoms", Toast.LENGTH_SHORT).show();
                            Intent i=new Intent(ShowSymptom.this,DashboardMainNew.class);
                            startActivity(i);
                            finish();

                    }


                    for (i = 0; i < jsonArray.length(); i++) {

                        JSONObject object = jsonArray.getJSONObject(i);

                        SymptomData data=new SymptomData(object.getString("symtom"),
                             object.getString("mobile"),
                                object.getString("date")
                        );
                         symtomlist.add(data);
                    }

                    symptomsAdapter=new SymptomsAdapter(symtomlist,ShowSymptom.this);
                    recyclerView.setAdapter(symptomsAdapter);
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