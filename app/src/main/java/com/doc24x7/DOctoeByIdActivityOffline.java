package com.doc24x7;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Dashbord.dto.GalleryListResponse;
import com.doc24x7.Dashbord.ui.DoctorListDashboadAdapter;

import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.FragmentActivityMessage;
import com.doc24x7.Utils.GlobalBus;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.text.DateFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class DOctoeByIdActivityOffline extends AppCompatActivity implements View.OnClickListener {
    RecyclerView recycler_view_doctor;
    String bookstatus="";
    String response = "";
    String type = "";
    String offline_fee="";
    String Symtom_id = "";
    String doctorDetail = "";
    String consultid="";
    public TextView name, specialities, address, qualification,tv_selectedDate,consultation,clinic_fees,heading,notAvailable;
    ImageView opImage, calander;
    LinearLayout lifirst, ll_week;
    TextView mon, tue, wed, thurs, fri, sat, add_new;
    ;
    Datum operator;
     Calendar myCalendar = Calendar.getInstance();

    @Override
    public void onStart() {
        super.onStart();
        if (!EventBus.getDefault().isRegistered(this)) {
            GlobalBus.getBus().register(this);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Subscribe
    public void onFragmentActivityMessage(FragmentActivityMessage activityFragmentMessage) {
        Log.e("activityFragmentMessage",activityFragmentMessage.getFrom());
        if (activityFragmentMessage.getMessage().equalsIgnoreCase("Doctorsres")) {
            recycler_view_doctor.setVisibility(View.VISIBLE);
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(ApplicationConstant.INSTANCE.Appointment, activityFragmentMessage.getFrom());
            editor.commit();
            dataParsedoctorbook(response);
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("AvailableOffline")) {
            recycler_view_doctor.setVisibility(View.GONE);
        } else if (activityFragmentMessage.getMessage().equalsIgnoreCase("AppointmentOffline")) {
            recycler_view_doctor.setVisibility(View.VISIBLE);
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(ApplicationConstant.INSTANCE.Appointment, activityFragmentMessage.getFrom());
            editor.commit();
            dataParsedoctorbook(activityFragmentMessage.getFrom());
        } else {
            recycler_view_doctor.setVisibility(View.VISIBLE);
            SharedPreferences prefs = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(ApplicationConstant.INSTANCE.Appointment, activityFragmentMessage.getFrom());
            editor.commit();
            dataParsedoctorbook(activityFragmentMessage.getFrom());
        }
    }
    DatePickerDialog.OnDateSetListener DatePickerDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_d_octoe_by_idoffline);
        consultid=getIntent().getStringExtra("cosultnowid");
        recycler_view_doctor = findViewById(R.id.recycler_view_doctor);
        bookstatus=getIntent().getStringExtra("bookstatus");
        response = getIntent().getStringExtra("response");
       // heading=findViewById(R.id.heading);
          offline_fee=getIntent().getStringExtra("offline_fee");
          clinic_fees=findViewById(R.id.clinic_fees);
          clinic_fees.setText("\u20B9 "+ offline_fee+" "+"Clinic Fees" );
        //Toast.makeText(this, ""+response, Toast.LENGTH_SHORT).show();
        type = getIntent().getStringExtra("type");
        Symtom_id = getIntent().getStringExtra("Symtom_id");
        mon = findViewById(R.id.mon);
        tue = findViewById(R.id.tue);
        wed = findViewById(R.id.wed);
        calander = findViewById(R.id.calander);
        consultation = findViewById(R.id.consultation);
        tv_selectedDate = findViewById(R.id.tv_selectedDate);
        thurs = findViewById(R.id.thurs);
        fri = findViewById(R.id.fri);
        sat = findViewById(R.id.sat);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        if (type.equalsIgnoreCase("1")) {

            toolbar.setTitle("Doctor Availability");


        } else if (type.equalsIgnoreCase("4")) {
            toolbar.setTitle("Patient Appointment");
            tv_selectedDate.setVisibility(View.GONE);
        }
        else if(type.equalsIgnoreCase("2"))
        {
            tv_selectedDate.setVisibility(View.GONE);

        }
        else {
            toolbar.setTitle("Doctor");
            //toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        }
//        toolbar.setTitle("Doctor");
//        toolbar.setTitleTextColor(getResources().getColor(R.color.colorPrimary));
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        notAvailable=findViewById(R.id.notAvailable);
        name = findViewById(R.id.name);
        specialities = findViewById(R.id.specialities);
        qualification = findViewById(R.id.qualification);
        address = findViewById(R.id.address);
        opImage = findViewById(R.id.opImage);
        ll_week = findViewById(R.id.ll_week);
        //lifirst = findViewById(R.id.lifirst);
       // lifirst.setVisibility(View.GONE);
        mon.setOnClickListener(this);
        tue.setOnClickListener(this);
        wed.setOnClickListener(this);
        thurs.setOnClickListener(this);
        fri.setOnClickListener(this);
        sat.setOnClickListener(this);
        calander.setOnClickListener(this);

        if (type.equalsIgnoreCase("1")) {
            ll_week.setVisibility(View.VISIBLE);
           // lifirst.setVisibility(View.VISIBLE);
           // heading.setVisibility(View.VISIBLE);
            doctorDetail = getIntent().getStringExtra("doctorDetail");

            Gson gson = new Gson();
            operator = gson.fromJson(doctorDetail, Datum.class);
            name.setText("Dr. " + operator.getName());
            qualification.setText("" + operator.getTypeName());
            address.setText("" + operator.getExperience()+" "+this.getResources().getString(R.string.year_experiance));
            specialities.setText("" + operator.getTypeName());
           // consultation.setText("\\u20B9 "+ operator.getDoctor_fees()+" consultation fees" );

            RequestOptions requestOptions = new RequestOptions();
            requestOptions.placeholder(R.drawable.customer_support);
            requestOptions.error(R.drawable.doctordemo);


            Glide.with(this)
                    .load(operator.getProfilePic())
                    .apply(requestOptions)
                    .into(opImage);

            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            String dateformate = formatter.format(calendar.getTime());
            int day = calendar.get(Calendar.DATE);
            int weekDay = calendar.get(Calendar.DAY_OF_WEEK);
           // Toa
            mon.setText(day + "\n" + getMonthForInt(calendar.get(Calendar.MONTH)));
            tue.setText(getFutureDays(1) + "\n" + getMonthForInt(calendar.get(Calendar.MONTH)));
            wed.setText(getFutureDays(2) + "\n" + getMonthForInt(calendar.get(Calendar.MONTH)));
            thurs.setText(getFutureDays(3) +"\n" + getMonthForInt(calendar.get(Calendar.MONTH)));
            fri.setText(getFutureDays(4) + "\n" + getMonthForInt(calendar.get(Calendar.MONTH)));
            sat.setText(getFutureDays(5) + "\n" + getMonthForInt(calendar.get(Calendar.MONTH)));
            setBackgroundColor(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(wed);
            setBackgroundColorW(thurs);
            setBackgroundColorW(fri);
            setBackgroundColorW(sat);
            currentDay = weekDay;
          //  formatter.getCalendar();
            tv_selectedDate.setText(dateformate);
           // tv_selectedDate.setText(calendar.get(Calendar.YEAR)+"-"+00+(calendar.get(Calendar.MONTH)+1)+"-"+00+day);
            switchDay(weekDay);
        } else if (type.equalsIgnoreCase("4")) {
            dataParseAppointment(response);

        } else {
            dataParsedoctor(response);
        }

    }

    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>();
    DoctorListDashboadAdapter doctormAdapter;
    DoctorBookingAdapter DoctorBookingAdapterboo;
    DoctorPatientAdapter DoctorPatient;
    StaggeredGridLayoutManager staggeredGridLayoutManager;
    LinearLayoutManager mLayoutManager;


    public void dataParsedoctor(String response) {
        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            doctormAdapter = new DoctorListDashboadAdapter(sliderLists, this);
            staggeredGridLayoutManager = new StaggeredGridLayoutManager(1,LinearLayout.VERTICAL);
            recycler_view_doctor.setLayoutManager(staggeredGridLayoutManager);
            recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_view_doctor.setAdapter(doctormAdapter);
            recycler_view_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_view_doctor.setVisibility(View.GONE);
        }

    }

    public void dataParsedoctorbook(String responseasas) {
        try {
            Log.e("activityFragmentMessage",responseasas);
            Gson gson = new Gson();
            GalleryListResponse sliderImagec = gson.fromJson(responseasas, GalleryListResponse.class);
            sliderLists = sliderImagec.getData();

            if (sliderLists.size() > 0) {
                DoctorBookingAdapterboo = new DoctorBookingAdapter(sliderLists, this, Symtom_id, getSupportFragmentManager(), tv_selectedDate.getText().toString(), operator,getIntent().getStringExtra("paymentStatus"),Integer.parseInt(getIntent().getStringExtra("payment_id")==null?"0":getIntent().getStringExtra("payment_id")),getIntent().getStringExtra("appointment_id")==null?"0":getIntent().getStringExtra("appointment_id"),bookstatus);
                mLayoutManager = new LinearLayoutManager(this);
                recycler_view_doctor.setLayoutManager(mLayoutManager);
                recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
                recycler_view_doctor.setAdapter(DoctorBookingAdapterboo);
                recycler_view_doctor.setVisibility(View.VISIBLE);
            } else {
                recycler_view_doctor.setVisibility(View.GONE);
            }
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        }
    }


    public void dataParseAppointment(String response) {

        Gson gson = new Gson();
        sliderImage = gson.fromJson(response, GalleryListResponse.class);
        sliderLists = sliderImage.getData();

        if (sliderLists.size() > 0) {
            DoctorPatient = new DoctorPatientAdapter(sliderLists, this,2);
            mLayoutManager = new LinearLayoutManager(this);
            recycler_view_doctor.setLayoutManager(mLayoutManager);
            recycler_view_doctor.setItemAnimator(new DefaultItemAnimator());
            recycler_view_doctor.setAdapter(DoctorPatient);
            recycler_view_doctor.setVisibility(View.VISIBLE);
        } else {
            recycler_view_doctor.setVisibility(View.GONE);
        }

    }


    @Override
    public void onClick(View v) {
        if (v == mon) {
            getFutureDayOfWeak(0);
            setBackgroundColor(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(wed);
            setBackgroundColorW(thurs);
            setBackgroundColorW(fri);
            setBackgroundColorW(sat);
        } else if (v == tue) {
            getFutureDayOfWeak(1);
            setBackgroundColor(tue);
            setBackgroundColorW(mon);
            setBackgroundColorW(wed);
            setBackgroundColorW(thurs);
            setBackgroundColorW(fri);
            setBackgroundColorW(sat);
        } else if (v == wed) {
            getFutureDayOfWeak(2);
            setBackgroundColor(wed);
            setBackgroundColorW(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(thurs);
            setBackgroundColorW(fri);
            setBackgroundColorW(sat);
        } else if (v == thurs) {
            getFutureDayOfWeak(3);
            setBackgroundColor(thurs);
            setBackgroundColorW(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(wed);
            setBackgroundColorW(fri);
            setBackgroundColorW(sat);
        } else if (v == fri) {
            getFutureDayOfWeak(4);
            setBackgroundColor(fri);
            setBackgroundColorW(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(wed);
            setBackgroundColorW(thurs);
            setBackgroundColorW(sat);
        } else if (v == sat) {
            getFutureDayOfWeak(5);
            setBackgroundColor(sat);
            setBackgroundColorW(mon);
            setBackgroundColorW(tue);
            setBackgroundColorW(wed);
            setBackgroundColorW(thurs);
            setBackgroundColorW(fri);
        } else if (v == calander) {
            DatePickerDialog = new DatePickerDialog.OnDateSetListener() {

                @Override
                public void onDateSet(DatePicker view, int year, int monthOfYear,
                                      int dayOfMonth) {
                    // TODO Auto-generated method stub
                    myCalendar.set(Calendar.YEAR, year);
                    myCalendar.set(Calendar.MONTH, monthOfYear);
                    myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                    String dateformate = formatter.format(myCalendar.getTime());
                    tv_selectedDate.setText(dateformate);
                  //  tv_selectedDate.setText(myCalendar.get(Calendar.YEAR)+"-"+00+(myCalendar.get(Calendar.DATE)+1)+"-"+00+myCalendar.get(Calendar.DAY_OF_MONTH));
                    switchDay(myCalendar.get(Calendar.DAY_OF_WEEK));
                }

            };
            new DatePickerDialog(DOctoeByIdActivityOffline.this, DatePickerDialog, myCalendar
                    .get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                    myCalendar.get(Calendar.DAY_OF_MONTH)).show();
        }
    }


    private void setBackgroundColorW(TextView mon) {
        mon.setBackground(getResources().getDrawable(R.drawable.rect1));
        mon.setTextColor(getResources().getColor(R.color.black));

    }

    int currentDay = 0;

    private void setBackgroundColor(TextView mon) {
        mon.setBackground(getResources().getDrawable(R.drawable.rect_blue));
        mon.setTextColor(getResources().getColor(R.color.white));

    }

    private void HitApi(String day) {
        if (UtilMethods.INSTANCE.isNetworkAvialable(this)) {
            Loader loader;
            loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);
            UtilMethods.INSTANCE.GetAvailabilityOffline(this, operator.getId(),tv_selectedDate.getText().toString(), day, loader,notAvailable);

        } else {
            UtilMethods.INSTANCE.NetworkError(this, getResources().getString(R.string.network_error_title),
                    getResources().getString(R.string.network_error_message));
        }


    }

    String getMonthForInt(int num) {
        String month = "wrong";
        DateFormatSymbols dfs = new DateFormatSymbols();
        String[] months = dfs.getMonths();
        if (num >= 0 && num <= 11) {
            month = months[num];
        }
        return month.substring(0, 3);
    }

    public int getFutureDays(int day) {
        Calendar calendar = Calendar.getInstance(); // this would default to now

        calendar.add(Calendar.DAY_OF_MONTH, day);
        currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateformate = formatter.format(calendar.getTime());
        tv_selectedDate.setText(dateformate);
       // tv_selectedDate.setText(calendar.get(Calendar.YEAR)+"-"+00+(int) (calendar.get(Calendar.MONTH)+1)+"-"+00+(int)calendar.get(Calendar.DAY_OF_MONTH));
        return calendar.get(Calendar.DATE);
    }

    public void getFutureDayOfWeak(int day) {
        Calendar calendar = Calendar.getInstance(); // this would default to now
        calendar.add(Calendar.DAY_OF_MONTH, day);
        currentDay = calendar.get(Calendar.DAY_OF_WEEK);
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        String dateformate = formatter.format(calendar.getTime());
        tv_selectedDate.setText(dateformate);
      //  tv_selectedDate.setText(calendar.get(Calendar.YEAR)+"-"+00+(int)(calendar.get(Calendar.MONTH)+1)+"-"+00+(int)calendar.get(Calendar.DAY_OF_MONTH));
        switchDay(currentDay);
    }

    public void switchDay(int day) {
        Log.e("currentDay", currentDay + "");
        switch (day) {
            case Calendar.SUNDAY:
                HitApi("Sunday");
                break;
            case Calendar.MONDAY:
                HitApi("Monday");
                break;
            case Calendar.TUESDAY:
                HitApi("Tuesday");
                break;
            case Calendar.WEDNESDAY:
                HitApi("Wednesday");
                break;
            case Calendar.THURSDAY:
                HitApi("Thursday");
                break;
            case Calendar.FRIDAY:
                HitApi("Friday");
                break;
            case Calendar.SATURDAY:
                HitApi("Saturday");
                break;
        }
    }
}