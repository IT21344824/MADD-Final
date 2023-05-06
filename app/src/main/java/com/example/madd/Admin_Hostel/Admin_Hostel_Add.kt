package com.example.madd.Admin_Hostel

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.madd.Admin_Agent.Admin_Agent_Add
import com.example.madd.R
import com.example.madd.databinding.ActivityAdminAgentAddBinding
import com.example.madd.databinding.ActivityAdminHostelAddBinding
import com.example.madd.databinding.AdminAgentItemBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class Admin_Hostel_Add : AppCompatActivity() {
    private lateinit var binding:ActivityAdminHostelAddBinding
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?){
        super.onCreate(savedInstanceState)

        binding= ActivityAdminHostelAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        firebaseStorage = FirebaseStorage.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Hostels")

        binding.HostelAdminAddButton.setOnClickListener {

            val name = binding.AdminHosAddName.text.toString().trim()
            val phone = binding.AdminHosAddPhone.text.toString().trim()
            val descrip = binding.AdminHosAddDescip.text.toString().trim()
            val img = selectedImageUri


            if (name.isEmpty() || phone.isEmpty() || descrip.isEmpty() || img == null) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                // Enable the button
                binding.HostelAdminAddButton.isEnabled = true
                return@setOnClickListener

            }
            // Upload image to Firebase Storage
            val imageRef =
                firebaseStorage.reference.child("hostels/${System.currentTimeMillis()}.jpg")
            imageRef.putFile(img)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Create Agent instance with data and image URL
                        val hostel =
                            Hostel(name, phone, descrip, img = uri.toString())

                        // Save Agent data to Firebase Realtime Database
                        val newRef = databaseRef.push()
                        newRef.setValue(hostel)

                        Toast.makeText(this, "Hostels added successfully", Toast.LENGTH_SHORT).show()
                        finish()

                        // Enable the button
                        binding.HostelAdminAddButton.isEnabled = true
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT)
                        .show()
                    // Enable the button
                    binding.HostelAdminAddButton.isEnabled = true
                }
        }

        binding.uploadimg.setOnClickListener {
            openImagePicker()
        }
    }
    private var selectedImageUri:Uri? =null

    private fun openImagePicker(){
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }
    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            selectedImageUri = result.data?.data
            binding.uploadimg.setImageURI(selectedImageUri)
        }
    }

    data class Hostel(val name: String = "", val Phone: String = "",
                     val Description: String = "" , val img:String ="")

}
