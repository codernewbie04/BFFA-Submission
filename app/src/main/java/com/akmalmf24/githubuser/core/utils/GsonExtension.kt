package com.akmalmf24.githubuser.core.utils

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by Akmal Muhamad Firdaus on 05/03/2023 17:36.
 * akmalmf007@gmail.com
 */
inline fun <reified T> Gson.fromJson(json:String) =
    this.fromJson<T>(json,object : TypeToken<T>(){}.type)