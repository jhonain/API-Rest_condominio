package com.vasquez.Backend_PAF_condominio.controller;


import com.vasquez.Backend_PAF_condominio.dto.LoginRequest;
import com.vasquez.Backend_PAF_condominio.dto.LoginResponseDto;
import com.vasquez.Backend_PAF_condominio.service.LoginService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class LoginController {

    private final LoginService loginService;

    //para loguearse con cuenta normal
    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequest request) {

        try {
            String token = loginService.login(request.getUsername(), request.getPassword());
            return ResponseEntity.ok(new LoginResponseDto(token));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

    //para probar en el navegador
    @GetMapping("/ping")
    public String ping() {
        return "Auth API OK";
    }

    // aca se hace para que se loguee con gloogleee


    // ← Login con Google
    @PostMapping("/google")
    public ResponseEntity<?> loginGoogle(@RequestBody Map<String, String> body) {
        try {
            String idToken = body.get("token");
            String jwt = loginService.loginWithGoogle(idToken);
            return ResponseEntity.ok(new LoginResponseDto(jwt));
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(401).body(e.getMessage());
        }
    }

}
