package com.mybury.waver.security;

import com.mybury.waver.common.code.ResultCode;
import com.mybury.waver.exception.WaverException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class JwtTokenParser {

    @Value("${waver.secret.name}")
    private String name;

    @Value("${waver.secret.key}")
    private String secret;

    public Key getSigningKey() {
        byte[] keyBytes = Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keyBytes);
    }

    public String getUserIdFromToken(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                .setSigningKey(getSigningKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
            return claims.get(name, String.class);

        } catch (ExpiredJwtException expiredJwtException) {
            log.error("Token Expired: {}", expiredJwtException.getMessage());
            throw new WaverException(ResultCode.TOKEN_EXPIRED);
        } catch (Exception e) {
            log.error("Token Error: {}", e.getMessage());
            throw new WaverException(ResultCode.INVALID_TOKEN);
        }
    }
}