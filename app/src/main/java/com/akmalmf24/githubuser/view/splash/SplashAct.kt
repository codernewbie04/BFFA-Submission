package com.akmalmf24.githubuser.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.akmalmf24.githubuser.R
import com.akmalmf24.githubuser.abstraction.base.BaseActivity
import com.akmalmf24.githubuser.core.factory.GithubViewModelFactory
import com.akmalmf24.githubuser.view.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashAct : BaseActivity() {
    private val viewModel by viewModels<SplashViewModel> {
        GithubViewModelFactory.getInstance(application)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
        if (viewModel.isThemeDark()) {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        } else {
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }


    override fun initView() {
        //Nothing to do, coz only redirect with delay
    }

    override fun initObservable() {
        lifecycleScope.launch {
            delay(DELAY_TIME_MS)
            startActivity(Intent(this@SplashAct, MainActivity::class.java))
            finish()
        }
    }

    companion object {
        const val DELAY_TIME_MS: Long = 2000
    }
}