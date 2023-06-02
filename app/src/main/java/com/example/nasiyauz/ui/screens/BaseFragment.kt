package com.example.nasiyauz.ui.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.GravityCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.nasiyauz.R
import com.example.nasiyauz.databinding.FragmentBaseBinding
import com.example.nasiyauz.databinding.MainViewLyBinding
import com.example.nasiyauz.data.models.MainModel
import com.example.nasiyauz.ui.adapters.MainAdapter
import com.example.nasiyauz.viewmodel.UserViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class BaseFragment : Fragment(R.layout.fragment_base) {
    private var _binding: FragmentBaseBinding? = null
    private lateinit var binding: FragmentBaseBinding
    private lateinit var mainViewLyBinding: MainViewLyBinding

    private val viewModel: UserViewModel by viewModel()
    private val navigation by lazy { findNavController() }
    private val mainAdapter: MainAdapter = MainAdapter()
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentBaseBinding.bind(view)
        mainViewLyBinding = binding.mainView
        _binding = binding

        setupDrawerLy()
        setupRecycler()
        initLiveData()
        initLoadData()

    }

    private fun initLoadData() = viewModel.run {
        getAllUsers()
    }

    private fun initLiveData() = viewModel.run {
        users.onEach { users -> mainAdapter.submitList(users) }.launchIn(lifecycleScope)
    }


    private fun setupDrawerLy() {
        mainViewLyBinding.toolbar.setNavigationOnClickListener {
            binding.drawerLy.openDrawer(GravityCompat.START, true)
        }

        binding.menuNavigation.setNavigationItemSelectedListener {
            it.isChecked = true
            binding.drawerLy.close()
            true
        }
    }

    private fun setupRecycler() = mainViewLyBinding.recyc.apply {
        layoutManager = LinearLayoutManager(requireContext())
        adapter = mainAdapter
    }

    private fun checkCurrentUser() {
        val user = Firebase.auth.currentUser
        if (user == null) {
            val action = BaseFragmentDirections.actionBaseFragmentToRegisterFragment()
            navigation.navigate(action)
        }
    }

    override fun onStart() {
        super.onStart()
        checkCurrentUser()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}