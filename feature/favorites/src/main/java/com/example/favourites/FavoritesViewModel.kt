package com.example.favourites

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.CourseEntity
import com.example.domain.repository.CourseRoomRepository
import com.example.domain.use_case.Tag.GetTagsUseCase
import com.example.domain.use_case.courses.GetCoursesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val getCoursesUseCase: GetCoursesUseCase,
//    private val getPopularCoursesUseCase: GetPopularCoursesUseCase,
    private val getTagsUseCase: GetTagsUseCase,
    private val courseRoomRepository: CourseRoomRepository

) : ViewModel() {

    private var _favouriteCourses: MutableStateFlow<List<CourseEntity>> = MutableStateFlow(emptyList())
    val favouriteCourses: StateFlow<List<CourseEntity>> = _favouriteCourses

    fun getFavoriteCourses(favorite: Boolean) = viewModelScope.launch(Dispatchers.IO) {
        _favouriteCourses.value = courseRoomRepository.findByFavorite(favorite)
    }

}