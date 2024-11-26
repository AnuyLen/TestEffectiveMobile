package com.example.domain

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.entity.CourseEntity
import com.example.domain.model.Course
import com.example.domain.model.Courses
import com.example.domain.model.SortOption
import com.example.domain.repository.CourseRoomRepository
import com.example.domain.response.DataState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ActivityViewModel @Inject constructor(
    private val courseRoomRepository: CourseRoomRepository
): ViewModel() {

    var coursesValue: MutableStateFlow<DataState<Courses>> = MutableStateFlow(DataState())

    var filter: List<Pair<String, *>>? = null

    var sortType: SortOption = SortOption.POPULAR

    fun insertCourses(vararg insCourse: Course) = viewModelScope.launch(Dispatchers.IO) {
        var courses: Array<CourseEntity> = emptyArray()
        for (i in insCourse.indices){
            courses += insCourse[i].toCourseEntity()
        }
        courseRoomRepository.insertAll(*courses)
    }
//
//    fun getFavoriteCourses(favorite: Boolean) = viewModelScope.launch(Dispatchers.IO) {
//            _favouriteCourses.value = courseRoomRepository.findByFavorite(favorite)
//        }

}