package com.prefere.web.dto.member.request

data class UserSignUpRequest(
    val name: String,
    val email: String,
    val password: String,
    val mobile: String,
)