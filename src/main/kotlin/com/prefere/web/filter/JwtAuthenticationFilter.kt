package com.prefere.web.filter

import com.prefere.web.security.provider.JwtAuthenticationProvider
import com.prefere.web.security.service.UserDetailService
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

//private val logger = KotlinLogging.logger {}

@Component
class JwtAuthenticationFilter(
    val userDetailService: UserDetailService,
    val jwtAuthenticationProvider: JwtAuthenticationProvider,
) : OncePerRequestFilter() {

    final val AUTHORIZATION_HEADER = "Authorization"
    final val BEARER_PREFIX = "BEARER "

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        val token: String = getToken(request) ?: return filterChain.doFilter(request, response)

        try {
            if (jwtAuthenticationProvider.isValidToken(token)) {
                val userName: String = jwtAuthenticationProvider.getUserName(token)
                val userDetails: UserDetails = userDetailService.loadUserByUsername(userName)

                val authentication = UsernamePasswordAuthenticationToken(
                    userDetails,
                    null,
                    userDetails.authorities
                )

                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: Exception) {
            logger.error {"Cannot set user authentication: ${e}"}
        }

        filterChain.doFilter(request, response)
    }

    private fun getToken(request: HttpServletRequest): String? {
        val authorizationHeader = request.getHeader(AUTHORIZATION_HEADER) ?: return null

        if (authorizationHeader.isNotEmpty() && authorizationHeader.startsWith(BEARER_PREFIX)) {
            return authorizationHeader.substring(BEARER_PREFIX.length)
        }

        return null
    }
}
