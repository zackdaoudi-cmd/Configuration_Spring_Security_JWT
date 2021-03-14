package com.GestionParc.Gestion_Parc.Repository;

import com.GestionParc.Gestion_Parc.Entity.Utilisateur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UtilisateurRepository extends JpaRepository<Utilisateur, Long> {
    Utilisateur findByEmail(String username);
}
