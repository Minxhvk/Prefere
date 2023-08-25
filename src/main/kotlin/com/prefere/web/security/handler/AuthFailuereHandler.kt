package com.prefere.web.security.handler

import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.BadCredentialsException
import org.springframework.security.authentication.InternalAuthenticationServiceException
import org.springframework.security.core.AuthenticationException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.web.authentication.SimpleUrlAuthenticationFailureHandler
import java.net.URLEncoder

@Configuration
class AuthFailuereHandler : SimpleUrlAuthenticationFailureHandler() {

    override fun onAuthenticationFailure(request: HttpServletRequest,
                                         response: HttpServletResponse,
                                         exception: AuthenticationException) {
        var errorMessage: String

        if (exception is BadCredentialsException) {
            errorMessage = "아이디와 비밀번호를 확인해주세요."
        } else if (exception is InternalAuthenticationServiceException) {
            errorMessage = "내부 시스템 문제로 로그인할 수 없습니다. 관리자에게 문의하세요."
        } else if (exception is UsernameNotFoundException) {
            errorMessage = "존재하지 않는 계정입니다."
        } else {
            errorMessage = "알 수없는 오류입니다."
        }

        errorMessage = URLEncoder.encode(errorMessage, "UTF-8")

        setDefaultFailureUrl("/login?error=$errorMessage")

        super.onAuthenticationFailure(request, response, exception)
    }
}