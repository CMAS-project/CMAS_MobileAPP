package com.capstone.bangkit.cmas.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class LoginViewModel : ViewModel() {
    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedIn = _isLoggedIn

    init {
        checkLoggedInState()
    }

    fun login(email: String, password: String) {
        firebaseAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                _isLoggedIn.value = task.isSuccessful
            }
    }

    fun logout() {
        firebaseAuth.signOut()
        _isLoggedIn.value = false
    }

    private fun checkLoggedInState() {
        val user = firebaseAuth.currentUser
        _isLoggedIn.value = user != null
    }
}