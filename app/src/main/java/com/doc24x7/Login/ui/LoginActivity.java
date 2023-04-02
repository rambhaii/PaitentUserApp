package com.doc24x7.Login.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    public TextView btLogin;
    Loader loader;
    EditText edMobile,doctorId;
    ImageView goback;
    String type="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);
        type=getIntent().getStringExtra("type");
        getId();
    }

    public void getId() {

        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
        btLogin = findViewById(R.id.btLogin);
        edMobile = findViewById(R.id.edMobile);
        doctorId = findViewById(R.id.doctorId);
        goback = findViewById(R.id.goback);


        setListners();

        if(type.equalsIgnoreCase("2"))
        {

            btLogin.setText("Register");
        }

    }


    public void setListners()
    {
        btLogin.setOnClickListener(this);
        goback.setOnClickListener(this);

    }


    @Override
    public void onClick(View v) {

        if (v == goback) {

           /* new AlertDialog.Builder(this)
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setTitle("Closing Activity")
                    .setMessage("Are you sure you want to close this activity?")
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener()
                    {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }

                    })
                    .setNegativeButton("No", null)
                    .show();
*/

            finish();

            //onBackPressed();

        }


        if (v == btLogin) {

            if (UtilMethods.INSTANCE.isNetworkAvialable(LoginActivity.this))
            {

                if (doctorId.getText().toString().isEmpty())
                {
                    doctorId.setError("Please enter DoctorId");
                    return;
                }
                if (edMobile.getText().toString().isEmpty())
                {
                    edMobile.setError("Please enter mobile Number");
                    return;
                }
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);

                SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                String SetType = "" + myPreferences.getString(ApplicationConstant.INSTANCE.SetType, null);


                if (SetType.equalsIgnoreCase("1"))
                {UtilMethods.INSTANCE.secureLogin(LoginActivity.this, edMobile.getText().toString().trim(), loader, this,1);
                }
                else if (SetType.equalsIgnoreCase("2"))
                {
                  UtilMethods.INSTANCE.DoctorLogin(LoginActivity.this, edMobile.getText().toString().trim(), loader, this);
                }
               } else {

                UtilMethods.INSTANCE.NetworkError(LoginActivity.this, getResources().getString(R.string.network_error_title),
                        getResources().getString(R.string.network_error_message));
            }
        }
    }

    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setMessage("Are you sure you want to go back")
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }

                })
                .setNegativeButton("No", null)
                .show();
    }


}
