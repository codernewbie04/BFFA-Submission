package com.akmalmf24.githubuser.view.detail

import android.annotation.SuppressLint
import android.content.ActivityNotFoundException
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import androidx.activity.viewModels
import coil.load
import com.akmalmf24.githubuser.R
import com.akmalmf24.githubuser.abstraction.base.BaseActivity
import com.akmalmf24.githubuser.abstraction.data.Resource
import com.akmalmf24.githubuser.abstraction.data.Status
import com.akmalmf24.githubuser.core.factory.GithubViewModelFactory
import com.akmalmf24.githubuser.core.response.DetailUser
import com.akmalmf24.githubuser.core.utils.convertToReadableDate
import com.akmalmf24.githubuser.databinding.ActivityDetailUserBinding
import com.akmalmf24.githubuser.view.adapter.SectionsPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator


class DetailUserAct : BaseActivity()  {
    private lateinit var binding: ActivityDetailUserBinding

    private val viewModel by viewModels<DetailUserViewModel>(){
        GithubViewModelFactory.getInstance(application)
    }

    companion object{
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
        viewModel.detailUser.observe(this){
            handleUiState(it)
        }
    }

    @SuppressLint("SetTextI18n")
    @JvmName("handleUiStateUserResponse")
    private fun handleUiState(resource: Resource<DetailUser>) {
        when(resource.status){
            Status.LOADING -> {
                binding.shimmerFollows.startShimmer()
                binding.shimmerProfile.startShimmer()
                binding.headerGroup.visibility = View.GONE
                binding.shimmerGroups.visibility = View.VISIBLE
            }
            Status.SUCCESS -> {
                binding.shimmerFollows.stopShimmer()
                binding.shimmerProfile.stopShimmer()
                binding.headerGroup.visibility = View.VISIBLE
                binding.shimmerGroups.visibility = View.GONE
                binding.imageUser.load(resource.data?.avatarUrl) {
                    placeholder(R.color.gray)
                    error(R.color.red_400)
                }
                binding.textName.text = resource.data?.name ?: "-"
                binding.textUsername.text = "@${resource.data?.login}"
                binding.textUsername.setOnClickListener{
                    val uri: Uri =
                        Uri.parse(resource.data?.htmlUrl ?: "https://github.com/")
                    val intent = Intent(Intent.ACTION_VIEW, uri)
                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                    intent.setPackage("com.android.chrome")
                    try {
                        startActivity(intent)
                    } catch (ex: ActivityNotFoundException) {
                        // Jika goole chrome tidak di install
                        intent.setPackage(null)
                        startActivity(intent)
                    }
                }
                binding.textJoined.text = resource.data?.createdAt?.convertToReadableDate() ?: "-"
                binding.textCountFollowers.text = resource.data?.followers.toString()
                binding.textCountRepos.text = resource.data?.public_repos.toString()
                binding.textCountFollowing.text = resource.data?.following.toString()
//                resource.data?.let { adapterListUsers.setItem(it.toMutableList()) }
//                binding.shimmerUser.visibility = View.GONE
//                binding.shimmerUser.stopShimmer()
//                binding.rvUsers.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                showErrorAlert(resource.cause, resource.message)
            }
        }
    }
}