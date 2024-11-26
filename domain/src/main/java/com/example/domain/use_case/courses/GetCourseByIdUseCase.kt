package com.example.domain.use_case.courses

import com.example.domain.model.Course
import com.example.domain.model.Courses
import com.example.domain.repository.CoursesRepository
import com.example.domain.response.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCourseByIdUseCase @Inject constructor(private val repository: CoursesRepository) {

    operator fun invoke(id: Int): Flow<ResponseState<Courses>> = flow {
        try {
            emit(ResponseState.Loading<Courses>())
            val data = repository.getCourseById(id)
            data.courses.elementAt(0).reviewSummaries = repository.getReviewCourse(id)
            emit(ResponseState.Success<Courses>(data))
        } catch (e: IOException) {
            emit(
                ResponseState.Error<Courses>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        } catch (e: Exception){

            emit(
                ResponseState.Error<Courses>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        }
    }
}