package com.prefere.web.dto.member

data class MemberDto(
    val id: String?,
    val email: String,
    val token: String,
) {
    companion object {
        fun from(id: String?, email: String, token: String): MemberDto {
            return MemberDto(id, email, token)
        }
    }
}