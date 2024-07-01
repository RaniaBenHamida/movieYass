package com.example.movietest.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import com.example.movietest.databinding.FragmentHomeBinding
import com.example.movietest.helpers.Navigation
import com.example.movietest.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeFragment : BaseFragment() {

    private val viewModel by viewModels<HomeViewModel>()



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentHomeBinding.inflate(inflater, container, false).let(::bind)


    private fun bind(binding: FragmentHomeBinding): View {
        binding.lifecycleOwner = viewLifecycleOwner
        binding.viewModel = viewModel
        return binding.root
    }




    override fun navigate(navigationTo: Navigation) {
        when (navigationTo) {


        }

    }
}