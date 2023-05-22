package com.kernelsoft.quora_clone.data.model

import com.google.gson.annotations.SerializedName

data class User(
    @SerializedName("user_name")
    val userName: String? = "", // insert a UID field
    @SerializedName("mail")
    val eMail: String? = "",
    //val password: String?,
    //val reputation, image, bookmarks, followed tags, follows, followers, questions, joined time, language
)
