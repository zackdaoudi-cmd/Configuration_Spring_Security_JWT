package com.GestionParc.Gestion_Parc.Config.security;

public class SecurityConstants {
    public static final String SECRET = "Secre";
    public static final String TOKEN_PREFIX = "bearer ";
    public static final String HEADER_STRING = "Authorization";
    public static final long EXPIRATION_TIME = 864_000_000L;

    private SecurityConstants(){}
}
