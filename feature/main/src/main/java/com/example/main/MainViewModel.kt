package com.example.main

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.CourseEntity
import com.example.domain.model.Courses
import com.example.domain.model.Tag
import com.example.domain.repository.CourseRoomRepository
import com.example.domain.response.DataState
import com.example.domain.response.ResponseState
import com.example.domain.use_case.Tag.GetTagsUseCase
import com.example.domain.use_case.courses.GetCoursesByRatingUseCase
import com.example.domain.use_case.courses.GetCoursesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    private val courseRoomRepository: CourseRoomRepository,
    private val getCoursesByRatingUseCase: GetCoursesByRatingUseCase
) : ViewModel() {

    private var _coursesValue = MutableStateFlow(DataState<Courses>())
    val coursesValue: StateFlow<DataState<Courses>> = _coursesValue

    private var _favouriteCourses: MutableStateFlow<List<CourseEntity>> =
        MutableStateFlow(emptyList())
    val favouriteCourses: StateFlow<List<CourseEntity>> = _favouriteCourses

    val dataCourses: StateFlow<DataState<Courses>> =
        combine(coursesValue, favouriteCourses) { dataState, vacanciesFavourite ->
            if (dataState.data != null && vacanciesFavourite.isNotEmpty()) {
                for (i in vacanciesFavourite.iterator()) {
                    val ind = dataState.data!!.courses.indexOfFirst { i.id == it.id }
                    if (ind >= 0) {
                        dataState.data!!.courses.elementAt(ind).isFavorite = i.isFavorite
                    }
                }
            }
            dataState
        }.stateIn(viewModelScope, SharingStarted.Lazily, DataState())

    private var lastPage = 0


    fun clearCourses() {
        _coursesValue.value = DataState()
    }

    fun updateCourses(data: DataState<Courses>) {
        _coursesValue.value = data
    }

    fun getCourses(
        page: Int? = 1,
        language: String = "ru",
        order: String? = null,
        pageSize: Int = 3,
        isPopular: Boolean? = null,
        filter: List<Pair<String, *>>? = null
    ) = viewModelScope.launch(Dispatchers.IO) {
        lastPage = if (page != null && page != 1) {
            page
        } else if (page == null) {
            1
        } else {
            lastPage + 1
        }
        getCoursesUseCase(lastPage, language, order, pageSize, isPopular, filter).collect { result ->
            when (result) {
                is ResponseState.Loading -> {
                    _coursesValue.value =
                        DataState(isLoading = true, data = _coursesValue.value.data)
                }

                is ResponseState.Success -> {
                    if (coursesValue.value.data?.courses?.isNotEmpty() == true) {
                        if (result.data?.courses?.isNotEmpty() == true) {
                            val newData = _coursesValue.value.data?.courses!!.plus(
                                result.data!!.courses
                            )
                            val courses =
                                Courses(result.data!!.meta, newData, result.data!!.enrollments)
                            _coursesValue.value = DataState(data = courses)
                        } else {
                            _coursesValue.value = DataState(data = result.data)
                        }

                    } else {
                        _coursesValue.value = DataState(data = result.data)
                    }
                }

                is ResponseState.Error -> {
                    _coursesValue.value = DataState(error = result.message)
                }
            }
        }
    }

    fun getCoursesByRating(
        page: Int? = 1,
        order: String? = null,
        pageSize: Int = 10,
        filter: List<Pair<String, *>>? = null
    ) = viewModelScope.launch(Dispatchers.IO) {
        lastPage = if (page != null && page != 1) {
            page
        } else if (page == null) {
            1
        } else {
            lastPage + 1
        }
        getCoursesByRatingUseCase(
            lastPage,
            order,
            coursesValue.value.data,
            pageSize,
            filter
        ).collect { result ->
            when (result) {
                is ResponseState.Loading -> {
                    _coursesValue.value =
                        DataState(isLoading = true, data = _coursesValue.value.data)
                }

                is ResponseState.Success -> {
                    if (coursesValue.value.data?.courses?.isNotEmpty() == true) {
                        if (result.data?.courses?.isNotEmpty() == true) {
                            val newData = _coursesValue.value.data?.courses!!.plus(
                                result.data!!.courses
                            )
                            val courses =
                                Courses(result.data!!.meta, newData, result.data!!.enrollments)
                            _coursesValue.value = DataState(data = courses)
                        }

                    } else {
                        _coursesValue.value = DataState(data = result.data)
                    }
                }

                is ResponseState.Error -> {
                    _coursesValue.value = DataState(error = result.message)
                }
            }
        }
    }

    private val _tagsValue = MutableStateFlow(DataState<List<Tag>>())
    val tagsValue: StateFlow<DataState<List<Tag>>> = _tagsValue

    fun getTags(
        page: Int = 1,
        order: String? = null,
        pageSize: Int? = null
    ) = viewModelScope.launch(Dispatchers.IO) {
        getTagsUseCase(page, order, pageSize).collect { result ->
            when (result) {
                is ResponseState.Loading -> {
                    _tagsValue.value = DataState(isLoading = true)
                }

                is ResponseState.Success -> {
                    _tagsValue.value = DataState(data = result.data)
                }

                is ResponseState.Error -> {
                    _tagsValue.value = DataState(error = result.message)
                }
            }
        }
    }


    fun getFavoriteCourses(favorite: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        _favouriteCourses.value = courseRoomRepository.findByFavorite(favorite)
    }

}