package com.example.whatsapp.activities

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.whatsapp.R
import com.example.whatsapp.databinding.ActivityProfileBinding

class ProfileActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var binding: ActivityProfileBinding
    val CAMERA_REQUEST_CODE = 0
    var img:String=""
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        
        binding.profileName.setText("Shivraj")
        binding.profilrAbout.setText("Welcome ")
        binding.profilrPhonenumber.setText("8904190446")
        
        binding.camera.setOnClickListener(this)
        binding.profileNameEdit.setOnClickListener(this)
        binding.profileAboutedit.setOnClickListener(this)
        binding.profilrPhoneEdit.setOnClickListener(this)
        binding.imageProfile.setOnClickListener(this)
        binding.btnProfileBackarrow.setOnClickListener(this)
        binding.showImage.setOnClickListener(this)

    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.camera -> {
                val callCameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                if (callCameraIntent.resolveActivity(packageManager) != null) {
                    startActivityForResult(callCameraIntent, CAMERA_REQUEST_CODE)
                }

            }
            R.id.image_profile -> {
                binding.showImage.visibility = View.VISIBLE
            }
            R.id.profile_name_edit -> {
                binding.profileName.text = null
                var name = binding.profileName.text
                binding.profileName.setText(name)
            }
            R.id.profile_aboutedit -> {
                binding.profilrAbout.text = null
                var about: String = binding.profilrAbout.text.toString()
                binding.profilrAbout.setText(about)
            }
            R.id.profilr_phoneEdit -> {
                binding.profilrPhonenumber.setText("")
                var phone_number = binding.profilrPhonenumber.text
                binding.profilrPhonenumber.setText(phone_number)
            }
            R.id.btn_profile_backarrow ->{
                val intent1=Intent(this@ProfileActivity,SettingActivity::class.java)
                startActivity(intent1)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent=Intent(this,SettingActivity::class.java)
        startActivity(intent)
    }
    
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            CAMERA_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK && data != null) {
                    val bitmap = data.extras?.get("data") as Bitmap
                    binding.imageProfile.setImageBitmap(bitmap)
                    img = bitmap.toString()
                }
            }
            else -> {
                Toast.makeText(this, "Unrecognized request code", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
