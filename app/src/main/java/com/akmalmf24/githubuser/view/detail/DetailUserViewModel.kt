package com.akmalmf24.githubuser.view.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akmalmf24.githubuser.abstraction.data.Resource
import com.akmalmf24.githubuser.core.response.DetailUser
import com.akmalmf24.githubuser.core.source.GithubDataSource
import kotlinx.coroutines.launch

/**
 * Created by Akmal Muhamad Firdaus on 05/03/2023 15:24.
 * akmalmf007@gmail.com
 */
class DetailUserViewModel(
    private val remote: GithubDataSource,
    application: Application,
): AndroidViewModel(application){
    private val _detailUser = MutableLiveData<Resource<DetailUser>>()
    val detailUser: LiveData<Resource<DetailUser>> get() = _detailUser

    fun detailUser(username: String){
        viewModelScope.launch {
            remote.detailUser(username).collect {
                _detailUser.postValue(it)
            }
        }
    }
}