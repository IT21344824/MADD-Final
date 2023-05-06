package com.example.madd

object Validator {
    fun validateinput(Email:String,name:String,message:String): Boolean {
        return !(Email.isEmpty()||  name.isEmpty() || message.isEmpty())

    }

}