package com.example.adapters

import android.os.Build
import android.text.Html
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.net.toUri
import androidx.core.view.marginTop
import androidx.core.view.updateLayoutParams
import androidx.core.view.updateMargins
import androidx.navigation.NavController
import com.bumptech.glide.Glide
import com.example.brandbook.databinding.CourseItemBinding
import com.example.common.Months
import com.example.domain.model.Course
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import kotlinx.coroutines.Job
import java.math.RoundingMode
import java.util.Calendar


object CoursesDelegate {

    fun coursesDelegate(
        loadData: ((Int?) -> Unit)?,
        insertCourses: (Course) -> Job,
        navController: NavController
    ) = adapterDelegateViewBinding<Course, Course, CourseItemBinding>(
        { layoutInflater, parent -> CourseItemBinding.inflate(layoutInflater, parent, false) }
    ) {

        binding.favorites.setOnClickListener {
            when (item.isFavorite) {
                true -> {
                    item.isFavorite = false
                    binding.favorites.setImageResource(com.example.brandbook.R.drawable.bookmark)
                }

                else -> {
                    item.isFavorite = true
                    binding.favorites.setImageResource(com.example.brandbook.R.drawable.bookmark_fill)
                }
            }
            insertCourses(item)
        }

        binding.more.setOnClickListener {
            val courseUri = "test://course/${item.id}".toUri()
            navController.navigate(courseUri)
        }

        bind {

            Glide.with(binding.image).load(item.cover?.toUri())
                .into(binding.image)

            if (item.reviewSummaries != null) {
                binding.dateRating.rating.text =
                    item.reviewSummaries!!.courseReviewSummaries[0].average.toBigDecimal()
                        .setScale(
                            1,
                            RoundingMode.HALF_UP
                        ).toDouble().toString()
            } else {
                binding.dateRating.ratingLayout.visibility = View.GONE
            }

            binding.dateRating.date.apply {
                if (item.createDate != null) {
                    val calendar = Calendar.getInstance()
                    calendar.time = item.createDate!!
                    val day = calendar.get(Calendar.DAY_OF_MONTH)
                    val year = calendar.get(Calendar.YEAR)
                    val month = Months.entries.toTypedArray()[calendar.get(Calendar.MONTH)].value
                    val date = "$day $month $year"
                    text = date
                } else {
                    visibility = View.GONE
                }
            }

            binding.title.text = item.title

            binding.description.apply {
                if (item.summary != "") {
                    text = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        Html.fromHtml(item.summary, Html.FROM_HTML_MODE_COMPACT)
                    } else {
                        Html.fromHtml(item.summary)
                    }
                } else {
                    binding.more.updateLayoutParams<ConstraintLayout.LayoutParams> {
                        updateMargins(top = this@apply.marginTop)
                    }
                    visibility = View.GONE
                }
            }

            binding.price.apply {
                if (item.price != null) {
                    text = item.displayPrice
                } else {
                    visibility = View.GONE
                }
            }
            when (item.isFavorite) {
                true -> {
                    binding.favorites.setImageResource(com.example.brandbook.R.drawable.bookmark_fill)
                }

                else -> {
                    binding.favorites.setImageResource(com.example.brandbook.R.drawable.bookmark)
                }
            }
        }

        onViewRecycled {
            if (loadData != null) {
                loadData(7)
            }
        }
    }
}