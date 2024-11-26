package com.example.login

import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.brandbook.isEmailValid
import com.example.brandbook.isPasswordValid
import com.example.login.databinding.FragmentRegistrationBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

@AndroidEntryPoint
class RegistrationFragment : Fragment() {

    private lateinit var binding: FragmentRegistrationBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentRegistrationBinding.inflate(layoutInflater)

        binding.password.setLabelAndHint(
            "Пароль",
            "Введите пароль",
            ResourcesCompat.getFont(
                requireContext(),
                com.example.brandbook.R.font.roboto
            ),
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        )
        binding.repeatPassword.setLabelAndHint(
            "Повторить пароль",
            "Введите пароль ещё раз",
            ResourcesCompat.getFont(
                requireContext(),
                com.example.brandbook.R.font.roboto
            ),
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        )
        val text = "Уже есть аккаунт? Войти"
        val start = text.indexOf("Войти")
        val spannable = SpannableString(text)
        spannable.setSpan(
            TextAppearanceSpan(context, com.example.brandbook.R.style.ButtonSmallGreen),
            start,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.registration.text = spannable

        binding.registration.setOnClickListener {
            findNavController().navigateUp()
        }

        binding.resume.button.text = resources.getString(R.string.registration)

        binding.resume.button.setOnClickListener {

            if (binding.password.editText.text.toString().isPasswordValid(
                    binding.repeatPassword.editText.text.toString()
            )) {
                viewModel.registration(
                    binding.email.editText.text.toString(),
                    binding.password.editText.text.toString()
                )
                viewModel.authResult
                    .onEach { data ->
                        when {
                            data.isCompleted == true -> {
                                val mainUri = "test://main".toUri()
                                findNavController().navigate(
                                    mainUri,
                                    NavOptions.Builder().setPopUpTo(
                                        findNavController().graph.startDestinationId,
                                        true
                                    )
                                        .build()
                                )
                            }

                            data.error != null -> {
                                val errorSnackbar = Snackbar.make(
                                    binding.root,
                                    "",
                                    Snackbar.LENGTH_LONG
                                )
                                errorSnackbar.setText(data.error!!).show()
                                viewModel.signOut()
                            }
                        }

                    }
                    .launchIn(lifecycleScope)
            } else {
                binding.password.editTextLayout.error = "Пароли не совпадают!"
                binding.repeatPassword.editTextLayout.error = "Пароли не совпадают!"
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            RegistrationFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}