package com.foodwizard.auth.util;

import com.foodwizard.auth.domain.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.JwtEncoderParameters;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.stream.Collectors;

@Slf4j
@Component
@RequiredArgsConstructor
public class JwtUtil {

    private final JwtEncoder encoder;
    private final JwtDecoder decoder;

    @Value("${jwt.expiration.time}")
    private long accessTokenExpTime;

    public String createAccessToken(User user) {
        return createToken(user, accessTokenExpTime);
    }

    private String createToken(User user, long expTime) {
        Instant now = Instant.now();

        String scope = user.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(" "));
        JwtClaimsSet claims = JwtClaimsSet.builder()
                .issuer("self")
                .issuedAt(now)
                .expiresAt(now.plusSeconds(expTime))
                .subject(user.getName())
                .claim("scope", scope)
                .claim("userId", user.getId())
                .build();

        return this.encoder.encode(JwtEncoderParameters.from(claims)).getTokenValue();
    }

    public String getUserId(String token) {
        return this.decoder.decode(token).getClaimAsString("userId");
    }

    public boolean validateToken(String token) {
        try {
            this.decoder.decode(token);
            return true;
        } catch (Exception e) {
            log.error("Error while validating token", e);
            return false;
        }
    }
}
