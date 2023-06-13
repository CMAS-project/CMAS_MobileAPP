package com.capstone.bangkit.cmas.ui.scan

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.io.File

class ScanViewModel: ViewModel() {
    private val _scannedImage = MutableLiveData<File?>()
    val scannedImage: LiveData<File?>
        get() = _scannedImage

    fun setScannedImage(file: File?) {
        _scannedImage.value = file
    }

    private val _isBackCamera = MutableLiveData<Boolean>()
    val isBackCamera: LiveData<Boolean> = _isBackCamera

    fun setIsBackCamera(isBackCamera: Boolean) {
        _isBackCamera.value = isBackCamera
    }

    fun clearScannedImage() {
        _scannedImage.value = null
    }
}