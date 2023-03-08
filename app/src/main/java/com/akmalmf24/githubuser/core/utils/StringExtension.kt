package com.akmalmf24.githubuser.core.utils

import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Akmal Muhamad Firdaus on 05/03/2023 17:08.
 * akmalmf007@gmail.com
 */


fun String.convertToReadableDate(): String {
    val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.getDefault())
    val outputFormat = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
    val date = inputFormat.parse(this)
    return outputFormat.format(date)
}