package com.akmalmf24.githubuser.abstraction.base

import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.akmalmf24.githubuser.R
import com.akmalmf24.githubuser.abstraction.data.HttpResult
import com.google.android.material.snackbar.Snackbar

/**
 * Created by Akmal Muhamad Firdaus on 05/03/2023 03:23.
 * akmalmf007@gmail.com
 */
abstract class BaseFragment : Fragment() {
    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun initObservable()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
        initObservable()
    }

    private fun snackBarError(message: String) {
        Snackbar.make(
            requireActivity().findViewById(android.R.id.content),
            message,
            Snackbar.LENGTH_SHORT
        )
            .setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.red_400))
            .show()
    }

    fun showErrorAlert(message: String? = null) {
        snackBarError(message ?: "Unknown Error!")
    }

}