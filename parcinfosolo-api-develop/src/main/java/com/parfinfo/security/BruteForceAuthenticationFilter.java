package com.parfinfo.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;
import java.io.IOException;
import java.util.Map;

public class BruteForceAuthenticationFilter extends OncePerRequestFilter {

    private final BruteForceProtection bruteForceProtection;
    private final AntPathRequestMatcher loginRequestMatcher;
    private final UsernamePasswordAuthenticationFilter authenticationFilter;

    public BruteForceAuthenticationFilter(
            BruteForceProtection bruteForceProtection,
            UsernamePasswordAuthenticationFilter authenticationFilter) {
        this.bruteForceProtection = bruteForceProtection;
        this.authenticationFilter = authenticationFilter;
        this.loginRequestMatcher = new AntPathRequestMatcher("/api/auth/login", "POST");
    }

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain) throws ServletException, IOException {

        if (loginRequestMatcher.matches(request)) {
            String username = request.getParameter("email");
            String ipAddress = getClientIp(request);

            if (bruteForceProtection.isBlocked(username, ipAddress)) {
                Map<String, Long> remainingTimes = bruteForceProtection.getRemainingBlockTimes(username, ipAddress);
                String message = buildBlockedMessage(remainingTimes);
                response.setStatus(HttpServletResponse.SC_TOO_MANY_REQUESTS);
                response.getWriter().write(message);
                return;
            }

            try {
                Authentication authentication = authenticationFilter.attemptAuthentication(request, response);
                if (authentication != null && authentication.isAuthenticated()) {
                    bruteForceProtection.resetFailedAttempts(username, ipAddress);
                }
            } catch (AuthenticationException e) {
                bruteForceProtection.registerFailedAttempt(username, ipAddress);
                throw e;
            }
        }

        filterChain.doFilter(request, response);
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader == null) {
            return request.getRemoteAddr();
        }
        return xfHeader.split(",")[0];
    }

    private String buildBlockedMessage(Map<String, Long> remainingTimes) {
        StringBuilder message = new StringBuilder("Compte temporairement bloqué. ");
        
        if (remainingTimes.containsKey("user")) {
            message.append("Tentatives utilisateur: ")
                  .append(bruteForceProtection.getFailedAttempts("user"))
                  .append("/5. ");
        }
        
        if (remainingTimes.containsKey("ip")) {
            message.append("Tentatives IP: ")
                  .append(bruteForceProtection.getIpFailedAttempts("ip"))
                  .append("/20. ");
        }
        
        message.append("Veuillez réessayer dans ");
        
        if (remainingTimes.containsKey("user")) {
            message.append(remainingTimes.get("user"))
                  .append(" minutes pour l'utilisateur");
        }
        
        if (remainingTimes.containsKey("ip")) {
            if (remainingTimes.containsKey("user")) {
                message.append(" et ");
            }
            message.append(remainingTimes.get("ip"))
                  .append(" minutes pour l'IP");
        }
        
        return message.toString();
    }
} 