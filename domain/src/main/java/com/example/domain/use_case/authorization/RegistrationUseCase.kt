package com.example.domain.use_case.authorization

import com.example.domain.repository.UserFirebaseRepository
import com.example.domain.response.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class RegistrationUseCase @Inject constructor(private val repository: UserFirebaseRepository) {

    operator fun invoke(email: String, password: String): Flow<ResponseState<Boolean>> = flow {
        try {
            emit(ResponseState.Loading<Boolean>())
            val data = repository.registration(email, password)
            emit(ResponseState.Success<Boolean>(data))
        } catch (e: IOException) {
            emit(
                ResponseState.Error<Boolean>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        } catch (e: Exception){

            emit(
                ResponseState.Error<Boolean>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        }
    }
    /*repository.signIn()*/
}