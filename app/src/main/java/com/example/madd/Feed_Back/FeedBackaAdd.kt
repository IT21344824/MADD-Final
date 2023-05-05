package com.example.madd.Feed_Back

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.widget.Toast
import com.example.madd.databinding.ActivityFeedBackAllBinding
import com.example.madd.databinding.ActivityFeedBackaAddBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage



class FeedBackaAdd : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBackaAddBinding
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var databaseRef: DatabaseReference

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBackaAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        firebaseStorage = FirebaseStorage.getInstance()
        databaseRef = FirebaseDatabase.getInstance().getReference().child("FeedBacks")

        binding.FeedBackAddButton.setOnClickListener {
            // Disable the button
            binding.FeedBackAddButton.isEnabled = false
            val name = binding.AddNameFeed.text.toString().trim()
            val email = binding.AddEmailFeed.text.toString().trim()
            val message = binding.AddMessageFeed.text.toString().trim()

            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                // Enable the button
                binding.FeedBackAddButton.isEnabled = true
                return@setOnClickListener
            }

            val feedback = FeedBack(name, email, message)
            databaseRef.push().setValue(feedback)
                .addOnSuccessListener {
                    Toast.makeText(this, "Feedback added successfully", Toast.LENGTH_SHORT).show()
                    // Enable the button
                    binding.FeedBackAddButton.isEnabled = true
                    // Clear the form fields
                    binding.AddNameFeed.text.clear()
                    binding.AddEmailFeed.text.clear()
                    binding.AddMessageFeed.text.clear()
                }
                .addOnFailureListener {
                    Toast.makeText(this, "Failed to add feedback", Toast.LENGTH_SHORT).show()
                    // Enable the button
                    binding.FeedBackAddButton.isEnabled = true
                }
        }
    }

    data class FeedBack(val name: String = "", val email: String = "", val message: String = "")
}

