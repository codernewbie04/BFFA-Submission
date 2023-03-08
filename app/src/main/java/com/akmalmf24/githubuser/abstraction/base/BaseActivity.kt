package com.akmalmf24.githubuser.abstraction.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.akmalmf24.githubuser.R
import com.akmalmf24.githubuser.abstraction.data.HttpResult
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Akmal Muhamad Firdaus on 03/03/2023 23:34.
 * akmalmf007@gmail.com
 */
abstract class BaseActivity : AppCompatActivity(){
    abstract fun initView()
    abstract fun initObservable()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initView()
        initObservable()
    }

    private fun snackBarError(message: String) {
        Snackbar.make(findViewById(android.R.id.content), message, Snackbar.LENGTH_SHORT)
            .setBackgroundTint(ContextCompat.getColor(this, R.color.red_400))
            .show()
    }


    fun showErrorAlert(cause: HttpResult, message: String? = null) {
        snackBarError(message ?: "Unknown Error!")
    }
}