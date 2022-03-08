package com.edc.ae.api

import com.edc.ae.model.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.mobatia.edcsurvey.survey.model.SurveyResponseModel
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import org.json.JSONObject
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.util.concurrent.TimeUnit

interface RetrofitClient {

    companion object {
        private val logging = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)
        private val client = OkHttpClient.Builder()
            .addInterceptor(logging)
            .readTimeout(1, TimeUnit.MINUTES)
            .build()


        fun getApiService(): RetrofitClient = Retrofit.Builder()
            .baseUrl("http://edcapp.mobatia.in:8081/api/v1/")
//            .baseUrl("http://192.168.1.5/edc-mobileapp/public/api/v1/")
            .addConverterFactory(GsonConverterFactory.create(GsonBuilder().setLenient().create()))
            .client(client)
            .build()
            .create(RetrofitClient::class.java)

        val get: RetrofitClient by lazy { getApiService() }


    }

    @GET("guest/contactus")
    suspend fun getContactUs(): ContactUsResponse

    @GET("guest/about")
    suspend fun getAboutUs(): AboutUsResponseModel

    @GET("guest/home")
    suspend fun getHomeData(): HomeResponseModel

    @GET("guest/services")
    suspend fun getServiceData(
        @Query("start") start: Int ,
        @Query("limit") limit: Int
    ): ServiceResponseModel

    @GET("guest/payment-instructions")
    suspend fun getPaymentInstruction(): PaymentInstructionResponseModel

    @GET("guest/news")
    suspend fun getNewsData(
        @Query("start") start: Int ,
        @Query("limit") limit: Int
    ): NewsResponseModel

    @GET("guest/notifications")
    suspend fun getNotifications(
        @Query("start") start: Int ,
        @Query("limit") limit: Int
    ): NotificationResponse

    //@FormUrlEncoded
    @POST("auth/login")
    suspend fun userLogin(@Body json:JsonObject): LoginResponseModel

    //@FormUrlEncoded
    @POST("auth/forgot-password")
    suspend fun forgotPassword(@Body json:JsonObject): ForgetPasswordResponseModel


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("srs/auth/change-passowrd")
    suspend fun changePassword(
        @Header("Authorization") authHeader: String?,
        @Body json: JsonObject
    ): ChangePasswordResponseModel


//   //@FormUrlEncoded
//    @POST("srs/auth/change-passowrd")
//    suspend fun changePassword(@Body json:JsonObject): ChangePasswordResponseModel

    @POST("auth/register")
    suspend fun userRegister(@Body json:JsonObject): RegisterResponseModel

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("create/complaint")
    suspend fun createComplaint(
        @Header("Authorization") authHeader: String?,
        @Body json: JsonObject
    ): ComplaintResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("create/feedback")
    suspend fun giveFeedBack(
        @Header("Authorization") authHeader: String?,
        @Body json: JsonObject
    ): FeedbackResponse


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("survey/submit")
    suspend fun surveySubmit(
        @Header("Authorization") authHeader: String?,
        @Body json: JsonObject
    ): SurveyResponseModel


    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("survey/get")
    suspend fun getSurvey(
        @Header("Authorization") authHeader: String?,
        @Query("student_id") student_id: String?
    ): SurveyResponseModel


//    @Headers("Content-Type: application/json;charset=UTF-8")
//    @GET("srs/get-student-profile")
//    suspend fun getStudentProfile(
//        @Header("Authorization") authHeader: String?
//    ): StudentResponseModel

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("srs/get-student-profile")
    suspend fun getStudentProfile(
        @Header("Authorization") authHeader: String?
    ): StudentProfileModelNew

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("auth/logout")
    suspend fun getLogout(
        @Header("Authorization") authHeader: String?
    ): ForgetPasswordResponseModel

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("student/update-notification-status")
    suspend fun getNotificationStatus(
        @Header("Authorization") authHeader: String?
    ): ForgetPasswordResponseModel

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("survey/submit")
    suspend fun submitSurvey(
        @Header("Authorization") authHeader: String?,
        @Body model: SurveySubmitModel
    ): FeedbackResponse

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("guest/device-register")
    suspend fun getDevRegResponse(
        @Header("Authorization") authHeader: String?,
        @Body json: JsonObject
    ): DevRegResponseModel

    @GET("srs/get-enroll-details")
    suspend fun getEnrollDetailsResponse(): EnrollDetailsModel

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("auth/refresh/token")
    suspend fun getRefreshToken(
        @Header("Authorization") authHeader: String?,
        @Body json:JsonObject): RefreshTokenModel

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("srs/validate-student")
    suspend fun getValidationResult(
        @Header("Authorization") authHeader: String?,
        @Body json: JsonObject
    ): ValidationResultModel

    @Headers("Content-Type: application/json;charset=UTF-8")
    @GET("srs/get-course-details")
    suspend fun getCourseDetails(
        @Header("Authorization") authHeader: String?,
    ): CourseDetailsModel

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("srs/get-course-details-cost")
    suspend fun getCourseCost(
        @Header("Authorization") authHeader: String?,
        @Body json: JsonObject
    ): CourseCostModel

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("srs/auth/register")
    suspend fun getRegisterResult(
        @Header("Authorization") authHeader: String?,
        @Body json: JsonObject
    ): RegisterResponseModel


    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("srs/payment/initiate")
    suspend fun  initiatePayment(
        @Header("Authorization") authHeader: String?,
        @Body json:JsonObject): PaymentInitiateModelNew

    @Headers("Content-Type: application/json;charset=UTF-8")
    @POST("srs/payment/submit")
    suspend fun  paymentSuccess(
        @Header("Authorization") authHeader: String?,
        @Body json:JsonObject): PaymentSuccessModel

//payment success


}