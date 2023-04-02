package com.doc24x7.Utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.NonNull;

import com.doc24x7.BuildConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;


public class ForceUpdateChecker {

    private static final String TAG = ForceUpdateChecker.class.getSimpleName();

    public static final String KEY_UPDATE_REQUIRED = "force_update_required_patient";
    public static final String KEY_CURRENT_VERSION = "force_update_current_version_patientApp";
    public static final String KEY_UPDATE_URL = "force_update_store_url_patient";
    public static final String KEY_URLLINKS = "force_update_store_url_patient";

    private OnUpdateNeededListener onUpdateNeededListener;
    private Context context;

    public interface OnUpdateNeededListener {
        void onUpdateNeeded(String updateUrl);
    }

    public static Builder with(@NonNull Context context) {
        return new Builder(context);
    }

    public ForceUpdateChecker(@NonNull Context context,
                              OnUpdateNeededListener onUpdateNeededListener) {
        this.context = context;
        this.onUpdateNeededListener = onUpdateNeededListener;
    }

    public void check() {
        final FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        if (remoteConfig.getBoolean(KEY_UPDATE_REQUIRED)) {
            String currentVersion = remoteConfig.getString(KEY_CURRENT_VERSION);
            String appVersion = getAppVersion(context);
            String updateUrl = remoteConfig.getString(KEY_UPDATE_URL);
            String updateUrllink = remoteConfig.getString(KEY_URLLINKS);

            Log.e("updateUrl",""+ updateUrllink+" updateUrl "+ updateUrl );


/* SharedPreferences sp=context.getSharedPreferences("Login",Context.MODE_PRIVATE);
SharedPreferences.Editor editor=sp.edit();
editor.putString("updateUrllink",updateUrllink).apply();*/

// new Gson().toJson(response.body()).toString()).apply();

            SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.putString(ApplicationConstant.INSTANCE.updateUrllink, updateUrllink).apply();
            editor.commit();
            if (!TextUtils.equals(currentVersion, appVersion)
                    && onUpdateNeededListener != null) {
                onUpdateNeededListener.onUpdateNeeded(updateUrl);
            }else{
                onUpdateNeededListener.onUpdateNeeded("");
            }
        }else{
            onUpdateNeededListener.onUpdateNeeded("");
        }
    }

    private String getAppVersion(Context context) {
        String result = "";

        try {
            result = BuildConfig.VERSION_NAME;
            result = result.replaceAll("[a-zA-Z]|-", "");
        } catch (Exception e) {
            Log.e(TAG, e.getMessage());
        }

        return result;
    }

    public static class Builder {

        private Context context;
        private OnUpdateNeededListener onUpdateNeededListener;

        public Builder(Context context) {
            this.context = context;
        }

        public Builder onUpdateNeeded(OnUpdateNeededListener onUpdateNeededListener) {
            this.onUpdateNeededListener = onUpdateNeededListener;
            return this;
        }

        public ForceUpdateChecker build() {
            return new ForceUpdateChecker(context, onUpdateNeededListener);
        }

        public ForceUpdateChecker check() {
            ForceUpdateChecker forceUpdateChecker = build();
            forceUpdateChecker.check();


            return forceUpdateChecker;
        }
    }
}