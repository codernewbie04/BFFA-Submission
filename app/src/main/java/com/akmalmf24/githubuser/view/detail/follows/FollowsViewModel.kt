package com.akmalmf24.githubuser.view.detail.follows

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akmalmf24.githubuser.abstraction.data.Resource
import com.akmalmf24.githubuser.core.data.remote.response.Users
import com.akmalmf24.githubuser.core.data.remote.source.GithubDataSource
import kotlinx.coroutines.launch

/**
 * Created by Akmal Muhamad Firdaus on 05/03/2023 14:54.
 * akmalmf007@gmail.com
 */
class FollowsViewModel(
    private val remote: GithubDataSource,
    application: Application,
) : AndroidViewModel(application) {
    private val _users = MutableLiveData<Resource<List<Users>>>()
    val users: LiveData<Resource<List<Users>>> get() = _users

    fun getFollows(username: String, type: String) {
        viewModelScope.launch {
            remote.getFollowsUser(username, type).collect {
                _users.postValue(it)
            }
        }
    }
}