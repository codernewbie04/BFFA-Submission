package com.akmalmf24.githubuser.view.main


import android.app.SearchManager
import android.content.Context
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import com.akmalmf24.githubuser.R
import com.akmalmf24.githubuser.abstraction.base.BaseActivity
import com.akmalmf24.githubuser.abstraction.data.Resource
import com.akmalmf24.githubuser.abstraction.data.Status
import com.akmalmf24.githubuser.core.data.remote.response.Users
import com.akmalmf24.githubuser.core.factory.GithubViewModelFactory
import com.akmalmf24.githubuser.databinding.ActivityMainBinding
import com.akmalmf24.githubuser.view.bottom_sheet.BottomSheetTheme
import com.akmalmf24.githubuser.view.adapter.UsersAdapter
import com.akmalmf24.githubuser.view.detail.DetailUserAct


class MainActivity : BaseActivity() {
    private lateinit var binding: ActivityMainBinding
    private val viewModel by viewModels<MainViewModel> {
        GithubViewModelFactory.getInstance(application)
    }
    private var isOnFav = false

    private val adapterListUsers by lazy { UsersAdapter(true) }

    override fun initView() {
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvUsers.setHasFixedSize(true)
        binding.rvUsers.adapter = adapterListUsers

        adapterListUsers.onItemClick = {
            it.login?.let { it1 -> DetailUserAct.start(this, it1) }
        }

        binding.fab.setOnClickListener {
            if (!isOnFav) {
                snackBar("Show favorite user")
                isOnFav = true
                viewModel.getFavoriteUser()
                binding.fab.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.fav_color
                    )
                )
            } else {
                snackBar("Show user")
                isOnFav = false
                viewModel.getPopularUser()
                binding.fab.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.white
                    )
                )
            }
        }
    }

    override fun onResume() {
        super.onResume()
        if (isOnFav) {
            isOnFav = true
            viewModel.getFavoriteUser()
            binding.fab.setColorFilter(
                ContextCompat.getColor(
                    this@MainActivity,
                    R.color.fav_color
                )
            )
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
                isOnFav = false
                binding.fab.setColorFilter(
                    ContextCompat.getColor(
                        this@MainActivity,
                        R.color.white
                    )
                )
                if (query.isNotEmpty()) {
                    viewModel.searchUser(query)
                } else {
                    viewModel.getPopularUser()
                }

                searchView.clearFocus()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                if (!searchView.isIconified && newText.isEmpty()) {
                    isOnFav = false
                    binding.fab.setColorFilter(
                        ContextCompat.getColor(
                            this@MainActivity,
                            R.color.white
                        )
                    )
                    viewModel.getPopularUser()
                }
                return false
            }
        })
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.setting -> {
                showBottomSheet()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun initObservable() {
        viewModel.users.observe(this) {
            handleUiState(it)
        }

    }

    private fun showBottomSheet() {
        val addPhotoBottomDialogFragment: BottomSheetTheme =
            BottomSheetTheme.newInstance()
        addPhotoBottomDialogFragment.show(
            supportFragmentManager,
            "asd"
        )
    }

    @JvmName("handleUiStateUserResponse")
    private fun handleUiState(resource: Resource<List<Users>>) {
        when (resource.status) {
            Status.LOADING -> {
                binding.onError.visibility = View.GONE
                binding.rvUsers.visibility = View.GONE
                binding.noData.visibility = View.GONE
                binding.shimmerUser.visibility = View.VISIBLE
                binding.shimmerUser.startShimmer()
            }
            Status.SUCCESS -> {
                resource.data?.let {
                    adapterListUsers.setItem(it.toMutableList())
                    binding.noData.visibility = if (it.isEmpty()) {
                        View.VISIBLE
                    } else {
                        View.GONE
                    }
                }
                binding.onError.visibility = View.GONE
                binding.shimmerUser.visibility = View.GONE
                binding.shimmerUser.stopShimmer()
                binding.rvUsers.visibility = View.VISIBLE
            }
            Status.ERROR -> {
                binding.rvUsers.visibility = View.GONE
                binding.noData.visibility = View.GONE
                binding.shimmerUser.visibility = View.GONE
                binding.onError.visibility = View.VISIBLE
                showErrorAlert(resource.message)
            }
        }
    }
}