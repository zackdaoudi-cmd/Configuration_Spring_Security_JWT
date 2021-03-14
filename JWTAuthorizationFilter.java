package com.GestionParc.Gestion_Parc.Config.security;

import com.GestionParc.Gestion_Parc.Entity.Utilisateur;
import com.GestionParc.Gestion_Parc.Services.UtilisateurService;
import io.jsonwebtoken.Jwts;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;



public class JWTAuthorizationFilter extends BasicAuthenticationFilter {

    private final UtilisateurService utilisateurService;

    public JWTAuthorizationFilter(AuthenticationManager authenticationManager,
                                  UtilisateurService utilisateurService) {
        super(authenticationManager);
        this.utilisateurService = utilisateurService;
    }


    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(SecurityConstants.HEADER_STRING);
        if (header == null || !header.startsWith(SecurityConstants.TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }
        UsernamePasswordAuthenticationToken usernamePasswordAuth = getAuthenticationToken(request);
        SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuth);
        chain.doFilter(request, response);


    }

    private UsernamePasswordAuthenticationToken getAuthenticationToken(HttpServletRequest request) {
        String token = request.getHeader(SecurityConstants.HEADER_STRING);
        if (token == null) return null;
        String username = Jwts.parser().setSigningKey(SecurityConstants.SECRET)
                .parseClaimsJws(token.replace(SecurityConstants.TOKEN_PREFIX, ""))
                .getBody()
                .getSubject();
        UserDetails userDetails = utilisateurService.loadUserByUsername(username);
        Utilisateur subscriber = utilisateurService.loadApplicationUserByUserName(username);

        return username != null ? new UsernamePasswordAuthenticationToken(subscriber, null, userDetails.getAuthorities()) : null;

    }
}
