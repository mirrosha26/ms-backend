package com.bondsbackend.ms.security;

import com.bondsbackend.ms.exceptions.JwtAuthenticationException;
import io.jsonwebtoken.Claims;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.filter.GenericFilterBean;
import jakarta.servlet.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Collections;
import java.util.List;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class JwtTokenFilter extends GenericFilterBean {

    private final JwtTokenProvider jwtTokenProvider;

    public JwtTokenFilter(JwtTokenProvider jwtTokenProvider) {
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain filterChain)
            throws IOException, ServletException {
        String token = resolveToken((HttpServletRequest) req);
        try {
            if (token != null && jwtTokenProvider.validateToken(token)) {
                Authentication auth = getAuthentication(token);
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        } catch (JwtAuthenticationException e) {
            HttpServletResponse httpResponse = (HttpServletResponse) res;
            httpResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            httpResponse.setContentType("application/json");
            httpResponse.setCharacterEncoding("UTF-8");
            httpResponse.getWriter().write("{\"error\": \"Invalid token\"}");
            return;
        }

        filterChain.doFilter(req, res);
    }

    private String resolveToken(HttpServletRequest req) {
        String bearerToken = req.getHeader("Authorization");
        if (bearerToken != null && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }

    private Authentication getAuthentication(String token) {
        String username = jwtTokenProvider.getUsername(token);
        String role = jwtTokenProvider.getRoleFromToken(token);

        List<GrantedAuthority> authorities = Collections.singletonList(new SimpleGrantedAuthority("ROLE_" + role.toUpperCase()));

        UserDetails userDetails = User.builder()
                .username(username)
                .password("")
                .authorities(authorities)
                .build();
        return new UsernamePasswordAuthenticationToken(userDetails, "", authorities);
    }
}
