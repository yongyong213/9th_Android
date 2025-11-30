package com.example.umc_flo_app

import android.provider.ContactsContract
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.serialization.Serializer

data class User(
    @SerializedName(value = "email") var email: String,
    @SerializedName(value = "password") var password: String,
    @SerializedName(value = "nickname") var nickname: String
){
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
