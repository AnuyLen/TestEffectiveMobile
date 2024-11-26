package com.example.login

import android.graphics.Typeface
import android.text.InputType
import com.example.login.databinding.InputLayoutBinding

fun InputLayoutBinding.setLabelAndHint(
    labelText: String,
    hintText: String,
    typeface: Typeface?,
    inputType: Int? = null
) {
    label.text = labelText
    editText.hint = hintText


    inputType?.let {
        editText.setInputType(InputType.TYPE_CLASS_TEXT or it)
    }
    editText.setTypeface(typeface)
}