package uz.sukhrob.lesson7task1.security;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;
import uz.sukhrob.lesson7task1.entity.Role;

import java.sql.Date;

@Component
public class JwtProvider {

    String secretKey = "extremelySecretWord";
    long expireTime = 1000000000l;

    public String generateToken(String username, Role role) {

        String json = Jwts
                .builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + expireTime))
                .claim("roles",role)
                .signWith(SignatureAlgorithm.HS512, secretKey)
                .compact();
        return json;
    }

    public boolean validateToken(String token) {
        try {
            Jwts
                    .parser()
                    .setSigningKey(secretKey)
                    .parseClaimsJws(token);
            return true;
        } catch (Exception r) {
            r.printStackTrace();
        }
        return false;
    }

    public String getUsernameFromToken(String token) {

        String username = Jwts
                .parser()
                .setSigningKey(secretKey)
                .parseClaimsJws(token)
                .getBody().getSubject();
        return username;
    }
}
