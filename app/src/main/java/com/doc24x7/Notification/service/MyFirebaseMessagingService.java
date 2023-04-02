package com.doc24x7.Notification.service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.content.ContextCompat;

import com.doc24x7.R;
import com.doc24x7.RTM.activity.MessageActivity;
import com.doc24x7.RTM.utils.MessageUtil;
import com.doc24x7.Splash.ui.Splash;
import com.doc24x7.Utils.UtilMethods;
import com.doc24x7.Video.model.ConstantApp;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.doc24x7.Notification.util.NotificationUtils;
import com.google.gson.Gson;


public class MyFirebaseMessagingService extends FirebaseMessagingService {

    private static final String TAG = MyFirebaseMessagingService.class.getSimpleName();

    private NotificationUtils notificationUtils;

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.e("remoteMessage", "remoteMessage        " + remoteMessage);
        String str = "";
        Bitmap icon = BitmapFactory.decodeResource(getApplicationContext().getResources(), R.mipmap.ic_launcher);
        Intent i;
        CallModel callModel = new Gson().fromJson(remoteMessage.getData().get("body"), CallModel.class);
        if (callModel != null) {
            if (callModel.getActivityType().equalsIgnoreCase("1")) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
                    Intent serviceIntent = new Intent(getApplicationContext(), HeadsUpNotificationService.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("inititator","Doctor");
                    mBundle.putString("call_type","Video");
                    mBundle.putString(ConstantApp.ACTION_KEY_CHANNEL_NAME, callModel.getRoom());
                    mBundle.putString(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, callModel.getEncryption_key());
                    mBundle.putString(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, callModel.getEncryption_type());
                    mBundle.putString(ConstantApp.ACCESS_TOKEN, callModel.getToken_with_int_uid());
                    mBundle.putString(ConstantApp.UID, callModel.getUid());
                    serviceIntent.putExtras(mBundle);
                    ContextCompat.startForegroundService(getApplicationContext(), serviceIntent);
                }else{
                    Intent serviceIntent = new Intent(getApplicationContext(), HeadsUpNotificationService.class);
                    Bundle mBundle = new Bundle();
                    mBundle.putString("inititator", "Doctor");
                    mBundle.putString("call_type","Video");
                    mBundle.putString(ConstantApp.ACTION_KEY_CHANNEL_NAME, callModel.getRoom());
                    mBundle.putString(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, callModel.getEncryption_key());
                    mBundle.putString(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, callModel.getEncryption_type());
                    mBundle.putString(ConstantApp.ACCESS_TOKEN, callModel.getToken_with_int_uid());
                    mBundle.putString(ConstantApp.UID, callModel.getUid());
                    serviceIntent.putExtras(mBundle);
                    getApplicationContext().startService(serviceIntent);
                }
            } else {
                i = new Intent(getApplicationContext(), MessageActivity.class);
                i.putExtra(MessageUtil.INTENT_EXTRA_IS_PEER_MODE, true);
                i.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, callModel.getTarget());
                i.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, callModel.getUserId());

                UtilMethods.INSTANCE.GetRTMAccessToken(getApplicationContext(),callModel.getTarget(),callModel.getUserId(),null,i);
                PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
                NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                        .setContentTitle(remoteMessage.getData().get("title"))
                        .setContentText(str)
                        .setAutoCancel(true)
                        .setSound(Uri.parse("android.resource://" + getPackageName() + "/raw/incoming"))
                        //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                        .setContentIntent(pendingIntent)
                        .setContentInfo(remoteMessage.getData().get("message"))
                        .setLargeIcon(icon)
                        .setColor(Color.RED)
                        .setColorized(true)
                        .setColor(getApplicationContext().getResources().getColor(R.color.white))
                        .setLights(Color.WHITE, 1000, 300)
                        .setDefaults(Notification.DEFAULT_VIBRATE)
                        .setSmallIcon(R.mipmap.ic_launcher);
                NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

                // Notification Channel is required for Android O and above
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    NotificationChannel channel = new NotificationChannel(
                            "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
                    );
                    channel.setDescription("channel description");
                    channel.setShowBadge(true);
                    channel.canShowBadge();
                    channel.enableLights(true);
                    channel.setLightColor(Color.RED);
                    channel.enableVibration(true);
                    channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
                    notificationManager.createNotificationChannel(channel);
                }
                notificationManager.notify(0, notificationBuilder.build());
            }

        } else {
            i = new Intent(getApplicationContext(), Splash.class);
            PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext(), 0, i, PendingIntent.FLAG_UPDATE_CURRENT);
            NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(getApplicationContext(), "channel_id")
                    .setContentTitle(remoteMessage.getData().get("title"))
                    .setContentText(str)
                    .setAutoCancel(true)
                    .setSound(Uri.parse("android.resource://" + getPackageName() + "/raw/incoming"))
                    //.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM))
                    .setContentIntent(pendingIntent)
                    .setContentInfo(remoteMessage.getData().get("message"))
                    .setLargeIcon(icon)
                    .setColor(Color.RED)
                    .setColorized(true)
                    .setColor(getApplicationContext().getResources().getColor(R.color.white))
                    .setLights(Color.WHITE, 1000, 300)
                    .setDefaults(Notification.DEFAULT_VIBRATE)
                    .setSmallIcon(R.mipmap.ic_launcher);
            NotificationManager notificationManager = (NotificationManager) getApplicationContext().getSystemService(Context.NOTIFICATION_SERVICE);

            // Notification Channel is required for Android O and above
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                NotificationChannel channel = new NotificationChannel(
                        "channel_id", "channel_name", NotificationManager.IMPORTANCE_DEFAULT
                );
                channel.setDescription("channel description");
                channel.setShowBadge(true);
                channel.canShowBadge();
                channel.enableLights(true);
                channel.setLightColor(Color.RED);
                channel.enableVibration(true);
                channel.setVibrationPattern(new long[]{100, 200, 300, 400, 500});
                notificationManager.createNotificationChannel(channel);
            }

            notificationManager.notify(0, notificationBuilder.build());
        }

    }


}
