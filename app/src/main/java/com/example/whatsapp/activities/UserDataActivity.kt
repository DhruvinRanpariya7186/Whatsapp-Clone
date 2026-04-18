package com.example.whatsapp.activities

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsapp.data.Users
import com.example.whatsapp.databinding.ActivityUserDataBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase

class UserDataActivity : AppCompatActivity() {
    private lateinit var binding: ActivityUserDataBinding
    private lateinit var auth: FirebaseAuth
    private lateinit var databaseReference: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUserDataBinding.inflate(layoutInflater)
        setContentView(binding.root)

        auth = Firebase.auth
        databaseReference = FirebaseDatabase.getInstance().getReference("Users")

        val currentUser = auth.currentUser
        if (currentUser == null) {
            Toast.makeText(this, "User not authenticated", Toast.LENGTH_SHORT).show()
            startActivity(Intent(this, LogInActivity::class.java))
            finish()
            return
        }

        // Fix: Use setText() for EditText/TextInputEditText
        binding.profileNumber.setText(currentUser.phoneNumber ?: "No Number")

        binding.nextHomeBtn.setOnClickListener {
            val name = binding.profileNameUser.text.toString().trim()
            val about = binding.profileAbout.text.toString().trim()

            if (name.isEmpty()) {
                binding.profileNameUser.error = "Name is required"
                return@setOnClickListener
            }
            if (about.isEmpty()) {
                binding.profileAbout.error = "About is required"
                return@setOnClickListener
            }

            updateFirebaseDatabase(name, about)
        }
    }

    private fun updateFirebaseDatabase(name: String, about: String) {
        val userId = auth.currentUser?.uid ?: return
        
        binding.flProgressBar.visibility = View.VISIBLE
        binding.nextHomeBtn.isEnabled = false

        val user = Users(
            name,
            auth.currentUser?.phoneNumber ?: "",
            about,
            userId
        )

        databaseReference.child(userId).setValue(user)
            .addOnSuccessListener {
                binding.flProgressBar.visibility = View.GONE
                Toast.makeText(this, "Profile saved", Toast.LENGTH_SHORT).show()
                val intent = Intent(this, HomeActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(intent)
                finish()
            }
            .addOnFailureListener { e ->
                binding.flProgressBar.visibility = View.GONE
                binding.nextHomeBtn.isEnabled = true
                Toast.makeText(this, "Error: ${e.message}", Toast.LENGTH_LONG).show()
            }
    }
}
