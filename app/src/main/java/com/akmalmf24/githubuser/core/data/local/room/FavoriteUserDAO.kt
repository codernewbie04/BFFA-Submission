package com.akmalmf24.githubuser.core.data.local.room

import androidx.room.*
import com.akmalmf24.githubuser.core.data.local.entity.FavoriteUserEntity

/**
 * Created by Akmal Muhamad Firdaus on 28/03/2023 05:01.
 * akmalmf007@gmail.com
 */
@Dao
interface FavoriteUserDAO {
    @Query("SELECT * FROM favorite_user")
    fun getFavoriteUser(): List<FavoriteUserEntity>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertFavoriteUser(favoriteUser: FavoriteUserEntity)

    @Query("SELECT * FROM favorite_user WHERE id=:id ")
    fun getFavoriteUserById(id: Int): List<FavoriteUserEntity>

    @Delete
    fun deleteFavoriteUser(user: FavoriteUserEntity)
}