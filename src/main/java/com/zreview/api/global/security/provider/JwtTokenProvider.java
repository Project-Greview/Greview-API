package com.zreview.api.global.security.provider;

import com.zreview.api.global.security.MemberDetails;
import com.zreview.api.global.security.MemberDetailsService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import java.util.Base64;
import java.util.Date;
import javax.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class JwtTokenProvider {

    private static final long ACCESS_TOKEN_VALID_TIME = 24 * 60 * 60 * 1000L;

    private final MemberDetailsService memberDetailsService;

    private String secretKey = "ZREVIEW_KEY";

    @PostConstruct
    private void init() {
        this.secretKey = Base64.getEncoder().encodeToString(secretKey.getBytes());
    }

    public String createAccessToken(final String email, final String nickname) {
        Claims claims = Jwts.claims().setSubject(email);
        claims.put("nickname", nickname);
        Date now = new Date();

        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + ACCESS_TOKEN_VALID_TIME))
                .signWith(SignatureAlgorithm.HS256, this.secretKey)
                .compact();
    }

    public Authentication getAuthentication(final String token) {
        String email = this.getAllClaims(token).getSubject();
        MemberDetails memberDetails = memberDetailsService.loadMemberByEmail(email);

        return new UsernamePasswordAuthenticationToken(memberDetails, null, memberDetails.getAuthorities());
    }

    public boolean isTokenExpired(final String token) {
        Date expiration = this.getAllClaims(token).getExpiration();

        return expiration.after(new Date());
    }

    public boolean validateToken(final String token) {
        try {
            return this.isTokenExpired(token);
        } catch (Exception e) {
            return false;
        }
    }

    private Claims getAllClaims(final String token) {
        return Jwts.parser()
                .setSigningKey(this.secretKey)
                .parseClaimsJws(token)
                .getBody();
    }
}
