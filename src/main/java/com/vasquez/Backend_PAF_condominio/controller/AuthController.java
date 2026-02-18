package com.vasquez.Backend_PAF_condominio.controller;


import com.vasquez.Backend_PAF_condominio.dto.LoginRequest;
import com.vasquez.Backend_PAF_condominio.dto.TokenResponseDto;
import com.vasquez.Backend_PAF_condominio.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {
    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            String token = authService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new TokenResponseDto(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    // opcional para probar en el navegador
    @GetMapping("/ping")
    public String ping() {
        return "Auth API OK";
    }

}
