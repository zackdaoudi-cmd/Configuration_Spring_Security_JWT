package com.GestionParc.Gestion_Parc.Services;

import com.GestionParc.Gestion_Parc.Entity.Utilisateur;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import com.GestionParc.Gestion_Parc.Repository.UtilisateurRepository;

@Component
public class UtilisateurService implements UserDetailsService {

    @Autowired
    private UtilisateurRepository utilisateurRepository;

    @Override
    public UserDetails loadUserByUsername(String s) {
        Utilisateur subscriber = loadApplicationUserByUserName(s);
        return new User(subscriber.getEmail(), subscriber.getMotDePasse(), AuthorityUtils.createAuthorityList("ROLE_ADMIN"));
    }

    public Utilisateur loadApplicationUserByUserName(String username) {
        Utilisateur susbcriber = utilisateurRepository.findByEmail(username);
        if (susbcriber == null) {
            throw new UsernameNotFoundException(String.format("No user found with username '%s'.", username));
        } else {
            return new Utilisateur(susbcriber.getEmail(), susbcriber.getMotDePasse());
        }
    }
}
