package com.akmalmf24.githubuser.core.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akmalmf24.githubuser.core.BaseAPI
import com.akmalmf24.githubuser.core.source.GithubDataSource
import com.akmalmf24.githubuser.view.detail.DetailUserViewModel
import com.akmalmf24.githubuser.view.detail.follows.FollowsViewModel
import com.akmalmf24.githubuser.view.main.MainViewModel

/**
 * Created by Akmal Muhamad Firdaus on 04/03/2023 01:32.
 * akmalmf007@gmail.com
 */
class GithubViewModelFactory(private val application: Application) : ViewModelProvider.NewInstanceFactory() {
    private val remoteDataSource = GithubDataSource.getInstance(BaseAPI.provideGithubService())

    companion object {
        @Volatile
        private var instance: GithubViewModelFactory? = null
        fun getInstance(application: Application): GithubViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: GithubViewModelFactory(application)
            }
    }


    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(remoteDataSource,application) as T
            modelClass.isAssignableFrom(FollowsViewModel::class.java) ->
                FollowsViewModel(remoteDataSource,application) as T
            modelClass.isAssignableFrom(DetailUserViewModel::class.java) ->
                DetailUserViewModel(remoteDataSource,application) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

}