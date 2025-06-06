package com.aidnalev.gestorideas.controller;

import com.aidnalev.gestorideas.entity.Usuario;
import com.aidnalev.gestorideas.repository.UsuarioRepository;
import com.aidnalev.gestorideas.security.JwtUtil;
import com.aidnalev.gestorideas.security.UsuarioDetails;
import java.util.Collections;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Map;
import org.springframework.security.core.annotation.AuthenticationPrincipal;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UsuarioRepository usuarioRepo;
    private final PasswordEncoder passwordEncoder;

    public AuthController(JwtUtil jwtUtil, AuthenticationManager authenticationManager,
                          UsuarioRepository usuarioRepo, PasswordEncoder passwordEncoder) {
        this.jwtUtil = jwtUtil;
        this.authenticationManager = authenticationManager;
        this.usuarioRepo = usuarioRepo;
        this.passwordEncoder = passwordEncoder;
    }
    @GetMapping("/usuario/nombre")
    public Map<String, String> obtenerNombreUsuario(@AuthenticationPrincipal UsuarioDetails usuarioDetails) {
    String nombre = usuarioDetails.getUsuario().getNombre();
    return Collections.singletonMap("nombre", nombre);
}
    // LOGIN
    @PostMapping("/login")
    public Map<String, String> login(@RequestBody Map<String, String> req) {
        String nombre = req.get("nombre");
        String contrasena = req.get("contrasena");

        try {
            authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(nombre, contrasena)
            );

            String token = jwtUtil.generarToken(nombre);
            return Map.of("token", token, "nombre", nombre);
        } catch (AuthenticationException ex) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Credenciales inválidas");
        }
    }

    // REGISTRO
    @PostMapping("/registro")
    public void register(@RequestBody Map<String, String> req) {
        String nombre = req.get("nombre");
        String correo = req.get("correo");
        String contrasena = req.get("contrasena");

        if (usuarioRepo.findByNombre(nombre).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Nombre de usuario en uso");
        }

        Usuario nuevo = new Usuario();
        nuevo.setNombre(nombre);
        nuevo.setCorreo(correo);
        nuevo.setContrasena(passwordEncoder.encode(contrasena));
        usuarioRepo.save(nuevo);
    }
}
