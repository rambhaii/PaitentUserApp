package com.doc24x7.Notification.service;

import android.app.KeyguardManager;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.AudioAttributes;
import android.media.AudioFocusRequest;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.Vibrator;
import android.provider.Settings;
import android.util.Log;
import android.widget.RemoteViews;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationCompat;

import com.doc24x7.Dashbord.NewUser;

import com.doc24x7.R;

import com.doc24x7.Video.model.ConstantApp;

import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class HeadsUpNotificationService extends Service implements MediaPlayer.OnPreparedListener  {
    private String CHANNEL_ID = "CallChannel";
    private String CHANNEL_NAME = "Call Channel";
    MediaPlayer mediaPlayer;
    Vibrator mvibrator;
    AudioManager audioManager;
    AudioAttributes playbackAttributes;
    private Handler handler;
    AudioManager.OnAudioFocusChangeListener afChangeListener;
    private boolean status = false;
    private boolean vstatus = false;


    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        if (Build.VERSION.SDK_INT >= 28) {
            String CHANNEL_ID = "my_channel_01";
            Intent intent = new Intent(this, NewUser.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

            PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);


            NotificationChannel channel = new NotificationChannel(CHANNEL_ID,
                    "Channel human readable title",
                    NotificationManager.IMPORTANCE_DEFAULT);

            ((NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE)).createNotificationChannel(channel);

            Notification notification = new NotificationCompat.Builder(this, CHANNEL_ID)
                    .setContentTitle("")
                    .setContentText("")
                    .setContentIntent(pendingIntent)
                    .build();
            startForeground(1, notification);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP_MR1)
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Bundle data = null;
        String name="",callType="";
        int NOTIFICATION_ID=120;
        try {
            audioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);

            if (audioManager != null) {
                switch (audioManager.getRingerMode()) {
                    case AudioManager.RINGER_MODE_NORMAL:
                        status = true;
                        break;
                    case AudioManager.RINGER_MODE_SILENT:
                        status = false;
                        break;
                    case AudioManager.RINGER_MODE_VIBRATE:
                        status = false;
                        vstatus=true;
                        Log.e("Service!!", "vibrate mode");
                        break;
                }
            }

            if (status) {
                Runnable delayedStopRunnable = new Runnable() {
                    @Override
                    public void run() {
                        releaseMediaPlayer();
                    }
                };

                afChangeListener =  new AudioManager.OnAudioFocusChangeListener() {
                    public void onAudioFocusChange(int focusChange) {
                        if (focusChange == AudioManager.AUDIOFOCUS_LOSS) {
                            // Permanent loss of audio focus
                            // Pause playback immediately
                            //mediaController.getTransportControls().pause();
                            if (mediaPlayer!=null) {
                                if (mediaPlayer.isPlaying()) {
                                    mediaPlayer.pause();
                                }
                            }
                            // Wait 30 seconds before stopping playback
                            handler.postDelayed(delayedStopRunnable,
                                    TimeUnit.SECONDS.toMillis(30));
                        }
                        else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT) {
                            // Pause playback
                        } else if (focusChange == AudioManager.AUDIOFOCUS_LOSS_TRANSIENT_CAN_DUCK) {
                            // Lower the volume, keep playing
                        } else if (focusChange == AudioManager.AUDIOFOCUS_GAIN) {
                            // Your app has been granted audio focus again
                            // Raise volume to normal, restart playback if necessary
                        }
                    }
                };
                KeyguardManager keyguardManager = (KeyguardManager) getSystemService(Context.KEYGUARD_SERVICE);


              // mediaPlayer= MediaPlayer.create(this, Settings.System.DEFAULT_RINGTONE_URI);
                mediaPlayer= MediaPlayer.create(this, Uri.parse("android.resource://" + getPackageName() + "/raw/incoming"));
                mediaPlayer.setOnPreparedListener(this);
                mediaPlayer.setLooping(true);
                mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    handler = new Handler();
                    playbackAttributes = new AudioAttributes.Builder()
                            .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                            .setContentType(AudioAttributes.CONTENT_TYPE_MUSIC)
                            .build();

                    AudioFocusRequest focusRequest = new AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT)
                            .setAudioAttributes(playbackAttributes)
                            .setAcceptsDelayedFocusGain(true)
                            .setOnAudioFocusChangeListener(afChangeListener, handler)
                            .build();
                    int res = audioManager.requestAudioFocus(focusRequest);
                    if (res == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        if(!keyguardManager.isDeviceLocked()) {

                            mediaPlayer.start();
                        }

                    }
                }else {

                    // Request audio focus for playback
                    int result = audioManager.requestAudioFocus(afChangeListener,
                            // Use the music stream.
                            AudioManager.STREAM_MUSIC,
                            // Request permanent focus.
                            AudioManager.AUDIOFOCUS_GAIN_TRANSIENT);

                    if (result == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                        if(!keyguardManager.isDeviceLocked()) {
                            // Start playback
                            mediaPlayer.start();
                        }
                    }

                }

            }
            else if(vstatus){
                mvibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Start without a delay
                // Each element then alternates between vibrate, sleep, vibrate, sleep...
                long[] pattern = {0, 250, 200, 250, 150, 150, 75,
                        150, 75, 150};

                // The '-1' here means to vibrate once, as '-1' is out of bounds in the pattern array
                mvibrator.vibrate(pattern,0);
                Log.e("Service!!", "vibrate mode start");

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (intent != null && intent.getExtras() != null) {

            data = intent.getExtras();
            name =data.getString("inititator");
//            if(AppController.getInstance().getCall_type().equalsIgnoreCase(ApplicationRef.Constants.AUDIO_CALL)){
//                callType ="Audio";
//            }
//            else {
//                callType ="Video";
//            }

        }
        try {


            Intent receiveCallAction = new Intent(getApplicationContext(), CallNotificationActionReceiver.class);
            Bundle mBundle = intent.getExtras();
            Log.e( "sdsdasd",intent.getExtras().getString(ConstantApp.ACTION_KEY_CHANNEL_NAME));
            receiveCallAction.putExtra(ConstantApp.ACTION_KEY_CHANNEL_NAME, mBundle.getString(ConstantApp.ACTION_KEY_CHANNEL_NAME));
            receiveCallAction.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_KEY, mBundle.getString(ConstantApp.ACTION_KEY_ENCRYPTION_KEY));
            receiveCallAction.putExtra(ConstantApp.ACTION_KEY_ENCRYPTION_MODE, mBundle.getString(ConstantApp.ACTION_KEY_ENCRYPTION_MODE));
            receiveCallAction.putExtra(ConstantApp.ACCESS_TOKEN, mBundle.getString(ConstantApp.ACCESS_TOKEN));
            receiveCallAction.putExtra(ConstantApp.UID, mBundle.getString(ConstantApp.UID));
            receiveCallAction.putExtra("ConstantApp.CALL_RESPONSE_ACTION_KEY", "ConstantApp.CALL_RECEIVE_ACTION");
            receiveCallAction.putExtra("ACTION_TYPE", "RECEIVE_CALL");
            receiveCallAction.putExtra("NOTIFICATION_ID",NOTIFICATION_ID);
            receiveCallAction.setAction("RECEIVE_CALL");
            receiveCallAction.setClass(this, CallNotificationActionReceiver.class);

            Intent cancelCallAction = new Intent(getApplicationContext(), CallNotificationActionReceiver.class);
            cancelCallAction.putExtra("ConstantApp.CALL_RESPONSE_ACTION_KEY", "ConstantApp.CALL_CANCEL_ACTION");
            cancelCallAction.putExtra("ACTION_TYPE", "CANCEL_CALL");
            cancelCallAction.putExtra("NOTIFICATION_ID",NOTIFICATION_ID);
            cancelCallAction.setAction("CANCEL_CALL");

            Intent callDialogAction = new Intent(getApplicationContext(), CallNotificationActionReceiver.class);
//            callDialogAction.putExtra("ACTION_TYPE", "DIALOG_CALL");
//            callDialogAction.putExtra("NOTIFICATION_ID",NOTIFICATION_ID);
//            callDialogAction.setAction("DIALOG_CALL");

            PendingIntent receiveCallPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1200, receiveCallAction, PendingIntent.FLAG_UPDATE_CURRENT);
            PendingIntent cancelCallPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1201, cancelCallAction, PendingIntent.FLAG_UPDATE_CURRENT);
                 PendingIntent callDialogPendingIntent = PendingIntent.getBroadcast(getApplicationContext(), 1202, callDialogAction, PendingIntent.FLAG_UPDATE_CURRENT);
            RemoteViews collapsedView= new RemoteViews(getPackageName(),R.layout.custom_call_view);
            collapsedView.setOnClickPendingIntent(R.id.imgaccept,receiveCallPendingIntent);
            collapsedView.setOnClickPendingIntent(R.id.imgreject,cancelCallPendingIntent);
            RemoteViews expended= new RemoteViews(getPackageName(),R.layout.custom_call_view);
              collapsedView.setTextViewText(R.id.textheading,name);
              collapsedView.setTextViewText(R.id.textdesc,"Incoming "+callType+" Call");
            expended.setOnClickPendingIntent(R.id.imgaccept,receiveCallPendingIntent);
            expended.setOnClickPendingIntent(R.id.imgreject,cancelCallPendingIntent);
            expended.setTextViewText(R.id.textheading,name);
            expended.setTextViewText(R.id.textdesc,"Incoming "+callType+" Call");
            createChannel();
            NotificationCompat.Builder notificationBuilder = null;
            if (data != null) {
                // Uri ringUri= Settings.System.DEFAULT_RINGTONE_URI;
                notificationBuilder = new NotificationCompat.Builder(this, CHANNEL_ID)
//                        .setContentTitle(name)
//                        .setContentText("Incoming "+callType+" Call")
                        .setCustomContentView(collapsedView)
                        .setCustomBigContentView(expended)
                        .setSmallIcon(R.drawable.ic_call_24dp)
                        .setPriority(NotificationCompat.PRIORITY_MAX)
                       // .setCategory(NotificationCompat.CATEGORY_CALL)
//                        .addAction(R.drawable.ic_denied, getString(R.string.reject_call), cancelCallPendingIntent)
//                        .addAction(R.drawable.icon_chat_onetoone, getString(R.string.answer_call), receiveCallPendingIntent)
                        .setAutoCancel(true)
                        //.setSound(ringUri)
                       .setFullScreenIntent(callDialogPendingIntent, true);

            }

            Notification incomingCallNotification = null;
            if (notificationBuilder != null) {
                incomingCallNotification = notificationBuilder.build();
            }
            startForeground(NOTIFICATION_ID, incomingCallNotification);


        } catch (Exception e) {
            e.printStackTrace();
        }

        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();// release your media player here audioManager.abandonAudioFocus(afChangeListener);
        releaseMediaPlayer();
        releaseVibration();
    }

    public void createChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            try {
                Uri ringUri= Settings.System.DEFAULT_RINGTONE_URI;
                NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
                channel.setDescription("Call Notifications");
                channel.setLockscreenVisibility(Notification.VISIBILITY_PUBLIC);
           /* channel.setSound(ringUri,
                    new AudioAttributes.Builder().setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                            .setLegacyStreamType(AudioManager.STREAM_RING)
                            .setUsage(AudioAttributes.USAGE_VOICE_COMMUNICATION).build());*/
                Objects.requireNonNull(getApplicationContext().getSystemService(NotificationManager.class)).createNotificationChannel(channel);

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }public void releaseVibration(){
        try {
            if(mvibrator!=null){
                if (mvibrator.hasVibrator()) {
                    mvibrator.cancel();
                }
                mvibrator=null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void releaseMediaPlayer() {
        try {
            if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                    mediaPlayer.reset();
                    mediaPlayer.release();
                }
                mediaPlayer = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public boolean onUnbind(Intent intent) {
        releaseMediaPlayer();
        return super.onUnbind(intent);
    }

    @Override
    public void onPrepared(MediaPlayer mediaPlayer) {
        Log.e("prepared","");
        mediaPlayer.start();

    }
}