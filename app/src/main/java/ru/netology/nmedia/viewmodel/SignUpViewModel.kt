package ru.netology.nmedia.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.netology.nmedia.api.PostsApi
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.error.ApiError
import ru.netology.nmedia.model.SignState
import ru.netology.nmedia.repository.PostRepository

class SignUpViewModel : ViewModel() {

    private val _singUpState = MutableLiveData<SignState>()
    val singUpState: LiveData<SignState>
        get() = _singUpState


    private val editedLogin = MutableLiveData<String?>()

    private val editedPass = MutableLiveData<String?>()

    private val editedName = MutableLiveData<String?>()

    fun edited(login: String, pass: String, name: String) {
        editedLogin.value = login
        editedPass.value = pass
        editedName.value = name
    }

    fun registrUser() {
        viewModelScope.launch {
            val login = editedLogin.value
            val pass = editedPass.value
            val name = editedName.value
            try {
                if (login != null) {
                    if (pass != null) {
                        if (name != null) {
                            val response = PostsApi.service.registerUser(login, pass, name)
                            val body = response.body() ?: throw ApiError(response.code(), response.message())
                            AppAuth.getInstance().setAuth(body.id, body.token)
                            _singUpState.value = SignState(false)
                        }
                    }
                }
            } catch (e: Exception) {
                _singUpState.value = SignState(true)
            }
            editedLogin.value = null
            editedPass.value = null
        }

    }
}
