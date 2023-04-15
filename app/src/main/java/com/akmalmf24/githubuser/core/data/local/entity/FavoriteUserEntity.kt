package com.akmalmf24.githubuser.core.data.local.entity

import androidx.annotation.NonNull
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Created by Akmal Muhamad Firdaus on 28/03/2023 04:52.
 * akmalmf007@gmail.com
 */
@Entity(tableName = "favorite_user")
data class FavoriteUserEntity(
    @PrimaryKey(autoGenerate = false)
    @ColumnInfo(name = "id")
    var id: Int,

    @ColumnInfo(name = "html_url")
    var htmlUrl: String? = null,

    @ColumnInfo(name = "username")
    var username: String? = null,

    @ColumnInfo(name = "avatar_url")
    var avatarUrl: String? = null,
)