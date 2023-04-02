package com.doc24x7.Utils;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentUris;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Build;
import android.os.CountDownTimer;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.content.res.AppCompatResources;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.doc24x7.DOctoeByIdActivity;
import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Dashbord.dto.GalleryListResponse;
import com.doc24x7.Dashbord.dto.ResponseReciept;
import com.doc24x7.Dashbord.ui.AllRecieptActivity;
import com.doc24x7.Dashbord.ui.GlobalData;
import com.doc24x7.Dashbord.ui.LanguageManager;
import com.doc24x7.Dashbord.ui.MedicineListAdapter;
import com.doc24x7.Dashbord.ui.PreciptionActivity;
import com.doc24x7.Dashbord.ui.ShowAllOnlineDoctors;
import com.doc24x7.Dashbord.ui.ShowNotification;
import com.doc24x7.Login.dto.Data;
import com.doc24x7.Login.ui.OtpActivity;
import com.doc24x7.PatientDetail;
import com.doc24x7.PatientDetailModel;
import com.doc24x7.PaymentGetway.ui.PaymentActivity;
import com.doc24x7.RTM.activity.MessageActivity;
import com.doc24x7.RTM.rtmtutorial.ChatManager;
import com.doc24x7.RTM.utils.MessageUtil;
import com.doc24x7.Register.ui.RegisterActivity;
import com.doc24x7.Splash.ui.SelectAccountActivity;
import com.doc24x7.Video.AGApplication;
import com.doc24x7.Video.CallActivity;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.Gson;
import com.doc24x7.Login.dto.secureLoginResponse;
import com.doc24x7.Login.ui.LoginActivity;
import com.doc24x7.R;
import com.doc24x7.Splash.ui.Splash;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import cn.pedant.SweetAlert.SweetAlertDialog;
import eightbitlab.com.blurview.BlurView;
import eightbitlab.com.blurview.RenderScriptBlur;
import io.agora.rtm.ErrorInfo;
import io.agora.rtm.ResultCallback;
import io.agora.rtm.RtmClient;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;
import static android.content.Context.MODE_PRIVATE;
import static com.google.android.gms.internal.zzahg.runOnUiThread;
import static com.doc24x7.PatientDetail.getDataColumn;
import static com.doc24x7.PatientDetail.isDownloadsDocument;
import static com.doc24x7.PatientDetail.isExternalStorageDocument;
import static com.doc24x7.PatientDetail.isGooglePhotosUri;
import static com.doc24x7.PatientDetail.isMediaDocument;


public enum UtilMethods {

    INSTANCE;
    public void requestStoragePermission(Activity context,int ASK_MULTIPLE_PERMISSION_REQUEST_CODE)
    {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M)
        {
            if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED) {

            } else {
                ActivityCompat.requestPermissions(context, new String[]{Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.READ_EXTERNAL_STORAGE,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, ASK_MULTIPLE_PERMISSION_REQUEST_CODE);
            }
        }
/*if (ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(context, Manifest.permission.ACCESS_COARSE_LOCATION) == PackageManager.PERMISSION_GRANTED)
            {Manifest.permission.ACCESS_FINE_LOCATION,

            }*/


