package com.example.t_ket.presentation.login

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.t_ket.core.domain.repository.EventUseCaseRepository
import com.example.t_ket.core.domain.repository.UserUseCaseRepository
import com.example.t_ket.core.domain.usecase.AssociatedUserLoginUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val loginUseCase: UserUseCaseRepository
) : ViewModel()  {
    private val _signUpState: MutableLiveData<Boolean> = MutableLiveData()
    val signUpState: LiveData<Boolean>
        get() = _signUpState

    fun signUp(code: String) {
        viewModelScope.launch {
            var result = loginUseCase.associateUser(code)
            _signUpState.value = result
            Log.d("TAG" ,"Result: $result")
            Log.d("TAG" ,"Comms")
        }
    }

    //private fun isValidEmail(email: String): Boolean  = userInteractor.associateUser(email)

}