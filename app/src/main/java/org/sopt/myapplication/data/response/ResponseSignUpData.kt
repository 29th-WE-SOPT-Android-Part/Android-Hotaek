package org.sopt.myapplication.data.response

data class ResponseSignUpData(
    val data: Data,
    val message: String,
    val status: Int,
    val success: Boolean
) {
    data class Data(
        val id: Int,
        val name: String,
        val email: String
    )
}