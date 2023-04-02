package com.doc24x7;

import android.Manifest;
import android.content.ClipData;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.DocumentsContract;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.doc24x7.Dashbord.dto.Datum;
import com.doc24x7.Login.dto.Data;
import com.doc24x7.Utils.ApplicationConstant;
import com.doc24x7.Utils.Loader;
import com.doc24x7.Utils.UtilMethods;
import com.google.gson.Gson;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PatientDetail extends AppCompatActivity implements View.OnClickListener {
    public TextView name, specialities, address, qualification, tv_datetime;
    LinearLayout linergendergroup,lifirst,liconsult,liappointment,lidate;
    ImageView opImage,imgreport;
    RadioGroup radiogroup;
    RadioButton self, other,male,female;
    AlertDialog alertDialog;
    String imageEncoded;
    String bookstatus="";
    ArrayList<String> imagesEncodedList;
    private PhotoAdapter photoAdapter;
    RecyclerView revPhoto;
    ImageView opencamera,imagereport, opengallery,view_image;
    TextView submit;
    String currentDay,strurl2, strurl7;
    Bitmap bitmap;
    String filePath;
    int AppointId=0;
    ArrayList<String> reporturlold;
   String gender="";
   String oldreporturl="";
    String profile_image = "1";
    ArrayList<Uri> mArrayUri = new ArrayList<Uri>();
    ArrayList<Uri> userimage=new ArrayList<>();
  public String urlrr="";
    public static ArrayList<String> reporturl=new ArrayList<>();

   EditText PatientName,PatientMobile,Patientage,ClientMobile,Clientemail,Patientsymptom,Patientweight,Patientheight,Patientbp,Patientsugar;

TextView PatientMobile_tv;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_patient_detail);
        bookstatus=getIntent().getStringExtra("bookstatus");
      //  Toast.makeText(this, ""+bookstatus, Toast.LENGTH_SHORT).show();
          reporturl.clear();
        Datum patientDetail = new Gson().fromJson(getIntent().getStringExtra("Doctordetail"), Datum.class);
        String DateTile = getIntent().getStringExtra("DateTile");
        currentDay = getIntent().getStringExtra("currentDay");
        AppointId = Integer.parseInt(getIntent().getStringExtra("appointment_id")==null?"0":getIntent().getStringExtra("appointment_id"));
       // Toast.makeText(this, ""+AppointId, Toast.LENGTH_SHORT).show();
        name = findViewById(R.id.name);
        imagereport=findViewById(R.id.imagereport);
        lifirst=findViewById(R.id.lifirst);
        liconsult=findViewById(R.id.liconsult);
        liappointment=findViewById(R.id.liAppointment);
        lidate=findViewById(R.id.lidate);
        tv_datetime = findViewById(R.id.tv_datetime);
        qualification = findViewById(R.id.qualification);
        address = findViewById(R.id.address);
        revPhoto = findViewById(R.id.rev_photo);
        photoAdapter = new PhotoAdapter(this);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this, 3);
        revPhoto.setLayoutManager(gridLayoutManager);
        revPhoto.setAdapter(photoAdapter);
        linergendergroup=findViewById(R.id.linergendergroup);
        PatientName = findViewById(R.id.PatientName);
        Patientsymptom = findViewById(R.id.Patientsymptom);
        PatientMobile = findViewById(R.id.PatientMobile);
        PatientMobile_tv = findViewById(R.id.PatientMobile_tv);
        Patientage = findViewById(R.id.Patientage);
        ClientMobile = findViewById(R.id.ClientMobile);
        Clientemail = findViewById(R.id.Clientemail);
        submit = findViewById(R.id.submit);
        imagereport.setOnClickListener(this);
        name.setText("Dr. " + patientDetail.getName());
        qualification.setText("" + patientDetail.getTypeName());
        opImage = findViewById(R.id.opImage);
        self = findViewById(R.id.self);
      //  imgreport=findViewById(R.id.imgreport);
        other = findViewById(R.id.other);
        male = findViewById(R.id.male);
        female=findViewById(R.id.female);
        radiogroup=findViewById(R.id.radiogroup);
        self.setChecked(true);
        opImage = findViewById(R.id.opImage);
        address.setText("" + patientDetail.getExperience() + " "+this.getResources().getString(R.string.year_experiance));
        tv_datetime.setText("" + currentDay);
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.placeholder(R.drawable.customer_support);
        requestOptions.error(R.drawable.doctordemo);
        SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
        String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
        String Mobile = myPreferences.getString(ApplicationConstant.INSTANCE.number, "");
        Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
        PatientName.setText(balanceCheckResponse.getName());
        PatientMobile.setText(Mobile);
        PatientMobile.setVisibility(View.GONE);
        PatientMobile_tv.setVisibility(View.GONE);
        Patientheight=findViewById(R.id.Patientheight);
        Patientweight=findViewById(R.id.Patientweight);
        Patientbp=findViewById(R.id.PatientBP);
        Patientsugar=findViewById(R.id.PatientSuger);
        if(bookstatus.equals("5"))
        {
            lifirst.setVisibility(View.GONE);
            lidate.setVisibility(View.GONE);
            liappointment.setVisibility(View.GONE);
            liconsult.setVisibility(View.GONE);
            Patientage.setText(getIntent().getStringExtra("age"));
            PatientName.setText(getIntent().getStringExtra("patientname"));
            ClientMobile.setText(getIntent().getStringExtra("mobile"));
            Patientsymptom.setText(getIntent().getStringExtra("symptoms"));
            Patientheight.setText(getIntent().getStringExtra("height"));
            Patientweight.setText(getIntent().getStringExtra("weight"));
            Patientbp.setText(getIntent().getStringExtra("bp"));
            Patientsugar.setText(getIntent().getStringExtra("sugar"));
            gender=getIntent().getStringExtra("gender");
            oldreporturl=getIntent().getStringExtra("ReportUrl");
//            Gson gson=new Gson();
//            gson.fromJson(oldreporturl,null);
            String strurl=  oldreporturl.replace("[","");
            strurl2= strurl.replace("]","");


            // strurl7=strurl2.replace('\"','"');
             //  reporturl.add(oldreporturl);
            Log.d("knjkhjjjjhgj: ",oldreporturl);
          //   Toast.makeText(PatientDetail.this, oldreporturl, Toast.LENGTH_SHORT).show();
           // PatientName.setVisibility(View.GONE);
        }

        //linergendergroup.setVisibility(View.GONE);
        ClientMobile.setText(Mobile);
        ClientMobile.setEnabled(false);
        Clientemail.setText(balanceCheckResponse.getEmail());

        Glide.with(this)
                .load(patientDetail.getProfilePic())
                .apply(requestOptions)
                .into(opImage);
        self.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
                    String Mobile = myPreferences.getString(ApplicationConstant.INSTANCE.number, "");
                    Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
                    PatientName.setText(balanceCheckResponse.getName());
                    PatientMobile.setText(balanceCheckResponse.getMobile());
                    PatientMobile.setVisibility(View.GONE);
                    PatientMobile_tv.setVisibility(View.GONE);
                    //linergendergroup.setVisibility(View.GONE);
                    ClientMobile.setText(Mobile);
                    Clientemail.setText(balanceCheckResponse.getEmail());
                }

            }
        });
        other.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    SharedPreferences myPreferences = getSharedPreferences(ApplicationConstant.INSTANCE.prefNamePref, MODE_PRIVATE);
                    String balanceResponse = myPreferences.getString(ApplicationConstant.INSTANCE.Loginrespose, null);
                    Data balanceCheckResponse = new Gson().fromJson(balanceResponse, Data.class);
                    String Mobile = myPreferences.getString(ApplicationConstant.INSTANCE.number, "");
                    PatientName.setText("");
                    PatientMobile.setText("");
                    PatientMobile.setVisibility(View.VISIBLE);
                    PatientMobile_tv.setVisibility(View.VISIBLE);
                   // linergendergroup.setVisibility(View.VISIBLE);
                    ClientMobile.setText(Mobile);
                    Clientemail.setText(balanceCheckResponse.getEmail());
                }
            }
        });
          radiogroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
              @Override
              public void onCheckedChanged(RadioGroup group, int checkedId) {
                     if (checkedId==R.id.male)
                     {
                        gender="male";
                     }
                    else if(checkedId==R.id.female)
                     {
                         gender="female";
                     }
                     else
                     {
                         gender="";
                     }
                 // Toast.makeText(PatientDetail.this, ""+gender, Toast.LENGTH_SHORT).show();
              }
          });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(PatientName.getText().toString().isEmpty()){
                    PatientName.setError("Please insert patient name");
                    return;
                }if(Patientage.getText().toString().isEmpty()){
                    Patientage.setError("Please insert patient age");
                    return;
                }if(PatientMobile.getText().toString().isEmpty()){
                    PatientMobile.setError("Please insert patient Mobile No");
                    return;
                }
                if (ClientMobile.getText().toString().isEmpty()) {
                    ClientMobile.setError("Please insert patient Mobile No");
                    return;
                }


                if (UtilMethods.INSTANCE.isNetworkAvialable(PatientDetail.this)) {
//                     String hgh= "\",\"";
                    if(bookstatus.equals("5")) {
                        String strurl3 = strurl2.replace("\\", "");
                        String strurl5 = strurl3.replace("\"", "");
                        String[] array = strurl5.split(",");
                        for (String s : array) {
                            reporturl.add(s);
                            //  Log.d( "dfsdff: ", s);
                        }
                    }
                    Datum patAppointmentDetail = new Gson().fromJson(getIntent().getStringExtra("AppintmentDetail"), Datum.class);
                    PatientDetailModel patientDetailModel = new PatientDetailModel(PatientName.getText().toString(), PatientMobile.getText().toString(),
                            ClientMobile.getText().toString(), Clientemail.getText().toString(), Patientage.getText().toString(), Patientsymptom.getText().toString(), gender, reporturl,
                            "2", Patientweight.getText().toString(), Patientheight.getText().toString(), Patientbp.getText().toString(), Patientsugar.getText().toString()
                    );
                    Loader loader = new Loader(PatientDetail.this, android.R.style.Theme_Translucent_NoTitleBar);
                    loader.show();
                    loader.setCancelable(false);
                    loader.setCanceledOnTouchOutside(false);
                    // Toast.makeText(PatientDetail.this, ""+getIntent().getStringExtra("doctorid"), Toast.LENGTH_SHORT).show();
                    if (bookstatus.equals("1")) {
                        UtilMethods.INSTANCE.BookAppointment(PatientDetail.this,
                                "" + getIntent().getStringExtra("doctorid"),
                                "" + getIntent().getStringExtra("slot_id"), "" + getIntent().getStringExtra("parent_slot_id"),
                                "" + currentDay, loader, "" + patAppointmentDetail.getNo_of_patient(), PatientDetail.this, patientDetailModel,
                                Integer.parseInt(getIntent().getStringExtra("payment_id")), AppointId);
                    } else if (bookstatus.equals("2")) {
                        UtilMethods.INSTANCE.BookAppointmentoffline(PatientDetail.this,
                                "" + getIntent().getStringExtra("doctorid"),
                                "" + getIntent().getStringExtra("slot_id"), "" + getIntent().getStringExtra("parent_slot_id"),
                                "" + currentDay, loader, "" + patAppointmentDetail.getNo_of_patient(), PatientDetail.this, patientDetailModel,
                                Integer.parseInt(getIntent().getStringExtra("payment_id")), AppointId);
                    } else if (bookstatus.equals("5")) {
                        Log.e("patientDetailModel: ",patientDetailModel.toString() );
                        UtilMethods.INSTANCE.UpdateReport(PatientDetail.this, PatientDetail.this, patientDetailModel,""+AppointId,loader );

                    }
                }

                else {
                        UtilMethods.INSTANCE.NetworkError(PatientDetail.this, getResources().getString(R.string.network_error_title),
                                PatientDetail.this.getResources().getString(R.string.network_error_message));
                    }



            }
        });
    }

    @Override
    public void onClick(View v) {

        if(v==imagereport)
        {
            Dexter.withContext(PatientDetail.this)
                    .withPermissions(
                            android.Manifest.permission.CAMERA,
                            android.Manifest.permission.READ_EXTERNAL_STORAGE,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE

                    ).withListener(new MultiplePermissionsListener() {
                @Override public void onPermissionsChecked(MultiplePermissionsReport report) {

                    AlertDialog.Builder dailog = new AlertDialog.Builder(PatientDetail.this);
                    LayoutInflater inflator = PatientDetail.this.getLayoutInflater();
                    View dailogview = inflator.inflate(R.layout.chooselangugelayout,null);
                    dailog.setView(dailogview);
                    alertDialog = dailog.create();
                    alertDialog.show();

                    opencamera = alertDialog.findViewById(R.id.camera);
                    opengallery = alertDialog.findViewById(R.id.gallery);
                    opencamera.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                            startActivityForResult(intent, 1);

                        }
                    });
                    opengallery.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            Intent intent = new Intent();
                            intent.setType("image/*");
                            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                            intent.setAction(Intent.ACTION_GET_CONTENT);
                            startActivityForResult(Intent.createChooser(intent, "Select Picture"), 2);
                        }
                    });


                }
                @Override public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                    token.continuePermissionRequest();
                }
            }).check();

        }

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            Loader loader = new Loader(PatientDetail.this, android.R.style.Theme_Translucent_NoTitleBar);
            alertDialog.dismiss();
            if (requestCode == 1){
                bitmap = (Bitmap) data.getExtras().get("data");
                Uri tempUri = getImageUri(PatientDetail.this, bitmap);

                mArrayUri.add(tempUri);
                userimage.add(tempUri);
                photoAdapter.setData(userimage);
                File finalFile = new File(getRealPathFromURI(tempUri));
                ArrayList<Uri> userimagess=new ArrayList<>();
                filePath = getPathFromUri(this,Uri.fromFile(finalFile));
                userimagess.add(Uri.parse(filePath));
                loader.show();
                loader.setCancelable(false);
                loader.setCanceledOnTouchOutside(false);
                UtilMethods.INSTANCE.UploadFile(getApplicationContext(),userimagess,loader,null,2);

            }
            else if (requestCode == 2) {

                String[] filePathColumn = {MediaStore.Images.Media.DATA};
                imagesEncodedList = new ArrayList<String>();

                if(data.getData()!=null){
                    try {
                        Uri selectedImage = data.getData();

                        userimage.add(selectedImage);
                        mArrayUri.add(selectedImage);

                        photoAdapter.setData(userimage);
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        String picturePath = c.getString(columnIndex);
                        c.close();
                        profile_image = picturePath;
                        Bitmap thumbnail = BitmapFactory.decodeFile(picturePath);
                        Log.e("xc", picturePath + "");
                        loader.show();
                        loader.setCancelable(false);
                        loader.setCanceledOnTouchOutside(false);
                        UtilMethods.INSTANCE.UploadFile(getApplicationContext(),mArrayUri,loader,null,2);
                    } catch (Exception e) {
                        e.printStackTrace();
                        BitmapFactory.Options options;
                        options = new BitmapFactory.Options();
                        options.inSampleSize = 2;
                        Uri selectedImage = data.getData();
                        String[] filePath = {MediaStore.Images.Media.DATA};
                        Cursor c = getContentResolver().query(selectedImage, filePath, null, null, null);
                        c.moveToFirst();
                        int columnIndex = c.getColumnIndex(filePath[0]);
                        String picturePath = c.getString(columnIndex);
                        c.close();
                        Bitmap bitmap = BitmapFactory.decodeFile(picturePath, options);
                        Log.e("xc", picturePath + "");
                       // imagereport.setImageBitmap(bitmap);

                       // imagereport.setBackground(null);
                    }
                }
               else if (data.getClipData() != null) {
                    ClipData mClipData = data.getClipData();

                    for (int i = 0; i < mClipData.getItemCount(); i++) {
                        ClipData.Item item = mClipData.getItemAt(i);
                        Uri uri = item.getUri();

                        mArrayUri.add(uri);
                        userimage.add(uri);
                        photoAdapter.setData(userimage);

                        Cursor cursor = getContentResolver().query(uri, filePathColumn, null, null, null);
                        // Move to first row
                        cursor.moveToFirst();

                        int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                        imageEncoded = cursor.getString(columnIndex);
                        imagesEncodedList.add(imageEncoded);
                        cursor.close();
                        if (i == mClipData.getItemCount() - 1) {
                            loader.show();
                            loader.setCancelable(false);
                            loader.setCanceledOnTouchOutside(false);
                            UtilMethods.INSTANCE.UploadFile(getApplicationContext(),mArrayUri,loader,null,2);                        }

                    }

                    Log.v("LOG_TAG", "Selected Images" + mArrayUri.size());
                }
            }
        }
    }


    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 75, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }

    public String getRealPathFromURI(Uri uri) {
        String path = "";
        if (getContentResolver() != null) {
            Cursor cursor = getContentResolver().query(uri, null, null, null, null);
            if (cursor != null) {
                cursor.moveToFirst();
                int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
                path = cursor.getString(idx);
                cursor.close();
            }
        }
        return path;
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

        return null;
    }

    public static String getDataColumn(Context context, Uri uri, String selection,
                                       String[] selectionArgs) {

        Cursor cursor = null;
        final String column = "_data";
        final String[] projection = {
                column
        };

        try {
            cursor = context.getContentResolver().query(uri, projection, selection, selectionArgs,
                    null);
            if (cursor != null && cursor.moveToFirst()) {
                final int index = cursor.getColumnIndexOrThrow(column);
                return cursor.getString(index);
            }
        } finally {
            if (cursor != null)
                cursor.close();
        }
        return null;
    }


    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is ExternalStorageProvider.
     */
    public static boolean isExternalStorageDocument(Uri uri) {
        return "com.android.externalstorage.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is DownloadsProvider.
     */
    public static boolean isDownloadsDocument(Uri uri) {
        return "com.android.providers.downloads.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is MediaProvider.
     */
    public static boolean isMediaDocument(Uri uri) {
        return "com.android.providers.media.documents".equals(uri.getAuthority());
    }

    /**
     * @param uri The Uri to check.
     * @return Whether the Uri authority is Google Photos.
     */
    public static boolean isGooglePhotosUri(Uri uri) {
        return "com.google.android.apps.photos.content".equals(uri.getAuthority());
    }

}
