package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.vasquez.Backend_PAF_condominio.Security.JwtUtils;
import com.vasquez.Backend_PAF_condominio.entity.Usuario;
import com.vasquez.Backend_PAF_condominio.service.LoginService;
import com.vasquez.Backend_PAF_condominio.service.PersonaService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LoginServiceImpl implements LoginService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtService;
    private final PersonaService service;

    @Value("${google.client-id}")
    private String googleClientId;

    @Override
    public String login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        UserDetails user = (UserDetails) auth.getPrincipal();
        return jwtService.generateToken(user);
    }

    //logica para la implementacion de gloogle
    @Override
    public String loginWithGoogle(String idToken) throws Exception {
        GoogleIdTokenVerifier verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(List.of(googleClientId))
                .build();

        GoogleIdToken googleToken = verifier.verify(idToken);
        if (googleToken == null) {
            throw new RuntimeException("Token de Google inválido");
        }

        GoogleIdToken.Payload payload = googleToken.getPayload();
        String email     = payload.getEmail();
        String nombre    = (String) payload.get("given_name");
        String apellidos = (String) payload.get("family_name");

        // Usa PersonaService en vez de UsuarioService
        Usuario usuario = service.findOrCreateByGoogle(email, nombre, apellidos);

        return jwtService.generateToken(usuario);
    }

}
