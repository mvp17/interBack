package com.interapp.externa.config.jwt

import com.interapp.externa.config.user.UserDetailsService
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletException
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders.AUTHORIZATION
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter
import java.io.IOException

class JwtAuthorizationFilter (
    private val jwtService: JwtService,
    private val service: UserDetailsService,
    authManager: AuthenticationManager
    ): BasicAuthenticationFilter(authManager) {

    @Throws(IOException::class, ServletException::class)
    override fun doFilterInternal(
        req: HttpServletRequest,
        res: HttpServletResponse,
        chain: FilterChain
    ) {
        val header = req.getHeader(AUTHORIZATION)
        if (header == null || !header.startsWith("Bearer ")) {
            chain.doFilter(req, res)
            return
        }
        getAuthentication(header.substring(7))?.also {
            SecurityContextHolder.getContext().authentication = it
        }
        chain.doFilter(req, res)
    }

    private fun getAuthentication(token: String): UsernamePasswordAuthenticationToken? {
        if (!jwtService.isTokenValid(token)) return null
        val email = jwtService.getEmail(token)
        val user = service.loadUserByUsername(email)
        return UsernamePasswordAuthenticationToken(user, null, user.authorities)
    }
}
