package com.akmalmf24.githubuser.core.response

import com.google.gson.annotations.SerializedName

/**
 * Created by Akmal Muhamad Firdaus on 04/03/2023 01:08.
 * akmalmf007@gmail.com
 */
data class DetailUser(
    @SerializedName("login")  val login: String? = null,
    @SerializedName("html_url")  val htmlUrl: String? = null,
    @SerializedName("avatar_url")  val avatarUrl: String? = null,
    @SerializedName("score")  val score: Any? = null,
    @SerializedName("name")  val name: String? = null,
    @SerializedName("company")  val company: String? = null,
    @SerializedName("location")  val location: String? = null,
    @SerializedName("public_repos")  val public_repos: String? = null,
    @SerializedName("followers")  val followers: Int? = null,
    @SerializedName("following")  val following: Int? = null,
    @SerializedName("created_at")  val createdAt: String? = null,

    )