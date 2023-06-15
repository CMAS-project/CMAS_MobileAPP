package com.capstone.bangkit.cmas.ui.scan

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.capstone.bangkit.cmas.R
import com.capstone.bangkit.cmas.data.remote.retrofit.ApiConfig
import com.capstone.bangkit.cmas.databinding.ActivityScanBinding
import com.capstone.bangkit.cmas.utils.rotateBitmap
import java.io.File

class ScanActivity : AppCompatActivity() {

    private var isBackCamera: Boolean = true

    private lateinit var binding: ActivityScanBinding
    private var getFile: File? = null

    private val viewModel: ScanViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityScanBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Observe changes in scanned image
        viewModel.scannedImage.observe(this, Observer { file ->
            getFile = file
            if (getFile != null) {
                val result = rotateBitmap(
                    BitmapFactory.decodeFile(getFile?.path),
                    isBackCamera
                )
                binding.imgPreview.setImageBitmap(result)
            } else {
                binding.imgPreview.setImageResource(R.drawable.ic_preview)
            }
        })

        viewModel.isLoading.observe(this) {
            showLoading(it)
        }

        if (!allPermissionsGranted()) {
            ActivityCompat.requestPermissions(
                this,
                REQUIRED_PERMISSIONS,
                REQUEST_CODE_PERMISSIONS
            )
        }

        customToolbar()
    }

    private fun customToolbar() {

        binding.apply {
            toolbar.navBack.setOnClickListener() {
                onBackPressed()
            }
            toolbar.tvToolbarName.setText(R.string.scan)

            btnCamera.setOnClickListener { startCamera() }
            btnTrash.setOnClickListener { removeImage() }
            btnCek.setOnClickListener { ScanAction() }
        }
    }

    private fun ScanAction() {
        val apiService = ApiConfig.getApiService() // Inisialisasi objek apiService sesuai implementasi Anda
        val label = "BAHAGIA" // Ganti dengan label yang sesuai
        val value = 0.8 // Ganti dengan nilai (value) yang sesuai
        showLoading(true)

        viewModel.scanFace(apiService, label, value).observe(this) { scanResponse ->
            if (scanResponse != null) {
                showPopup(scanResponse.label)
                showLoading(false)
            } else {
                showLoading(false)
                Toast.makeText(this, "Pindai wajahmu dahulu!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun showPopup(label: String) {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)

        val ivEmo = dialog.findViewById<ImageView>(R.id.iv_emo)
        val tvJudul = dialog.findViewById<TextView>(R.id.tv_judul)
        val tvDetail = dialog.findViewById<TextView>(R.id.tv_detail)
        val buttonOk = dialog.findViewById<Button>(R.id.btn_ok)

        ivEmo.setImageResource(getEmoImageRes(label)) // Mengatur gambar sesuai dengan sumber daya yang ada
        tvJudul.text = getEmoTitle(label) // Mengatur teks judul dari string resources
        tvDetail.text = getEmoDescription(label) // Mengatur teks detail dari string resources

        buttonOk.setOnClickListener {
            dialog.dismiss() // Menutup dialog saat tombol "OK" ditekan
        }
        dialog.show()
    }

    private fun removeImage() {
        getFile = null
        viewModel.clearScannedImage()
    }

    private fun startCamera() {
        val intent = Intent(this, CameraActivity::class.java)
        launcherIntentCameraX.launch(intent)
    }

    private val launcherIntentCameraX = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) {
        if (it.resultCode == CAMERA_X_RESULT) {
            val myFile = it.data?.getSerializableExtra("picture") as File
            val isBackCamera = it.data?.getBooleanExtra("isBackCamera", true) as Boolean

            viewModel.setScannedImage(myFile)
            this.isBackCamera = isBackCamera
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE_PERMISSIONS) {
            if (!allPermissionsGranted()) {
                Toast.makeText(
                    this,
                    "Tidak mendapatkan permission.",
                    Toast.LENGTH_SHORT
                ).show()
                finish()
            }
        }
    }
    private fun allPermissionsGranted() = REQUIRED_PERMISSIONS.all {
        ContextCompat.checkSelfPermission(baseContext, it) == PackageManager.PERMISSION_GRANTED
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }


    // Emotion Pop up
    private fun getEmoImageRes(label: String): Int {
        return when (label) {
            "MARAH" -> R.drawable.angry
            "JIJIK / RISIH" -> R.drawable.disgusted
            "SEDIH" -> R.drawable.sad
            "KETAKUTAN" -> R.drawable.scared
            "SENANG / BAHAGIA", "BAHAGIA" -> R.drawable.happy
            "NORMAL / BIASA-BIASA SAJA" -> R.drawable.biasa_aja
            else -> R.drawable.confused
        }
    }

    private fun getEmoTitle(label: String): String {
        return when (label) {
            "MARAH" -> getString(R.string.angry)
            "JIJIK / RISIH" -> getString(R.string.disgust)
            "SEDIH" -> getString(R.string.sad)
            "KETAKUTAN" -> getString(R.string.scared)
            "SENANG / BAHAGIA", "BAHAGIA" -> getString(R.string.happy)
            "NORMAL / BIASA-BIASA SAJA" -> getString(R.string.normal)
            else -> getString(R.string.nothing_emo)
        }
    }

    private fun getEmoDescription(label: String): String {
        return when (label) {
            "MARAH" -> getString(R.string.angry_desc)
            "JIJIK / RISIH" -> getString(R.string.disgust_desc)
            "SEDIH" -> getString(R.string.sad_desc)
            "KETAKUTAN" -> getString(R.string.scared_desc)
            "SENANG / BAHAGIA", "BAHAGIA" -> getString(R.string.happy_desc)
            "NORMAL / BIASA-BIASA SAJA" -> getString(R.string.normal_desc)
            else -> getString(R.string.nothing_emo_desc)
        }
    }

    private fun showLoading(loading: Boolean) {
        binding.progressBar.visibility = if (loading) View.VISIBLE else View.GONE
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}