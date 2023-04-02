package com.doc24x7.Dashbord.ui;

import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import com.doc24x7.Login.dto.Data;
import com.doc24x7.R;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;
import com.google.gson.Gson;
import com.jaiselrahman.filepicker.activity.FilePickerActivity;
import com.jaiselrahman.filepicker.config.Configurations;
import com.jaiselrahman.filepicker.model.MediaFile;
import com.vincent.filepicker.Constant;
import com.vincent.filepicker.FileUtils;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;


public class ActivityUserProfile extends AppCompatActivity implements View.OnClickListener {

    CircleImageView customerimage;
    ImageView edit_plus;
    String userimage="";

    Button btn_ok;
    Loader loader;
    int i=0;

    AutoCompleteTextView name,mobile_no,email,age;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

         Getid();
        loader = new Loader(this, android.R.style.Theme_Translucent_NoTitleBar);


    }

    private void Getid() {

        name=findViewById(R.id.name);
        mobile_no=findViewById(R.id.mobile_no);
        email=findViewById(R.id.email);
        age=findViewById(R.id.age);
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok=findViewById(R.id.btn_ok);
        btn_ok=findViewById(R.id.btn_ok);


        btn_ok=findViewById(R.id.btn_ok);

        customerimage=findViewById(R.id.customerimage);

        edit_plus=findViewById(R.id.edit_plus);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Member Profile");
        toolbar.setNavigationIcon(R.drawable.ic_arrow_back);
        setSupportActionBar(toolbar);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });


        SetData();

        edit_plus.setOnClickListener(this);

        btn_ok.setOnClickListener(this);


    }


    private void SetData() {

        SharedPreferences myPreferences =  getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);

        String username = balanceCheckResponse.getName();
        String contectnumber = balanceCheckResponse.getMobileNo();



        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.customer_support);

        Glide.with(this)
                .load(ApplicationConstant.INSTANCE.baseUrl+"/assets/img/patients/"+balanceCheckResponse.getImage())
                .apply(requestOptions)
                .into(customerimage);

        mobile_no.setText(""+contectnumber);
        name.setText(""+username);
        email.setText(""+balanceCheckResponse.getEmail());
        age.setText(""+balanceCheckResponse.getAge());


    }





    @Override
    public void onClick(View v) {

if(v==btn_ok){




    HitApi();


}


if(v==edit_plus){

    i=1;


    showCameraGalleryDialog("1");

}



    }

    private void HitApi() {





    }

    public void showCameraGalleryDialog(String id) {

        final Dialog dialog = new Dialog(this);

        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);

        dialog.setCancelable(true);

        dialog.setContentView(R.layout.camera_gallery_popup);

        dialog.show();

        RelativeLayout rrCancel = (RelativeLayout) dialog.findViewById(R.id.rl_close);
        LinearLayout llCamera = (LinearLayout) dialog.findViewById(R.id.ll_camera);
        LinearLayout llGallery = (LinearLayout) dialog.findViewById(R.id.ll_gallery);

        rrCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        });

        llCamera.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                captureImage();
                dialog.dismiss();
            }
        });

        llGallery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent2 = new Intent(ActivityUserProfile.this, FilePickerActivity.class);

                intent2.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                        .setCheckPermission(true)
                        .setShowImages(true)
                        .setShowAudios(false)
                        .setShowFiles(false)
                        .setShowVideos(false)
                        .enableImageCapture(true)
                        .enableVideoCapture(false)
                        .setMaxSelection(1)
                        .setSingleChoiceMode(true)
                        .setSkipZeroSizeFiles(true)
                         .build());

                startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_IMAGE);

                dialog.dismiss();
            }
        });
    }


    private void captureImage() {

//        Intent intent1 = new Intent(ActivityUserProfile.this, ImagePickActivity.class);
//        intent1.putExtra(IS_NEED_CAMERA, true);
//        intent1.putExtra(Constant.MAX_NUMBER, 1);
//        intent1.putExtra(IS_NEED_FOLDER_LIST, true);
//        startActivityForResult(intent1, Constant.REQUEST_CODE_PICK_IMAGE);

        Intent intent2 = new Intent(ActivityUserProfile.this, FilePickerActivity.class);

        intent2.putExtra(FilePickerActivity.CONFIGS, new Configurations.Builder()
                .setCheckPermission(true)
                .setShowImages(true)
                .setShowAudios(false)
                .setShowFiles(false)
                .setShowVideos(false)
                .enableImageCapture(true)
                .enableVideoCapture(false)
                .setMaxSelection(1)
                .setSingleChoiceMode(true)
                .setSkipZeroSizeFiles(true)
                .build());

        startActivityForResult(intent2, Constant.REQUEST_CODE_PICK_IMAGE);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case Constant.REQUEST_CODE_PICK_IMAGE:
                if (resultCode == RESULT_OK) {
//                    ArrayList<ImageFile> list = data.getParcelableArrayListExtra(Constant.RESULT_PICK_IMAGE);
                    ArrayList<MediaFile> mediaFiles = data.getParcelableArrayListExtra(FilePickerActivity.MEDIA_FILES);

                    String path = "";

                    for (int j = 0; j < mediaFiles.size(); j++) {

                        path = FileUtils.getPath(ActivityUserProfile.this, mediaFiles.get(j).getUri());

                        Log.d("filePath", "File Path: " + path +"  name "+ mediaFiles.get(j).getName() +"     +    i     "+ i);

                        userimage=""+ path;

                    }


                    }

                 break;

        }
    }

}