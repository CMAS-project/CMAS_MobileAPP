package com.capstone.bangkit.cmas.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth

class ProfileViewModel: ViewModel() {

    private val firebaseAuth: FirebaseAuth = FirebaseAuth.getInstance()

    private val _isLoggedIn: MutableLiveData<Boolean> = MutableLiveData()
    val isLoggedIn: LiveData<Boolean> = _isLoggedIn

    init {
        checkLoggedInState()
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