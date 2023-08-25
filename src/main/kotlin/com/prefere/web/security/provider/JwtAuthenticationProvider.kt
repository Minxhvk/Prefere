package com.prefere.web.security.provider

import com.prefere.web.security.service.UserDetailService
import io.jsonwebtoken.*
import io.jsonwebtoken.security.Keys
import jakarta.annotation.PostConstruct
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service
import java.nio.charset.StandardCharsets
import java.security.Key
import java.util.*

@Service
class JwtAuthenticationProvider(
    private val userDetailsService: UserDetailService
) {

    @Value("\${spring.jwt.secret}")
    private lateinit var SECRET_KEY: String
    private lateinit var signingKey: Key
    private lateinit var jwtParser: JwtParser
    
    private val KEY_USERNAME: String = "userEmail"
    private val tokenValidTime = 30 * 60 * 1000L

    @PostConstruct
    fun init() {
        SECRET_KEY = Base64.getEncoder().encodeToString(SECRET_KEY.toByteArray())
        signingKey = Keys.hmacShaKeyFor(SECRET_KEY.toByteArray(StandardCharsets.UTF_8))
        jwtParser = Jwts.parserBuilder().setSigningKey(SECRET_KEY).build()
    }

    fun isValidToken(token: String): Boolean {
        return jwtParser.isSigned(token)
    }

    // 토큰에서 회원 정보 추출
    fun getUserName(token: String): String {
        return Jwts.parserBuilder()
            .setSigningKey(signingKey).build()
            .parseClaimsJws(token).body.subject
    }


    fun generateToken(userDetail: UserDetails): String {

        val claims: Claims = Jwts.claims() // JWT payload 에 저장되는 정보단위
        claims[KEY_USERNAME] = userDetail.username

        return createToken(claims)
    }

    private fun createToken(claims: Claims): String {

        val now = Date()

        return Jwts.builder()
            .setHeaderParam("type", "JWT")
            .setClaims(claims) // 정보 저장
            .setIssuedAt(now) // 토큰 발행 시간 정보
            .setExpiration(Date(now.time + tokenValidTime)) // set Expire Time
            .signWith(signingKey, SignatureAlgorithm.HS256) // 사용할 암호화 알고리즘과 signature 에 들어갈 secret값 세팅
            .compact()
    }

    fun getAuthentication(token: String): UsernamePasswordAuthenticationToken {
        val userDetails = userDetailsService.loadUserByUsername(getUserName(token))
        return UsernamePasswordAuthenticationToken(userDetails, "", userDetails.authorities)
    }

//    fun validateJwtToken(jwt: String): Boolean {
//
//        try {
//            jwtParser.parseClaimsJwt(jwt)
//            return true
//        } catch (signatureException: java.security.SignatureException) {
//            throw JwtInvalidException("Signature key is different")
//        } catch (expiredJwtException: ExpiredJwtException) {
//            throw JwtInvalidException("ExpiredJwtException")
//        } catch (malformedJwtException: MalformedJwtException) {
//            throw JwtInvalidException("Malformed Token")
//        } catch (illegalArgumentException: IllegalArgumentException) {
//            throw JwtInvalidException("Illegal Argument Like Null")
//        }
//
//        return false
//    }
}
