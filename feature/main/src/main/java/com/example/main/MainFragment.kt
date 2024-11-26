package com.example.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.adapters.CoursesDelegate
import com.example.domain.ActivityViewModel
import com.example.domain.model.Course
import com.example.domain.model.SortOption
import com.example.domain.model.TypeSort
import com.example.main.databinding.FragmentMainBinding
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach


@AndroidEntryPoint
class MainFragment : Fragment() {

    private lateinit var binding: FragmentMainBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: MainViewModel by viewModels()
    private lateinit var adapterCourses: ListDelegationAdapter<List<Course>>
    private var sortTypeIsChanged = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentMainBinding.inflate(layoutInflater)

        val text = when (activityViewModel.sortType) {
            SortOption.POPULAR -> {
                "По популярности"
            }

            SortOption.RATING -> {
                "По рейтингу"
            }

            SortOption.DATE -> {
                "По дате добавления"
            }
        }
        binding.toolbar.sort.text = text

        if (activityViewModel.coursesValue.value.data != null) {
            viewModel.updateCourses(activityViewModel.coursesValue.value)
        }

        adapterCourses = ListDelegationAdapter(
            CoursesDelegate.coursesDelegate(
                ::getCoursesSorted,
                activityViewModel::insertCourses,
                findNavController()
            )
        )
        binding.courses.adapter = adapterCourses

        binding.toolbar.sort.setOnClickListener {
            val sort = activityViewModel.sortType
            SetSortingDialog.showDialog(requireContext(), ::setTypeSort, sort)
        }

        viewModel.getFavoriteCourses(true)

        getCoursesSorted()
        viewModel.dataCourses
            .onEach { result ->
                when {
                    result.data != null -> {
                        if (result.data!!.courses.isNotEmpty()) {
                            adapterCourses.apply {
                                items = result.data!!.courses.toList()
                                notifyItemChanged(id)
                            }
                            activityViewModel.coursesValue.value = result
                            if (activityViewModel.coursesValue.value.data?.courses?.size
                                ?.compareTo(3)!! <= 0
                            ) {
                                getCoursesSorted()
                            }
                        }
//                        else {
//                            getCoursesSorted()
//                        }
                    }

                    result.error != null -> {
                        val errorSnackbar = Snackbar.make(
                            binding.root,
                            "",
                            Snackbar.LENGTH_LONG
                        ).setAction("ПОВТОРИТЬ") {
                            getCoursesSorted()
                        }
                        errorSnackbar.setText(result.error!!).show()
                    }
                }
            }
            .launchIn(lifecycleScope)

        binding.toolbar.filter.setOnClickListener {
            FilterDialog.showDialog(requireContext(), activityViewModel.filter, ::setFilter)

        }

        return binding.root
    }

    private fun setTypeSort(sortOption: SortOption, page: Int? = null, sortChanged: Boolean) {
        sortTypeIsChanged = sortChanged
        if (sortChanged) {
            activityViewModel.sortType = sortOption
            val text = when (sortOption) {
                SortOption.POPULAR -> {
                    "По популярности"
                }

                SortOption.RATING -> {
                    "По рейтингу"
                }

                SortOption.DATE -> {
                    "По дате добавления"
                }
            }
            viewModel.clearCourses()
//            val itemCount = adapterCourses.itemCount
//            adapterCourses.items = viewModel.coursesValue.value.data?.courses?.toList()
//            adapterCourses.notifyItemRangeRemoved(0, itemCount)
            binding.toolbar.sort.text = text
//            getCoursesSorted()
        }
    }

    private fun setFilter(filter: List<Pair<String, *>>? = null){
        if (filter != null) {
            activityViewModel.filter = filter
            viewModel.clearCourses()
//            val itemCount = adapterCourses.itemCount
//            adapterCourses.items = viewModel.coursesValue.value.data?.courses?.toList()
//            adapterCourses.notifyItemRangeRemoved(0, itemCount)
            getCoursesSorted()
        }
    }

    private fun getCoursesSorted(pageSize: Int? = null) {
        var page: Int? = 1
        if (sortTypeIsChanged) {
            page = null
            sortTypeIsChanged = false
        }
        when (activityViewModel.sortType) {
            SortOption.POPULAR -> {
                val isPopular = activityViewModel.sortType.type == TypeSort.DOWN
                if (activityViewModel.filter != null) {
                    if (pageSize != null) {
                        viewModel.getCourses(
                            isPopular = isPopular,
                            pageSize = pageSize,
                            page = page,
                            filter = activityViewModel.filter
                        )
                    } else {
                        viewModel.getCourses(
                            isPopular = isPopular, page = page,
                            filter = activityViewModel.filter
                        )
                    }
                } else {
                    if (pageSize != null) {
                        viewModel.getCourses(
                            isPopular = isPopular,
                            pageSize = pageSize,
                            page = page
                        )
                    } else {
                        viewModel.getCourses(isPopular = isPopular, page = page)
                    }
                }
            }

            SortOption.RATING -> {
                val rating = if (activityViewModel.sortType.type == TypeSort.DOWN) {
                    "-average"
                } else {
                    "average"
                }
                if (activityViewModel.filter != null) {
                    viewModel.getCoursesByRating(order = rating, page = page, filter = activityViewModel.filter)
                } else {
                    viewModel.getCoursesByRating(order = rating, page = page)
                }
            }

            SortOption.DATE -> {
                val order = if (activityViewModel.sortType.type == TypeSort.DOWN) {
                    "-create_date"
                } else {
                    "create_date"
                }
                if (activityViewModel.filter != null) {
                    if (pageSize != null) {
                        viewModel.getCourses(order = order, pageSize = pageSize, page = page, filter = activityViewModel.filter)
                    } else {
                        viewModel.getCourses(order = order, page = page, filter = activityViewModel.filter)
                    }
                } else {
                    if (pageSize != null) {
                        viewModel.getCourses(order = order, pageSize = pageSize, page = page)
                    } else {
                        viewModel.getCourses(order = order, page = page)
                    }
                }
            }
        }
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            MainFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}