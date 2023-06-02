package com.example.nasiyauz.ui.screens

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.BounceInterpolator
import androidx.core.view.isVisible
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.example.nasiyauz.R
import com.example.nasiyauz.databinding.FragmentSplashBinding
import com.example.nasiyauz.utils.BaseUtils.alphaSimpleAnimation
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashFragment : Fragment(R.layout.fragment_splash) {
    private var _binding: FragmentSplashBinding? = null
    private lateinit var binding: FragmentSplashBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentSplashBinding.bind(view)
        _binding = binding

        setOptions

        binding.lottieAnimView.setOnLongClickListener {
            navigation()
            true
        }

    }

    private val setOptions = lifecycleScope.launch {
        repeatOnLifecycle(Lifecycle.State.STARTED) {
            binding.lottieAnimView.apply {
                setAnimation(R.raw.splash_screen_anim)
                repeatCount = LottieDrawable.RESTART
                playAnimation()
            }

            delay(2000)
            with(binding.appNameTxt) {
                isVisible = true
                alphaSimpleAnimation(2000)
            }
            delay(2200)
            navigation()
        }


    }

    private fun navigation() {
        val action = SplashFragmentDirections.actionSplashFragmentToBaseFragment()
        findNavController().navigate(action)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}