package ru.netology.nmedia.viewmodel

import kotlinx.coroutines.ExperimentalCoroutinesApi
import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.netology.nmedia.auth.AppAuth
import ru.netology.nmedia.db.AppDb
import ru.netology.nmedia.model.SignState
import ru.netology.nmedia.repository.PostRepository
import ru.netology.nmedia.repository.PostRepositoryImpl

@ExperimentalCoroutinesApi
class SignInViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PostRepository =
        PostRepositoryImpl(AppDb.getInstance(context = application).postDao())

    private val _singInState = MutableLiveData<SignState>()
    val singInState: LiveData<SignState>
        get() = _singInState


    private val editedLogin = MutableLiveData<String?>()

    private val editedPass = MutableLiveData<String?>()

    fun edited(login: String, pass: String) {
        editedLogin.value = login
        editedPass.value = pass
    }


    fun uploadUser() {
        viewModelScope.launch {
            val login = editedLogin.value
            val pass = editedPass.value
            try {
                if (login != null) {
                    if (pass != null) {
                        val token = repository.authentication(login, pass)
                        AppAuth.getInstance().setAuth(token.id, token.token)
                        _singInState.value = SignState(false)
                    }
                }
            } catch (e: Exception) {
                _singInState.value = SignState(true)
            }
            editedLogin.value = null
            editedPass.value = null
        }

    }


}
