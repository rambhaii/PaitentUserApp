package com.doc24x7.Dashbord.ui;





import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.doc24x7.Dashbord.dto.SymptomData;
import com.doc24x7.R;
import com.doc24x7.Video.CallActivity;
import com.doc24x7.Video.model.ConstantApp;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class NotificationAdapter extends RecyclerView.Adapter<NotificationAdapter.notiSymtomlist> {
    List<SymptomData> notiSymtomlist;
    Context mContext;

    public NotificationAdapter(List<SymptomData> notiSymtomlist, Context mContext) {
        this.notiSymtomlist = notiSymtomlist;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public NotificationAdapter.notiSymtomlist onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.notificationview, parent, false);
        notiSymtomlist notilist = new notiSymtomlist(view);
        return notilist;
    }

    @Override
    public void onBindViewHolder(@NonNull NotificationAdapter.notiSymtomlist holder, int position) {
        final SymptomData currentitem = notiSymtomlist.get(position);
               holder.doctor.setText(currentitem.getNotidoctname());
               holder.date.setText(currentitem.getCreate_date());
               holder.cardclick.setOnClickListener(new View.OnClickListener() {
                   @Override
                   public void onClick(View v) {
                     //  if (currentitem.getActivityType().equalsIgnoreCase("1")) {
                           Intent i = new Intent(mContext, CallActivity.class);
                           i.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, currentitem.getRoom());
                           i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, currentitem.getEncryption_key());
                           i.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, currentitem.getEncryption_type());
                           i.putExtra(ConstantApp.ACCESS_TOKEN, currentitem.getToken_with_int_uid());
                           mContext.startActivity(i);
//                       }
//                       else {
//                          Intent i = new Intent(getApplicationContext(), MessageActivity.class);
//                           i.putExtra(MessageUtil.INTENT_EXTRA_IS_PEER_MODE, true);
//                           i.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, currentitem.getTarget());
//                           i.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, currentitem.getUserId());
//                           UtilMethods.INSTANCE.GetRTMAccessToken(getApplicationContext(), currentitem.getTarget(), null, i);
//
//                       }
                   }

               });
                   String shortn=currentitem.getNotidoctname().charAt(0)+"";
                    holder.shortname.setText(shortn);
        try {
            JSONObject jsonObject=new JSONObject(currentitem.getNotimessage());
            String title=jsonObject.getString("title");
        //   String type=jsonObject.getString("activityType");
       //   Toast.makeText(mContext, ""+title, Toast.LENGTH_SHORT).show();
            holder.message.setText(title);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date = new Date();
        findDifference(currentitem.getCreate_date(), formatter.format(date),holder);
    }

    @Override
    public int getItemCount() {
        return notiSymtomlist.size();

    }

    public class notiSymtomlist extends RecyclerView.ViewHolder {
        TextView message,doctor,shortname,date;
  LinearLayout cardclick;
        public notiSymtomlist(@NonNull View itemView) {
            super(itemView);
            message = itemView.findViewById(R.id.notimessage);
            doctor=itemView.findViewById(R.id.doctname);
              shortname=itemView.findViewById(R.id.shortname);
//            mobile = itemView.findViewById(R.id.tv_mobile);
               date = itemView.findViewById(R.id.notidate);
               cardclick=itemView.findViewById(R.id.cardclick);
        }
    }
    static void findDifference(String start_date,
                               String end_date,NotificationAdapter.notiSymtomlist holder)
    {

        // SimpleDateFormat converts the
        // string format to date object
        SimpleDateFormat sdf = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss");

        // Try Block
        try {

            // parse method is used to parse
            // the text from a string to
            // produce the date
            Date d1 = sdf.parse(start_date);
            Date d2 = sdf.parse(end_date);

            // Calucalte time difference
            // in milliseconds
            long difference_In_Time
                    = d2.getTime() - d1.getTime();

            // Calucalte time difference in
            // seconds, minutes, hours, years,
            // and days
            long difference_In_Seconds
                    = (difference_In_Time
                    / 1000)
                    % 60;

            long difference_In_Minutes
                    = (difference_In_Time
                    / (1000 * 60))
                    % 60;

            long difference_In_Hours
                    = (difference_In_Time
                    / (1000 * 60 * 60))
                    % 24;

            long difference_In_Years
                    = (difference_In_Time
                    / (1000l * 60 * 60 * 24 * 365));

            long difference_In_Days
                    = (difference_In_Time
                    / (1000 * 60 * 60 * 24))
                    % 365;
      if(difference_In_Days==0 && difference_In_Hours==0 )
      {
          holder.date.setText(difference_In_Minutes + " Minutes ago");
      }
      else if(difference_In_Days==0 && difference_In_Hours!=0) {
          holder.date.setText(difference_In_Hours + " Hour ago");
      }
      else if(difference_In_Minutes==0 && difference_In_Days==0 && difference_In_Hours==0 )
      {
          holder.date.setText("just now");
      }
      else {
          holder.date.setText(difference_In_Days + " days ago");
      }
            // Print the date difference in
            // years, in days, in hours, in
            // minutes, and in seconds

//            System.out.print(
//                    "Difference "
//                            + "between two dates is: ");
//
//            System.out.println(
//                    difference_In_Years
//                            + " years, "
//                            + difference_In_Days
//                            + " days, "
//                            + difference_In_Hours
//                            + " hours, "
//                            + difference_In_Minutes
//                            + " minutes, "
//                            + difference_In_Seconds
//                            + " seconds");
        }

        // Catch the Exception
        catch (ParseException e) {
            e.printStackTrace();
        }
    }
}