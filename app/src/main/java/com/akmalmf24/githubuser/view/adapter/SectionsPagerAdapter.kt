package com.akmalmf24.githubuser.view.adapter

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.akmalmf24.githubuser.view.detail.follows.FollowsFragment

/**
 * Created by Akmal Muhamad Firdaus on 05/03/2023 04:05.
 * akmalmf007@gmail.com
 */
class SectionsPagerAdapter(activity: AppCompatActivity) : FragmentStateAdapter(activity) {
    var username: String? = null

    override fun getItemCount(): Int {
        return 2
    }

    override fun createFragment(position: Int): Fragment {
        return username?.let { FollowsFragment.newInstance(it, position) }!!
    }
}