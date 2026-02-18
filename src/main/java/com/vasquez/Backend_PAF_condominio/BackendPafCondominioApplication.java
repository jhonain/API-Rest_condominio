package com.vasquez.Backend_PAF_condominio;

import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Encoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.crypto.SecretKey;

@SpringBootApplication
public class BackendPafCondominioApplication {

	public static void main(String[] args) {
		SpringApplication.run(BackendPafCondominioApplication.class, args);

		//SecretKey key = Keys.secretKeyFor(SignatureAlgorithm.HS256);
		//String secret = Encoders.BASE64.encode(key.getEncoded());
		//System.out.println(secret);
	}

}
