package com.akmalmf24.githubuser.view.bottom_sheet

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akmalmf24.githubuser.core.data.sharepreference.SharePrefRepository
import com.akmalmf24.githubuser.core.utils.default
import kotlinx.coroutines.launch

/**
 * Created by Akmal Muhamad Firdaus on 29/03/2023 00:54.
 * akmalmf007@gmail.com
 */
class BottomSheetThemeViewModel(private val pref: SharePrefRepository, application: Application): AndroidViewModel(application){
    private val _darkThemeIsActive = MutableLiveData<Boolean>().default(pref.isDarkThemeActive())
    val darkThemeIsActive: LiveData<Boolean> get() = _darkThemeIsActive

    fun setDarkTheme(stat: Boolean){
        viewModelScope.launch {
            pref.setTheme(stat)
            _darkThemeIsActive.postValue(stat)
        }
    }
}