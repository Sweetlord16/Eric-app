package com.example.t_ket.presentation.login


import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.t_ket.R
import com.example.t_ket.databinding.FragmentEventInfoBinding
import com.example.t_ket.databinding.FragmentLoginBinding
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.lifecycle.HiltViewModel
@AndroidEntryPoint
class LoginFragment : Fragment() {

    private var _binding: FragmentLoginBinding? = null
    private val binding get() = _binding!!

    private val LoginViewModel by viewModels<LoginViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentLoginBinding.inflate(layoutInflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initObservers()
        initListeners()
    }

    private fun initObservers() {
        LoginViewModel.signUpState.observe(viewLifecycleOwner) { state ->
            when(state) {
                true -> {
                    // Si signUpState es true, verificamos signUpAdmin
                    LoginViewModel.signUpAdmin.observe(viewLifecycleOwner) { admin ->
                        if(admin) {
                            // Si ambos signUpState y signUpAdmin son true
                            with(binding){
                                TextToVerify.isVisible = true
                                findNavController().navigate(R.id.action_loginFragment_to_eventInfoFragment)
                                Log.d("TAG", "eres admin rey")
                            }
                        } else {
                            // Si signUpState es true pero signUpAdmin es false
                            // Aquí puedes manejar este caso si es necesario
                            with(binding){
                                TextToVerify.isVisible = true
                                findNavController().navigate(R.id.action_loginFragment_to_userInfoFragment)
                                Log.d("TAG", "No eres admin")
                            }
                        }
                    }
                }
                false -> {
                    with(binding){
                        TextToError.isVisible=true
                    }
                }
            }
        }
    }

    private fun initListeners() {
        with(binding) {
            buttonlogin.setOnClickListener {
                handleLogIn()
            }
        }
    }

    private fun handleLogIn() {
        val code = binding.etlogin.text.toString()
        LoginViewModel.signUp(code)
    }
}