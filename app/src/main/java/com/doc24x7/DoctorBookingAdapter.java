package com.doc24x7;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.Dashbord.dto.Datum;

import com.doc24x7.Splash.ui.SelectAccountActivity;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.gson.Gson;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static android.content.Context.MODE_PRIVATE;


public class DoctorBookingAdapter extends RecyclerView.Adapter<DoctorBookingAdapter.MyViewHolder> implements DatePickerDialog.OnDateSetListener {

    private ArrayList<Datum> transactionsList;
    private Activity mContext;
    String Symtom_id = "";
    int appDay = 0;
    Calendar[] calendars = new Calendar[100];
    FragmentManager fragmentManager;
    private String currentDay;
    Datum doctorDetail;
    String paymentStatus;
    String appointment_id;
    String bookstatus;
    int payment_id;

    public void filter(ArrayList<Datum> newlist) {
        transactionsList = new ArrayList<>();
        transactionsList.addAll(newlist);
        notifyDataSetChanged();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tv_title;
        public GridLayout ll;


        public MyViewHolder(View view) {
            super(view);
            tv_title = view.findViewById(R.id.tv_title);
            ll = view.findViewById(R.id.ll);
        }
    }

    public DoctorBookingAdapter(ArrayList<Datum> transactionsList, Activity mContext, String Symtom_id, FragmentManager fragmentManager, String curentDay, Datum doctorDetail, String paymentStatus, int payment_id, String appointment_id,String bookstatus) {
        this.transactionsList = transactionsList;
        this.mContext = mContext;
        this.Symtom_id = Symtom_id;
        this.fragmentManager = fragmentManager;
        this.currentDay = curentDay;
        this.doctorDetail = doctorDetail;
        this.paymentStatus = paymentStatus;
        this.payment_id = payment_id;
        this.appointment_id = appointment_id;
        this.bookstatus=bookstatus;
    }

    @Override
    public DoctorBookingAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.finftimebooking_adapter, parent, false);
        return new DoctorBookingAdapter.MyViewHolder(itemView);

    }

    @Override
    public void onBindViewHolder(final DoctorBookingAdapter.MyViewHolder holder, int position) {
        final Datum operator = transactionsList.get(position);
        int patient = Integer.parseInt(operator.getNo_of_patient());
        SimpleDateFormat sdf = new SimpleDateFormat("hh:mm a");
        Date CurrentTime = null;
        Date startdate = null;
        holder.ll.removeAllViews();
        try {
            startdate = sdf.parse(operator.getStart_time());
            CurrentTime = sdf.parse(sdf.format(new Date()));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long time = startdate.getTime();
        Log.e("time:", time + "");
        Date enddate = null;
        try {
            enddate = sdf.parse(operator.getEnd_time());
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long endddate = enddate.getTime();
        Log.e("time:endddate:", endddate + "");
        long slot = (endddate - time) / patient;
        Log.e("time:slot:", slot + "");
        long minutes = TimeUnit.MILLISECONDS.toMinutes(slot);
        Log.e("time:minutes:", minutes + "");
        String sTime = "";
        long lotTime = startdate.getTime();

        holder.tv_title.setText(operator.getStart_time() + "-" + operator.getEnd_time());
        for (int i = 0; i < operator.getSlot_id().size(); i++) {
            final int subSlotPosition = i;
            TextView textView = new TextView(mContext);
            textView.setBackground(mContext.getResources().getDrawable(R.drawable.rectsearch));
            textView.setPadding(10, 10, 10, 10);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            params.setMargins(10, 10, 10, 10);
            params.gravity = Gravity.CENTER;
            textView.setLayoutParams(params);
            textView.setText("  "+operator.getSlot_id().get(i).getStart_time()+"  ");
            textView.setTextSize(mContext.getResources().getDimension(R.dimen._4sdp));
            textView.setTag(operator.getSlot_id().get(i).getStart_time());
            holder.ll.addView(textView);
            if (operator.getSlot_id().get(i).getBooked_status()) {
                textView.setEnabled(false);
                textView.setBackground(mContext.getResources().getDrawable(R.drawable.circular_grey_bordersolid));
            }
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    SharedPreferences myPreferences =  mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
                    String pref = balanceResponse;

                    if(pref==null||pref.equalsIgnoreCase(""))
                    {
                        Intent intent = new Intent(mContext, SelectAccountActivity.class);
                        mContext.startActivity(intent);
                    }
                    else {

                        if (UtilMethods.INSTANCE.isNetworkAvialable(mContext)) {
                            Loader loader = new Loader(mContext, android.R.style.Theme_Translucent_NoTitleBar);
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            if (paymentStatus != null) {
                                Intent i = new Intent(mContext, PatientDetail.class);
                                i.putExtra("currentDay", currentDay + "");
                                i.putExtra("dateTime", "" + currentDay + " " + textView.getText().toString());
                                i.putExtra("Doctordetail", new Gson().toJson(operator));
                                i.putExtra("slot_id", operator.getSlot_id().get(subSlotPosition).getId());
                                i.putExtra("parent_slot_id", "" + operator.getId());
                                i.putExtra("payment_id", "" + payment_id);
                                i.putExtra("AppintmentDetail", new Gson().toJson(operator));
                                i.putExtra("appointment_id", appointment_id);
                                i.putExtra("bookstatus",bookstatus);
                                mContext.startActivity(i);
                            } else {


                                UtilMethods.INSTANCE.chkpayment(mContext, operator.getDoctor_id(), doctorDetail, loader, "",
                                        "" + operator.getDoctor_id(), "" + operator.getSlot_id().get(subSlotPosition).getId(), "" + operator.getId(),
                                        ""+(bookstatus.equals("1")?doctorDetail.getDoctor_fees():doctorDetail.getClinic_fees()), operator, currentDay + " " + textView.getText().toString(), currentDay, null,bookstatus,"1");
                            }
                        } else {
                            UtilMethods.INSTANCE.NetworkError(mContext, mContext.getResources().getString(R.string.network_error_title),
                                    mContext.getResources().getString(R.string.network_error_message));
                        }

                    }
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return transactionsList.size();
    }

}