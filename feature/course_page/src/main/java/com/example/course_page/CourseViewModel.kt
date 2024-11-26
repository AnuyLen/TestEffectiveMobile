package com.example.course_page

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.CourseEntity
import com.example.domain.model.Authors
import com.example.domain.model.Course
import com.example.domain.model.Courses
import com.example.domain.repository.CourseRoomRepository
import com.example.domain.response.DataState
import com.example.domain.response.ResponseState
import com.example.domain.use_case.authors.GetAuthorByIdUseCase
import com.example.domain.use_case.courses.GetCourseByIdUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class CourseViewModel @Inject constructor(
    private val getAuthorByIdUseCase: GetAuthorByIdUseCase,
     private val getCourseByIdUseCase: GetCourseByIdUseCase
) : ViewModel() {

    private var _authorValue = MutableStateFlow(DataState<Authors>())
    val authorValue: StateFlow<DataState<Authors>> = _authorValue

    fun getAuthorById(id: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            getAuthorByIdUseCase(id).collect { result ->
                when (result) {
                    is ResponseState.Loading -> {
                        _authorValue.value = DataState(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _authorValue.value = DataState(data = result.data)
                    }

                    is ResponseState.Error -> {
                        _authorValue.value = DataState(error = result.message)
                    }
                }
            }
        }

    private var _courseValue = MutableStateFlow(DataState<Courses>())
    val courseValue: StateFlow<DataState<Courses>> = _courseValue

    fun getCourseById(id: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            getCourseByIdUseCase(id).collect { result ->
                when (result) {
                    is ResponseState.Loading -> {
                        _courseValue.value = DataState(isLoading = true)
                    }

                    is ResponseState.Success -> {
                        _courseValue.value = DataState(data = result.data)
                    }

                    is ResponseState.Error -> {
                        _courseValue.value = DataState(error = result.message)
                    }
                }
            }
        }

}