package com.oliviermarteaux.shared.firebase.firestore.domain.model

import java.io.Serializable

data class Address(
    val street: String = "",
    val district: String = "",
    val city: String = "",
    val zipCode: String = "",
    val country: String = ""
): Serializable{

    // Explicit no-arg constructor for Firebase deserialization --> needed for minification
    constructor() : this(
        street = "",
        district = "",
        city = "",
        zipCode = "",
        country = ""
    )
    var fullAddress: String = ""
        get() = field.ifEmpty { computeFullAddress() }

    private fun computeFullAddress() = listOf(street, district, city, zipCode, country)
        .filter { it.isNotBlank() }
        .joinToString(", ")
}
