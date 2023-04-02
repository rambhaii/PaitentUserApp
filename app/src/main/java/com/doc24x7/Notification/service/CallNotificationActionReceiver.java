package com.doc24x7.Notification.service;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import androidx.core.content.ContextCompat;

import com.doc24x7.Video.CallActivity;
import com.doc24x7.Video.model.ConstantApp;

public class CallNotificationActionReceiver extends BroadcastReceiver {


    Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.mContext=context;
        if (intent != null && intent.getExtras() != null) {

            String action ="";
            action=intent.getStringExtra("ACTION_TYPE");

            if (action != null&& !action.equalsIgnoreCase("")) {

                performClickAction(context, action,intent);
            }

            // Close the notification after the click action is performed.
            Intent iclose = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(iclose);
            context.stopService(new Intent(context, HeadsUpNotificationService.class));

        }


    }
    private void performClickAction(Context context, String action,Intent intent) {
        if(action.equalsIgnoreCase("RECEIVE_CALL")) {

//            if (checkAppPermissions()) {
                Intent intentCallReceive = new Intent(mContext, CallActivity.class);
                intentCallReceive.putExtra("Call", "incoming");
                intentCallReceive.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                intentCallReceive.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, intent.getExtras().getString(ConstantApp.ACTION_KEY_CHANNEL_NAME));
                intentCallReceive.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, intent.getExtras().getString(ConstantApp.ACTION_KEY_ENCRYPTION_KEY));
                intentCallReceive.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, intent.getExtras().getString(ConstantApp.ACTION_KEY_ENCRYPTION_MODE));
                intentCallReceive.putExtra(ConstantApp.ACCESS_TOKEN, intent.getExtras().getString(ConstantApp.ACCESS_TOKEN));
                intentCallReceive.putExtra(ConstantApp.UID, intent.getExtras().getString(ConstantApp.UID));
                mContext.startActivity(intentCallReceive);
           // }
//            else{
//                Intent intent = new Intent(getApplicationContext(), CallActivity.class);
//                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
//                intent.putExtra("CallFrom","call from push");
//                mContext.startActivity(intent);
//
//            }
        }
        else if(action.equalsIgnoreCase("DIALOG_CALL")){

            // show ringing activity when phone is locked
            Intent intentCallReceive = new Intent(mContext, CallActivity.class);
            intentCallReceive.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentCallReceive.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
            intentCallReceive.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, intent.getExtras().getString(ConstantApp.ACTION_KEY_CHANNEL_NAME));
            intentCallReceive.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, intent.getExtras().getString(ConstantApp.ACTION_KEY_ENCRYPTION_KEY));
            intentCallReceive.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, intent.getExtras().getString(ConstantApp.ACTION_KEY_ENCRYPTION_MODE));
            intentCallReceive.putExtra(ConstantApp.ACCESS_TOKEN, intent.getExtras().getString(ConstantApp.ACCESS_TOKEN));
            intentCallReceive.putExtra(ConstantApp.UID, intent.getExtras().getString(ConstantApp.UID));
            mContext.startActivity(intentCallReceive);
        }

        else {
            context.stopService(new Intent(context, HeadsUpNotificationService.class));
            Intent it = new Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS);
            context.sendBroadcast(it);
        }
    }

    private Boolean checkAppPermissions() {
        return hasReadPermissions() && hasWritePermissions() && hasCameraPermissions() && hasAudioPermissions();
    }

    private boolean hasAudioPermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.RECORD_AUDIO) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasReadPermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }

    private boolean hasWritePermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED);
    }
    private boolean hasCameraPermissions() {
        return (ContextCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED);
    }
}