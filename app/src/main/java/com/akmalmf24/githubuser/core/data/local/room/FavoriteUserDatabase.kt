package com.akmalmf24.githubuser.core.data.local.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.akmalmf24.githubuser.core.data.local.entity.FavoriteUserEntity

/**
 * Created by Akmal Muhamad Firdaus on 28/03/2023 05:07.
 * akmalmf007@gmail.com
 */
@Database(
    entities = [FavoriteUserEntity::class],
    version = 1,
    exportSchema = false
)
abstract class FavoriteUserDatabase : RoomDatabase() {
    abstract fun favoriteUserDAO(): FavoriteUserDAO

    companion object {

        @Volatile
        private var INSTANCE: FavoriteUserDatabase? = null

        fun getInstance(context: Context): FavoriteUserDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    FavoriteUserDatabase::class.java,
                    "GithubUserCodernewbie04.db"
                ).build()
            }
    }
}