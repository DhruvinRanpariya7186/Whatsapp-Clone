package com.example.whatsapp.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsapp.databinding.ActivityMainBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.hbb20.CountryCodePicker

class MainActivity : AppCompatActivity(), CountryCodePicker.OnCountryChangeListener {
    private lateinit var binding: ActivityMainBinding
    private var countryCode: String? = null
    private var countryName: String? = null
    private var verificationCode: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.countryCodePicker.setOnCountryChangeListener(this)
        binding.countryCodePicker.setDefaultCountryUsingNameCode("JP")
        FirebaseApp.initializeApp(this)
        binding.btnOtp.setOnClickListener {
            sendVerificationCode()
        }
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(p0: String, p1: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(p0, p1)
                verificationCode = p0
            }

            override fun onVerificationCompleted(p0: PhoneAuthCredential) {
                val code: String? = p0.smsCode
                if (code != null) {
                    verifyCode(code)
                }
            }

            override fun onVerificationFailed(p0: FirebaseException) {
                Toast.makeText(
                    this@MainActivity,
                    "verification failed - ${p0.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }

    private fun verifyCode(code: String) {
        val credential: PhoneAuthCredential = PhoneAuthProvider.getCredential(verificationCode.toString(),code)
    }

    private fun sendVerificationCode() {
    }

    override fun onCountrySelected() {
        countryCode = binding.countryCodePicker.selectedCountryCode
        countryName = binding.countryCodePicker.selectedCountryName

        Toast.makeText(this, "Country Code " + countryCode, Toast.LENGTH_SHORT).show()
        Toast.makeText(this, "Country Name " + countryName, Toast.LENGTH_SHORT).show()
    }
}
