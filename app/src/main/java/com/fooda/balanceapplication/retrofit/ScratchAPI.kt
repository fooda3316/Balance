package com.fooda.balanceapplication.retrofit

import com.fooda.balanceapplication.models.balance.Balance
import com.fooda.balanceapplication.models.buy.BuyBalanceResponse
import com.fooda.balanceapplication.models.buy.BuyScratchResponse
import com.fooda.balanceapplication.models.charge.ChargeHistory
import com.fooda.balanceapplication.models.charge.ChargeResponse
import com.fooda.balanceapplication.models.phone.ProfileResponse
import com.fooda.balanceapplication.models.phone.UpdateNumbersResponse
import com.fooda.balanceapplication.models.phone.UpdateImageResponse
import com.fooda.balanceapplication.models.registeration.FindClintResponse
import com.fooda.balanceapplication.models.registeration.RegisterResponse
import com.fooda.balanceapplication.models.sell_history.SellHistoryResponse
import com.fooda.balanceapplication.models.services.Service
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.http.*


interface ScratchAPI {

    @GET("receivedBalance/{userId}")
    suspend fun getBalances(
        @Path("userId") userId: Int
    ): List<Balance>
    @GET("displaySellHistory/{userId}")
    suspend fun displaySellHistory(
            @Path("userId") userId: Int
    ): SellHistoryResponse
    suspend fun displayChargeHistory(
        @Path("userId") userId: Int
    ): SellHistoryResponse

    @GET("allServices")
    suspend fun getServices(

    ): List<Service>
@POST("login")
suspend fun login( @Query("phone") phone: String,
    @Query("password") password: String) :RegisterResponse

  /*  @Multipart
    @POST("register")
    suspend   fun register(

            @Part("name") name: RequestBody,
            @Part("phone") phone: RequestBody,
            @Part("password") password: RequestBody,
            @Part("password_confirmation") passwordConfirmation: RequestBody,
            @Part("type") type: RequestBody,
            @Part image: MultipartBody.Part
    ):RegisterResponse*/
  @POST("register")
  suspend fun register(
       //   @Header("Authorization")
      @Query("name") name: String,
      @Query("phone") phone: String,
      @Query("password") password: String,
      @Query("password_confirmation") passwordConfirmation: String,
      @Query("type") type: String
  ) :RegisterResponse

    @GET("showProfile/{userId}")
    suspend fun getPhones(
        @Header("Authorization") token: String,
        @Path("userId") userId: Int
    ):ProfileResponse

    @POST("updatePhone")
    suspend fun updateProfile(
         //   @Header("Authorization: token") token: String,
       // @Header("Authorization") token: String?,
        @Query("user_id") userId: Int?,
        @Query("zain") zain: String,
        @Query("mtn") mtn: String,
        @Query("sudani") sudani: String
    ): UpdateNumbersResponse

  @Multipart
@POST("uploadImage")
suspend   fun uploadImage(
          @Header("Authorization") accessToken: String,
       //   @Header("Authorization: token") accessToken: String,
     // @Header("Authorization") token: String,
    //  @Part("desc") desc: RequestBody,
      @Part("id") id: RequestBody,
      @Part image: MultipartBody.Part
):UpdateImageResponse
    @PUT("saveChargeOp/{userId}")
    suspend fun saveChargeOp(
    //   @Header("Authorization: token") token: String,
            @Query("userId") userId: Int): ChargeResponse


    @PUT("buyBalance")
    suspend fun buyBalance(
        //   @Header("Authorization: token") token: String,
        @Path("user_id") userId: Int,
        @Query("amount") amount: Int,
        @Query("type") type:String,
        @Query("phone") phone:String): BuyBalanceResponse

    @PUT("buyScratch")
    suspend fun buyScratch(
        //   @Header("Authorization: token") token: String,
        @Path("user_id") userId: Int,
        @Query("amount") amount: Int,
        @Query("type") type:String): BuyScratchResponse


    @PUT("buyBalance")
    suspend fun chargeOtherClint(
        //   @Header("Authorization: token") token: String,
        @Query("user_id") userId: Int,
        @Query("clint_id") clintId: Int,
        @Query("amount") amount: Int): ChargeHistory

    @GET("lookForClint/{phone}")
    suspend fun lookForClint(
        //   @Header("Authorization: token") token: String,
    @Path("phone") phone:String): FindClintResponse
}