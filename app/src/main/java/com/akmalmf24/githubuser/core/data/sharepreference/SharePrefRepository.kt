package com.akmalmf24.githubuser.core.data.sharepreference

import android.content.Context
import com.akmalmf24.githubuser.abstraction.base.SharePrefKey

/**
 * Created by Akmal Muhamad Firdaus on 29/03/2023 00:16.
 * akmalmf007@gmail.com
 */
class SharePrefRepository(context: Context) {
    private val sharePref =
        context.getSharedPreferences(SharePrefKey.GITHUB_KEY, Context.MODE_PRIVATE)

    fun isDarkThemeActive(): Boolean = sharePref.getBoolean(SharePrefKey.THEME_KEY, false)
    fun setTheme(isDarkThemeActive: Boolean) =
        sharePref.edit().putBoolean(SharePrefKey.THEME_KEY, isDarkThemeActive).apply()

    companion object {
        @Volatile
        private var instance: SharePrefRepository? = null

        fun getInstance(context: Context): SharePrefRepository =
            instance ?: synchronized(this) {
                instance ?: SharePrefRepository(context)
            }
    }
}