package com.doc24x7.DoctorDashboad;

import android.app.Dialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.TimePicker;

import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.gson.Gson;

import java.util.Calendar;
import java.util.Date;

import cn.pedant.SweetAlert.SweetAlertDialog;

import static android.content.Context.MODE_PRIVATE;


public class SchedulingFragment extends Fragment  implements View.OnClickListener {

    Button bt_Submit;
    EditText selectdate,starttime,endtime;
    Calendar calendar;
    private int CalendarHour, CalendarMinute;
    TimePickerDialog timepickerdialog;
    String format;
    Loader loader;
LinearLayout Logout;
TextView name,contentnumber;




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_scheduling, container, false);


        Getid(v);

        return v;


    }

    private void Getid(View v) {

        contentnumber=v.findViewById(R.id.contentnumber);
        name=v.findViewById(R.id.name);

        SharedPreferences myPreferences =  getActivity().getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);

        name.setText(""+balanceCheckResponse.getName());
         contentnumber.setText(""+balanceCheckResponse.getMobile());


        loader = new Loader(getActivity(), android.R.style.Theme_Translucent_NoTitleBar);





        Logout=v.findViewById(R.id.Logout);

        endtime=v.findViewById(R.id.endtime);
        starttime=v.findViewById(R.id.starttime);
        selectdate=v.findViewById(R.id.selectdate);

        bt_Submit=v.findViewById(R.id.bt_Submit);
        bt_Submit.setOnClickListener(this);

        selectdate.setOnClickListener(this);
        endtime.setOnClickListener(this);
        starttime.setOnClickListener(this);
        Logout.setOnClickListener(this);


    }

    public void Datepicker() {

        LayoutInflater inflater = (LayoutInflater) getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.datepicker_pop, null);

        Button tvLater =  view.findViewById(R.id.tv_later);
        Button tv_ok =  view.findViewById(R.id.tv_ok);
        final DatePicker datePicker = (DatePicker) view.findViewById(R.id.date_picker);




        final Dialog dialog = new Dialog(getActivity());

        dialog.setCancelable(false);
        dialog.setContentView(view);


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



    @Override
    public void onClick(View view) {

        if(view==Logout){


            final SweetAlertDialog alertDialog = new SweetAlertDialog(getActivity());
            alertDialog.setTitle("Alert!");
            alertDialog.setContentText("Do you want to logout?");
            alertDialog.setCancelButton("Cancel", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {
                    alertDialog.dismiss();
                }
            });

            alertDialog.setConfirmButton("Yes", new SweetAlertDialog.OnSweetClickListener() {
                @Override
                public void onClick(SweetAlertDialog sweetAlertDialog) {

                    UtilMethods.INSTANCE.logout(getActivity());


                }
            });

            alertDialog.show();

        }


        if(view==selectdate){


            Datepicker();


        }

      if(view==endtime){




          calendar = Calendar.getInstance();
          CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
          CalendarMinute = calendar.get(Calendar.MINUTE);


          timepickerdialog = new TimePickerDialog(getActivity(),
                  new TimePickerDialog.OnTimeSetListener() {

                      @Override
                      public void onTimeSet(TimePicker view, int hourOfDay,
                                            int minute) {

                          if (hourOfDay == 0) {

                              hourOfDay += 12;

                            //  format = "AM";
                              format = "";
                          }
                          else if (hourOfDay == 12) {

                              format = "";
                             // format = "PM";

                          }
                          else if (hourOfDay > 12) {

                              hourOfDay -= 12;

                              format = "PM";

                          }
                          else {

                              format = "AM";
                          }


                          endtime.setText(hourOfDay + ":" + minute + format);

                      }
                  }, CalendarHour, CalendarMinute, false);
          timepickerdialog.show();




      }

      if(view==starttime){



          calendar = Calendar.getInstance();
          CalendarHour = calendar.get(Calendar.HOUR_OF_DAY);
          CalendarMinute = calendar.get(Calendar.MINUTE);


          timepickerdialog = new TimePickerDialog(getActivity(),
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


                          starttime.setText(hourOfDay + ":" + minute + format);

                      }
                  }, CalendarHour, CalendarMinute, false);
          timepickerdialog.show();




        }


      if(view==bt_Submit){



          if (UtilMethods.INSTANCE.isNetworkAvialable( getActivity())) {

              loader.show();
              loader.setCancelable(false);
              loader.setCanceledOnTouchOutside(false);


              UtilMethods.INSTANCE.SetAvailability(getActivity(), "",""+starttime.getText().toString().trim(),
                      ""+endtime.getText().toString().trim(), ""+selectdate.getText().toString().trim(),loader);

          } else {
              UtilMethods.INSTANCE.NetworkError(getActivity(), getActivity().getResources().getString(R.string.network_error_title),
                      getActivity().getResources().getString(R.string.network_error_message));
          }


      }


    }
}