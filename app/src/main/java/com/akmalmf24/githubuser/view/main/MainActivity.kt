package com.akmalmf24.githubuser.view.main


import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import com.akmalmf24.githubuser.R
import com.akmalmf24.githubuser.abstraction.base.BaseActivity
import com.akmalmf24.githubuser.abstraction.data.Resource
import com.akmalmf24.githubuser.abstraction.data.Status
import com.akmalmf24.githubuser.core.factory.GithubViewModelFactory
import com.akmalmf24.githubuser.core.response.Users
import com.akmalmf24.githubuser.databinding.ActivityMainBinding
import com.akmalmf24.githubuser.view.adapter.UsersAdapter
import com.akmalmf24.githubuser.view.detail.DetailUserAct


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel>(){
        GithubViewModelFactory.getInstance(application)
    }

    private val adapterListUsers by lazy { UsersAdapter(true) }

    override fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapterListUsers

        adapterListUsers.onItemClick = {
            it.login?.let { it1 -> DetailUserAct.start(this, it1) }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.option_menu, menu)

        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = menu.findItem(R.id.search).actionView as SearchView

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.queryHint = resources.getString(R.string.search_text)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()) {
                    viewModel.searchUser(query)
                } else {
                    viewModel.getPopularUser()
                }

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if(!searchView.isIconified && newText.isEmpty()){
                    viewModel.getPopularUser()
                }
                return false
            }
        })
        return true
    }

    override fun initObservable() {
        viewModel.users.observe(this){
            handleUiState(it)
        }
    }

    @JvmName("handleUiStateUserResponse")
    private fun handleUiState(resource: Resource<List<Users>>) {
        when(resource.status){
            Status.LOADING -> {
                binding.shimmerUser.visibility = View.VISIBLE
                binding.shimmerUser.startShimmer()
            }
            Status.SUCCESS -> {
                resource.data?.let { adapterListUsers.setItem(it.toMutableList()) }
                binding.shimmerUser.visibility = View.GONE
                binding.shimmerUser.stopShimmer()
                binding.rvUsers.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                showErrorAlert(resource.cause, resource.message)
            }
        }
    }
}