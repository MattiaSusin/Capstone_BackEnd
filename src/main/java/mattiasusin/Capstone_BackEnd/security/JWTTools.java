package mattiasusin.Capstone_BackEnd.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import mattiasusin.Capstone_BackEnd.entities.Admin;
import mattiasusin.Capstone_BackEnd.exceptions.UnauthorizedException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JWTTools {
    @Value("${jwt.secret}")
    private String secret;

    public String createToken(Admin cliente) {
        return Jwts.builder()
                .issuedAt(new Date(System.currentTimeMillis()))
                .expiration(new Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24 * 7))
                .subject(String.valueOf(cliente.getId()))
                .signWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .compact();
    }

    public void verifyToken(String token) {
        try {
            Jwts.parser().verifyWith(Keys.hmacShaKeyFor(secret.getBytes())).build().parse(token);
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            throw new UnauthorizedException("Problemi col token! Per favore effettua di nuovo il login!");
        }
    }

    public String extractIdFromToken(String accessToken) {
        return Jwts.parser()
                .verifyWith(Keys.hmacShaKeyFor(secret.getBytes()))
                .build().parseSignedClaims(accessToken)
                .getPayload()
                .getSubject(); // Il subject è l'id dell'utente
    }
}
