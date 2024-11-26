package com.example.main

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.widget.AppCompatToggleButton
import com.example.domain.model.SortOption
import com.example.domain.model.TypeSort
import com.example.main.databinding.SortDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

object SetSortingDialog {
    fun showDialog(
        context: Context,
        setTypeSort: (SortOption, Int?, Boolean) -> Unit,
        currentSortOption: SortOption
    ) {
        val option = currentSortOption.name
        val type = currentSortOption.type.name

        val dialog = BottomSheetDialog(context)
        val binding = SortDialogBinding.inflate(LayoutInflater.from(context))

        val text = "Применить"
        binding.set.button.text = text

        dialog.setContentView(binding.root)
        dialog.onAttachedToWindow()
        dialog.setCancelable(true)

        when (currentSortOption) {
            SortOption.POPULAR -> {
                binding.popular.isChecked = true
                binding.sort.isChecked = currentSortOption.type.ifType()
                binding.sort.setText("Популярные", "Не популярные")
            }

            SortOption.RATING -> {
                binding.rating.isChecked = true
                binding.sort.isChecked = currentSortOption.type.ifType()
                binding.sort.setText("По убыванию", "По возрастанию")
            }

            SortOption.DATE -> {
                binding.date.isChecked = true
                binding.sort.isChecked = currentSortOption.type.ifType()
                binding.sort.setText("От новых к старым", "От старых к новым")
            }
        }
        var sortOption = currentSortOption
        binding.buttons.setOnCheckedChangeListener { _, checkedId ->
            when (checkedId) {
                R.id.date -> {
                    sortOption = SortOption.DATE
                    sortOption.type = TypeSort.valueOf(currentSortOption.type.name)
                    binding.date.isChecked = true
                    binding.sort.setText("От новых к старым", "От старых к новым")
                }

                R.id.popular -> {
                    sortOption = SortOption.POPULAR
                    sortOption.type = TypeSort.valueOf(currentSortOption.type.name)
                    binding.popular.isChecked = true
                    binding.sort.setText("Популярные", "Не популярные")
                }

                R.id.rating -> {
                    sortOption = SortOption.RATING
                    sortOption.type = TypeSort.valueOf(currentSortOption.type.name)
                    binding.rating.isChecked = true
                    binding.sort.setText("По убыванию", "По возрастанию")
                }
            }
        }

        binding.sort.setOnCheckedChangeListener { buttonView, isChecked ->
            sortOption.type = if (isChecked) {
                TypeSort.valueOf("UP")
            } else {
                TypeSort.valueOf("DOWN")
            }
        }

        binding.set.button.setOnClickListener {
            if (option != sortOption.name || type != sortOption.type.name) {
                setTypeSort(sortOption, null, true)
            }
            dialog.dismiss()
        }

        dialog.show()
    }

    private fun AppCompatToggleButton.setText(newTextOff: String, newTextOn: String) {
        textOff = newTextOff
        textOn = newTextOn
        val checked = isChecked
        isChecked = checked
    }

    private fun TypeSort.ifType(): Boolean {
        return when (this) {
            TypeSort.UP -> true
            TypeSort.DOWN -> false
        }
    }
}