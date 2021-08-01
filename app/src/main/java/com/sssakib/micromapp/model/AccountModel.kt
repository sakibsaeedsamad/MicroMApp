package com.sssakib.micromapp.model

import com.google.gson.annotations.SerializedName

data class AccountModel (
    @SerializedName("id")
    var id: String?,

    @SerializedName("amount")
    var amount: String?,

    @SerializedName("remarks")
    var remarks: String?,

//    @SerializedName("flag")
//    var flag: String?,


    @SerializedName("outCode")
    var outCode: String?,

    @SerializedName("outMessage")
    var outMessage: String?
        )