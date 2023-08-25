package com.prefere.service.member

import com.prefere.core.domain.member.MemberRepository
import com.prefere.core.service.member.MemberService
import com.prefere.web.dto.member.request.UserSignUpRequest
import org.assertj.core.api.AssertionsForInterfaceTypes.assertThat
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class MemberServiceTest @Autowired constructor(
    private val memberRepository: MemberRepository,
    private val memberService: MemberService,
) {
    @AfterEach
    internal fun clean() {
        memberRepository.deleteAll()
    }

    @Test
    internal fun saveUserTest() {
        val request = UserSignUpRequest(
            "김민혁",
            "minxhvk@gmail.com",
            "asd1234!",
            "010-0000-0000"
        )

        memberService.createUser(request)

        val results = memberRepository.findAll()

        assertThat(results).hasSize(1)
        assertThat(results[0].id is Long).isEqualTo(true)
        assertThat(results[0].name).isEqualTo("김민혁")
        assertThat(results[0].email).isEqualTo("minxhvk@gmail.com")
        assertThat(results[0].mobile).isEqualTo("010-0000-0000")
    }

    @Test
    internal fun saveuserDuplicateEmailTest() {
        val request = UserSignUpRequest(
            "김민혁",
            "minxhvk@gmail.com",
            "asd1234!",
            "010-0000-0000"
        )
    }

    @Test
    internal fun saveUserFailTest() {

        val request = UserSignUpRequest(
            "40자가 넘어가는 이름40자가 넘어가는 이름40자가 넘어가는 이름40자가 넘어가는 이름",
            "minxhvk@gmail.com",
            "asd1234!",
            "010-0000-0000"
        )

        val exception = assertThrows<IllegalArgumentException> {
            memberService.createUser(request)
        }

        assertThat(exception.message).isEqualTo("이름은 40자 이내로 입력해야 합니다.")
    }
}