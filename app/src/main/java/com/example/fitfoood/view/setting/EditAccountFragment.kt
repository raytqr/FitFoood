package com.example.fitfoood.view.setting

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Base64
import android.widget.Toast
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.lifecycle.ViewModelProvider
import com.example.fitfoood.R
import com.example.fitfoood.databinding.FragmentEditAccountBinding
import com.example.fitfoood.view.ViewModelFactory
import com.example.fitfoood.view.main.AccountViewModel
import com.google.android.material.datepicker.MaterialDatePicker
import java.io.ByteArrayOutputStream
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class EditAccountFragment : Fragment() {
    private var _binding: FragmentEditAccountBinding? = null

    private lateinit var accountViewModel: AccountViewModel
    private val binding get() = _binding!!

    private val REQUEST_CAMERA = 1
    private val REQUEST_GALLERY = 2

    private var selectedImageUri: Uri? = null
    private var currentBitmap: Bitmap? = null
    private lateinit var token: String

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditAccountBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val tbTitle = view.findViewById<TextView>(R.id.title_toolbar)
        tbTitle.text = getString(R.string.account_edit)

        binding.toolbar.setOnClickListener {
            activity?.onBackPressed()
        }
        if(ContextCompat.checkSelfPermission(requireContext(), android.Manifest.permission.CAMERA) != android.content.pm.PackageManager.PERMISSION_GRANTED) {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA), 1)
        }
        accountViewModel = ViewModelProvider(this, ViewModelFactory.getInstance(requireContext())).get(AccountViewModel::class.java)
        accountViewModel.getSession().observe(viewLifecycleOwner) { user ->
            token = user.token
            val dateOfBirth = user.dateOfBirth
            binding.dumbAge.text = calculateAge(dateOfBirth)
        }
        observeViewModel()



        binding.captureImage.setOnClickListener {
            showPhotoDialog()
        }

        binding.btnSave.setOnClickListener {
            currentBitmap?.let { bitmap ->
                saveProfilePicture(bitmap)
                Toast.makeText(requireContext(), "Changes saved", Toast.LENGTH_SHORT).show()
            }
        }

        // Load the saved profile picture
        loadProfilePicture()
    }
    private fun observeViewModel() {
        accountViewModel.getSession().observe(viewLifecycleOwner) { userModel ->
            binding.dumbName.text = userModel.username
            binding.dumbEmail.text = userModel.email
        }
    }

    private fun calculateAge(dateOfBirth: String): String {
        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
        val dob = sdf.parse(dateOfBirth)
        val today = Calendar.getInstance()

        val dobCalendar = Calendar.getInstance().apply { time = dob }
        var age = today.get(Calendar.YEAR) - dobCalendar.get(Calendar.YEAR)

        if (today.get(Calendar.DAY_OF_YEAR) < dobCalendar.get(Calendar.DAY_OF_YEAR)) {
            age--
        }

        return age.toString()
    }

    private fun loadProfilePicture() {
        val sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val encodedImage = sharedPreferences.getString("profile_picture", null)
        if (encodedImage != null) {
            val byteArray = Base64.decode(encodedImage, Base64.DEFAULT)
            val bitmap = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
            binding.photoProfile.setImageBitmap(bitmap)
            currentBitmap = bitmap
        }
    }

    private fun showPhotoDialog() {
        val options = arrayOf("Camera", "Gallery")
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle("Choose a source")
        builder.setItems(options) { _, which ->
            when (which) {
                0 -> takePhotoFromCamera()
                1 -> choosePhotoFromGallery()
            }
        }
        builder.show()
    }

    private fun takePhotoFromCamera() {
        val intent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        startActivityForResult(intent, REQUEST_CAMERA)
    }

    private fun choosePhotoFromGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, REQUEST_GALLERY)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CAMERA -> {
                    val imageBitmap = data?.extras?.get("data") as Bitmap
                    val circularBitmap = getCircularBitmap(imageBitmap)
                    binding.photoProfile.setImageBitmap(circularBitmap)
                    selectedImageUri = null // Clear Uri after setting image
                    currentBitmap = circularBitmap
                }

                REQUEST_GALLERY -> {
                    selectedImageUri = data?.data
                    if (selectedImageUri != null) {
                        // Handle valid Uri
                        val bitmap = MediaStore.Images.Media.getBitmap(
                            requireContext().contentResolver,
                            selectedImageUri
                        )
                        val circularBitmap = getCircularBitmap(bitmap)
                        binding.photoProfile.setImageBitmap(circularBitmap)
                        currentBitmap = circularBitmap
                    } else {
                        // Handle invalid Uri (optional)
                    }
                }
            }
        }
    }

    private fun saveProfilePicture(bitmap: Bitmap) {
        val sharedPreferences = requireContext().getSharedPreferences("ProfilePrefs", Context.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        val byteArrayOutputStream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream)
        val byteArray = byteArrayOutputStream.toByteArray()
        val encodedImage = Base64.encodeToString(byteArray, Base64.DEFAULT)
        editor.putString("profile_picture", encodedImage)
        editor.apply()
    }

    private fun getCircularBitmap(bitmap: Bitmap): Bitmap {
        val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
        val canvas = Canvas(output)

        val paint = Paint()
        val rect = Rect(0, 0, bitmap.width, bitmap.height)

        paint.isAntiAlias = true
        canvas.drawARGB(0, 0, 0, 0)
        canvas.drawCircle(
            (bitmap.width / 2).toFloat(),
            (bitmap.height / 2).toFloat(),
            (bitmap.width / 2).toFloat(),
            paint
        )
        paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
        canvas.drawBitmap(bitmap, rect, rect, paint)

        return output
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
