package com.doc24x7.Dashbord.ui;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.content.res.Resources;

import java.util.Locale;

public class LanguageManager {
    private Context ct;
    public SharedPreferences sharedPreferences;
    public LanguageManager(Context ctx)
    {
        ct=ctx;
     sharedPreferences=ct.getSharedPreferences("LANGUAGE",Context.MODE_PRIVATE);
    }
    public void updateResource(String code)
    {
        Locale locale=new Locale(code);
        Locale.setDefault(locale);
        Resources resources=ct.getResources();
        Configuration configuration=resources.getConfiguration();
        configuration.locale=locale;
        resources.updateConfiguration(configuration,resources.getDisplayMetrics());
        setlang(code);
    }
    public String getLang()
    {

        return sharedPreferences.getString("lang","en");
    }


    public  void setlang(String code)
    {
        SharedPreferences.Editor editor=sharedPreferences.edit();
        editor.putString("lang",code);
        editor.commit();
    }
}
