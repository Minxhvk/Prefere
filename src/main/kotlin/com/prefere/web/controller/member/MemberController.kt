package com.prefere.web.controller.member


import com.prefere.core.service.member.MemberService
import com.prefere.web.dto.member.request.UserLoginRequest
import com.prefere.web.dto.member.request.UserSignUpRequest
import jakarta.servlet.http.Cookie
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/user")
class MemberController(
    private val memberService: MemberService
) {

    @PostMapping("/signup")
    @ResponseStatus(code = HttpStatus.CREATED)
    fun signUp(@RequestBody request: UserSignUpRequest, response: HttpServletResponse): String {

        val token = memberService.createUser(request)
        response.addCookie(Cookie("Authorization", token))

        return token
    }

    @PostMapping("/login")
    fun signIn(@RequestBody request: UserLoginRequest, response: HttpServletResponse): String {

        val token = memberService.login(request)
        response.addCookie(Cookie("Authorization", token))

        return token
    }
}