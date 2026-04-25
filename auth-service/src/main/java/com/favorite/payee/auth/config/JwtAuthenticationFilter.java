package com.favorite.payee.auth.config;



	import io.jsonwebtoken.Claims;
	import io.jsonwebtoken.Jwts;
	import io.jsonwebtoken.security.Keys;
	import jakarta.servlet.FilterChain;
	import jakarta.servlet.ServletException;
	import jakarta.servlet.http.HttpServletRequest;
	import jakarta.servlet.http.HttpServletResponse;
	import org.springframework.beans.factory.annotation.Value;
	import org.springframework.http.HttpHeaders;
	import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
	import org.springframework.security.core.context.SecurityContextHolder;
	import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
	import org.springframework.stereotype.Component;
	import org.springframework.web.filter.OncePerRequestFilter;

	import javax.crypto.SecretKey;
	import java.io.IOException;
	import java.nio.charset.StandardCharsets;
	import java.util.Collections;

	@Component
	public class JwtAuthenticationFilter extends OncePerRequestFilter {

	    @Value("${security.jwt.secret:very-secure-default-secret-key-that-is-at-least-32-bytes}")
	    private String secret;

	    @Override
	    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
	            throws ServletException, IOException {
	        String path = request.getRequestURI();
	        if (path.startsWith("/api/auth/") || path.startsWith("/api/customers/") || path.startsWith("/api/notifications/")) {
	            filterChain.doFilter(request, response);
	            return;
	        }

	        String header = request.getHeader(HttpHeaders.AUTHORIZATION);
	        if (header == null || !header.startsWith("Bearer ")) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	            return;
	        }

	        String token = header.substring(7);
	        try {
	            SecretKey key = Keys.hmacShaKeyFor(secret.getBytes(StandardCharsets.UTF_8));
	            Claims claims = Jwts.parser().verifyWith(key).build().parseSignedClaims(token).getPayload();
	            String subject = claims.getSubject();
	            var auth = new UsernamePasswordAuthenticationToken(subject, null, Collections.emptyList());
	            auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
	            SecurityContextHolder.getContext().setAuthentication(auth);
	            filterChain.doFilter(request, response);
	        } catch (Exception ex) {
	            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
	        }
	    }
	}

}
