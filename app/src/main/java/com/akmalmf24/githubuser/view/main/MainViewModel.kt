package com.akmalmf24.githubuser.view.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akmalmf24.githubuser.abstraction.data.Resource
import com.akmalmf24.githubuser.abstraction.data.Status
import com.akmalmf24.githubuser.core.data.local.LocalDataSource
import com.akmalmf24.githubuser.core.data.local.entity.FavoriteUserEntity
import com.akmalmf24.githubuser.core.data.remote.response.Users
import com.akmalmf24.githubuser.core.data.remote.source.GithubDataSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Akmal Muhamad Firdaus on 04/03/2023 00:47.
 * akmalmf007@gmail.com
 */
class MainViewModel(
    private val local: LocalDataSource,
    private val remote: GithubDataSource,
    application: Application,
) : AndroidViewModel(application) {
    private val _users = MutableLiveData<Resource<List<Users>>>()
    val users: LiveData<Resource<List<Users>>> get() = _users

    init {
        viewModelScope.launch { getPopularUser() }
    }

    fun getPopularUser() {

        viewModelScope.launch {
            remote.popularUser().collect {
                _users.postValue(it)
            }
        }
    }

    fun getFavoriteUser() {
        viewModelScope.launch(Dispatchers.IO) {
            _users.postValue(mapToResource(local.getFavoriteUser()))
        }
    }

    fun searchUser(username: String) {
        viewModelScope.launch {
            remote.searchUser(username).collect {
                val dataUsers: List<Users> = if (it.status == Status.SUCCESS) {
                    it.data?.items as List<Users>
                } else {
                    listOf()
                }

                _users.postValue(Resource(it.status, dataUsers, it.message, it.code, it.cause))
            }
        }
    }

    private fun mapToResource(fav: List<FavoriteUserEntity>): Resource<List<Users>> {
        return Resource(Status.SUCCESS, mapToListUser(fav), "ok!", 200)
    }

    private fun mapToListUser(fav: List<FavoriteUserEntity>) =
        fav.map { f -> Users(f.username, f.htmlUrl, f.avatarUrl, f.id) }

}
