package com.akmalmf24.githubuser.view.detail.follows

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.akmalmf24.githubuser.abstraction.base.BaseFragment
import com.akmalmf24.githubuser.abstraction.data.Resource
import com.akmalmf24.githubuser.abstraction.data.Status
import com.akmalmf24.githubuser.core.factory.GithubViewModelFactory
import com.akmalmf24.githubuser.core.response.Users
import com.akmalmf24.githubuser.databinding.FragmentFollowsBinding
import com.akmalmf24.githubuser.view.adapter.UsersAdapter

class FollowsFragment : BaseFragment() {
    companion object {
        private const val EXTRA_USERNAME = "username"
        private const val EXTRA_SECTION = "section"
        @JvmStatic
        fun newInstance(username: String, section: Int) =
            FollowsFragment().apply {
                arguments = Bundle().apply {
                    putString(EXTRA_USERNAME, username)
                    putInt(EXTRA_SECTION, section)
                }
            }
    }

    private var _binding: FragmentFollowsBinding? = null
    private val binding get() = _binding!!

    private val viewModel by viewModels<FollowsViewModel> {
        GithubViewModelFactory.getInstance(requireActivity().application)
    }

    private val adapterListUsers by lazy { UsersAdapter(false) }


    override fun initView(savedInstanceState: Bundle?) {
        val section = arguments?.getInt(EXTRA_SECTION, 0)
        val username = arguments?.getString(EXTRA_USERNAME)
        val type = if (section == 0) "followers" else "following"
        binding.rvUsers.setHasFixedSize(true)
//        val linearLayoutManager = object : LinearLayoutManager(context) {
//            override fun canScrollVertically(): Boolean {
//                return false
//            }
//        }
        binding.rvUsers.adapter = adapterListUsers
        username?.let { viewModel.getFollows(it, type) }
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


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFollowsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}