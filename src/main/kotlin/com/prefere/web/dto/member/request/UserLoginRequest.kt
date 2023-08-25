package com.prefere.web.dto.member.request

data class UserLoginRequest(
    val email: String,
    val password: String,
)
