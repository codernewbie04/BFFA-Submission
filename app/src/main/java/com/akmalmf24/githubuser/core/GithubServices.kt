package com.akmalmf24.githubuser.core

import com.akmalmf24.githubuser.BuildConfig
import com.akmalmf24.githubuser.core.response.DetailUser
import com.akmalmf24.githubuser.core.response.SearchResponse
import com.akmalmf24.githubuser.core.response.Users
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Akmal Muhamad Firdaus on 04/03/2023 00:25.
 * akmalmf007@gmail.com
 */
interface GithubServices {
    companion object {
        private const val TOKEN = BuildConfig.TOKEN
    }
    @GET("users")
    @Headers("Authorization: token $TOKEN", "UserResponse-Agent: request")
    suspend fun users(): List<Users>

    @GET("search/users")
    @Headers("Authorization: token $TOKEN", "UserResponse-Agent: request")
    suspend fun searchUsers(@Query("q") query: String): SearchResponse

    @GET("users/{username}")
    @Headers("Authorization: token $TOKEN", "UserResponse-Agent: request")
    suspend fun detailUser(@Path("username") username: String): DetailUser

    @GET("users/{username}/{type}")
    @Headers("Authorization: token $TOKEN", "UserResponse-Agent: request")
    suspend fun userFollows(@Path("username") username: String,
                            @Path("type") type: String): List<Users>
}