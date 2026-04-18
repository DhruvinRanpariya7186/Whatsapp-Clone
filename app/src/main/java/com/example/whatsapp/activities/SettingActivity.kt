package com.example.whatsapp.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.whatsapp.AccountActivity
import com.example.whatsapp.NotificationActivity
import com.example.whatsapp.R
import com.example.whatsapp.databinding.ActivitySettingBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase

class SettingActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySettingBinding
    private lateinit var auth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        binding.tvSettingsUserName.text = auth.currentUser?.phoneNumber ?: "User"

        // Navigate to Account Settings
        binding.accountLayout.setOnClickListener {
            val intent = Intent(this, AccountActivity::class.java)
            startActivity(intent)
        }

        // Navigate to Notification Settings
        binding.notificationLayout.setOnClickListener {
            val intent = Intent(this, NotificationActivity::class.java)
            startActivity(intent)
        }

        // Navigate to Profile
        binding.profileClick.setOnClickListener {
            val intent = Intent(this, ProfileActivity::class.java)
            startActivity(intent)
        }

        // Invite a Friend (Share Intent)
        binding.llInviteFriend.setOnClickListener {
            val shareIntent = Intent().apply {
                action = Intent.ACTION_SEND
                type = "text/plain"
                putExtra(
                    Intent.EXTRA_TEXT,
                    "Let's chat on WhatsApp! It's a fast, simple, and secure app we can use to message and call each other for free. Get it at https://whatsapp.com/dl/"
                )
            }
            startActivity(
                Intent.createChooser(
                    shareIntent,
                    getString(R.string.share_whatsapp_text)
                )
            )
        }

        // Navigate to Help Settings
        binding.llHelpSettings.setOnClickListener {
            val helpIntent = Intent(this@SettingActivity, HelpActivity::class.java)
            startActivity(helpIntent)
        }

        // Back Arrow
        binding.ivSettingsArrow.setOnClickListener {
            val homeIntent = Intent(this@SettingActivity, HomeActivity::class.java)
            startActivity(homeIntent)
        }
    }
}
