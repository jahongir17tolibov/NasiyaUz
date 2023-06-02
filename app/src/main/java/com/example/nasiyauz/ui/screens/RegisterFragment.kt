package com.example.nasiyauz.ui.screens

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.nasiyauz.R
import com.example.nasiyauz.databinding.FragmentRegisterBinding
import com.example.nasiyauz.domain.LoginUIState
import com.example.nasiyauz.utils.BaseUtils.showSnackToast
import com.example.nasiyauz.viewmodel.RegisterViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.auth.api.signin.GoogleSignInClient
import com.google.android.gms.auth.api.signin.GoogleSignInOptions
import com.google.android.gms.tasks.Task
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import org.koin.androidx.viewmodel.ext.android.viewModel

class RegisterFragment : Fragment(R.layout.fragment_register) {
    private var _binding: FragmentRegisterBinding? = null
    private lateinit var binding: FragmentRegisterBinding

    private val navigation by lazy { findNavController() }
    private val viewModel by viewModel<RegisterViewModel>()
    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var googleSignInClient: GoogleSignInClient
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentRegisterBinding.bind(view)
        _binding = binding

        firebaseAuth = FirebaseAuth.getInstance()
        initGoogleSignIn()

        initStateFlow()
        initClicks()

    }

    private fun initStateFlow() = viewModel.authState.onEach { state ->
        when (state) {
            is LoginUIState.Success -> {
                val action = RegisterFragmentDirections.actionRegisterFragmentToBaseFragment()
                navigation.navigate(action)
                progress(false)
            }

            is LoginUIState.Loading -> progress(true)

            is LoginUIState.Error -> showSnackToast(state.message)

            else -> {}
        }
    }.launchIn(lifecycleScope)

    private fun sigInGoogle() {
        val signInIntent = googleSignInClient.signInIntent
        signInLauncher.launch(signInIntent)
    }

    private val signInLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            val task = GoogleSignIn.getSignedInAccountFromIntent(result.data)
            handleResults(task)
        }

    private fun handleResults(task: Task<GoogleSignInAccount>) {
        val account: GoogleSignInAccount? = task.result
        if (account != null) {
            viewModel.googleAuth(account.idToken!!)
        } else {
            showSnackToast(task.exception?.message)
        }
    }

    private fun initClicks() {

        binding.registerButton.setOnClickListener {
            val email = binding.emailInput.text.toString().trim()
            val pass = binding.passwordInput.text.toString().trim()
            val repass = binding.passwordReInput.text.toString().trim()

            if (checkAllFields()) {
                if (pass == repass) {
                    viewModel.register(email, pass)
                } else {
                    binding.passwordInputLy.apply {
                        error = resources.getString(R.string.parol_equal_txt)
                        isErrorEnabled = true
                        errorIconDrawable = null
                    }
                }
            }

        }

        binding.registerWithGoogleAccBtn.setOnClickListener {
            sigInGoogle()
        }

    }

    private fun initGoogleSignIn() {
        val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
            .requestIdToken(getString(R.string.default_web_client_id))
            .requestEmail()
            .build()
        googleSignInClient = GoogleSignIn.getClient(requireActivity(), gso)
    }

    private fun checkAllFields(): Boolean {
        val email = binding.emailInput.text.toString()

        if (binding.emailInput.text.toString().isBlank()) {
            binding.emailInputLy.apply {
                error = context.getString(R.string.require_field_txt)
                isErrorEnabled = true
            }
            return false
        }

        if (Patterns.EMAIL_ADDRESS.matcher(email).matches().not()) {
            binding.emailInputLy.apply {
                error = context.getString(R.string.email_format_error_txt)
                isErrorEnabled = true
            }
            return false
        }

        if (binding.passwordInput.text.toString().isBlank()) {
            binding.passwordInputLy.apply {
                error = context.getString(R.string.require_field_txt)
                isErrorEnabled = true
                errorIconDrawable = null
            }
            return false
        }

        if (binding.passwordReInput.text.toString().isBlank()) {
            binding.passwordReInputLy.apply {
                error = context.getString(R.string.require_field_txt)
                isErrorEnabled = true
                errorIconDrawable = null
            }
            return false
        }
        return true
    }

    private fun progress(bool: Boolean) {
        binding.progressIndicator.isVisible = bool
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

}