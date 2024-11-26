package com.example.login

import android.os.Bundle
import android.text.InputType
import android.text.Spannable
import android.text.SpannableString
import android.text.style.TextAppearanceSpan
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.core.net.toUri
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import com.example.brandbook.isEmailValid
import com.example.login.databinding.FragmentLoginBinding
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import android.widget.Button


@AndroidEntryPoint
class LoginFragment : Fragment() {

    private lateinit var binding: FragmentLoginBinding
    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLoginBinding.inflate(layoutInflater)

        binding.password.setLabelAndHint(
            "Пароль",
            "Введите пароль",
            ResourcesCompat.getFont(
                requireContext(),
                com.example.brandbook.R.font.roboto
            ),
            InputType.TYPE_TEXT_VARIATION_PASSWORD
        )
        val text = "Нет аккаунта? Регистрация"
        val start = text.indexOf("Регистрация")
        val spannable = SpannableString(text)
        spannable.setSpan(
            TextAppearanceSpan(context, com.example.brandbook.R.style.ButtonSmallGreen),
            start,
            text.length,
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE
        )
        binding.entrance.text = spannable

        binding.entrance.setOnClickListener {
            findNavController().navigate(R.id.action_login_to_registration)
        }

        binding.resume.button.text = resources.getString(R.string.entrance)

        binding.resume.button.setOnClickListener {
            val email = binding.email.editText.text.toString()
            if (email.isEmailValid()) {
                viewModel.signIn(
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
                binding.email.editTextLayout.error = "Проверьте email!"
            }
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            LoginFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}