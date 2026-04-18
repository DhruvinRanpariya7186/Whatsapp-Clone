package com.example.whatsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsapp.databinding.ActivityVerifyOtpBinding
import com.google.firebase.FirebaseException
import com.google.firebase.auth.*
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.util.concurrent.TimeUnit

class VerifyOtpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityVerifyOtpBinding
    private var phoneNumberRec: String? = null
    private lateinit var auth: FirebaseAuth
    private var storedVerificationId: String? = null
    private var resendToken: PhoneAuthProvider.ForceResendingToken? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityVerifyOtpBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        // Restore verification ID if activity was recreated
        storedVerificationId = savedInstanceState?.getString("verificationId")
        
        val phoneNum: String = intent.getStringExtra("phone") ?: ""
        auth = Firebase.auth
        binding.tvVerifiedPhoneNumber.text = phoneNum
        binding.numInText.text = phoneNum
        phoneNumberRec = phoneNum
        
        // Disable button until ID is received
        binding.otpSumbitBtn.isEnabled = (storedVerificationId != null)
        
        if (storedVerificationId == null) {
            sendVerificationCode(false)
        }

        binding.otpSumbitBtn.setOnClickListener {
            val otp = binding.tvInputOtp.text.toString().trim()
            if (otp.isEmpty() || otp.length < 6) {
                binding.tvInputOtp.error = "Invalid OTP"
            } else if (storedVerificationId == null) {
                Toast.makeText(this, "Please wait, OTP is still sending...", Toast.LENGTH_SHORT).show()
            } else {
                binding.flProgressBar.visibility = View.VISIBLE
                verifyCode(otp)
            }
        }

        binding.tvResendCode.setOnClickListener {
            sendVerificationCode(true)
        }

        binding.tvCallMe.setOnClickListener {
            sendVerificationCode(true)
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("verificationId", storedVerificationId)
    }

    private val mCallbacks: PhoneAuthProvider.OnVerificationStateChangedCallbacks =
        object : PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
            override fun onCodeSent(verificationId: String, token: PhoneAuthProvider.ForceResendingToken) {
                super.onCodeSent(verificationId, token)
                storedVerificationId = verificationId
                resendToken = token
                binding.otpSumbitBtn.isEnabled = true
                binding.flProgressBar.visibility = View.GONE
                Toast.makeText(this@VerifyOtpActivity, "OTP Sent", Toast.LENGTH_SHORT).show()
            }

            override fun onVerificationCompleted(credential: PhoneAuthCredential) {
                binding.flProgressBar.visibility = View.GONE
                credential.smsCode?.let {
                    binding.tvInputOtp.setText(it)
                }
                siginTheUserByCredntials(credential)
            }

            override fun onVerificationFailed(e: FirebaseException) {
                binding.flProgressBar.visibility = View.GONE
                binding.otpSumbitBtn.isEnabled = false
                
                val message = if (e.message?.contains("quota", ignoreCase = true) == true) {
                    "Quota exceeded. Try again later."
                } else if (e is FirebaseAuthInvalidCredentialsException) {
                    "Invalid request. Check phone number."
                } else {
                    e.localizedMessage ?: "Verification failed."
                }
                
                Toast.makeText(this@VerifyOtpActivity, message, Toast.LENGTH_LONG).show()
            }
        }

    private fun verifyCode(code: String) {
        if (storedVerificationId != null) {
            val credential = PhoneAuthProvider.getCredential(storedVerificationId!!, code)
            siginTheUserByCredntials(credential)
        } else {
            Toast.makeText(this, "Verification ID missing. Please resend OTP.", Toast.LENGTH_SHORT).show()
        }
    }

    private fun siginTheUserByCredntials(credential: PhoneAuthCredential) {
        auth.signInWithCredential(credential)
            .addOnCompleteListener { task ->
                binding.flProgressBar.visibility = View.GONE
                if (task.isSuccessful) {
                    Toast.makeText(this@VerifyOtpActivity, "Verified Successfully", Toast.LENGTH_LONG).show()
                    val intent = Intent(this@VerifyOtpActivity, UserDataActivity::class.java)
                    startActivity(intent)
                    finish()
                } else {
                    Toast.makeText(this@VerifyOtpActivity, "Login Error: ${task.exception?.message}", Toast.LENGTH_LONG).show()
                }
            }
    }

    private fun sendVerificationCode(isResend: Boolean) {
        if (!phoneNumberRec.isNullOrEmpty()) {
            binding.flProgressBar.visibility = View.VISIBLE
            val builder = PhoneAuthOptions.newBuilder(auth)
                .setPhoneNumber(phoneNumberRec!!)
                .setTimeout(60L, TimeUnit.SECONDS)
                .setActivity(this)
                .setCallbacks(mCallbacks)
            
            if (isResend && resendToken != null) {
                builder.setForceResendingToken(resendToken!!)
            }
            
            PhoneAuthProvider.verifyPhoneNumber(builder.build())
        }
    }
}
