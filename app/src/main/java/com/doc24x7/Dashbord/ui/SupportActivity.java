package com.doc24x7.Dashbord.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.doc24x7.BuildConfig;
import com.doc24x7.R;

public class SupportActivity extends AppCompatActivity {
    TextView ver;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_support);

        ver=findViewById(R.id.ver);
        ver.setText(""+ BuildConfig.VERSION_NAME);
    }
}