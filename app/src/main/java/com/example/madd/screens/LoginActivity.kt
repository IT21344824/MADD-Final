package com.example.madd.screens

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.madd.AdminMainActivity
import com.example.madd.ClientMainActivity

import com.example.madd.R
import com.example.madd.databinding.ActivityLoginBinding
import com.example.madd.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener

class LoginActivity : AppCompatActivity() {


    private lateinit var binding: ActivityLoginBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)


        firebaseAuth = FirebaseAuth.getInstance()
        binding.textView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        binding.button.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty()) {

                firebaseAuth.signInWithEmailAndPassword(email, pass).addOnCompleteListener {
                    if (it.isSuccessful) {
                        val currentUser = firebaseAuth.currentUser
                        val uid = currentUser?.uid
                        val databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(uid!!)
                        databaseReference.addListenerForSingleValueEvent(object :
                            ValueEventListener {
                            override fun onDataChange(dataSnapshot: DataSnapshot) {
                                val userRole = dataSnapshot.child("role").value as String?
                                if (userRole == "admin") {
                                    val intent = Intent(this@LoginActivity, AdminMainActivity::class.java)
                                    startActivity(intent)
                                } else {
                                    val intent = Intent(this@LoginActivity, ClientMainActivity::class.java)
                                    startActivity(intent)
                                }
                            }

                            override fun onCancelled(databaseError: DatabaseError) {
                                Toast.makeText(
                                    this@LoginActivity,
                                    "Error: ${databaseError.message}",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        })
                    } else {
                        Toast.makeText(
                            this,
                            it.exception?.message ?: "Unknown error",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            } else {
                Toast.makeText(this, "Empty field are not allowed", Toast.LENGTH_SHORT).show()
            }

        }
    }

    override fun onStart() {
        super.onStart()

        if (firebaseAuth.currentUser != null) {
            val currentUser = firebaseAuth.currentUser
            val uid = currentUser?.uid
            val usersRef = FirebaseDatabase.getInstance().getReference("users").child(uid!!)

            usersRef.addValueEventListener(object: ValueEventListener {
                override fun onDataChange(snapshot: DataSnapshot) {
                    val role = snapshot.child("role").value.toString()

                    val intent = when (role) {
                        "admin" -> Intent(this@LoginActivity, AdminMainActivity::class.java)
                        "user" -> Intent(this@LoginActivity, ClientMainActivity::class.java)
                        else -> Intent(this@LoginActivity, AdminMainActivity::class.java)
                    }

                    startActivity(intent)
                    finish()
                }

                override fun onCancelled(error: DatabaseError) {
                    // handle error
                }
            })
        }
    }


}