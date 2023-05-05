package com.example.madd.Feed_Back


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madd.databinding.ActivityFeedBackUpdateBinding
import com.google.firebase.FirebaseApp
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage


class Feed_Back_Update : AppCompatActivity() {
    private lateinit var binding: ActivityFeedBackUpdateBinding
    private lateinit var firebaseStorage: FirebaseStorage
    private lateinit var databaseRef: DatabaseReference


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFeedBackUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize Firebase
        FirebaseApp.initializeApp(this)

        val key = intent.getStringExtra("key") ?: ""
        // retrieve the key from the intent
        // Log.d("Update_Agentkey", "key: ${key}")

        // get a reference to the agent with the given key
        firebaseStorage = FirebaseStorage.getInstance()
        databaseRef = FirebaseDatabase.getInstance().reference.child("Agents").child(key)

        binding.FeedBackUpdateButton.setOnClickListener {
            // Disable the button
            binding.FeedBackUpdateButton.isEnabled = false

            val name = binding.UpdateNameFeed.text.toString().trim()
            val email = binding.UpdateEmailFeed.text.toString().trim()
            val message = binding.UpdateMessageFeed.text.toString().trim()


            if (name.isEmpty() || email.isEmpty() || message.isEmpty()) {
                Toast.makeText(this, "Please fill all the fields", Toast.LENGTH_SHORT).show()
                // Enable the button
                binding.FeedBackUpdateButton.isEnabled = true
                return@setOnClickListener
            }


            // Create a hashmap to hold the updated values
            val updatedValues = HashMap<String, Any>()
            updatedValues["name"] = name
            updatedValues["email"] = email
            updatedValues["message"] = message

            // Update the child with the given key
            databaseRef.updateChildren(updatedValues).addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "feedback updated successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to update feedback", Toast.LENGTH_SHORT).show()
                    binding.FeedBackUpdateButton.isEnabled = true
                }
            }
        }
        // Delete feedback button click event
        binding.FeedDeleteButton.setOnClickListener {
            // Disable the button
            binding.FeedDeleteButton.isEnabled = false

            // Delete the child with the given key
            databaseRef.removeValue().addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    Toast.makeText(this, "Feedback deleted successfully", Toast.LENGTH_SHORT).show()
                    finish()
                } else {
                    Toast.makeText(this, "Failed to delete feedback", Toast.LENGTH_SHORT).show()
                    binding.FeedDeleteButton.isEnabled = true
                }
            }
        }
        data class Feed(
            val name: String = "", val Email: String = "",
            val message: String = ""
        )

    }
}