       /* if (ActivityCompat.shouldShowRequestPermissionRationale(context, Manifest.permission.READ_EXTERNAL_STORAGE))
        {
            new AlertDialog.Builder(context)
                    .setTitle("Permission needed")
                    .setMessage("This permission is needed to download files")
                    .setPositiveButton("ok", (dialog, which) ->
                            ActivityCompat.requestPermissions(context,
                            new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE)
                    )
                    .setNegativeButton("cancel", (dialog, which) -> dialog.dismiss())
                    .create().show();
        } else
        {
            ActivityCompat.requestPermissions(context,
                    new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, STORAGE_PERMISSION_CODE);
        }*/
    }

      public void notification(Context context)
      {
          SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
          String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.one, null);
          String pref = balanceResponse;
          if(pref==null||pref.equalsIgnoreCase(""))
          {
              Intent intent = new Intent(context, SelectAccountActivity.class);
              context.startActivity(intent);
          }
          else {
              Intent i = new Intent(context, ShowNotification.class);
              context.startActivity(i);
          }
      }
      public  void chooseLanguage(Activity context, Intent intent)
      {
        androidx.appcompat.app.AlertDialog alertDialog;
          LanguageManager languageManager=new LanguageManager(context);

          androidx.appcompat.app.AlertDialog.Builder dailog = new androidx.appcompat.app.AlertDialog.Builder(context);
          LayoutInflater inflator =context.getLayoutInflater();
          View dailogview = inflator.inflate(R.layout.custom_img_layout, null);
          dailog.setView(dailogview);
          alertDialog = dailog.create();
          alertDialog.show();
          LinearLayout hindi=dailogview.findViewById(R.id.li_hindi);
          LinearLayout englis=dailogview.findViewById(R.id.li_english);
          hindi.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  languageManager.updateResource("hi");
                  context.finish();
                  context.startActivity(intent);
                  alertDialog.dismiss();

              }
          });
          englis.setOnClickListener(new View.OnClickListener()
          {
              @Override
              public void onClick(View v) {
                  languageManager.updateResource("en");
                  context.finish();
                  context.startActivity(intent);
                  alertDialog.dismiss();}
          });

      }

      public void allReciept(Context context ,Loader loader)
      {

          String header = ApplicationConstant.INSTANCE.Headertoken;
          SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
          String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
          Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
          String userId = balanceCheckResponse.getPatient_id();

          Log.e("GetPatientPreciptdfgion", "   name   " + userId );

          try {

              EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
              Call<ResponseReciept> call = git.GetPatientPreciptionlist(header, userId, "");
              call.enqueue(new Callback<ResponseReciept>() {
                  @Override
                  public void onResponse(Call<ResponseReciept> call, retrofit2.Response<ResponseReciept> response)
                  { Log.d("GetPatientPreciptionres", "is : " + new Gson().toJson(response.body().getData()).toString());
                      if (response != null)
                      {
                          if (loader != null)
                          {
                              if (loader.isShowing())
                                  loader.dismiss();
                          }
                              Intent intent = new Intent(context, AllRecieptActivity.class);
                              intent.putExtra("data", new Gson().toJson(response.body()).toString());
                              context.startActivity(intent);

                          }
                      }

                  @Override
                  public void onFailure(Call<ResponseReciept> call, Throwable t)
                  {

                          if (loader != null)
                          {
                              if (loader.isShowing())
                                  loader.dismiss();
                          }

                      Toast.makeText(context, "Data Not Available ", Toast.LENGTH_SHORT).show();
                  }
              });


          } catch (Exception e) {
              e.printStackTrace();
          }





      }




    public void GetRTMAccessToken(final Context context, String userid,String docid ,Loader loader, Intent intent) {

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);

        String doctor_name = balanceCheckResponse.getName();
        String header = ApplicationConstant.INSTANCE.Headertoken;
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<secureLoginResponse> call = git.GetRTMAccessToken(header,Integer.parseInt(docid),Integer.parseInt(userid), 6, 2);

            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("GetAccessToken", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        //  loader.isShowing();
                        try {
                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                ChatManager mChatManager = AGApplication.the().getChatManager();
                                RtmClient mRtmClient = mChatManager.getRtmClient();
                                // mRtmClient.logout(null);

                                MessageUtil.cleanMessageListBeanList();
                                mChatManager.enableOfflineMessage(true);
                                Log.e("adasdadasd", response.body().getData().getRtm_token());
                                mRtmClient.logout(null);
                                mRtmClient.login(response.body().getData().getRtm_token(), docid, new ResultCallback<Void>() {
                                    @Override
                                    public void onSuccess(Void responseInfo) {
                                        Log.i(TAG, "login success");
                                        runOnUiThread(() -> {
                                            intent.putExtra(MessageUtil.INTENT_EXTRA_IS_PEER_MODE, true);
                                            intent.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, userid);
                                            intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, docid);
                                            intent.putExtra("doctorname", doctor_name);
                                            //   context.startActivity(intent);
                                            //loader.dismiss();
                                        });
                                    }

                                    @Override
                                    public void onFailure(ErrorInfo errorInfo) {
                                        Log.i(TAG, "login failed: " + errorInfo.getErrorDescription());
                                    }
                                });


                            } else {
                                Log.e("notimessage: ",response.body().getMessage());
                               // UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }

                        } catch (Exception ex) {
                            Log.e("useruploadexception", ex.getMessage());
                            if (loader != null) {
                                if (loader.isShowing())
                                    try {
                                        loader.dismiss();
                                    } catch (Exception e) {


                                    }
                            }
                           // UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    Log.e("useruploadonfali", "userupload    " + t.getMessage());
                  //  UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GetDrProfileData(final Activity context, String doctorID, Datum operator, String currentDate, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<DoctorProfileResponse> call = git.GetDrProfileData(header, doctorID);
            call.enqueue(new Callback<DoctorProfileResponse>() {

                @Override
                public void onResponse(Call<DoctorProfileResponse> call, final retrofit2.Response<DoctorProfileResponse> response) {

                    Log.e("GetDrProfileData", "is : " + new Gson().toJson(response.body()).toString());
                    //Toast.makeText(context, ""+new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                if (!response.body().getData().getName().isEmpty()) {
                                  new  AlertDialog.Builder(context).setTitle("Alert")
                                          .setMessage("Doctor you requested is not available."+response.body().getData().getName()+" is available.Do you want to consult.")
                                          .setPositiveButton("Consult Now", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int which) {
                                                  UtilMethods.INSTANCE.chkpayment(context, response.body().getData().getId(), operator, loader, null,
                                                          "" + response.body().getData().getId(), "" + 0, "" + 0,
                                                          "" + response.body().getData().getDoctor_fees(), operator, "" + " " + "" + currentDate, "" + currentDate, null,"1","1");

                                              }
                                          })
                                          .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                              @Override
                                              public void onClick(DialogInterface dialog, int which) {
                                                  dialog.dismiss();
                                              }
                                          })
                                          .show();
                                }
                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<DoctorProfileResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void FetchDrByName(final Context context, String val, String latitude, String longitude, Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        //  GlobalData.latitude, GlobalData.longitude


        Log.e("Createghazal", "usertoken   :  " + "token" + "     :   vall     " + GlobalData.longitude + "  latitude   " + GlobalData.latitude);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.FetchDrByName(header, latitude, 200, 0, longitude);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Createghazalres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }


                        try {
                            FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("alldoctorsondash",
                                    "" + new Gson().toJson(response.body()).toString());
                            GlobalBus.getBus().post(fragmentActivityMessage);

//                            FragmentActivityMessage activityActivityMessage = new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "createghazal");
//                            GlobalBus.getBus().post(activityActivityMessage);
//

                        }
                        catch (Exception ex) {

                            //  UtilMethods.INSTANCE.Failed(context, " Invalid behar mein keep writing as per count below ", 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {


                    if (loader != null) {
                        if (loader.isShowing())
                            loader.dismiss();
                    }


                    //  Toast.makeText(context, "Invalid Behar mein keep writing as per count below", Toast.LENGTH_SHORT).show();


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void rescheduleAppointment(final Context context, int doctor_Id, int AppointId) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        //  GlobalData.latitude, GlobalData.longitude


        Log.e("Createghazal", "usertoken   :  " + "token" + "     :   vall     " + GlobalData.longitude + "  latitude   " + GlobalData.latitude);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.rescheduleAppointment(header, doctor_Id, AppointId);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {
                    Log.e("Createghazalres", "is : " + new Gson().toJson(response.body()).toString());
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void rescheduleAppointmentoffline(final Context context, int doctor_Id, int AppointId) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        //  GlobalData.latitude, GlobalData.longitude


        Log.e("Createghazal", "usertoken   :  " + "token" + "     :   vall     " + GlobalData.longitude + "  latitude   " + GlobalData.latitude);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.rescheduleAppointmentoffline(header, doctor_Id, AppointId);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {
                    Log.e("Createghazalres", "is : " + new Gson().toJson(response.body()).toString());
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void locationreposeval(Context context, String locationreposeval) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.locationreposeval, locationreposeval);
        editor.commit();

    }


    public void Successful(final Context context, final String message, final int i, final Activity activity) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.poprespose, null);


        Button okButton = (Button) view.findViewById(R.id.okButton);
        TextView msg = (TextView) view.findViewById(R.id.msg);

        final Dialog dialog = new Dialog(context);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        msg.setText("" + message);
        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (i == 1) {

                    context.startActivity(new Intent(context, LoginActivity.class));
                    dialog.dismiss();
                    activity.finish();
                } else if (i == 2) {
                    dialog.dismiss();
                    activity.finish();

                } else if (i == 3) {
                       Intent i=new Intent(context, DOctoeByIdActivity.class);
                       i.putExtra("type","4");
                       i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK|Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        context.startActivity(i);
                         //activity.finish();
                         dialog.dismiss();
                        activity.finish();

                }
                else if(i==4)
                {
                    dialog.dismiss();
                    activity.finish();
                }
                else {
                    dialog.dismiss();
                }


            }
        });


        dialog.show();


    }

    public void Failed(final Context context, final String message, final int i) {

        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.poprespose, null);

        Button okButton = (Button) view.findViewById(R.id.okButton);
        TextView msg = (TextView) view.findViewById(R.id.msg);
        TextView resposestatus = (TextView) view.findViewById(R.id.resposestatus);

        final Dialog dialog = new Dialog(context);

        dialog.setCancelable(false);
        dialog.setContentView(view);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.show();
        msg.setText("" + message);
        resposestatus.setText(context.getResources().getString(R.string.failed));

        resposestatus.setTextColor(context.getResources().getColor(R.color.red));

        okButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                dialog.dismiss();


            }
        });







        /*

        SweetAlertDialog pDialog = new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE);
        pDialog.setTitleText(context.getResources().getString(R.string.attention_error_title));
        pDialog.setContentText(message);
        pDialog.setCustomImage(AppCompatResources.getDrawable(context, R.drawable.ic_cancel_black_24dp));
        pDialog.setCancelable(false);
        pDialog.setConfirmClickListener(new SweetAlertDialog.OnSweetClickListener() {
            @Override
            public void onClick(SweetAlertDialog sweetAlertDialog) {
                if (i == 1) {

                    sweetAlertDialog.dismiss();

                   *//* context.startActivity(new Intent(context, LoginActivity.class));
                    sweetAlertDialog.dismiss();*//*
                } else if (i == 10) {

                    context.startActivity(new Intent(context, ActivityUserProfile.class));
                    sweetAlertDialog.dismiss();
                } else {
                    sweetAlertDialog.dismiss();
                }
            }
        });
        pDialog.show();*/


    }

    public void NetworkError(final Context context, String title, final String message) {
        new SweetAlertDialog(context, SweetAlertDialog.CUSTOM_IMAGE_TYPE)
                .setTitleText(title)
                .setContentText(message)
                .setCustomImage(AppCompatResources.getDrawable(context, R.drawable.ic_connection_lost_24dp))
                .show();
    }

    public boolean isNetworkAvialable(Context context) {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        return netInfo != null && netInfo.isConnectedOrConnecting();
    }


    TextView textotp, resendotp, resend;
    public EditText edMobileFwp;
    public TextInputLayout tilMobileFwp;

    public Button FwdokButton;
    public Button cancelButton;
    private static CountDownTimer countDownTimer;


    private void startTimer(int noOfMinutes) {
        countDownTimer = new CountDownTimer(noOfMinutes, 1000) {
            public void onTick(long millisUntilFinished) {
                long millis = millisUntilFinished;
                //Convert milliseconds into hour,minute and seconds
                String hms = String.format("%02d:%02d:%02d", TimeUnit.MILLISECONDS.toHours(millis), TimeUnit.MILLISECONDS.toMinutes(millis) - TimeUnit.HOURS.toMinutes(TimeUnit.MILLISECONDS.toHours(millis)), TimeUnit.MILLISECONDS.toSeconds(millis) - TimeUnit.MINUTES.toSeconds(TimeUnit.MILLISECONDS.toMinutes(millis)));
                resendotp.setText(hms);//set text
            }

            public void onFinish() {

                resendotp.setVisibility(View.GONE);
                resend.setVisibility(View.VISIBLE);

                countDownTimer = null;//set CountDownTimer to null
                //  resendotp.setText(getString(R.string.start_timer));//Change button text
            }
        }.start();

    }

    private void stopCountdown() {
        if (countDownTimer != null) {
            countDownTimer.cancel();
            countDownTimer = null;
        }
    }

    public void setLoginrespose(Context context, String Loginrespose, String one) {

        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.Loginrespose, Loginrespose);
        editor.putString(ApplicationConstant.INSTANCE.one, one);
        editor.commit();

    }

    public void setnumber(Context context, String number) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.number, number);
        editor.commit();
    }

    public void SetType(Context context, String SetType) {
        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.SetType, SetType);
        editor.commit();
    }

    public void SaveToken(final Context context, String token) {
        Log.e("token", token);
        String header = ApplicationConstant.INSTANCE.Headertoken;
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.SaveToken(header, member_id, token, "1");
            call.enqueue(new Callback<secureLoginResponse>() {

                //8574857485

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("SaveToken", "is : " + new Gson().toJson(response.body()).toString());


                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StartRecording(final Context context, String room, int uid) {


        String header = ApplicationConstant.INSTANCE.Headertoken;


        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<secureLoginResponse> call = git.startRecording(header, uid, room);

            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("GetAccessToken", "is : " + new Gson().toJson(response.body()).toString());
                    // Toast.makeText(context, ""+new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();

                    if (response != null) {
                        CallActivity.resourceID = response.body().getData().getResourceId();
                        CallActivity.sid = response.body().getData().getSid();
                        try {
                        } catch (Exception ex) {
                            Log.e("useruploadexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    Log.e("useruploadonfali", "userupload    " + t.getMessage());
                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void StopRecording(final Context context, String room, int uid, String resourceId, String sid) {


        String header = ApplicationConstant.INSTANCE.Headertoken;


        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

            Call<secureLoginResponse> call = git.stopRecording(header, uid, room, resourceId, sid);

            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("StopRecording", "is : " + new Gson().toJson(response.body()).toString());
                    // Toast.makeText(context, ""+new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();

                    if (response != null) {
                        CallActivity.resourceID = response.body().getData().getResourceId();
                        CallActivity.sid = response.body().getData().getSid();
                        try {
                        } catch (Exception ex) {
                            Log.e("useruploadexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    Log.e("useruploadonfali", "userupload    " + t.getMessage());
                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void secureLogin(final Context context, final String edMobile, final Loader loader, final Activity loginActivity,int flag) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("login", " , email : " + edMobile);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.secureLogin(header, edMobile);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {
                          if(flag==1) {
                              if (response.body().getData().getOtp() != null) {
                                  Intent intent = new Intent(context, OtpActivity.class);
                                  intent.putExtra("number", "" + edMobile);
                                  intent.putExtra("otp", "" + response.body().getData().getOtp());
                                  context.startActivity(intent);
                                  loginActivity.finish();
                              } else if (response.body().getData().getStatus().equalsIgnoreCase("0")) {
                                  UtilMethods.INSTANCE.Failed(context, "" + response.body().getData().getMessage(), 0);
                              }
                          }
                        else if(flag==2)
                          {
                              if (response.body().getData().getOtp() != null) {
                                  Toast.makeText(context, "OTP Resend Succesful", Toast.LENGTH_SHORT).show();
                              } else if (response.body().getData().getStatus().equalsIgnoreCase("0")) {
                                  UtilMethods.INSTANCE.Failed(context, "" + response.body().getData().getMessage(), 0);
                              }
                          }
                        else {

                          }
                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());
                            UtilMethods.INSTANCE.Failed(context, "Something went wrong..."+ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    //  UtilMethods.INSTANCE.Failed(context,"please check login id and password",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DoctorLogin(final Context context, final String edMobile, final Loader loader, final LoginActivity loginActivity) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("login", " , email : " + edMobile);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.DoctorLogin(header, edMobile);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                if (!response.body().getData().getStatus().equalsIgnoreCase("0")) {

                                    Intent intent = new Intent(context, OtpActivity.class);
                                    intent.putExtra("number", "" + edMobile);
                                    intent.putExtra("otp", "" + response.body().getData().getOtp());
                                    context.startActivity(intent);
                                    loginActivity.finish();


                                } else {

                                    UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);
                                }


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            // UtilMethods.INSTANCE.Failed(context,"Sorry Wrong Password or Email...",0);

                            Intent intent = new Intent(context, OtpActivity.class);
                            intent.putExtra("number", "" + edMobile);
                            intent.putExtra("otp", "" + response.body().getData().getOtp());
                            context.startActivity(intent);
                            loginActivity.finish();

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    //  UtilMethods.INSTANCE.Failed(context,"please check login id and password",0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void OtpVerification(final Activity context, final String otp_user, final String number, final Loader loader,
                                final Activity loginActivity) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("login", " , email : " + otp_user + "  " + number);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.verify(header, number, otp_user);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {


                                if (response.body().getData().getName().equalsIgnoreCase(""))
                                {



                                    Intent intent = new Intent(context, RegisterActivity.class);
                                    intent.putExtra("number", "" + number);
                                    intent.putExtra("patient_id", "" + response.body().getData().getPatient_id());
                                    context.startActivity(intent);

                                    loginActivity.finish();


                                } else {

                                    UtilMethods.INSTANCE.setLoginrespose(context, "" + new Gson().toJson(response.body().getData()).toString(), "1");
                                    UtilMethods.INSTANCE.setnumber(context, number);
                                    SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                    UtilMethods.INSTANCE.SaveToken(context, prefs.getString(ApplicationConstant.INSTANCE.token, ""));
                                    context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                    loginActivity.finish();

                                }


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, "Sorry Wrong Password or Email...", 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "please check login id and password", 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updatePatient(final Activity context, final String name, final String email, String patient_id, final String number,
                              final Loader loader, final Activity loginActivity) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("updatePatient", " , email : " + name + "  " + number + "  email  " + email + "  patient_id     " + patient_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.updatePatient(header, number, name, email, patient_id);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("updatePatientres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                Toast.makeText(context, "Success", Toast.LENGTH_SHORT).show();

                                UtilMethods.INSTANCE.setLoginrespose(context, "" + new Gson().toJson(response.body().getData()).toString(), "1");
                                UtilMethods.INSTANCE.setnumber(context, number);
                                SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                UtilMethods.INSTANCE.SaveToken(context, prefs.getString(ApplicationConstant.INSTANCE.token, ""));

                                /*Intent intent = new Intent(context, DashboardMainNew.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
                                context.startActivity(intent);*/
                                context.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                                loginActivity.finish();

                                //  UtilMethods.INSTANCE.secureLogin(context, number, loader, loginActivity);


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, "Sorry Wrong Password or Email...", 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "please check login id and password", 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void DoctorOtpVerify(final Activity context, final String otp_user, final String number, final Loader loader, final OtpActivity loginActivity) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("login", " , email : " + otp_user + "  " + number);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.DoctorOtpVerify(header, number, otp_user);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("loginres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {


                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, "Sorry Wrong Password or Email...", 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "please check login id and password", 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void setconsult(final Context context, String consulttext, String mobile, final Loader loader, final Activity activity) {
        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();


        Log.e("getbanner", "   name   " + member_id + "   consulttext  " + consulttext);
        //Toast.makeText(context, ""+member_id, Toast.LENGTH_SHORT).show();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.setconsult(header, consulttext, mobile, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());

                    //   Toast.makeText(context, ""+new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getStatus().equalsIgnoreCase("1")) {

                                UtilMethods.INSTANCE.Successful(context, "" + response.body().getMessage(), 2, activity);
                                //Toast.makeText(context, ""+response.message(), Toast.LENGTH_SHORT).show();

                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());
                            //  Toast.makeText(context, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
                            // UtilMethods.INSTANCE.Successful(context, "We will be back to you soon", 0, activity);
                            //    UtilMethods.INSTANCE.Successful(context, "We will contact you back soon", 2, activity);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

 public void request_to_doctor(final Activity context, String doctor_id, String request_id, String send_type, String symtom, String fees, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();


        //Toast.makeText(context, ""+member_id, Toast.LENGTH_SHORT).show();
        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.request_to_doctor(header, doctor_id, request_id, send_type, symtom,fees, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());

                    //   Toast.makeText(context, ""+new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();
                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getStatus().equalsIgnoreCase("1")) {

                                UtilMethods.INSTANCE.Successful(context, "" + response.body().getMessage(), 2, context);
                                //Toast.makeText(context, ""+response.message(), Toast.LENGTH_SHORT).show();

                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());
                            //  Toast.makeText(context, ""+ex.getMessage(), Toast.LENGTH_SHORT).show();
                            // UtilMethods.INSTANCE.Successful(context, "We will be back to you soon", 0, activity);
                            //    UtilMethods.INSTANCE.Successful(context, "We will contact you back soon", 2, activity);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                                UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);
                            } catch (Exception e) {


                            }
                    }



                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void AllSymtoms(final Context context, final Loader loader) {
        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();


        Log.e("getbanner", "   name   " + member_id + "   consulttext  " + "consulttext");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.AllSymtoms(header);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(ApplicationConstant.INSTANCE.allsympton, new Gson().toJson(response.body()).toString());
                                    editor.commit();
                                }
                            } else {

                                UtilMethods.INSTANCE.Failed(context, "" + response.body().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void getbanner(final Context context, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("getbanner", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.getbanner(header);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("getbannerres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("GalleryList",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);

                            } else {

                                // UtilMethods.INSTANCE.Failed(context,""+response.body().getMessage(),0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Doctors(final Context context, String id, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctorsaaaaaaaaaa", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.Doctors(header, id, 0, 200);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {

                        }
                        try {

                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("alldoctorsondash",
                                            "" + new Gson().toJson(response.body()).toString());
                                    GlobalBus.getBus().post(fragmentActivityMessage);
//                                    Intent intent = new Intent(context, DOctoeByIdActivity.class);
//                                    intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
//                                    intent.putExtra("type", "2");
//                                    context.startActivity(intent);
                                } else {
                                    FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("alldoctorsondash",
                                            "" + new Gson().toJson(response.body()).toString());
                                    GlobalBus.getBus().post(fragmentActivityMessage);
                                    Toast.makeText(context, ""+context.getResources().getString(R.string.no_doctors_found), Toast.LENGTH_SHORT).show();
                                    //UtilMethods.INSTANCE.Failed(context, context.getResources().getString(R.string.no_doctors_found), 0);
                                }

                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetAvailability(final Context context, String id, String date, String day, final Loader loader,TextView tv) {
        loader.show();
        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("GetAvailability", " \n  name   " + id+ "\ndate"+date+" \n  day"+day+"\nheader"+header );

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAvailability(header, id, date, day);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("GetAvailabilityres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("Appointment",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                            } else {
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("Available",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                              //  Toast.makeText(context, "Doctor is not Available today", Toast.LENGTH_SHORT).show();
                                tv.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GetAvailabilityOffline(final Context context, String id, String date, String day, final Loader loader,TextView tv) {
        loader.show();
        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("GetAvailabilityoffline", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAvailabilityOffline(header, id, date, day);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("GetAvailabilityresggfg", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("AppointmentOffline",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                            } else {
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("AvailableOffline",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                               // Toast.makeText(context, "Doctor is not Available today", Toast.LENGTH_SHORT).show();
                                tv.setVisibility(View.VISIBLE);
                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    public void chkpayment(final Activity context, final String id, final Datum operator,
                           final Loader loader, final String Symtom_id, String doctor_id, String slot_id, String parent_slot_id,
                           String amount, Datum patAppointmentDetail, String tv_datetime, String DateTile, Activity activity,String bookstatus,String paymentStatus) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();



        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            // Call<secureLoginResponse> call = git.chkpayment(header, id, member_id);
            Call<secureLoginResponse> call = git.chkpayment(header, member_id, doctor_id, slot_id, DateTile);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                  //  Toast.makeText(context, ""+id, Toast.LENGTH_SHORT).show();
                    Log.e("Paymentsusses", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {
                            if (response.body().getData() != null) {
                                if (response.body().getData().getStatus().equalsIgnoreCase("0")) {

                                    Intent intent = new Intent(context, PaymentActivity.class);
                                    intent.putExtra("amount", "" + amount);
                                    intent.putExtra("namepack", "1");
                                    intent.putExtra("doctorid", id);
                                    intent.putExtra("paymentStatus",paymentStatus);
                                    intent.putExtra("Symtom_id", Symtom_id);
                                    intent.putExtra("slot_id", slot_id);
                                    intent.putExtra("parent_slot_id", parent_slot_id);
                                    intent.putExtra("payment_id", response.body().getData().getPayment_id() + "");
                                    intent.putExtra("tv_datetime", tv_datetime);
                                    intent.putExtra("DateTile", DateTile);
                                    intent.putExtra("bookstatus",bookstatus);
                                    intent.putExtra("doctorDetail", new Gson().toJson(operator));
                                    intent.putExtra("AppintmentDetail", new Gson().toJson(patAppointmentDetail));
                                    context.startActivity(intent);
                                } else {
                                    Intent i = new Intent(context, PatientDetail.class);
                                    i.putExtra("currentDay", DateTile + "");
                                    i.putExtra("dateTime", "" + tv_datetime);
                                    i.putExtra("slot_id", slot_id);

                                    i.putExtra("bookstatus",bookstatus);
                                    i.putExtra("doctorid", id);
                                    i.putExtra("parent_slot_id", parent_slot_id);
                                    i.putExtra("Doctordetail", new Gson().toJson(operator));
                                    i.putExtra("payment_id", response.body().getData().getPayment_id() + "");
                                    i.putExtra("AppintmentDetail", new Gson().toJson(patAppointmentDetail));
                                    context.startActivity(i);





                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void GetPatientPreciption(final Context context, final String appointment_id,
                                     RecyclerView recycler_view_Preciption, final Loader loader, Datum operator, String book_date) {
        String header = ApplicationConstant.INSTANCE.Headertoken;
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String userId = balanceCheckResponse.getPatient_id();

        Log.e("GetPatientPreciption", "   name   " + userId + "  appointment_id   :  " + appointment_id);

        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.GetPatientPreciption(header, userId, appointment_id);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("GetPatientPreciptionres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                Intent intent = new Intent(context, PreciptionActivity.class);
                                intent.putExtra("response", "" + new Gson().toJson(response.body().getData()).toString());
                                intent.putExtra("doctorDetail", new Gson().toJson(operator));
                                intent.putExtra("book_date", book_date);
                                context.startActivity(intent);


                                //  intent.putExtra("doctorDetail", new Gson().toJson(operator));
                                //     UtilMethods.INSTANCE.GetMedicineById(context,response.body().getData().getMedicine_id(),null,recycler_view_Preciption);


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void GetPatientPreciptionhide(final Context context, final String appointment_id, TextView View_Preciption,int status) {

        String header = ApplicationConstant.INSTANCE.Headertoken;
        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String userId = balanceCheckResponse.getPatient_id();

        Log.e("GetPatientPreciption", "   name   " + userId + "  appointment_id   :  " + appointment_id);

        try {

            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.GetPatientPreciption(header, userId, appointment_id);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("GetPatientPreciptionres", "is : " + new Gson().toJson(response.body()).toString() + "  mmmm   :  " + response.body().getData());


                    if (response != null) {

                        try {

                            if (response.body().getData() != null) {
                                if(status==1) {
                                    View_Preciption.setVisibility(View.VISIBLE);
                                }
                                else {
                                    View_Preciption.setVisibility(View.GONE);

                                }

                            } else {

                                // View_Preciption.setVisibility(View.GONE);

                                View_Preciption.setClickable(false);
                                View_Preciption.setText("  No Prescription");
                                View_Preciption.setTextColor(context.getResources().getColor(R.color.red));

                            }


                        } catch (Exception ex) {

                            //  View_Preciption.setVisibility(View.GONE);
                            View_Preciption.setClickable(false);
                            View_Preciption.setText("  No Prescription");
                            View_Preciption.setTextColor(context.getResources().getColor(R.color.red));


                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    //   View_Preciption.setVisibility(View.GONE);
                    View_Preciption.setClickable(false);
                    View_Preciption.setText("  No Prescription");
                    View_Preciption.setTextColor(context.getResources().getColor(R.color.red));


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void Paymentsusses(final Context context, final String DOctorid, String id, final Loader loader, String amount,
                              final Datum operator, final String Symtom_id, String slot_id, String parent_slot_id, Datum patAppointmentDetail,
                              String tv_datetime, Activity activity, String DateTile, Datum patAppointmentDetailSlo,String bookstatus,String Paymentstatus) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        Log.e("Paymentsusses", "   name   " + member_id + "  slot_id  " + slot_id + "  doctor_id  " + DOctorid + "  amount   " + amount);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            //  Call<secureLoginResponse> call = git.Paymentsusses(header, "" + DOctorid, "" + amount, "" + member_id, "xyxyxyrgcv ", "1");
            Call<secureLoginResponse> call = git.Paymentsusses(header, "" + member_id, "" + DOctorid, "" + slot_id,
                    Paymentstatus, "Booking Payment", amount);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("Paymentsussesres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {

                                if (response.body().getData().getStatus().equalsIgnoreCase("1")) {

                                    activity.finish();
                                    Toast.makeText(context, "" + response.body().getData().getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent i = new Intent(context, PatientDetail.class);
                                    i.putExtra("currentDay", DateTile + "");
                                    i.putExtra("dateTime", "" + tv_datetime);
                                    i.putExtra("Doctordetail", new Gson().toJson(operator));
                                    i.putExtra("slot_id", slot_id);
                                    i.putExtra("doctorid",DOctorid);
                                    i.putExtra("bookstatus",bookstatus);
                                    i.putExtra("payment_id", response.body().getData().getPayment_id() + "");
                                    i.putExtra("parent_slot_id", parent_slot_id);
                                    i.putExtra("AppintmentDetail", new Gson().toJson(patAppointmentDetailSlo));
                                    context.startActivity(i);


                                } else {
                                    UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);

                                }


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void AllSymtimsDoctors(final Context context, String id, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   " + id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.AllSymtimsDoctors(header, id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null) {
                                Intent intent = new Intent(context, DOctoeByIdActivity.class);
                                intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
                                intent.putExtra("type", "3");
                                context.startActivity(intent);
                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    GalleryListResponse sliderImage;
    ArrayList<Datum> sliderLists = new ArrayList<>();
    MedicineListAdapter medicinelistadapter;
    LinearLayoutManager mLayoutManager;


    public void GetAllPatientAppointment(final Context context, final Loader loader) {

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAllPatientAppointment(header, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("online_tabs_appointment",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                            } else {

                                UtilMethods.INSTANCE.Failed(context, context.getResources().getString(R.string.no_data_found), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GetAllPatientAppointmentoffline(final Context context, final Loader loader) {

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAllPatientAppointmentOffline(header, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {

                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("offline_tabs_appointment",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);

                            } else {

                                UtilMethods.INSTANCE.Failed(context, context.getResources().getString(R.string.no_data_found), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void GetAllPatientAppointment2(final Context context, final Loader loader) {

        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctors", "   name   " + member_id);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.GetAllPatientAppointment(header, member_id);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("Doctorsres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData() != null && response.body().getData().size() > 0) {
//                                Intent intent = new Intent(context, DOctoeByIdActivity.class);
//                                intent.putExtra("response", "" + new Gson().toJson(response.body()).toString());
//                                intent.putExtra("type", "4");
//                                context.startActivity(intent);
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("Doctorsres",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);
                            } else {

                                UtilMethods.INSTANCE.Failed(context, context.getResources().getString(R.string.no_data_found), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void SetAvailability(final Context context, String id, String start_time, String end_time, String date, final Loader loader) {


        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();

        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Doctorsvvv", "   name   " + member_id + "   vv   " + start_time + "    vv   " + end_time + "  dd    " + date);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.SetAvailability(header, member_id, start_time, end_time, date);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("Doctorsvvvres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {

                                UtilMethods.INSTANCE.Successful(context, response.body().getData().getMessage(), 0, null);

                            } else {

                                UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);

                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
      public void UpdateReport(Context context,Activity activity,PatientDetailModel patientDetail,String appointmentid,Loader loader)
      {
          String header = ApplicationConstant.INSTANCE.Headertoken;
            try
            {
                EndPointInterface  git=ApiClient.getClient().create(EndPointInterface.class);
                Call<secureLoginResponse> call=git.UpdateReport(header,appointmentid,new Gson().toJson(patientDetail));
                call.enqueue(new Callback<secureLoginResponse>() {
                    @Override
                    public void onResponse(Call<secureLoginResponse> call, retrofit2.Response<secureLoginResponse> response) {
                        if (response != null) {

                            if (loader != null) {
                                if (loader.isShowing())
                                    loader.dismiss();
                            }

                            try {

                                if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                   // UtilMethods.INSTANCE.rescheduleAppointment(context, Integer.parseInt(doctor_id), AppointId);
                                    UtilMethods.INSTANCE.Successful(context, response.body().getData().getMessage(), 4, activity);
                                } else {

                                    // activity.finish();
                                    UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);


                                }


                            } catch (Exception ex) {
                                Log.e("signupexception", ex.getMessage());

                                UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                        if (loader != null) {
                            if (loader.isShowing())
                                try {
                                    loader.dismiss();
                                } catch (Exception e) {


                                }
                        }

                        UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);
                    }
                });
            }
            catch (Exception e)
            {

            }
      }
    public void BookAppointment(final Context context, String doctor_id, String slot_id, String parent_slot_id, String date, final Loader loader,
                                String no_of_patient, Activity activity, PatientDetailModel patientDetail, int payment_id, int AppointId) {


        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();
     //   Toast.makeText(context, ""+slot_id+"\n"+parent_slot_id, Toast.LENGTH_SHORT).show();
        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Paymentsussesbooking", "   member_id   " + member_id + "   doctor_id   " + doctor_id + "    slot_id   " + slot_id + "  date    " +
                date);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.BookAppointment(header, doctor_id, slot_id, parent_slot_id, member_id, new Gson().toJson(patientDetail), no_of_patient, payment_id, date);
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("Paymentsussesbookingres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                UtilMethods.INSTANCE.rescheduleAppointment(context, Integer.parseInt(doctor_id), AppointId);
                                UtilMethods.INSTANCE.Successful(context, response.body().getData().getMessage(), 3, activity);
                            } else {

                                // activity.finish();
                                UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void BookAppointmentoffline(final Context context, String doctor_id, String slot_id, String parent_slot_id, String date, final Loader loader,
                                String no_of_patient, Activity activity, PatientDetailModel patientDetail, int payment_id, int AppointId) {


        SharedPreferences myPreferences = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        String member_id = balanceCheckResponse.getPatient_id();
        //   Toast.makeText(context, ""+slot_id+"\n"+parent_slot_id, Toast.LENGTH_SHORT).show();
        String header = ApplicationConstant.INSTANCE.Headertoken;

        Log.e("Paymentsussesbooking", "   member_id   " + member_id + "   doctor_id   " + doctor_id + "    slot_id   " + slot_id + "  date    " +
                date);

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<secureLoginResponse> call = git.BookAppointmentoffline(header, doctor_id, slot_id, parent_slot_id, member_id, new Gson().toJson(patientDetail), no_of_patient, payment_id, date);
            Log.e( "callddddd: ", String.valueOf(call));
            call.enqueue(new Callback<secureLoginResponse>() {

                @Override
                public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                    Log.e("Paymentsussesbookingres", "is : " + new Gson().toJson(response.body()).toString());

                    if (response != null) {

                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }

                        try {

                            if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                UtilMethods.INSTANCE.rescheduleAppointmentoffline(context, Integer.parseInt(doctor_id), AppointId);
                                UtilMethods.INSTANCE.Successful(context, response.body().getData().getMessage(), 3, activity);
                            } else {

                                // activity.finish();
                                UtilMethods.INSTANCE.Failed(context, response.body().getData().getMessage(), 0);


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<secureLoginResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void doctorlist(final Context context, final Loader loader) {

        String header = ApplicationConstant.INSTANCE.Headertoken;


        Log.e("specialities", "   name   ");

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.doctorlist(header);
            call.enqueue(new Callback<GalleryListResponse>() {

                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                    Log.e("specialitiesres", "is : " + new Gson().toJson(response.body()).toString());


                    if (response != null)
                    {
                        if (loader != null)
                        {

                        }
                        try {

                            if (response.body().getData() != null) {
                                if (response.body().getData().size() > 0) {
                                    SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
                                    SharedPreferences.Editor editor = prefs.edit();
                                    editor.putString(ApplicationConstant.INSTANCE.allCategoryDoctor, new Gson().toJson(response.body()).toString());
                                    editor.commit();
                                }
                                FragmentActivityMessage fragmentActivityMessage = new FragmentActivityMessage("specialities",
                                        "" + new Gson().toJson(response.body()).toString());
                                GlobalBus.getBus().post(fragmentActivityMessage);


                            } else {


                            }


                        } catch (Exception ex) {
                            Log.e("signupexception", ex.getMessage());

                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {


                            }
                    }

                    UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


                }
            });

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void logout(final Context context) {

        UtilMethods.INSTANCE.setLoginrespose(context, "", "");
//        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
//
//        SharedPreferences.Editor editor = prefs.edit();
//         editor.clear();
//         editor.commit();
        Intent startIntent = new Intent(context, Splash.class);
        startIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(startIntent);

    }

    public boolean isValidMobile(String mobile) {

        String mobilePattern = "^(?:0091|\\\\+91|0)[6-9][0-9]{9}$";
        String mobileSecPattern = "[6-9][0-9]{9}$";

        if (mobile.matches(mobilePattern) || mobile.matches(mobileSecPattern)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean isValidEmail(String email) {

        boolean isValid = false;

        String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
        CharSequence inputStr = email;

        Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(inputStr);
        if (matcher.matches()) {
            isValid = true;
        }
        return isValid;
    }


    public void setgetKeyId(Context context, String token) {


        SharedPreferences prefs = context.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, context.MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        editor.putString(ApplicationConstant.INSTANCE.token, token);
        editor.commit();


    }

    public void noticount(Context context, String id, Loader loader, TextView count) {
        loader.isShowing();
        StringRequest request = new StringRequest(Request.Method.GET, "http://patient.globalforex.in/api/Notification/CountActiveNotification?userId=" + id, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                loader.dismiss();

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    String countt = jsonObject.getString("Data");

                    count.setText(countt);
                    loader.dismiss();
                } catch (JSONException e) {
                    e.printStackTrace();
                    loader.dismiss();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loader.dismiss();
            }

        }) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> header = new HashMap<String, String>();
                header.put("x-api-key", "apikey@animationmedia.org");

                return header;
            }


        };
        RequestQueue requestQueue = Volley.newRequestQueue(context);
        requestQueue.add(request);
    }
//    public void getOnlineDoctor(Context context, String id, Loader loader) {
//        loader.isShowing();
//        StringRequest request = new StringRequest(Request.Method.GET, "http://doc24x7.co.in/api/DoctorOnline/getAllOnlineDoctors?type_id=1", new Response.Listener<String>() {
//            @Override
//            public void onResponse(String response) {
//                Toast.makeText(context, ""+response, Toast.LENGTH_SHORT).show();
//                try {
//                    JSONObject jsonObject = new JSONObject(response);
//                    String countt = jsonObject.getString("Data");
//
//                    loader.dismiss();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                    loader.dismiss();
//                }
//            }
//        }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//                loader.dismiss();
//            }
//
//        }) {
//            @Override
//            public Map<String, String> getHeaders() throws AuthFailureError {
//                Map<String, String> header = new HashMap<String, String>();
//                header.put("x-api-key", "apikey@animationmedia.org");
//
//                return header;
//            }
//
//
//        };
//        RequestQueue requestQueue = Volley.newRequestQueue(context);
//        requestQueue.add(request);
//    }
 public void getOnlineDoctor(Context context, String id, Loader loader) {
     String header = ApplicationConstant.INSTANCE.Headertoken;

     Log.e("Doctors", "   name   " + id);

     try {
         EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
         Call<GalleryListResponse> call = git.GetOnlinedoctors(header, id);
         call.enqueue(new Callback<GalleryListResponse>() {

             @Override
             public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {

                 Log.e("onlineDoctorsres", "is : " + new Gson().toJson(response.body()).toString());
                //Toast.makeText(context, ""+new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();

                 if (response != null) {
                     if (loader != null) {
                         if (loader.isShowing())
                             loader.dismiss();
                     }
                     try {

                         if (response.body().getData() != null) {
                             if (response.body().getData().size() > 0) {
                                 FragmentActivityMessage activityActivityMessage = new FragmentActivityMessage("" + new Gson().toJson(response.body()).toString(), "onlinedoctors");
                                 GlobalBus.getBus().post(activityActivityMessage);
                                 Intent i=new Intent(context, ShowAllOnlineDoctors.class);
                                 i.putExtra("response", new Gson().toJson(response.body()).toString());
                                 context.startActivity(i);
                             } else {
                                 UtilMethods.INSTANCE.Failed(context, context.getResources().getString(R.string.no_doctors_online), 0);
                             }

                         } else {

                             Toast.makeText(context, ""+context.getResources().getString(R.string.no_doctors_online), Toast.LENGTH_SHORT).show();

                         }


                     } catch (Exception ex) {
                         Log.e("signupexception", ex.getMessage());

                         UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);

                     }
                 }
             }

             @Override
             public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                 if (loader != null) {
                     if (loader.isShowing())
                         try {
                             loader.dismiss();
                         } catch (Exception e) {


                         }
                 }

                 UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);


             }
         });

     } catch (Exception e) {
         e.printStackTrace();
     }
    }
    public void UploadFile(final Context context, ArrayList<Uri> userimage,Loader loader,String path,int flag) {
        String header = ApplicationConstant.INSTANCE.Headertoken;

        ArrayList<MultipartBody.Part> fileToUpload1 = new ArrayList<>();
    if(flag==2)
    {
        for (int i = 0; i < userimage.size(); i++) {
            File file = new File(getPathFromUri(context, userimage.get(i)));
            RequestBody requestBody1 = RequestBody.create(MediaType.parse("*image/*"), file);
            fileToUpload1.add(MultipartBody.Part.createFormData("files[]", file.getName(), requestBody1));
        }
    }


//        ArrayList<MultipartBody.Part> fileToUpload1 = new ArrayList<>();
//        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*"), file);
//       // fileToUpload1 = MultipartBody.Part.createFormData("files[]", file.getName(), requestBody1);
//        fileToUpload1.add(MultipartBody.Part.createFormData("files[]", file.getName(), requestBody1));
//      //  Toast.makeText(context, "hello", Toast.LENGTH_SHORT).show();

        try {
            EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);
            Call<GalleryListResponse> call = git.UploadFile(header, fileToUpload1);
            call.enqueue(new Callback<GalleryListResponse>() {
                @Override
                public void onResponse(Call<GalleryListResponse> call, final retrofit2.Response<GalleryListResponse> response) {
                    Log.e("UploadRecording", "is : " + new Gson().toJson(response.body()));
                   // Toast.makeText(context, "" + new Gson().toJson(response.body()).toString(), Toast.LENGTH_SHORT).show();

                    if (response != null) {
                        if (loader != null) {
                            if (loader.isShowing())
                                loader.dismiss();
                        }
                        if (response != null)
                        {
                            //Toast.makeText(context, "Recording Uploaded", Toast.LENGTH_SHORT).show();
                            Log.e("UploadRecordingggg", "is : " + response.body().getFilePath());
                            //reporturl.clear();
                            PatientDetail.reporturl.addAll(response.body().getFilePath());
                            userimage.clear();
                        }



                        try {

                        } catch (Exception ex) {
                            Log.e("useruploadexception", ex.getMessage());
                            loader.dismiss();
                            UtilMethods.INSTANCE.Failed(context, " Exception  :  " + ex.getMessage(), 0);
                        }
                    }
                }

                @Override
                public void onFailure(Call<GalleryListResponse> call, Throwable t) {
                    if (loader != null) {
                        if (loader.isShowing())
                            try {
                                loader.dismiss();
                            } catch (Exception e) {

                            }
                    }
                    Log.e("useruploadonfali", "userupload    " + t.getMessage());
                    //  UtilMethods.INSTANCE.Failed(context, "Failure " + t.getMessage(), 0);
                }
            });

        } catch (Exception e) {
            e.printStackTrace();
            loader.dismiss();
        }
    }
    public static String getPathFromUri(final Context context, final Uri uri) {

        final boolean isKitKat = Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;

        // DocumentProvider
        if (isKitKat && DocumentsContract.isDocumentUri(context, uri)) {
            // ExternalStorageProvider
            if (isExternalStorageDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                if ("primary".equalsIgnoreCase(type)) {
                    return Environment.getExternalStorageDirectory() + "/" + split[1];
                }

                // TODO handle non-primary volumes
            }
            // DownloadsProvider
            else if (isDownloadsDocument(uri)) {

                final String id = DocumentsContract.getDocumentId(uri);
                final Uri contentUri = ContentUris.withAppendedId(
                        Uri.parse("content://downloads/public_downloads"), Long.valueOf(id));

                return getDataColumn(context, contentUri, null, null);
            }
            // MediaProvider
            else if (isMediaDocument(uri)) {
                final String docId = DocumentsContract.getDocumentId(uri);
                final String[] split = docId.split(":");
                final String type = split[0];

                Uri contentUri = null;
                if ("image".equals(type)) {
                    contentUri = MediaStore.Images.Media.EXTERNAL_CONTENT_URI;
                } else if ("video".equals(type)) {
                    contentUri = MediaStore.Video.Media.EXTERNAL_CONTENT_URI;
                } else if ("audio".equals(type)) {
                    contentUri = MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
                }

                final String selection = "_id=?";
                final String[] selectionArgs = new String[] {
                        split[1]
                };

                return getDataColumn(context, contentUri, selection, selectionArgs);
            }
        }
        // MediaStore (and general)
        else if ("content".equalsIgnoreCase(uri.getScheme())) {

            // Return the remote address
            if (isGooglePhotosUri(uri))
                return uri.getLastPathSegment();

            return getDataColumn(context, uri, null, null);
        }
        // File
        else if ("file".equalsIgnoreCase(uri.getScheme())) {
            return uri.getPath();
        }

        return uri.toString();
    }

    public void CreateGetRTMAccessToken(Activity mContext, String doctor_id, String name, Loader loader) {

            SharedPreferences myPreferences = mContext.getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
            String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
            Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
            String userid = balanceCheckResponse.getPatient_id();
            String doctor_name = balanceCheckResponse.getName();

        Log.d("lojnfdsjj ",userid+"::"+doctor_id);
            String header = ApplicationConstant.INSTANCE.Headertoken;


            try {
                EndPointInterface git = ApiClient.getClient().create(EndPointInterface.class);

                Call<secureLoginResponse> call = git.GetRTMAccessToken(header, Integer.parseInt(doctor_id), Integer.parseInt(userid), 5,2);

                call.enqueue(new Callback<secureLoginResponse>() {

                    @Override
                    public void onResponse(Call<secureLoginResponse> call, final retrofit2.Response<secureLoginResponse> response) {

                        Log.e("GetAccessToken", "is : " + new Gson().toJson(response.body()).toString());


                        if (response != null) {
                            loader.isShowing();

                            try {
                                if (response.body().getData().getStatus().equalsIgnoreCase("1")) {
                                    ChatManager mChatManager = AGApplication.the().getChatManager();
                                    RtmClient mRtmClient = mChatManager.getRtmClient();
                                    mRtmClient.logout(null);
                                    MessageUtil.cleanMessageListBeanList();
                                    mRtmClient.login(response.body().getData().getRtm_token(),doctor_id , new ResultCallback<Void>() {
                                        @Override
                                        public void onSuccess(Void responseInfo) {
                                            Log.i(TAG, "login success");
                                            runOnUiThread(() -> {
                                                Intent intent = new Intent(mContext, MessageActivity.class);
                                                intent.putExtra(MessageUtil.INTENT_EXTRA_IS_PEER_MODE, true);
                                                intent.putExtra(MessageUtil.INTENT_EXTRA_TARGET_NAME, userid);
                                                intent.putExtra(MessageUtil.INTENT_EXTRA_USER_ID, doctor_id);
                                                intent.putExtra("doctorname",name);
                                                mContext.startActivityForResult(intent, 1);
                                                loader.dismiss();
                                            });
                                        }

                                        @Override
                                        public void onFailure(ErrorInfo errorInfo) {
                                            Log.i(TAG, "login failed: " + errorInfo.getErrorDescription());
                                            loader.dismiss();
                                        }
                                    });



                                } else {

                                    UtilMethods.INSTANCE.Failed(mContext, "" + response.body().getMessage(), 0);
                                    loader.dismiss();
                                }

                            } catch (Exception ex) {
                                Log.e("useruploadexception", ex.getMessage());
                                if (loader != null) {
                                    if (loader.isShowing())
                                        try {
                                            loader.dismiss();
                                        } catch (Exception e) {


                                        }
                                }
                                UtilMethods.INSTANCE.Failed(mContext, " Exception  :  " + ex.getMessage(), 0);
                                loader.dismiss();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<secureLoginResponse> call, Throwable t) {

                        Log.e("useruploadonfali", "userupload    " + t.getMessage());
                        UtilMethods.INSTANCE.Failed(mContext, "Failure " + t.getMessage(), 0);
                    }
                });

            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    public void blur(Context context, BlurView blurView, View decorView)
    {
        float radius = 11f;
        ViewGroup rootView = (ViewGroup) decorView.findViewById(android.R.id.content);
        Drawable windowBackground = decorView.getBackground();
        blurView.setupWith(rootView)
                .setFrameClearDrawable(windowBackground)
                .setBlurAlgorithm(new RenderScriptBlur(context))
                .setBlurRadius(radius)
                .setBlurAutoUpdate(false)
                .setHasFixedTransformationMatrix(false);
    }
}