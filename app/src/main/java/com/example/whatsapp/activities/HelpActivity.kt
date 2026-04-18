package com.example.whatsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whatsapp.databinding.ActivityHelpBinding

class HelpActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHelpBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHelpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.ivHelpArrow.setOnClickListener {
            val settingsIntent = Intent(this@HelpActivity, SettingActivity::class.java)
            startActivity(settingsIntent)
        }

        binding.llFaq.setOnClickListener {
            val fAQIntent = Intent(this@HelpActivity, FAQActivity::class.java)
            startActivity(fAQIntent)
        }
        
        binding.llContactUs.setOnClickListener {
            val contactIntent = Intent(this@HelpActivity, ContactUsActivity::class.java)
            startActivity(contactIntent)
        }
        
        binding.llTermsAndPrivacy.setOnClickListener {
            val termsIntent = Intent(this@HelpActivity, TermPolicyActivity::class.java)
            startActivity(termsIntent)
        }
    }
}
