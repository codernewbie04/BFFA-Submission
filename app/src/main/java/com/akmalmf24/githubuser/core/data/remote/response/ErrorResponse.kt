package com.akmalmf24.githubuser.core.data.remote.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 05/03/2023 17:37.
 * akmalmf007@gmail.com
 */
data class ErrorResponse(
    @SerializedName("message") val message: String? = null,
    @SerializedName("documentation_url") val documentationUrl: String? = null,
)