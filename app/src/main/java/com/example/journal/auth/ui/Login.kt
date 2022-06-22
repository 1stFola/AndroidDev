package com.example.journal.auth.ui

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.journal.auth.events.AuthEvent
import com.example.journal.auth.viewmodels.LoginViewModel
import com.example.journal.auth.viewmodels.RegisterViewModel
import com.example.journal.data.model.LoginRequest
import com.example.journal.data.model.Status
import com.example.journal.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.example.journal.R


class Login : Fragment() {

    private lateinit var loginBinding: FragmentLoginBinding
    private val loginViewModel: LoginViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        loginBinding = FragmentLoginBinding.inflate(
            inflater,
            container,
            false
        )
        return loginBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)

        val btn = loginBinding.loginBtn
        val registerLink = loginBinding.registerLink

        val navController = findNavController()


        btn.setOnClickListener {
            val email = loginBinding.emailInput.text.toString()
            val password = loginBinding.passwordInput.text.toString()

            val request = LoginRequest(email, password)
            val event = AuthEvent.LoginEvent(request)
            Log.i("login-event", event.toString())
            loginViewModel.onEvent(event)
        }

        registerLink.setOnClickListener {
            val action = LoginDirections.actionLoginToRegister();
            navController.navigate(action)
        }

        loginViewModel.isFormValid.observe(viewLifecycleOwner) {
            it?.let { isValid ->
                if (!isValid) {
                    val snackbar = Snackbar.make (
                        loginBinding.root,
                        "Please complete all fields",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()
                }
            }
        }
        loginViewModel.status.observe(viewLifecycleOwner) {
            it?.let {
                if (it.status == Status.LOADING) {
                    btn.text = "Loading..."
                }
                if (it.status == Status.ERROR) {
                    btn.text = "Login"
                    val snackbar = Snackbar.make(
                        loginBinding.root,
                        it.message ?: "Looks like something went wrong",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.show()
                }
                if (it.status == Status.SUCCESS) {
                    val snackbar = Snackbar.make(
                        loginBinding.root,
                        "login Success",
                        Snackbar.LENGTH_SHORT
                    )
                    snackbar.setAction("Dismiss") {
                        val action = LoginDirections.actionLoginToApplicationNavigationGraph()
                        navController.navigate(action)
                    }
//                     Add a callback in case the user doesn't dismiss via our action
                    snackbar.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                val action = LoginDirections.actionLoginToApplicationNavigationGraph()
                                navController.navigate(action)
                            }
                        }
                    })
                    snackbar.show()
                }


            }
        }

    }
}