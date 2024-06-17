package com.example.fitfoood.view.signup


//class SignUpActivity : AppCompatActivity() {
//    private lateinit var binding: ActivitySignUpBinding
//    private lateinit var viewModel: SignUpViewModel
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySignUpBinding.inflate(layoutInflater)
//        viewModel = ViewModelFactory.getInstance(this).create(SignUpViewModel::class.java)
//        setContentView(binding.root)
//
//        setupView()
//        setupAction()
//    }
//
//    private fun setupView() {
//        @Suppress("DEPRECATION")
//        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
//            window.insetsController?.hide(WindowInsets.Type.statusBars())
//        } else {
//            window.setFlags(
//                WindowManager.LayoutParams.FLAG_FULLSCREEN,
//                WindowManager.LayoutParams.FLAG_FULLSCREEN
//            )
//        }
//        supportActionBar?.hide()
//    }
//
//    private fun setupAction() {
//        binding.SignUpButton.setOnClickListener {
//            val name = binding.nameEditText.text.toString()
//            val email = binding.emailEditText.text.toString()
//            val password = binding.passwordEditText.text.toString()
//            val dateOfBirth = binding.dateEditText.text.toString()
//
//            viewModel.register(name, email, password, dateOfBirth).observe(this) { result ->
//                when (result) {
//                    is ApiResponse.Error -> {
//                        Toast.makeText(
//                            this,
//                            "Terjadi kesalahan: " + result.message,
//                            Toast.LENGTH_SHORT
//                        ).show()
//                    }
//
//                    ApiResponse.Loading -> {}
//                    is ApiResponse.Success -> {
//                        if (result.data == null) {
//                            AlertDialog.Builder(this).apply {
//                                setTitle("Oops!")
//                                setMessage("Email atau password yang Anda masukkan salah.")
//                                setPositiveButton("OK") { _, _ -> }
//                                create()
//                                show()
//                            }
//                        } else {
//                            result.data.username?.let { it1 -> UserModel(it1, email) }
//                                ?.let { it2 -> viewModel.saveSession(it2) }
//                            startActivity(Intent(this, MainActivity::class.java))
//                            finish() // Finish this activity to prevent returning to login screen on back press
//                        }
//                    }
//                }
//            }
//        }
//    }
//}

//class SignupActivity : AppCompatActivity() {
//    private lateinit var binding: ActivitySignUpBinding
//    private lateinit var apiService: ApiService
//
//    override fun onCreate(savedInstanceState: Bundle?) {
//        super.onCreate(savedInstanceState)
//        binding = ActivitySignUpBinding.inflate(layoutInflater)
//        setContentView(binding.root)
//
//        // Initialize Retrofit
//        val retrofit = Retrofit.Builder()
//            .baseUrl("https://cc-fitfood-xrre4szdka-et.a.run.app/")
//            .addConverterFactory(GsonConverterFactory.create())
//            .build()
//
//        apiService = retrofit.create(ApiService::class.java)
//
//        binding.SignUpButton.setOnClickListener {
//            val name = binding.nameEditText.text.toString()
//            val email = binding.emailEditText.text.toString()
//            val password = binding.passwordEditText.text.toString()
//            val dateOfBirth = binding.dateEditText.text.toString()
//
//            // Validate input
//            if (name.isBlank() || email.isBlank() || password.isBlank() || dateOfBirth.isBlank()) {
//                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
//                return@setOnClickListener
//            }
//
//            // Perform registration request
//            val registerCall = apiService.register(RegisterRequest(name, email, password, dateOfBirth))
//            registerCall.enqueue(object : Callback<Void> {
//                override fun onResponse(call: Call<Void>, response: Response<Void>) {
//                    if (response.isSuccessful) {
//                        // Handle successful registration, navigate to login
//                        startActivity(Intent(this@SignupActivity, LoginActivity::class.java))
//                        finish()
//                    } else {
//                        val errorMessage = response.errorBody()?.string()
//                        Log.e("SignUpActivity", "Registration failed: $errorMessage")
//                        Toast.makeText(this@SignupActivity, "Registration failed: $errorMessage", Toast.LENGTH_SHORT).show()
//                    }
//                }
//
//                override fun onFailure(call: Call<Void>, t: Throwable) {
//                    Log.e("SignUpActivity", "Error: ${t.message}")
//                    Toast.makeText(this@SignupActivity, "Error: ${t.message}", Toast.LENGTH_SHORT).show()
//                }
//            })
//        }
//
//        binding.LoginTextView.setOnClickListener {
//            startActivity(Intent(this, LoginActivity::class.java))
//            finish()
//        }
//
//        val genderRadioGroup = findViewById<RadioGroup>(R.id.genderRadioGroup)
//        genderRadioGroup.setOnCheckedChangeListener { _, checkedId ->
//            when (checkedId) {
//                R.id.radioMan -> {
//                    // Handle 'Man' selection
//                }
//                R.id.radioWoman -> {
//                    // Handle 'Woman' selection
//                }
//            }
//        }
//
//        binding.dateEditText.setOnClickListener {
//            showDatePickerDialog()
//        }
//
//    }
//
//    private fun showDatePickerDialog() {
//        val calendar = Calendar.getInstance()
//        val dateSetListener = DatePickerDialog.OnDateSetListener { _, year, month, dayOfMonth ->
//            calendar.set(Calendar.YEAR, year)
//            calendar.set(Calendar.MONTH, month)
//            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
//            updateDateEditText(calendar)
//        }
//
//        DatePickerDialog(
//            this,
//            dateSetListener,
//            calendar.get(Calendar.YEAR),
//            calendar.get(Calendar.MONTH),
//            calendar.get(Calendar.DAY_OF_MONTH)
//        ).show()
//    }
//
//    private fun updateDateEditText(calendar: Calendar) {
//        val sdf = SimpleDateFormat("dd-MM-yyyy", Locale.US)
//        binding.dateEditText.setText(sdf.format(calendar.time))
//    }
//
//
//}
