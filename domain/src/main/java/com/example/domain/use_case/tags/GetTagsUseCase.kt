package com.example.domain.use_case.Tag

import com.example.domain.model.Tag
import com.example.domain.repository.TagsRepository
import com.example.domain.response.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetTagsUseCase @Inject constructor(private val repository: TagsRepository) {

    operator fun invoke(page: Int, order: String?, pageSize: Int?): Flow<ResponseState<List<Tag>>> = flow {
        try {
            emit(ResponseState.Loading<List<Tag>>())
            val data = repository.getTags(page, order, pageSize)
            emit(ResponseState.Success<List<Tag>>(data.tags))
        } catch (e: IOException) {
            emit(
                ResponseState.Error<List<Tag>>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        } catch (e: Exception){

            emit(
                ResponseState.Error<List<Tag>>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        }
    }
}