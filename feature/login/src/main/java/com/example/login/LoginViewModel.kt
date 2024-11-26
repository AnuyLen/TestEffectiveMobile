package com.example.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.response.LoadingState
import com.example.domain.response.ResponseState
import com.example.domain.use_case.authorization.RegistrationUseCase
import com.example.domain.use_case.authorization.SignInUseCase
import com.example.domain.use_case.authorization.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(
    private val signInUseCase: SignInUseCase,
    private val signOutUseCase: SignOutUseCase,
    private val registrationUseCase: RegistrationUseCase
): ViewModel() {

    private val _authResult = MutableStateFlow(LoadingState())
    val authResult: StateFlow<LoadingState> = _authResult

    fun signIn(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        signInUseCase(email, password).collect { result ->
            when (result) {
                is ResponseState.Loading -> {
                    _authResult.value = LoadingState(isLoading = true)
                }

                is ResponseState.Success -> {
                    _authResult.value = LoadingState(isCompleted = result.data)
                }

                is ResponseState.Error -> {
                    _authResult.value = LoadingState(error = result.message)
                }
            }

        }
    }

    fun signOut() = viewModelScope.launch(Dispatchers.IO) {
        signOutUseCase().collect { result ->
            when (result) {
                is ResponseState.Loading -> {
                    _authResult.value = LoadingState(isLoading = true)
                }

                is ResponseState.Success -> {
                    _authResult.value = LoadingState()
                }

                is ResponseState.Error -> {
                    _authResult.value = LoadingState(error = result.message)
                }
            }

        }
    }

    fun registration(email: String, password: String) = viewModelScope.launch(Dispatchers.IO) {
        registrationUseCase(email, password).collect { result ->
            when (result) {
                is ResponseState.Loading -> {
                    _authResult.value = LoadingState(isLoading = true)
                }

                is ResponseState.Success -> {
                    _authResult.value = LoadingState(isCompleted = result.data)
                }

                is ResponseState.Error -> {
                    _authResult.value = LoadingState(error = result.message)
                }
            }
        }
    }

}