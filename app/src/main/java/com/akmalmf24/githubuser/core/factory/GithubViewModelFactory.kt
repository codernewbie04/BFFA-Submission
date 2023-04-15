package com.akmalmf24.githubuser.core.factory

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.akmalmf24.githubuser.core.BaseAPI
import com.akmalmf24.githubuser.core.data.local.LocalDataSource
import com.akmalmf24.githubuser.core.data.local.room.FavoriteUserDatabase
import com.akmalmf24.githubuser.core.data.remote.source.GithubDataSource
import com.akmalmf24.githubuser.view.detail.DetailUserViewModel
import com.akmalmf24.githubuser.view.detail.follows.FollowsViewModel
import com.akmalmf24.githubuser.view.main.MainViewModel
import com.akmalmf24.githubuser.core.data.sharepreference.SharePrefRepository
import com.akmalmf24.githubuser.view.bottom_sheet.BottomSheetThemeViewModel
import com.akmalmf24.githubuser.view.splash.SplashViewModel

/**
 * Created by Akmal Muhamad Firdaus on 04/03/2023 01:32.
 * akmalmf007@gmail.com
 */
class GithubViewModelFactory(private val application: Application) :
    ViewModelProvider.NewInstanceFactory() {
    private val database = FavoriteUserDatabase.getInstance(application)
    private val remoteDataSource = GithubDataSource.getInstance(BaseAPI.provideGithubService())
    private val localDataSource = LocalDataSource.getInstance(database.favoriteUserDAO())
    private val pref = SharePrefRepository.getInstance(application)

    override fun <T : ViewModel> create(modelClass: Class<T>): T =
        when {
            modelClass.isAssignableFrom(SplashViewModel::class.java) ->
                SplashViewModel(pref, application) as T
            modelClass.isAssignableFrom(BottomSheetThemeViewModel::class.java) ->
                BottomSheetThemeViewModel(pref, application) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(localDataSource, remoteDataSource, application) as T
            modelClass.isAssignableFrom(FollowsViewModel::class.java) ->
                FollowsViewModel(remoteDataSource, application) as T
            modelClass.isAssignableFrom(DetailUserViewModel::class.java) ->
                DetailUserViewModel(localDataSource, remoteDataSource, application) as T
            else -> throw Throwable("Unknown ViewModel class: " + modelClass.name)
        }

    companion object {
        @Volatile
        private var instance: GithubViewModelFactory? = null
        fun getInstance(application: Application): GithubViewModelFactory =
            instance ?: synchronized(this) {
                instance ?: GithubViewModelFactory(application)
            }
    }
}