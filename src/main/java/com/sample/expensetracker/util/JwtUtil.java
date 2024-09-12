package com.sample.expensetracker.util;

import com.nimbusds.jose.JOSEException;
import com.nimbusds.jose.JWSAlgorithm;
import com.nimbusds.jose.JWSHeader;
import com.nimbusds.jose.crypto.MACSigner;
import com.nimbusds.jose.jwk.OctetSequenceKey;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.SignedJWT;
import com.sample.expensetracker.config.JwtConfig;
import com.sample.expensetracker.exception.ServiceException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;
import java.time.Instant;
import java.util.Date;
import java.util.Map;

/**
 * @author M.khoshnevisan
 * @since 9/12/2024
 */
@Component
@AllArgsConstructor
public class JwtUtil {
    public static final String JWT_KEY = "79dc2831647cf661e0685437309564da1de5759a6e7c0eb83106e0253d5e032a";
    public static final SecretKey SECRET_KEY = new OctetSequenceKey.Builder(JWT_KEY.getBytes()).build().toSecretKey();
    public static final JWSAlgorithm ALGORITHM = JWSAlgorithm.parse("HS256");

    private final JwtConfig jwtConfig;
    public String generateJWT(Map<String, Object> claims) {
        var header = new JWSHeader(ALGORITHM);
        var claimsSet = buildClaimsSet(claims);
        var jwt = new SignedJWT(header, claimsSet);
        try {
            var signer = new MACSigner(SECRET_KEY);
            jwt.sign(signer);
        } catch (JOSEException e) {
            throw new ServiceException("Unable to generate JWT", e);
        }
        return jwt.serialize();
    }

    private JWTClaimsSet buildClaimsSet(Map<String, Object> claims) {
        String issuer = jwtConfig.getIssuer();
        Instant issuedAt = Instant.now();
        Instant expirationTime = issuedAt.plus(jwtConfig.getExpiresIn());
        var builder = new JWTClaimsSet.Builder()
                .issuer(issuer)
                .issueTime(Date.from(issuedAt))
                .expirationTime(Date.from(expirationTime));
        claims.forEach(builder::claim);
        return builder.build();
    }
}