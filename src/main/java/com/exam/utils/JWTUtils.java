package com.exam.utils;

import cn.hutool.json.JSONUtil;
import com.exam.pojo.dto.UserDTO;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
@Data
@ConfigurationProperties(prefix = "exam.jwt")
public class JWTUtils {

    private long expiry;
    private String secret;

    private String header;

    public String creatToken(String userDTO) {
        Date now = new Date();
        Date expiryDate = new Date(now.getTime() + (1000 * expiry));

        return Jwts.builder()
                .setHeaderParam("type", "JWT")
                .setSubject(String.valueOf(userDTO))
                .setIssuedAt(now)
                .setExpiration(expiryDate)
                .signWith(SignatureAlgorithm.HS512, secret)
                .compact();
    }

    public Claims getClaimsByToken(String jwt) {
        try {
            return Jwts.parser()
                    .setSigningKey(secret)
                    .parseClaimsJws(jwt)
                    .getBody();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 返回jwt用户信息
     *
     * @param jwt
     * @return
     */
    public UserDTO getUserInfo(String jwt) {
        Claims claims = getClaimsByToken(jwt);
        String userStr = claims.getSubject();
        return JSONUtil.toBean(userStr, UserDTO.class);
    }

    public boolean isTokenExpired(Claims claims) {
        return claims.getExpiration().before(new Date());
    }

}
