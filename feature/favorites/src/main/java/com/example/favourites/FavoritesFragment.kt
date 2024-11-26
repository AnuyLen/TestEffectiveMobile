package com.example.favourites

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.example.adapters.CoursesDelegate
import com.example.domain.ActivityViewModel
import com.example.domain.model.Course
import com.example.domain.model.SortOption
import com.example.domain.model.TypeSort
import com.example.favourites.databinding.FragmentFavoritesBinding
import com.google.android.material.snackbar.Snackbar
import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class FavoritesFragment : Fragment() {

    private lateinit var binding: FragmentFavoritesBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: FavoritesViewModel by viewModels()
    private lateinit var adapterCourses: ListDelegationAdapter<List<Course>>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentFavoritesBinding.inflate(layoutInflater)

        adapterCourses = ListDelegationAdapter(
            CoursesDelegate.coursesDelegate(
                null,
                activityViewModel::insertCourses,
                findNavController()
            )
        )
        binding.courses.adapter = adapterCourses

        viewModel.getFavoriteCourses(true)
        viewModel.favouriteCourses
            .onEach { result ->
                when {
                    result.isNotEmpty() -> {
                        val courses: MutableList<Course> = mutableListOf()
                        for (i in result) {
                            courses += i.toCourse()
                        }
                        adapterCourses.apply {
                            items = courses
                            notifyItemChanged(id)
                        }
//                            activityViewModel.coursesValue.value = result
                    }
                }
            }
            .launchIn(lifecycleScope)

        return binding.root
    }


    companion object {
        @JvmStatic
        fun newInstance() =
            FavoritesFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}