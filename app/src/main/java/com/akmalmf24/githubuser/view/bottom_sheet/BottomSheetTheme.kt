package com.akmalmf24.githubuser.view.bottom_sheet

import android.os.Bundle
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CompoundButton
import androidx.appcompat.app.AppCompatDelegate
import androidx.fragment.app.viewModels
import com.akmalmf24.githubuser.core.factory.GithubViewModelFactory
import com.akmalmf24.githubuser.databinding.FragmentBottomSheetThemeBinding


class BottomSheetTheme : BottomSheetDialogFragment() {

    private var _binding: FragmentBottomSheetThemeBinding? = null
    private val binding get() = _binding!!
    private val viewModel by viewModels<BottomSheetThemeViewModel> {
        GithubViewModelFactory.getInstance(requireActivity().application)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentBottomSheetThemeBinding.inflate(inflater, container, false)
        binding.switchTheme.setOnCheckedChangeListener { _: CompoundButton?, isChecked: Boolean ->
            if (isChecked) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                viewModel.setDarkTheme(true)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                viewModel.setDarkTheme(false)
            }
        }

        viewModel.darkThemeIsActive.observe(this){
            binding.switchTheme.isChecked = it
        }

        return binding.root
    }

    companion object {
        fun newInstance(): BottomSheetTheme = BottomSheetTheme()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}