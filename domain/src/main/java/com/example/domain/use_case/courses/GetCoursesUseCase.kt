package com.example.domain.use_case.courses

import com.example.domain.model.Courses
import com.example.domain.repository.CoursesRepository
import com.example.domain.repository.TagsRepository
import com.example.domain.response.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCoursesUseCase @Inject constructor(
    private val repository: CoursesRepository,
    private val tagsRepository: TagsRepository
) {

    operator fun invoke(
        page: Int,
        language: String,
        order: String?,
        pageSize: Int,
        isPopular: Boolean?,
        filters: List<Pair<String, *>>? = null
    ): Flow<ResponseState<Courses>> = flow {
        try {
            emit(ResponseState.Loading<Courses>())
            val data = repository.getCourses(page, language, order, pageSize, isPopular)
            for (i in data.courses.indices) {
                val id = data.courses.elementAt(i).id
                data.courses.elementAt(i).reviewSummaries = repository.getReviewCourse(id)
            }
            if (filters != null) {
                for (filter in filters) {
                    when (filter.first) {
                        "tag" -> {
                            val filterValue = filter.second as String
                            val page = 1
                            var find = false
                            do {
                                var tagsData = tagsRepository.getTags(page, null, 10)
                                for (tag in tagsData.tags) {
                                    if (tag.title.contains(filterValue, true)) {
                                        find = true
                                    }
                                }
                            } while (!find)
                        }

                        "difficulty" -> {
                            val filterValue = when (filter.second as String) {
                                "Beginner" -> "easy"
                                "Advanced" -> "hard"
                                else -> "normal"
                            }
                            data.courses =
                                data.courses.filter { it.difficulty == filterValue }.toSet()
                        }

                        "price" -> {
                            val filterValue = filter.second as Boolean
                            data.courses = data.courses.filter { it.isPaid == filterValue }.toSet()
                        }
                    }
                }
            }
            emit(ResponseState.Success<Courses>(data))
        } catch (e: IOException) {
            emit(
                ResponseState.Error<Courses>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        } catch (e: Exception) {
            emit(
                ResponseState.Error<Courses>(
                    "Ошибка ${e.localizedMessage}"
                )
            )
        }
    }
}