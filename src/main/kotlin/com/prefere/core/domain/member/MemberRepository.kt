package com.prefere.core.domain.member

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Repository


@Repository
interface MemberRepository: JpaRepository<Member, Long> {

    fun findByEmail(email: String): UserDetails?

    fun findUserByEmail(email: String?): Member?
}