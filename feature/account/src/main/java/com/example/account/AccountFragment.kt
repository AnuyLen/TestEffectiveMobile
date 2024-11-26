package com.example.account

import android.app.AlertDialog
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.account.databinding.FragmentAccountBinding
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AccountFragment : Fragment() {
    private lateinit var binding: FragmentAccountBinding
    private val viewModel: AccountViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {}
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentAccountBinding.inflate(layoutInflater)

        binding.exit.setOnClickListener{
            val builder = MaterialAlertDialogBuilder(requireContext())
            builder
                .setMessage("Вы уверены, что хотите выйти?")
                .setPositiveButton("Да") { dialog, which ->
                    viewModel.signOut()
                    dialog.dismiss()
                    findNavController().popBackStack()
                    val onboardingUri = "test://onboarding".toUri()
                    findNavController().navigate(
                        onboardingUri
                    )
                }
                .setNegativeButton("Нет") { dialog, which ->
                    dialog.cancel()
                }


            val dialog = builder.create()
            dialog.show()
        }

        return binding.root
    }

    companion object {
        @JvmStatic
        fun newInstance() =
            AccountFragment().apply {
                arguments = Bundle().apply {}
            }
    }
}