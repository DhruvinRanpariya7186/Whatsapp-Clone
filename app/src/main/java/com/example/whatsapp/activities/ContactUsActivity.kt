package com.example.whatsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.whatsapp.databinding.ActivityContactUsBinding

class ContactUsActivity : AppCompatActivity() {
    private lateinit var binding: ActivityContactUsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityContactUsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Contact Us"
        binding.contactUsWebView.webViewClient = WebViewClient()
        binding.contactUsWebView.loadUrl("https://web.whatsapp.com/")
        val webSettings = binding.contactUsWebView.settings
        webSettings.javaScriptEnabled = true
    }
}
