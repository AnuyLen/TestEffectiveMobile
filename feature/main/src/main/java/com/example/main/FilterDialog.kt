package com.example.main

import android.content.Context
import android.view.LayoutInflater
import androidx.appcompat.widget.PopupMenu
import com.example.main.databinding.FilterDialogBinding
import com.google.android.material.bottomsheet.BottomSheetDialog

object FilterDialog {
    fun showDialog(
        context: Context,
        filters: List<Pair<String, *>>?,
        setFilter: (List<Pair<String, *>>?) -> Unit
    ) {

        val dialog = BottomSheetDialog(context)
        val binding = FilterDialogBinding.inflate(LayoutInflater.from(context))

        dialog.setContentView(binding.root)
        dialog.onAttachedToWindow()
        dialog.setCancelable(true)

        var find = filters?.find { it.first == "difficulty" }?.second
        if (find != null) {
            binding.difficulty.text = find.toString()
        }
        find = filters?.find { it.first == "price" }?.second
        if (find != null) {
            binding.price.isChecked = find as Boolean
        }
        find = filters?.find { it.first == "tag" }?.second
        if (find != null) {
            binding.tag.setText(find.toString())
        }

        var filter: MutableList<Pair<String, *>> = mutableListOf()

        binding.difficulty.setOnClickListener {
            val popupMenu = PopupMenu(context, binding.difficulty)

            popupMenu.menu.add("Beginner")
            popupMenu.menu.add("Intermediate")
            popupMenu.menu.add("Advanced")

            popupMenu.setOnMenuItemClickListener { menuItem ->
                binding.difficulty.text = menuItem.title
                true
            }

            popupMenu.show()
        }


        binding.set.button.setOnClickListener {
            if (binding.tag.text.isNotEmpty()) {
                val ind = filter.indexOfFirst { it.first == "tag" }
                if (ind >= 0) {
                    filter.removeAt(ind)
                }
                filter += Pair("tag", binding.price.isChecked)
            }
            if (binding.cbPrice.isChecked) {
                val ind = filter.indexOfFirst { it.first == "price" }
                if (ind >= 0) {
                    filter.removeAt(ind)
                }
                filter.plusAssign(Pair("price", binding.price.isChecked))
            }
            if (binding.cbDifficulty.isChecked) {
                val ind = filter.indexOfFirst { it.first == "difficulty" }
                if (ind >= 0) {
                    filter.removeAt(ind)
                }
                filter.plusAssign(Pair("difficulty", binding.difficulty.text))
            }
            if (filter.isNotEmpty()) {
                setFilter(filter)
            }
            dialog.dismiss()
        }

        dialog.show()
    }
}