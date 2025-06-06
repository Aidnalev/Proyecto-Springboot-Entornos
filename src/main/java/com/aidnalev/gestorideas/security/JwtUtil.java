package com.aidnalev.gestorideas.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;

@Component
public class JwtUtil {

    // Clave secreta debe ser al menos de 256 bits para HS256 (32 bytes)
    private static final String SECRET = "miClaveSuperSecretaQueTieneAlMenos32Bytes!1234";

    // Convertir la clave en Key para jjwt moderno
    private final Key SECRET_KEY = Keys.hmacShaKeyFor(SECRET.getBytes());

    // Genera token válido por 1 día
    public String generarToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 día
                .signWith(SECRET_KEY, SignatureAlgorithm.HS256)
                .compact();
    }

    // Extrae el username del token
    public String extraerUsername(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(SECRET_KEY)
                .build()
                .parseClaimsJws(token)
                .getBody()
                .getSubject();
    }

    // Valida el token, atrapa cualquier excepción y devuelve false si no es válido
    public boolean validarToken(String token) {
        try {
            Jwts.parserBuilder()
                    .setSigningKey(SECRET_KEY)
                    .build()
                    .parseClaimsJws(token);
            return true;
        } catch (JwtException e) {
            // aquí puedes loguear la excepción para depurar
            return false;
        }
    }
}
