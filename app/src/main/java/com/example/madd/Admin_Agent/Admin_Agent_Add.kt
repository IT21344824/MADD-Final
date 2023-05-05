package com.example.madd.Admin_Agent

import android.content.Intent
import android.net.Uri
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import com.example.madd.R
import com.example.madd.databinding.ActivityAdminAgentAddBinding
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.FirebaseApp


class Admin_Agent_Add : AppCompatActivity() {
    private lateinit var binding: ActivityAdminAgentAddBinding
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminAgentAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        firebaseStorage = FirebaseStorage.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Agents")

        binding.AgentAdminAddButton.setOnClickListener {
            // Disable the button
            binding.AgentAdminAddButton.isEnabled = false

            val name = binding.AdminAgentAddName.text.toString().trim()
            val email = binding.AdminAgentAddEmail.text.toString().trim()
            val phone = binding.AdminAgentAddPhone.text.toString().trim()
            val descrip = binding.AdminAgentAddDescip.text.toString().trim()
            val img = selectedImageUri

            // Check if fields are empty
            if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || descrip.isEmpty() || img == null) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                // Enable the button
                binding.AgentAdminAddButton.isEnabled = true
                return@setOnClickListener
            }

            // Check if phone is a valid number
            val phoneRegex = Regex("^\\d{10}$")
            if (!phone.matches(phoneRegex)) {
                Toast.makeText(this, "Please enter a valid phone number", Toast.LENGTH_SHORT).show()
                // Enable the button
                binding.AgentAdminAddButton.isEnabled = true
                return@setOnClickListener
            }

            // Check if email is in the correct format
            val emailRegex = Regex("^\\S+@\\S+\\.\\S+\$")
            if (!email.matches(emailRegex)) {
                Toast.makeText(this, "Please enter a valid email address", Toast.LENGTH_SHORT).show()
                // Enable the button
                binding.AgentAdminAddButton.isEnabled = true
                return@setOnClickListener
            }

            // Inform the user that the upload process is ongoing
            Toast.makeText(this, "Uploading Data. Please wait...", Toast.LENGTH_LONG).show()

            // Upload image to Firebase Storage
            val imageRef = firebaseStorage.reference.child("agents/${System.currentTimeMillis()}.jpg")
            imageRef.putFile(img)
                .addOnSuccessListener {
                    imageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Create Agent instance with data and image URL
                        val agent = Agent(name, email, phone, descrip , img=uri.toString())

                        // Save Agent data to Firebase Realtime Database
                        val newRef = databaseRef.push()
                        newRef.setValue(agent)

                        Toast.makeText(this, "Agent added successfully", Toast.LENGTH_SHORT).show()
                        finish()

                        // Enable the button
                        binding.AgentAdminAddButton.isEnabled = true
                    }
                }
                .addOnFailureListener { e ->
                    Toast.makeText(this, "Failed to upload image: ${e.message}", Toast.LENGTH_SHORT).show()
                    // Enable the button
                    binding.AgentAdminAddButton.isEnabled = true
                }
        }

//
        binding.uploadimg.setOnClickListener {
            openImagePicker()
        }
    }

    private var selectedImageUri: Uri? = null

    private fun openImagePicker() {
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

    data class Agent(val name: String = "", val Email: String = "", val Phone: String = "",
                     val Description: String = "" , val img:String ="")

}
