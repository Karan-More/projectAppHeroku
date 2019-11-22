package com.capstone.kanbantool.security;

import com.capstone.kanbantool.domain.User;
import io.jsonwebtoken.*;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.capstone.kanbantool.security.SecurityConstant.SECRET;
import static com.capstone.kanbantool.security.SecurityConstant.TOKEN_EXPIRATION_TIME;

@Component
public class JWTTokenProvider {

    public String generatedToken(Authentication authentication){
        User user = (User) authentication.getPrincipal();

        Date now = new Date(System.currentTimeMillis());


        Date expiryDate = new Date(now.getTime() + TOKEN_EXPIRATION_TIME);

        String userId = Long.toString(user.getId());

        Map<String,Object> claims = new HashMap<>();

        claims.put("id", (Long.toString(user.getId())));
        claims.put("username", user.getUsername());
        claims.put("firstName", user.getFirstName());
        claims.put("lastName", user.getLastName());
        // here also role can be kept for projectOwner in case we need access control
        return Jwts.builder().setSubject(userId).setClaims(claims).setIssuedAt(now)
                .setExpiration(expiryDate).signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public boolean validateToken(String token){
        try{
            Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token);
            return true;
        }catch (SignatureException ex){
                System.out.println("Invalid JWT Signature");
        }catch (MalformedJwtException ex){
            System.out.println("Invalid JWT Token");
        }catch (ExpiredJwtException ex){
            System.out.println("Expired JWT token");
        }catch (UnsupportedJwtException ex){
            System.out.println("Unsupported JWT Token");
        }catch (IllegalArgumentException ex){
            System.out.println("JWT Claims string is empty");
        }

        return false;
    }

    public Long getUserIdFromJwt(String token){
        Claims claims = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token).getBody();

        String id  = (String)claims.get("id");

        return Long.parseLong(id);


    }
}
