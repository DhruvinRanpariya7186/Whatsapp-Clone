package com.example.whatsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import com.example.whatsapp.databinding.ActivityLogInBinding

class LogInActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLogInBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLogInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Register the EditText with CCP for better formatting and validation
        binding.ccp.registerCarrierNumberEditText(binding.etPhoneNumber)

        binding.nextPhnBtn.setOnClickListener {
            val phoneNumber = binding.etPhoneNumber.text.toString().trim()
            
            if (phoneNumber.isEmpty()) {
                binding.etPhoneNumber.error = "Phone number required"
            } else if (!binding.ccp.isValidFullNumber) {
                binding.etPhoneNumber.error = "Invalid phone number"
            } else {
                // fullNumberWithPlus returns the number in E.164 format (e.g., +919876543210)
                val fullPhoneNumber = binding.ccp.fullNumberWithPlus
                
                binding.flProgressBarLogIn.visibility = View.VISIBLE
                
                val intent = Intent(this@LogInActivity, VerifyOtpActivity::class.java)
                intent.putExtra("phone", fullPhoneNumber)
                startActivity(intent)
                binding.flProgressBarLogIn.visibility = View.GONE
            }
        }
    }
}
