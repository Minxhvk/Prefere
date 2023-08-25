package com.prefere.web.security.service


import com.prefere.core.domain.member.MemberRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException

@Service
class UserDetailService(
    val userRepository: MemberRepository
) : UserDetailsService {
    override fun loadUserByUsername(email: String): UserDetails {
        return userRepository.findByEmail(email)
            ?: throw UsernameNotFoundException("유저를 찾을 수 없습니다. Email : $email")
    }
}