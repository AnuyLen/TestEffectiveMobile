package com.example.course_page

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.bumptech.glide.Glide
import com.example.common.Months
import com.example.course_page.databinding.FragmentCourseBinding
import com.example.domain.ActivityViewModel
import com.example.domain.model.Author
import com.example.domain.model.Course
import com.example.domain.model.Courses
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import java.math.RoundingMode
import java.util.Calendar

@AndroidEntryPoint
class CourseFragment : Fragment() {

    private lateinit var binding: FragmentCourseBinding
    private val activityViewModel: ActivityViewModel by activityViewModels()
    private val viewModel: CourseViewModel by viewModels()
    private lateinit var course: Course

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCourseBinding.inflate(layoutInflater)
        var textButton = "Начать курс"
        binding.beginCourse.button.apply {
            text = textButton
            visibility = View.GONE
        }
        textButton = "Перейти на платформу"
        binding.goPlatform.button.apply {
            text = textButton
            backgroundTintList = resources.getColorStateList(
                com.example.brandbook.R.color.dark_gray,
                requireActivity().theme
            )
            visibility = View.GONE
        }

        binding.dateRating.ratingLayout.visibility = View.GONE


        viewModel.authorValue
            .onEach { result ->
                when {
                    result.data != null -> {
                        setVisibility(View.VISIBLE)
                        val author = result.data!!.authors.elementAt(0)
                        binding.authorName.text = author.fullName
                        course?.let { setData(it, author) }
                    }

                    result.error != null -> {
                        val errorSnackbar = Snackbar.make(
                            binding.root,
                            "",
                            Snackbar.LENGTH_LONG
                        )
                        errorSnackbar.setAction("ПОВТОРИТЬ") {
                            viewModel.courseValue.value.data?.courses?.elementAt(0)
                                ?.let { it1 ->
                                viewModel.getAuthorById(
                                    it1.owner)
                            }
                            errorSnackbar.dismiss()
                        }
                        errorSnackbar.setText(result.error!!).show()
                    }
                }
            }
            .launchIn(lifecycleScope)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val courseId = arguments?.getString("courseId")?.toInt()
        var findCourse = activityViewModel.coursesValue.value.data?.courses?.find { it.id == courseId }
        if (findCourse != null){
            course = findCourse
            viewModel.getAuthorById(findCourse.owner)
        } else {
            if (courseId != null) {
                viewModel.getCourseById(courseId)
                viewModel.courseValue
                    .onEach { result ->
                        when {
                            result.data != null -> {
                                viewModel.getAuthorById(result.data!!.courses.elementAt(0).owner)
                            }

                            result.error != null -> {
                                val errorSnackbar = Snackbar.make(
                                    binding.root,
                                    "",
                                    Snackbar.LENGTH_LONG
                                )
                                errorSnackbar.setAction("ПОВТОРИТЬ") {
                                    viewModel.getCourseById(courseId)
                                    errorSnackbar.dismiss()
                                }
                                errorSnackbar.setText(result.error!!).show()
                            }
                        }
                    }
                    .launchIn(lifecycleScope)
            } else {
                findNavController().navigateUp()
                val errorSnackbar = Snackbar.make(
                    requireView(),
                    "",
                    Snackbar.LENGTH_LONG
                )
                errorSnackbar.setText("Ошибка").show()
            }
        }


    }

    private fun setData(course: Course, author: Author) {
        setVisibility(View.VISIBLE)
        binding.authorName.text = author.fullName
        when (course.isFavorite) {
            true -> {
                binding.favorites.setImageResource(com.example.brandbook.R.drawable.bookmark_fill)
            }

            else -> {
                binding.favorites.setImageResource(com.example.brandbook.R.drawable.bookmark)
            }
        }

        Glide.with(binding.authorImage).load(author.avatar?.toUri())
            .into(binding.authorImage)

        Glide.with(binding.image).load(course.cover?.toUri())
            .into(binding.image)


        if (course.reviewSummaries != null) {
            val rating = course.reviewSummaries!!.courseReviewSummaries[0].average.toBigDecimal()
                .setScale(
                    1,
                    RoundingMode.HALF_UP
                ).toDouble().toString()
            binding.dateRating.rating.text = rating
        } else {
            binding.dateRating.ratingLayout.visibility = View.GONE
        }

        binding.dateRating.date.apply {
            if (course.createDate != null) {
                val calendar = Calendar.getInstance()
                calendar.time = course.createDate!!
                val day = calendar.get(Calendar.DAY_OF_MONTH)
                val year = calendar.get(Calendar.YEAR)
                val month = Months.entries.toTypedArray()[calendar.get(Calendar.MONTH)].value
                val date = "$day $month $year"
                text = date
            } else {
                visibility = View.GONE
            }
        }

        binding.title.text = course.title

        binding.description.apply {
            if (course.description != "") {
                text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    Html.fromHtml(course.description, Html.FROM_HTML_MODE_COMPACT)
                } else {
                    Html.fromHtml(course.description)
                }
            } else {
                visibility = View.GONE
            }
        }

        binding.goPlatform.button.setOnClickListener {
            val urlIntent = Intent(
                Intent.ACTION_VIEW,
                Uri.parse(course.canonicalUrl)
            )
            startActivity(urlIntent)
        }
        binding.favorites.setOnClickListener {
            when (course.isFavorite) {
                true -> {
                    course.isFavorite = false
                    binding.favorites.setImageResource(com.example.brandbook.R.drawable.bookmark)
                }

                else -> {
                    course.isFavorite = true
                    binding.favorites.setImageResource(com.example.brandbook.R.drawable.bookmark_fill)
                }
            }
            activityViewModel.insertCourses(course)
        }
    }


    private fun setVisibility(visible: Int){
        binding.apply {
            dateRating.ratingLayout.visibility = visible
            title.visibility = visible
            authorInfo.visibility = visible
            beginCourse.button.visibility = visible
            goPlatform.button.visibility = visible
            aboutCourse.visibility = visible
            description.visibility = visible
        }
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            CourseFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}