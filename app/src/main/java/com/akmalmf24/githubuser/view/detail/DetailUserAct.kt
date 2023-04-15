package com.akmalmf24.githubuser.view.detail

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.viewModels
import androidx.core.content.ContextCompat
import coil.load
import com.akmalmf24.githubuser.R
import com.akmalmf24.githubuser.abstraction.base.BaseActivity
import com.akmalmf24.githubuser.abstraction.data.Resource
import com.akmalmf24.githubuser.abstraction.data.Status
import com.akmalmf24.githubuser.core.factory.GithubViewModelFactory
import com.akmalmf24.githubuser.core.data.remote.response.DetailUser
import com.akmalmf24.githubuser.core.utils.convertToReadableDate
import com.akmalmf24.githubuser.databinding.ActivityDetailUserBinding
import com.akmalmf24.githubuser.view.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserAct : BaseActivity() {
    private lateinit var binding: ActivityDetailUserBinding
    private var isFav = false
    private val viewModel by viewModels<DetailUserViewModel> {
        GithubViewModelFactory.getInstance(application)
    }

    override fun initView() {
        binding = ActivityDetailUserBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val sectionsPagerAdapter = SectionsPagerAdapter(this@DetailUserAct)
        sectionsPagerAdapter.username = intent?.getStringExtra(EXTRA_USERNAME)
        binding.viewPager.adapter = sectionsPagerAdapter
        TabLayoutMediator(binding.tabLayout, binding.viewPager) { tab, position ->
            tab.text = resources.getString(TAB_TITLES[position])
        }.attach()
    }

    override fun onResume() {
        super.onResume()
        intent?.getStringExtra(EXTRA_USERNAME)?.let { viewModel.detailUser(it) }
    }

    override fun initObservable() {
        viewModel.detailUser.observe(this) {
            handleUiState(it)
        }
        viewModel.isFavorite.observe(this) {
            isFav = it
            if (it) {
                binding.fab.setColorFilter(
                    ContextCompat.getColor(
                        this@DetailUserAct,
                        R.color.fav_color
                    )
                )
            } else {
                binding.fab.setColorFilter(
                    ContextCompat.getColor(
                        this@DetailUserAct,
                        R.color.white
                    )
                )
            }

        }
    }

    @SuppressLint("SetTextI18n")
    @JvmName("handleUiStateUserResponse")
    private fun handleUiState(resource: Resource<DetailUser>) {
        when (resource.status) {
            Status.LOADING -> {
                binding.apply {
                    shimmerFollows.startShimmer()
                    shimmerProfile.startShimmer()
                    headerGroup.visibility = View.GONE
                    shimmerGroups.visibility = View.VISIBLE
                }
            }
            Status.SUCCESS -> {
                binding.apply {
                    shimmerFollows.stopShimmer()
                    shimmerProfile.stopShimmer()
                    headerGroup.visibility = View.VISIBLE
                    shimmerGroups.visibility = View.GONE
                    imageUser.load(resource.data?.avatarUrl) {
                        placeholder(R.color.gray)
                    }
                    textName.text = resource.data?.name ?: "-"
                    textUsername.text = "@${resource.data?.login}"
                    textUsername.setOnClickListener {
                        val uri: Uri =
                            Uri.parse(resource.data?.htmlUrl ?: "https://github.com/")
                        val intent = Intent(Intent.ACTION_VIEW, uri)
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        intent.setPackage("com.android.chrome")
                        try {
                            startActivity(intent)
                        } catch (ex: ActivityNotFoundException) {
                            // Jika google chrome tidak di install
                            intent.setPackage(null)
                            startActivity(intent)
                        }
                    }
                    textJoined.text = resource.data?.createdAt?.convertToReadableDate() ?: "-"
                    textCountFollowers.text = resource.data?.followers.toString()
                    textCountRepos.text = resource.data?.public_repos.toString()
                    textCountFollowing.text = resource.data?.following.toString()
                    fab.setOnClickListener {
                        if (!isFav) {
                            snackBar("Add to favorite!")
                        } else {
                            snackBar("Delete from favorite!")
                        }
                        resource.data?.let { viewModel.insertFavoriteUser(it) }
                    }
                }
            }
            Status.ERROR -> {
                showErrorAlert(resource.message)
            }
        }
    }

    companion object {
        const val EXTRA_USERNAME = "username"
        fun start(context: Context, username: String) {
            context.startActivity(
                Intent(context, DetailUserAct::class.java).apply {
                    putExtra(EXTRA_USERNAME, username)
                }
            )
        }


        private val TAB_TITLES = intArrayOf(
            R.string.followers,
            R.string.following
        )
    }
}