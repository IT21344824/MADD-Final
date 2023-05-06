package com.example.madd

object AgentsValidator {
    fun validateinput(
        email: String,
        name: String,
        phone: String,
        description: String,
        img: String
    ): Boolean {
        return !(email.isEmpty() || name.isEmpty() || phone.isEmpty() || description.isEmpty() || img.isEmpty())
    }
}
