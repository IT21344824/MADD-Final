package com.example.madd.Feed_Back

data class Feed_Back_Data(

    val name: String? = null,
    var email: String? = null,
    val message: String? = null,
    var key: String? = null  // Add key as a nullable string
) {
    constructor() : this("", "", "", null)
}


