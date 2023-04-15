package com.akmalmf24.githubuser.view.detail

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.akmalmf24.githubuser.abstraction.data.Resource
import com.akmalmf24.githubuser.core.data.local.LocalDataSource
import com.akmalmf24.githubuser.core.data.local.entity.FavoriteUserEntity
import com.akmalmf24.githubuser.core.data.remote.response.DetailUser
import com.akmalmf24.githubuser.core.data.remote.source.GithubDataSource
import com.akmalmf24.githubuser.core.utils.default
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

/**
 * Created by Akmal Muhamad Firdaus on 05/03/2023 15:24.
 * akmalmf007@gmail.com
 */
class DetailUserViewModel(
    private val local: LocalDataSource,
    private val remote: GithubDataSource,
    application: Application,
) : AndroidViewModel(application) {
    private val _detailUser = MutableLiveData<Resource<DetailUser>>()
    val detailUser: LiveData<Resource<DetailUser>> get() = _detailUser

    private val _isFavorite = MutableLiveData<Boolean>().default(false)
    val isFavorite: LiveData<Boolean> get() = _isFavorite

    fun detailUser(username: String) {
        viewModelScope.launch {
            remote.detailUser(username).collect {
                _detailUser.postValue(it)
                it.data?.let { it1 -> isFav(it1.id) }
            }
        }
    }

    private fun isFav(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _isFavorite.postValue(local.getFavoriteUserById(id).isNotEmpty())
        }
    }

    fun insertFavoriteUser(user: DetailUser) {
        viewModelScope.launch(Dispatchers.IO) {
            val isEmptyMentorHistory = local.getFavoriteUserById(user.id).isEmpty()
            if (isEmptyMentorHistory) {
                //insert to db
                local.insertFavoriteUser(favoriteUserMap(user))
                _isFavorite.postValue(true)
            } else {
                // delete from db
                _isFavorite.postValue(false)
                local.deleteFavoriteUser(favoriteUserMap(user))
            }
        }
    }

    private fun favoriteUserMap(user: DetailUser): FavoriteUserEntity {
        return FavoriteUserEntity(
            id = user.id,
            username = user.login,
            avatarUrl = user.avatarUrl,
            htmlUrl = user.htmlUrl
        )
    }
}