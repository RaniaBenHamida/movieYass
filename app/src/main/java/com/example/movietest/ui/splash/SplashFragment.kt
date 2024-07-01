package com.example.movietest.ui.splash

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.hilt.navigation.fragment.hiltNavGraphViewModels
import androidx.navigation.fragment.findNavController
import com.example.movietest.R
import com.example.movietest.databinding.FragmentSplashBinding
import com.example.movietest.helpers.DebugLog
import com.example.movietest.helpers.Navigation
import com.example.movietest.ui.base.BaseFragment

import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SplashFragment : BaseFragment() {

    private val viewModel by hiltNavGraphViewModels<SplashViewModel>(R.id.main_nav_graphe)

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ) = FragmentSplashBinding.inflate(inflater, container, false).let(::bind)


    private fun bind(binding: FragmentSplashBinding): View {
        binding.lifecycleOwner = viewLifecycleOwner
        registerBaseObservers(viewModel)
        viewModel.splash()
        return binding.root
    }


    override fun navigate(navigationTo: Navigation) {
        DebugLog.i("Test__", "navigate")
        when (navigationTo) {

            is Navigation.Home ->
                findNavController().navigate(SplashFragmentDirections.actionSplashFragmentToHomeFragment())

            else -> super.navigate(navigationTo)
        }
    }
}