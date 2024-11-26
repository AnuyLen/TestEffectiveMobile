package com.example.domain.use_case.authorization

import com.example.domain.repository.UserFirebaseRepository
import com.example.domain.response.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class SignOutUseCase @Inject constructor(private val repository: UserFirebaseRepository) {

    operator fun invoke(): Flow<ResponseState<Unit>> = flow {
        try {
            emit(ResponseState.Loading<Unit>())
            val data = repository.signOut()
            emit(ResponseState.Success<Unit>())
        } catch (e: IOException) {
            emit(
                ResponseState.Error<Unit>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        } catch (e: Exception){

            emit(
                ResponseState.Error<Unit>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        }
    }
    /*repository.signIn()*/
}