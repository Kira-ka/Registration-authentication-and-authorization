package ru.netology.nmedia.activity

import androidx.lifecycle.ViewModelProvider
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import kotlinx.coroutines.ExperimentalCoroutinesApi
import ru.netology.nmedia.R
import ru.netology.nmedia.databinding.FragmentNewPostBinding
import ru.netology.nmedia.databinding.FragmentSignInBinding
import ru.netology.nmedia.viewmodel.PostViewModel
import ru.netology.nmedia.viewmodel.SignInViewModel

class SignInFragment : Fragment() {

    companion object {
        fun newInstance() = SignInFragment()
    }


    private val viewModel: SignInViewModel by viewModels()


    private var fragmentBinding: FragmentSignInBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val binding = FragmentSignInBinding.inflate(
            inflater,
            container,
            false
        )
        fragmentBinding = binding



        binding.getIn.setOnClickListener {
            val login = binding.login.editText?.text.toString()
            val pass = binding.password.editText?.text.toString()
            viewModel.edited(login, pass)
            viewModel.uploadUser()
        }

        viewModel.singInState.observe(viewLifecycleOwner) {
            if (!it.error) findNavController().navigateUp()  else Snackbar.make(binding.root, R.string.error_loading, Snackbar.LENGTH_LONG)
                .show()

        }

        return binding.root
    }

}
