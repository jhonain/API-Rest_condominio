package com.vasquez.Backend_PAF_condominio.service;

public interface LoginService {

    String login(String username, String password);

    String loginWithGoogle(String idToken) throws Exception;
}
