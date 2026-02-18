package com.vasquez.Backend_PAF_condominio.service.Impl;

import com.vasquez.Backend_PAF_condominio.Security.JwtUtils;
import com.vasquez.Backend_PAF_condominio.repository.UsuarioRepository;
import com.vasquez.Backend_PAF_condominio.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtils jwtService;
    private final UsuarioRepository usuarioRepository;

    @Override
    public String login(String username, String password) {
        Authentication auth = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(username, password)
        );
        UserDetails user = (UserDetails) auth.getPrincipal();
        return jwtService.generateToken(user);
    }
}
