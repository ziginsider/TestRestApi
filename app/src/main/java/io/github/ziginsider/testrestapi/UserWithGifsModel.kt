package io.github.ziginsider.testrestapi

data class UserWithGifsModel(
    val photoUrl: String,
    val name: String,
    val lastName: String,
    val gender: String,
    val email: String,
    val phone: String,
    val gifOneUrl: String,
    val gifTwoUrl: String,
    val gifTreeUrl: String
)
