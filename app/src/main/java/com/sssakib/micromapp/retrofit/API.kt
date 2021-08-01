package com.sssakib.micromapp.retrofit


import android.service.autofill.UserData
import com.sssakib.micromapp.model.AccountModel
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.*


interface API {



    @FormUrlEncoded
    @POST("AgentApi")
    fun doGetAllPendingTransaction(
        @Field("requestCode") requestCode: String?
    ): Single < List<AccountModel>>


    @FormUrlEncoded
    @POST("AgentApi")
    fun doApprove(
        @Field("requestCode") requestCode: String?,
        @Field("flag") flag: String?,
        @Field("id") id: String?
    ): Single<AccountModel>





}
