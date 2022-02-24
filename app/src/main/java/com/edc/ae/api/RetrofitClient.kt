package com.edc.ae.api

import com.edc.ae.model.*
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import com.mobatia.edcsurvey.survey.model.SurveyResponseModel
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
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
    suspend fun getServiceData(): ServiceResponseModel

    @GET("guest/payment-instructions")
    suspend fun getPaymentInstruction(): PaymentInstructionResponseModel

    @GET("guest/news")
    suspend fun getNewsData(): NewsResponseModel

    @GET("guest/notifications")
    suspend fun getNotifications(
        @Query("start") start: Int = 0,
        @Query("limit") limit: Int = 100
    ): NotificationResponse

    //@FormUrlEncoded
    @POST("auth/login")
    suspend fun userLogin(@Body json:JsonObject): LoginResponseModel
   //@FormUrlEncoded
    @POST("srs/auth/change-passowrd")
    suspend fun changePassword(@Body json:JsonObject): ChangePasswordResponseModel

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

    @POST("auth/refresh/token")
    suspend fun getRefreshToken(@Body json:JsonObject): RefreshTokenModel

//    @POST("srs/validate-student")
//    suspend fun getValidationResult(): ValidationResultModel

//    @POST("srs/payment/initiate")
//    suspend fun  initiatePayment(@Body json:JsonObject): PaymentInitiateModel




}