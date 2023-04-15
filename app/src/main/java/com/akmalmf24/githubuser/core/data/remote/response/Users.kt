package com.akmalmf24.githubuser.core.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 05/03/2023 00:32.
 * akmalmf007@gmail.com
 */
data class Users(
    @SerializedName("login") val login: String? = null,
    @SerializedName("html_url") val htmlUrl: String? = null,
    @SerializedName("avatar_url") val avatarUrl: String? = null,
    @SerializedName("id")val id: Int? = null,
)