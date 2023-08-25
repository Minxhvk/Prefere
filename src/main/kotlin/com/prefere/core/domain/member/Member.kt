package com.prefere.core.domain.member

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails
import java.time.LocalDate


@Entity
class Member constructor(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    var name: String,

    val email: String,

    val mobile: String,

    private var password: String,

    @CreatedDate
    val createdAt: LocalDate? = null,

    @LastModifiedDate
    val updatedAt: LocalDate? = null

    ): UserDetails {


    // TODO
    init {
        require(name.length < 40) {
            throw IllegalArgumentException("이름은 40자 이내로 입력해야 합니다.")
        }

        require(email.length < 40) {
            throw IllegalArgumentException("이메일은 40자 이내로 입력해야 합니다.")
        }

        check(name.isNotBlank()) {
            throw IllegalArgumentException("이름을 입력해 주세요.")
        }

        check(email.isNotBlank()) {
            throw IllegalArgumentException("이메일을 입력해 주세요.")
        }

    }

    override fun getAuthorities(): MutableCollection<out GrantedAuthority>? {
        return null
    }

    override fun getPassword(): String {
        return password
    }

    override fun getUsername(): String {
        return email
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }

    fun withId(id: Long?): Member = Member(id, name, email, mobile, password)


    companion object {
        fun fixture(
            name: String = "Test",
            email: String = "minxhvk@gmail.com",
            mobile: String = "010-0000-0000",
            password: String = "asd1234!",
        ): Member {
            return Member(
                name = name,
                email = email,
                mobile = mobile,
                password = password,
            )
        }
    }
}