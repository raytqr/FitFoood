package com.example.fitfoood.view.foodchecker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import com.example.fitfoood.databinding.ActivityCameraBinding
import com.example.fitfoood.ml.Model
import com.example.fitfoood.utils.createCustomTempFile
import org.tensorflow.lite.DataType
import org.tensorflow.lite.support.tensorbuffer.TensorBuffer
import java.io.IOException
import java.io.InputStream
import java.nio.ByteBuffer
import java.nio.ByteOrder

class CameraActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCameraBinding
    private var cameraSelector: CameraSelector = CameraSelector.DEFAULT_BACK_CAMERA
    private var imageCapture: ImageCapture? = null

    private var currentImageUri: Uri? = null
    private lateinit var classes: Array<String>

    private val MANUAL_ENTRY_REQUEST_CODE = 1001 // Arbitrary request code for manual entry

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCameraBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadLabels()

        binding.switchCamera.setOnClickListener {
            cameraSelector = if (cameraSelector.equals(CameraSelector.DEFAULT_BACK_CAMERA)) CameraSelector.DEFAULT_FRONT_CAMERA
            else CameraSelector.DEFAULT_BACK_CAMERA
            startCamera()
        }
        binding.captureImage.setOnClickListener { takePhoto() }
        binding.galleryButton.setOnClickListener { startGallery() }
        binding.isiManual.setOnClickListener { startIsiManual() }
    }

    private fun startIsiManual() {
        val intent = Intent(this, SearchFoodActivity::class.java)
        startActivityForResult(intent, MANUAL_ENTRY_REQUEST_CODE)
    }

    public override fun onResume() {
        super.onResume()
        hideSystemUI()
        startCamera()
    }

    private fun startCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(this)

        cameraProviderFuture.addListener({
            val cameraProvider: ProcessCameraProvider = cameraProviderFuture.get()
            val preview = Preview.Builder()
                .build()
                .also {
                    it.setSurfaceProvider(binding.viewFinder.surfaceProvider)
                }

            imageCapture = ImageCapture.Builder().build()

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (exc: Exception) {
                Toast.makeText(
                    this@CameraActivity,
                    "Gagal memunculkan kamera.",
                    Toast.LENGTH_SHORT
                ).show()
                Log.e(TAG, "startCamera: ${exc.message}")
            }
        }, ContextCompat.getMainExecutor(this))
    }

    private fun takePhoto() {
        val imageCapture = imageCapture ?: return
        val photoFile = createCustomTempFile(application)
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()
        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(this),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(output: ImageCapture.OutputFileResults) {
                    val savedUri = Uri.fromFile(photoFile)
                    currentImageUri = savedUri
                    classifyImage()
                }

                override fun onError(exc: ImageCaptureException) {
                    Toast.makeText(
                        this@CameraActivity,
                        "Gagal mengambil gambar.",
                        Toast.LENGTH_SHORT
                    ).show()
                    Log.e(TAG, "onError: ${exc.message}")
                }
            }
        )
    }

    private fun classifyImage() {
        currentImageUri?.let { uri ->
            val imageStream: InputStream? = contentResolver.openInputStream(uri)
            val bitmap = BitmapFactory.decodeStream(imageStream)
            bitmap?.let { classifyImage(it) }
        }
    }

    private fun classifyImage(bitmap: Bitmap) {
        val imageSize = 224

        try {
            val model = Model.newInstance(applicationContext)

            val inputFeature0 = TensorBuffer.createFixedSize(intArrayOf(1, imageSize, imageSize, 3), DataType.FLOAT32)
            val byteBuffer = ByteBuffer.allocateDirect(4 * imageSize * imageSize * 3)
            byteBuffer.order(ByteOrder.nativeOrder())

            val resizedBitmap = Bitmap.createScaledBitmap(bitmap, imageSize, imageSize, false)
            val intValues = IntArray(imageSize * imageSize)
            resizedBitmap.getPixels(intValues, 0, resizedBitmap.width, 0, 0, resizedBitmap.width, resizedBitmap.height)

            var pixel = 0
            for (i in 0 until imageSize) {
                for (j in 0 until imageSize) {
                    val value = intValues[pixel++]
                    byteBuffer.putFloat(((value shr 16) and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat(((value shr 8) and 0xFF) * (1f / 255f))
                    byteBuffer.putFloat((value and 0xFF) * (1f / 255f))
                }
            }

            inputFeature0.loadBuffer(byteBuffer)

            val outputs = model.process(inputFeature0)
            val outputFeature0 = outputs.outputFeature0AsTensorBuffer
            val confidences = outputFeature0.floatArray

            val threshold = 0.001f
            val resultLabels = mutableListOf<String>()

            for (i in confidences.indices) {
                if (confidences[i] > threshold) {
                    resultLabels.add(classes[i])
                }
            }

            model.close()

            val intent = Intent(this, ListFoodActivity::class.java)
            intent.putStringArrayListExtra("resultLabels", ArrayList(resultLabels))
            startActivity(intent)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun hideSystemUI() {
        @Suppress("DEPRECATION")
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            window.insetsController?.hide(WindowInsets.Type.statusBars())
        } else {
            window.setFlags(
                WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN
            )
        }
        supportActionBar?.hide()
    }

    private fun startGallery() {
        launcherGallery.launch("image/*")
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.GetContent()
    ) { uri: Uri? ->
        if (uri != null) {
            currentImageUri = uri
            classifyImage()
        } else {
            Log.d("Photo Picker", "No media selected")
        }
    }

    private fun loadLabels() {
        val inputStream: InputStream = assets.open("label.txt")
        val labels = mutableListOf<String>()
        inputStream.bufferedReader().forEachLine {
            labels.add(it)
        }
        classes = labels.toTypedArray()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == MANUAL_ENTRY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
            val resultLabels = data?.getStringArrayListExtra("manualEntryLabels")
            resultLabels?.let {
                val intent = Intent(this, ListFoodActivity::class.java)
                intent.putStringArrayListExtra("resultLabels", ArrayList(resultLabels))
                startActivity(intent)
            }
        }
    }

    companion object {
        private const val TAG = "CameraActivity"
    }
}
