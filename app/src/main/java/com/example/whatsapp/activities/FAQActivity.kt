package com.example.whatsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.whatsapp.databinding.ActivityFAQBinding

class FAQActivity : AppCompatActivity() {
    private lateinit var binding: ActivityFAQBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFAQBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        title = "FAQ"
        binding.fAQWebView.webViewClient = WebViewClient()
        binding.fAQWebView.loadUrl("https://faq.whatsapp.com/web?lang=en")
        val webSettings = binding.fAQWebView.settings
        webSettings.javaScriptEnabled = true
    }
}
