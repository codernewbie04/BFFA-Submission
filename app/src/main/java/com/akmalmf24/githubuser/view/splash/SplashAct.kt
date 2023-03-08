package com.akmalmf24.githubuser.view.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.lifecycleScope
import com.akmalmf24.githubuser.R
import com.akmalmf24.githubuser.abstraction.base.BaseActivity
import com.akmalmf24.githubuser.view.main.MainActivity
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashAct : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)
    }


    override fun initView() {
        //Nothing to do, coz only redirect with delay
    }

    override fun initObservable() {
        lifecycleScope.launch {
            delay(2000)
            startActivity(Intent(this@SplashAct, MainActivity::class.java))
            finish()
        }
    }
}