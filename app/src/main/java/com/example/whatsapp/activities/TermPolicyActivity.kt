package com.example.whatsapp.activities

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebViewClient
import com.example.whatsapp.databinding.ActivityTermPolicyactivityBinding

class TermPolicyActivity : AppCompatActivity() {
    private lateinit var binding: ActivityTermPolicyactivityBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityTermPolicyactivityBinding.inflate(layoutInflater)
        setContentView(binding.root)

        title = "Terms And Privacy Policy"
        binding.termPolicyWebView.webViewClient = WebViewClient()
        binding.termPolicyWebView.loadUrl("https://www.whatsapp.com/legal/?lg=en")
        val webSettings = binding.termPolicyWebView.settings
        webSettings.javaScriptEnabled = true
    }
}
