package com.doc24x7.DocterList;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;

import java.util.Calendar;
import java.util.Date;

public class DoctorBookingActivity extends AppCompatActivity implements View.OnClickListener {

    EditText  time,selectdate;
    Button bt_submit;
    Calendar calendar;
    private int CalendarHour, CalendarMinute;
    String format;
    String timeval;
    Loader loader;
    String specialities_id="";
    String doctor_id="";
    String name="";
    String address="";
    String image="";

    TimePickerDialog timepickerdialog;
    ImageView doctorimage;
    TextView doctorname,doctoraddress;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_doctor_booking);

        doctorname=findViewById(R.id.doctorname);
        doctoraddress=findViewById(R.id.doctoraddress);
        doctorimage=findViewById(R.id.doctorimage);

        loader = new Loader(this,android.R.style.Theme_Translucent_NoTitleBar);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Doctor Booking");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        // specialities_id=getIntent().getStringExtra("specialities_id");
        doctor_id=getIntent().getStringExtra("id");
        name=getIntent().getStringExtra("name");
        address=getIntent().getStringExtra("address");
        image=getIntent().getStringExtra("image");


        doctorname.setText(""+name);
        doctoraddress.setText(""+address);

        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);


        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseUrldoctorimage+image)
                .apply(requestOptions)
                .into(doctorimage);







        bt_submit= findViewById(R.id.bt_submit);

          time= findViewById(R.id.time);
         selectdate= findViewById(R.id.selectdate);

        selectdate.setOnClickListener(this);

        time.setOnClickListener(this);
        bt_submit.setOnClickListener(this);



    }

    @Override
    public void onClick(View view) {



        if(view==time){


            calendar = Calendar.getInstance();
            CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
            CalendarMinute = calendar.get(Calendar.MINUTE);


            timepickerdialog = new TimePickerDialog(this,
                    new TimePickerDialog.OnTimeSetListener() {

                        @Override
                        public void onTimeSet(TimePicker view, int hourOfDay,
                                              int minute) {

                            if (hourOfDay == 0) {

                                hourOfDay += 12;

                                format = "AM";
                            }
                            else if (hourOfDay == 12) {

                                format = "PM";

                            }
                            else if (hourOfDay > 12) {

                                hourOfDay -= 12;

                                format = "PM";

                            }
                            else {

                                format = "AM";
                            }


                            time.setText(hourOfDay + ":" + minute + format);

                            timeval=hourOfDay + ":" + minute + format;

                        }
                    }, CalendarHour, CalendarMinute, false);
            timepickerdialog.show();

        }
        if(view==selectdate){


            Datepicker();

        }

        if(view==bt_submit){

           if(time.getText().toString().trim().equalsIgnoreCase("")){

                Toast.makeText( this, "Select Time", Toast.LENGTH_SHORT).show();

            }else if(selectdate.getText().toString().trim().equalsIgnoreCase("")){

                Toast.makeText( this, "Select Date", Toast.LENGTH_SHORT).show();

            }else   {

              /*  if (UtilMethods.INSTANCE.isNetworkAvialable( this)) {

                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);

                    UtilMethods.INSTANCE.appointment( this, specialities_id,doctor_id,
                            selectdate.getText().toString().trim(),time.getText().toString().trim(), loader,DoctorBookingActivity.this);

                } else {
                    UtilMethods.INSTANCE.NetworkError( this, getResources().getString(R.string.network_error_title),
                            getResources().getString(R.string.network_error_message));
                }

                */


            }
        }

    }


    public void Datepicker() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datepicker_pop, null);

        Button tvLater =  view.findViewById(R.id.tv_later);
        Button tv_ok =  view.findViewById(R.id.tv_ok);
        final DatePicker  datePicker = (DatePicker) view.findViewById(R.id.date_picker);


     /*   private DatePicker datePicker;
        private TextView dateValueTextView;
        private Button updateDateButton;*/

        final Dialog dialog = new Dialog(this);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        //  dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));


        Calendar today = Calendar.getInstance();
        long now = today.getTimeInMillis();
        datePicker.setMinDate(now);


        Date currentTime = Calendar.getInstance().getTime();


        String timewah=currentTime.toString().replace(" ",",");

        String[] recent;
        recent = timewah.split(",");


        Log.e("currentTime","currentTime :   "+ recent[3] );

        tv_ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                // Toast.makeText(Verder_Detail_Selection.this, "Date  : " +  "Selected date: " + (datePicker.getMonth()+1) + "/" + datePicker.getDayOfMonth() + "/" + datePicker.getYear(), Toast.LENGTH_SHORT).show();
                dialog.dismiss();

                selectdate.setText( datePicker.getYear() + "-" +  (datePicker.getMonth()+1) + "-" + datePicker.getDayOfMonth() );





            }
        });



        tvLater.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                dialog.dismiss();
            }
        });



        dialog.show();
    }



}