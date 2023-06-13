package com.capstone.bangkit.cmas.ui.scan

import android.Manifest
import android.app.Dialog
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.capstone.bangkit.cmas.R
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

    }

    private fun removeImage() {
        getFile = null
        viewModel.clearScannedImage()
//        binding.apply {
//            imgPreview.setImageResource(R.drawable.ic_preview)
//        }
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
//            getFile = myFile
//            val result = rotateBitmap(
//                BitmapFactory.decodeFile(getFile?.path),
//                isBackCamera
//            )
//
//            binding.imgPreview.setImageBitmap(result)
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
    private fun happyEmo() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)

        val ivEmo = dialog.findViewById<ImageView>(R.id.iv_emo)
        val tvJudul = dialog.findViewById<TextView>(R.id.tv_judul)
        val tvDetail = dialog.findViewById<TextView>(R.id.tv_detail)
        val buttonOk = dialog.findViewById<Button>(R.id.btn_ok)

        ivEmo.setImageResource(R.drawable.happy) // Mengatur gambar sesuai dengan sumber daya yang ada
        tvJudul.text = getString(R.string.happy) // Mengatur teks judul dari string resources
        tvDetail.text = getString(R.string.happy_desc) // Mengatur teks detail dari string resources

        buttonOk.setOnClickListener {
            dialog.dismiss() // Menutup dialog saat tombol "OK" ditekan
        }

        dialog.show()
    }

    private fun scaredEmo() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)

        val ivEmo = dialog.findViewById<ImageView>(R.id.iv_emo)
        val tvJudul = dialog.findViewById<TextView>(R.id.tv_judul)
        val tvDetail = dialog.findViewById<TextView>(R.id.tv_detail)
        val buttonOk = dialog.findViewById<Button>(R.id.btn_ok)

        ivEmo.setImageResource(R.drawable.scared) // Mengatur gambar sesuai dengan sumber daya yang ada
        tvJudul.text = getString(R.string.scared) // Mengatur teks judul dari string resources
        tvDetail.text = getString(R.string.scared_desc) // Mengatur teks detail dari string resources

        buttonOk.setOnClickListener {
            dialog.dismiss() // Menutup dialog saat tombol "OK" ditekan
        }

        dialog.show()
    }

    private fun normalEmo() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)

        val ivEmo = dialog.findViewById<ImageView>(R.id.iv_emo)
        val tvJudul = dialog.findViewById<TextView>(R.id.tv_judul)
        val tvDetail = dialog.findViewById<TextView>(R.id.tv_detail)
        val buttonOk = dialog.findViewById<Button>(R.id.btn_ok)

        ivEmo.setImageResource(R.drawable.biasa_aja) // Mengatur gambar sesuai dengan sumber daya yang ada
        tvJudul.text = getString(R.string.normal) // Mengatur teks judul dari string resources
        tvDetail.text = getString(R.string.normal_desc) // Mengatur teks detail dari string resources

        buttonOk.setOnClickListener {
            dialog.dismiss() // Menutup dialog saat tombol "OK" ditekan
        }

        dialog.show()
    }

    private fun angryEmo() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)

        val ivEmo = dialog.findViewById<ImageView>(R.id.iv_emo)
        val tvJudul = dialog.findViewById<TextView>(R.id.tv_judul)
        val tvDetail = dialog.findViewById<TextView>(R.id.tv_detail)
        val buttonOk = dialog.findViewById<Button>(R.id.btn_ok)

        ivEmo.setImageResource(R.drawable.angry) // Mengatur gambar sesuai dengan sumber daya yang ada
        tvJudul.text = getString(R.string.angry) // Mengatur teks judul dari string resources
        tvDetail.text = getString(R.string.angry_desc) // Mengatur teks detail dari string resources

        buttonOk.setOnClickListener {
            dialog.dismiss() // Menutup dialog saat tombol "OK" ditekan
        }

        dialog.show()
    }

    private fun disgustEmo() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)

        val ivEmo = dialog.findViewById<ImageView>(R.id.iv_emo)
        val tvJudul = dialog.findViewById<TextView>(R.id.tv_judul)
        val tvDetail = dialog.findViewById<TextView>(R.id.tv_detail)
        val buttonOk = dialog.findViewById<Button>(R.id.btn_ok)

        ivEmo.setImageResource(R.drawable.disgusted) // Mengatur gambar sesuai dengan sumber daya yang ada
        tvJudul.text = getString(R.string.disgust) // Mengatur teks judul dari string resources
        tvDetail.text = getString(R.string.disgust_desc) // Mengatur teks detail dari string resources

        buttonOk.setOnClickListener {
            dialog.dismiss() // Menutup dialog saat tombol "OK" ditekan
        }

        dialog.show()
    }

    private fun sadEmo() {
        val dialog = Dialog(this)
        dialog.setContentView(R.layout.popup_layout)

        val ivEmo = dialog.findViewById<ImageView>(R.id.iv_emo)
        val tvJudul = dialog.findViewById<TextView>(R.id.tv_judul)
        val tvDetail = dialog.findViewById<TextView>(R.id.tv_detail)
        val buttonOk = dialog.findViewById<Button>(R.id.btn_ok)

        ivEmo.setImageResource(R.drawable.sad) // Mengatur gambar sesuai dengan sumber daya yang ada
        tvJudul.text = getString(R.string.sad) // Mengatur teks judul dari string resources
        tvDetail.text = getString(R.string.sad_desc) // Mengatur teks detail dari string resources

        buttonOk.setOnClickListener {
            dialog.dismiss() // Menutup dialog saat tombol "OK" ditekan
        }

        dialog.show()
    }

    companion object {
        const val CAMERA_X_RESULT = 200
        private val REQUIRED_PERMISSIONS = arrayOf(Manifest.permission.CAMERA)
        private const val REQUEST_CODE_PERMISSIONS = 10
    }

}