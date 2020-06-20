package gc.garcol.nalsolution.authentication;

import io.jsonwebtoken.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

import java.util.Date;

import static gc.garcol.nalsolution.utils.DateTimeUtil.DATE_TIME_UTIL;

/**
 * @author thai-van
 **/
@Slf4j
@Component
@PropertySource("classpath:auth.properties")
public class JwtProvider {

    @Value("${server.jwt.secret_key}")
    private String jwtSecret;

    @Value("${server.jwt.expiration}")
    private long expiration;

    @Value("${server.jwt.SignatureAlgorithm}")
    private String signatureAlgorithm;

    @Value("${server.auth-mode}")
    private int authMode;

    private static final int TEST_MODE = 0;

    /**
     * Persist email as subject
     * @param userDetail
     * @return JWT
     */
    public String generateJWT(SimpleUserDetail userDetail) {

        if (authMode == TEST_MODE) return userDetail.getUsername();

        SignatureAlgorithm signatureType = SignatureAlgorithm.forName(signatureAlgorithm);

        return Jwts.builder()
                .setSubject(userDetail.getUsername())
                .setIssuedAt(new Date())
                .setExpiration(new Date(DATE_TIME_UTIL.currentTimeMillis() + expiration))
                .signWith(signatureType, jwtSecret)
                .compact();
    }

    /**
     * Decode JWT to get email - subject.
     * See {@link JwtProvider#generateJWT(SimpleUserDetail)}
     * @param jwt
     * @return email
     */
    public String getEmailFromJWT(String jwt) {

        if (authMode == TEST_MODE) return jwt;

        Claims claims = Jwts.parser()
                .setSigningKey(jwtSecret)
                .parseClaimsJws(jwt)
                .getBody();

        return claims.getSubject();

    }

    public boolean validateJWT(String jwt) {
        try {
            Jwts.parser().setSigningKey(jwtSecret).parseClaimsJws(jwt);
            return true;
        } catch (SignatureException e) {
            log.error("Invalid JWT signature ", e);
        } catch (MalformedJwtException e) {
            log.error("Invalid JWT token ", e);
        } catch (ExpiredJwtException e) {
            log.error("Expired JWT token ", e);
        } catch (UnsupportedJwtException e) {
            log.error("Unsupported JWT token ", e);
        } catch (IllegalArgumentException e) {
            log.error("JWT claims string is empty. ", e);
        }
        return false;
    }
}
