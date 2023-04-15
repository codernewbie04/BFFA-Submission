package com.akmalmf24.githubuser.core.utils

import androidx.lifecycle.MutableLiveData

/**
 * Created by Akmal Muhamad Firdaus on 28/03/2023 05:47.
 * akmalmf007@gmail.com
 */


fun <T : Any?> MutableLiveData<T>.default(initialValue: T) = apply { setValue(initialValue) }