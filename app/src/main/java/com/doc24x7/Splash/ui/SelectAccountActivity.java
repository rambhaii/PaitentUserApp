package com.doc24x7.Splash.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;

import androidx.appcompat.app.AppCompatActivity;

import com.doc24x7.Login.ui.LoginActivity;
import com.doc24x7.R;
import com.doc24x7.Utils.UtilMethods;

public class SelectAccountActivity extends AppCompatActivity implements View.OnClickListener {

    Button bt_login,signup;
    RadioButton radioPrepaid;
    RadioButton radioUtility;
    String fundType ="1";

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_account);

        radioPrepaid = (RadioButton) findViewById(R.id.radio_prepaid);
        radioUtility = (RadioButton) findViewById(R.id.radio_postpaid);

        radioPrepaid.setOnClickListener(this);
        radioUtility.setOnClickListener(this);

        signup=findViewById(R.id.bt_signup);
        bt_login=findViewById(R.id.bt_login);

        signup.setOnClickListener(this);
        bt_login.setOnClickListener(this);

    }

    @Override
    public void onClick(View v)
    {

        if (v == radioPrepaid) {
            fundType = "1";

            UtilMethods.INSTANCE.SetType(this, "1");
            signup.setVisibility(View.VISIBLE);

        }
        if (v == radioUtility) {
            fundType = "2";
            signup.setVisibility(View.GONE);

            UtilMethods.INSTANCE.SetType(this, "2");



         }

        if(bt_login==v){

            UtilMethods.INSTANCE.SetType(this, fundType);
            Intent intent = new Intent(SelectAccountActivity.this, LoginActivity.class);
            intent.putExtra("type","1");
            startActivity(intent);
            overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

          //  finish();

        }
       if(signup==v)
       {

           UtilMethods.INSTANCE.SetType(this, fundType);
           Intent intent = new Intent(SelectAccountActivity.this, LoginActivity.class);
           intent.putExtra("type","2");
           startActivity(intent);
           overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);

           finish();

        }

    }
}