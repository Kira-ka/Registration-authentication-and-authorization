package ru.netology.nmedia.activity

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import ru.netology.nmedia.R

import ru.netology.nmedia.databinding.FragmentSignInBinding
import ru.netology.nmedia.databinding.FragmentSignUpBinding

import ru.netology.nmedia.viewmodel.SignUpViewModel

class SignUpFragment : Fragment() {

    private val viewModel: SignUpViewModel by viewModels()

    private var fragmentBinding: FragmentSignUpBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignUpBinding.inflate(
            inflater,
            container,
            false
        )
        fragmentBinding = binding

        binding.SingUp.setOnClickListener {
            val name = binding.nameSingUp.editText?.text.toString()
            val login = binding.loginSingUp.editText?.text.toString()
            val pass = binding.passwordSingUp.editText?.text.toString()
            val confirm = binding.confirmPasswordSingUp.editText?.text.toString()
            if (confirm == pass) {
                viewModel.edited(login, pass, name)
                viewModel.registrUser()
            } else binding.confirmPasswordSingUp.error = getString(
                R.string.error
            )
        }

        viewModel.singUpState.observe(viewLifecycleOwner) {
            if (!it.error) findNavController().navigateUp() else Snackbar.make(
                binding.root, R.string.error_loading, Snackbar.LENGTH_LONG
            ).show()
        }

        return binding.root
    }

    override fun onDestroyView() {
        fragmentBinding = null
        super.onDestroyView()
    }
}
