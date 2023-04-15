package com.akmalmf24.githubuser.view.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.akmalmf24.githubuser.core.data.sharepreference.SharePrefRepository


/**
 * Created by Akmal Muhamad Firdaus on 29/03/2023 00:19.
 * akmalmf007@gmail.com
 */
class SplashViewModel(
    private val pref: SharePrefRepository,
    application: Application
) : AndroidViewModel(application) {

    fun isThemeDark() = pref.isDarkThemeActive()
}