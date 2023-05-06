package com.example.madd.Admin_Hostel

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import com.example.madd.R
import com.example.madd.databinding.ActivityAdminAgentUpdateBinding
import com.example.madd.databinding.ActivityAdminHostelUpdateBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage

class Admin_hostel_update : AppCompatActivity() {

    private lateinit var binding: ActivityAdminHostelUpdateBinding
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var databaseRef: DatabaseReference
    private var selectedImageUri: Uri? = null // Declare a variable to store the selected image URI

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= ActivityAdminHostelUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        val key = intent.getStringExtra("key") ?: ""
        // retrieve the key from the intent
        // Log.d("Update_Agentkey", "key: ${key}")

        // get a reference to the agent with the given key
        firebaseStorage = FirebaseStorage.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Hostels").child(key)

        binding.HosAdminUpdateButton.setOnClickListener {
            // Disable the button
            binding.HosAdminUpdateButton.isEnabled = false

            val name = binding.AdminHosUpdateName.text.toString().trim()
            val phone = binding.AdminHosUpdatePhone.text.toString().trim()
            val descrip = binding.AdminHosUpdateDescip.text.toString().trim()
            val img = selectedImageUri

            if (name.isEmpty() ||  phone.isEmpty() || descrip.isEmpty() || img == null) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                // Enable the button
                binding.HosAdminUpdateButton.isEnabled = true
                return@setOnClickListener
            }

            // Check if an image is selected and upload it to Firebase Storage
            if (selectedImageUri != null) {
                val storageRef = firebaseStorage.reference.child("agent_images").child(key)
                storageRef.putFile(selectedImageUri!!).addOnSuccessListener { taskSnapshot ->
                    // Get the download URL for the uploaded image
                    storageRef.downloadUrl.addOnSuccessListener { uri ->
                        // Update the agent's image URL in the database
                        //befor scuuess
                        // databaseRef.child("img").setValue(uri.toString())
                        databaseRef.child("img").setValue(uri.toString()).addOnSuccessListener {
//                            // Pass the key to Admin_Agent_single activity
//                            val intent = Intent(this, Admin_Agent_single::class.java)
                            intent.putExtra("key", key)
//                            startActivity(intent)
//                            finish()
                        }
                    }
                }
            }

            // Create a hashmap to hold the updated values
            val updatedValues = HashMap<String, Any>()
            updatedValues["name"] = name
            updatedValues["phone"] = phone
            updatedValues["description"] = descrip

            // Update the child with the given key
            databaseRef.updateChildren(updatedValues).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Hostels updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to update agent", Toast.LENGTH_SHORT).show()
                    binding.HosAdminUpdateButton.isEnabled = true
                }
            }
        }

        binding.UpdateUploadimg.setOnClickListener {
            openImagePicker()
        }
    }

    private fun openImagePicker() {
        val intent = Intent(Intent.ACTION_PICK)
        intent.type = "image/*"
        resultLauncher.launch(intent)
    }



    private val resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
        if (result.resultCode == RESULT_OK) {
            selectedImageUri = result.data?.data
            binding.UpdateUploadimg.setImageURI(selectedImageUri)
        }
    }


    data class Hostels(val name: String = "",  val Phone: String = "",
                     val Description: String = "" , val img:String ="")

}