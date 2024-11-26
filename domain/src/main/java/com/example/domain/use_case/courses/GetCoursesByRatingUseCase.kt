package com.example.domain.use_case.courses

import com.example.domain.model.CourseReviewSummaries
import com.example.domain.model.Courses
import com.example.domain.model.Meta
import com.example.domain.repository.CoursesRepository
import com.example.domain.repository.TagsRepository
import com.example.domain.response.ResponseState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import java.io.IOException
import javax.inject.Inject

class GetCoursesByRatingUseCase @Inject constructor(
    private val repository: CoursesRepository,
    private val tagsRepository: TagsRepository
) {

    operator fun invoke(
        page: Int,
        order: String? = null,
        courses: Courses?,
        pageSize: Int,
        filters: List<Pair<String, *>>? = null
    ): Flow<ResponseState<Courses>> = flow {
        try {
            emit(ResponseState.Loading<Courses>())
            val coursesSummaries = repository.getReviewCourses(page, order, pageSize)
            val data: Courses = courses ?: Courses()
            for (reviewSummary in coursesSummaries.courseReviewSummaries) {
                data.courses += repository.getCourseById(reviewSummary.course).courses.elementAt(0)
                data.courses.elementAt(data.courses.size - 1).reviewSummaries =
                    CourseReviewSummaries(
                        meta = Meta(),
                        courseReviewSummaries = listOf(reviewSummary)
                    )
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

            data.meta = coursesSummaries.meta
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