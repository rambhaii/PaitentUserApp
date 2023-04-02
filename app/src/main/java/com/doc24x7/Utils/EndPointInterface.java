package com.doc24x7.Utils;

import com.android.volley.Response;
import com.doc24x7.Dashbord.dto.GalleryListResponse;
import com.doc24x7.Dashbord.dto.ResponseReciept;
import com.doc24x7.Login.dto.secureLoginResponse;

import java.util.ArrayList;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface EndPointInterface {

    @FormUrlEncoded
    @POST("/api/Login")
    Call<secureLoginResponse> secureLogin(@Header("X-API-KEY") String authorization,
                                          @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("/api/SaveToken")
    Call<secureLoginResponse> SaveToken(@Header("X-API-KEY") String authorization,
                                        @Field("userId") String userId,
                                        @Field("firbase_token") String firbase_token,
                                        @Field("userType") String userType);

    @Multipart
    @POST("/api//Recording/StartRecording")
    Call<secureLoginResponse> startRecording(@Header("X-API-KEY") String authorization,
                                             @Part("uid") Integer uid,
                                             @Part("cname") String cname
    );


    @Multipart
    @POST("/api//Recording/StopRecording")
    Call<secureLoginResponse> stopRecording(@Header("X-API-KEY") String authorization,
                                            @Part("uid") Integer uid,
                                            @Part("cname") String cname,
                                            @Part("resourceId") String resourceId,
                                            @Part("sid") String sid
    );

    @Multipart
    @POST("/api/GetRTMAccessToken")
    Call<secureLoginResponse> GetRTMAccessToken(@Header("X-API-KEY") String authorization,
                                                @Part("doctor_id") Integer doctor_id,
                                                @Part("patient_id") Integer patient_id,
                                                @Part("noti_type") Integer noti_type,
                                                @Part("activityType") Integer activitytype
    );
    @GET("/api/Doctors/GetDrProfileData")
    Call<DoctorProfileResponse> GetDrProfileData(@Header("X-API-KEY") String authorization,
                                               @Query("doctor_id") String id);
    @FormUrlEncoded
    @POST("/api/DoctorLogin")
    Call<secureLoginResponse> DoctorLogin(@Header("X-API-KEY") String authorization,
                                          @Field("mobile") String mobile);

    @FormUrlEncoded
    @POST("/api/OtpVerify")
    Call<secureLoginResponse> verify(@Header("X-API-KEY") String authorization,
                                     @Field("mobile") String mobile,
                                     @Field("otp") String otp);

    @FormUrlEncoded
    @POST("/api/OtpVerify/updatePatient")
    Call<secureLoginResponse> updatePatient(@Header("X-API-KEY") String authorization,
                                            @Field("mobile") String mobile,
                                            @Field("name") String name,
                                            @Field("email") String email,
                                            @Field("patient_id") String patient_id);

    @FormUrlEncoded
    @POST("/api/DoctorLogin/DoctorOtpVerify")
    Call<secureLoginResponse> DoctorOtpVerify(@Header("X-API-KEY") String authorization,
                                              @Field("mobile") String mobile,
                                              @Field("otp") String otp);

    @GET("/api/AllTypes")
    Call<GalleryListResponse> doctorlist(@Header("X-API-KEY") String authorization);


    @GET("/api/Doctors")
    Call<GalleryListResponse> Doctors(@Header("X-API-KEY") String authorization,
                                      @Query("type_id") String id,
                                      @Query("start") Integer start,
                                      @Query("limit") Integer limit);

    //http://patient.globalforex.in/api/Availability/GetAvailabilityForPatient/?day=Tuesday&doctor_id=4&date=2021-03-11
    @GET("/api/Availability/GetAvailabilityForPatient")
    Call<GalleryListResponse> GetAvailability(@Header("X-API-KEY") String authorization,
                                              @Query("doctor_id") String id,
                                              @Query("date") String date,
                                              @Query("day") String day);
    @GET("/api/AvailabilityOffline/GetAvailabilityForPatient")
    Call<GalleryListResponse> GetAvailabilityOffline(@Header("X-API-KEY") String authorization,
                                              @Query("doctor_id") String id,
                                              @Query("date") String date,
                                              @Query("day") String day);


    @GET("/api/Patient/GetPatientPreciption")
    Call<secureLoginResponse> GetPatientPreciption(@Header("X-API-KEY") String authorization,
                                                   @Query("userId") String userId,
                                                   @Query("appointment_id") String appointment_id);
    @GET("/api/Patient/GetPatientPreciptionList")
    Call<ResponseReciept> GetPatientPreciptionlist(@Header("X-API-KEY") String authorization,
                                                   @Query("userId") String userId,
                                                   @Query("date") String date);

    @GET("/api/Preciption/GetMedicineById")
    Call<GalleryListResponse> GetMedicineById(@Header("X-API-KEY") String authorization,
                                              @Query("medicine_id") String medicine_id);

    @GET("/api/Payment/chkpayment")
    Call<secureLoginResponse> chkpayment(@Header("X-API-KEY") String authorization,
                                         @Query("user_id") String user_id,
                                         @Query("doctor_id") String doctor_id,
                                         @Query("slot_id") String slot_id,
                                         @Query("txt_date") String txt_date);


    @FormUrlEncoded
    @POST("/api/Payment")
    Call<secureLoginResponse> Paymentsusses(@Header("X-API-KEY") String authorization,
                                            @Field("user_id") String user_id,
                                            @Field("doctor_id") String doctor_id,
                                            @Field("slot_id") String slot_id,
                                            @Field("payment_status") String payment_status,
                                            @Field("remark") String remark,
                                            @Field("amount") String amount);

    @GET("/api/Symtoms/FetchDoctorBySymtom")
    Call<GalleryListResponse> AllSymtimsDoctors(@Header("X-API-KEY") String authorization,
                                                @Query("symtom_id") String id);


    @GET("/api/Appointment/GetAllPatientAppointment")
    Call<GalleryListResponse> GetAllPatientAppointment(@Header("X-API-KEY") String authorization,
                                                       @Query("userId") String id);
    @GET("/api/OfflineBookAppointment/GetAllPatientAppointment")
    Call<GalleryListResponse> GetAllPatientAppointmentOffline(@Header("X-API-KEY") String authorization,
                                                       @Query("userId") String id);

    @FormUrlEncoded
    @POST("/api/Appointment/BookAppointment")
    Call<secureLoginResponse> BookAppointment(@Header("X-API-KEY") String authorization,
                                              @Field("doctor_id") String doctor_id,
                                              @Field("slot_id") String slot_id,
                                              @Field("parent_slot_id") String parent_slot_id,
                                              @Field("userId") String userId,
                                              @Field("patient_details") String patient_details,
                                              @Field("no_of_patient") String no_of_patient,
                                              @Field("payment_id") Integer payment_id,
                                              @Field("book_date") String date);
    @FormUrlEncoded
    @POST("/api/Appointment/UploadPatientReport")
    Call<secureLoginResponse> UpdateReport(@Header("X-API-KEY") String authorization,
                                           @Field("appointment_id") String appointment_id,
                                           @Field("patient_details") String patient_details
    );


    @FormUrlEncoded
    @POST("/api/OfflineBookAppointment/BookAppointment")
    Call<secureLoginResponse> BookAppointmentoffline(@Header("X-API-KEY") String authorization,
                                              @Field("doctor_id") String doctor_id,
                                              @Field("slot_id") String slot_id,
                                              @Field("parent_slot_id") String parent_slot_id,
                                              @Field("userId") String userId,
                                              @Field("patient_details") String patient_details,
                                              @Field("no_of_patient") String no_of_patient,
                                              @Field("payment_id") Integer payment_id,
                                              @Field("book_date") String date);


    @FormUrlEncoded
    @POST("/api/Availability/SetAvailability")
    Call<secureLoginResponse> SetAvailability(@Header("X-API-KEY") String authorization,
                                              @Field("doctor_id") String id,
                                              @Field("start_time") String start_time,
                                              @Field("end_time") String end_time,
                                              @Field("date") String date);


    @GET("/api/Doctors/GetDrByLatLong")
    Call<GalleryListResponse> FetchDrByName(@Header("X-API-KEY") String authorization,
                                            @Query("latitude") String latitude,
                                            @Query("limit") Integer limit,
                                            @Query("start") Integer start,
                                            @Query("longitude") String longitude);

    @GET("/api/Appointment/rescheduleAppointment")
    Call<GalleryListResponse> rescheduleAppointment(@Header("X-API-KEY") String authorization,
                                                    @Query("doctor_id") Integer doctor_id,
                                                    @Query("appointment_id") Integer appointment_id);
    @GET("/api/OfflineBookAppointment/rescheduleAppointment")
    Call<GalleryListResponse> rescheduleAppointmentoffline(@Header("X-API-KEY") String authorization,
                                                    @Query("doctor_id") Integer doctor_id,
                                                    @Query("appointment_id") Integer appointment_id);

    @GET("/api/Banner")
    Call<GalleryListResponse> getbanner(@Header("X-API-KEY") String authorization);


    @GET("/api/Symtoms/AllSymtoms")
    Call<GalleryListResponse> AllSymtoms(@Header("X-API-KEY") String authorization);

    @FormUrlEncoded
    @POST("/api/Symtoms/Request")
    Call<GalleryListResponse> setconsult(@Header("X-API-KEY") String authorization,
                                         @Field("symtom") String symtom,
                                         @Field("mobile") String mobile,
                                         @Field("userId") String customer_id);

    @FormUrlEncoded
    @POST("/api/SendRequest/request_to_doctor")
    Call<GalleryListResponse> request_to_doctor(@Header("X-API-KEY") String authorization,
                                                @Field("doctor_id") String doctor_id,
                                                @Field("request_id") String request_id,
                                                @Field("send_type") String send_type,
                                                @Field("symtom") String symtom,
                                                @Field("fee") String fee,
                                                @Field("userId") String userId);

    @POST("/api/Symtoms/GetUserRequest")
    Call<GalleryListResponse> showsymtoms(@Header("X-API-KEY") String authorization,
                                          @Field("userid") String userid
    );

    @GET("/api/DoctorOnline/getAllOnlineDoctors")
    Call<GalleryListResponse> GetOnlinedoctors(@Header("X-API-KEY") String authorization,
                                               @Query("type_id") String type_id
    );
    @Multipart
    @POST("/api/UploadFile")
    Call<GalleryListResponse> UploadFile(@Header("X-API-KEY") String authorization,
                                         @Part ArrayList<MultipartBody.Part> file5
    );


}
