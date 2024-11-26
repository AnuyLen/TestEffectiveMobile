package com.example.domain.use_case.authors

import com.example.domain.model.Authors
import com.example.domain.repository.AuthorsRepository
import com.example.domain.response.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetAuthorByIdUseCase @Inject constructor(private val repository: AuthorsRepository) {

    operator fun invoke(id: Int): Flow<ResponseState<Authors>> = flow {
        try {
            emit(ResponseState.Loading<Authors>())
            val data = repository.getAuthorById(id)
            emit(ResponseState.Success<Authors>(data))
        } catch (e: IOException) {
            emit(
                ResponseState.Error<Authors>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        } catch (e: Exception){

            emit(
                ResponseState.Error<Authors>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        }
    }
}