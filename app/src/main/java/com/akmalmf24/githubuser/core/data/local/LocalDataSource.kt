package com.akmalmf24.githubuser.core.data.local

import com.akmalmf24.githubuser.core.data.local.entity.FavoriteUserEntity
import com.akmalmf24.githubuser.core.data.local.room.FavoriteUserDAO

/**
 * Created by Akmal Muhamad Firdaus on 28/03/2023 05:12.
 * akmalmf007@gmail.com
 */
class LocalDataSource private constructor(private val favoriteUserDAO: FavoriteUserDAO) {
    companion object {
        private var INSTANCE: LocalDataSource? = null

        fun getInstance(favoriteUserDAO: FavoriteUserDAO): LocalDataSource =
            INSTANCE ?: LocalDataSource(favoriteUserDAO)
    }

    fun getFavoriteUser(): List<FavoriteUserEntity> = favoriteUserDAO.getFavoriteUser()

    fun insertFavoriteUser(data: FavoriteUserEntity) = favoriteUserDAO.insertFavoriteUser(data)

    fun deleteFavoriteUser(user: FavoriteUserEntity) = favoriteUserDAO.deleteFavoriteUser(user)

    fun getFavoriteUserById(id: Int) = favoriteUserDAO.getFavoriteUserById(id)

}