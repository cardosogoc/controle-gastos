package com.portifolio.controlegastos.infrastructure.security;

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken;
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.gson.GsonFactory;
import com.portifolio.controlegastos.exception.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.List;

@Component
public class GoogleTokenVerifier {

    private final GoogleIdTokenVerifier verifier;

    public GoogleTokenVerifier(@Value("${google.client-id}") String clientId) {
        this.verifier = new GoogleIdTokenVerifier.Builder(
                new NetHttpTransport(), GsonFactory.getDefaultInstance())
                .setAudience(List.of(clientId))
                .build();
    }

    public GoogleIdToken.Payload verificar(String idToken) {
        try {
            GoogleIdToken token = verifier.verify(idToken);
            if (token != null) {
                return token.getPayload();
            }
            throw new UnauthorizedException("Token do Google inválido");
        } catch (GeneralSecurityException | IOException e) {
            throw new UnauthorizedException("Não foi possível validar o token do Google", e);
        }
    }
}