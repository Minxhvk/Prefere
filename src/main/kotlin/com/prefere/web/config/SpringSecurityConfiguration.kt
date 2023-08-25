package com.prefere.web.config


import com.prefere.web.filter.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.HttpMethod
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter


@Configuration
@EnableWebSecurity
class MultiHttpSecurityConfig(
    private val jwtFilter: JwtAuthenticationFilter
) {

    val AUTH_WHITELIST = arrayOf(
        /* 회원가입 */
        "/api/v1/user/signup",
        "/api/v1/user/login"
    )

    @Bean
    fun filterChain(http: HttpSecurity): SecurityFilterChain {

        return http
            .csrf { csrf -> csrf.disable() }
            .authorizeHttpRequests { authorize ->
                authorize
                    .requestMatchers(*AUTH_WHITELIST).permitAll()
                    .requestMatchers(HttpMethod.GET, "/ideal-types").permitAll()
                    .requestMatchers(HttpMethod.GET, "/interests").permitAll()
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
            .httpBasic(Customizer.withDefaults())
            .build()

//            .requestMatchers("/admin/**").hasRole("ADMIN") // '/admin/*' 요청은 ADMIN 권한을 가진 사용자만 접근 가능
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }
}