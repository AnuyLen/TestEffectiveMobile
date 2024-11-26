package com.example.account

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.response.LoadingState
import com.example.domain.response.ResponseState
import com.example.domain.use_case.authorization.SignOutUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class AccountViewModel @Inject constructor(
    private val signOutUseCase: SignOutUseCase
): ViewModel() {

    private val _authResult = MutableStateFlow(LoadingState())
    val authResult: StateFlow<LoadingState> = _authResult

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
}