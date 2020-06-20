package gc.garcol.nalsolution.authentication;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @author thai-van
 **/
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private static final String TOKEN_PREFIX = "Bearer ";

    private static final String AUTHORIZATION_KEY = "Authorization";

    @Autowired
    private JwtProvider jwtProvider;

    @Autowired
    private UserDetailServiceImpl userDetailsService;

    @Autowired
    AuthenticationManager authenticationManager;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        setAuthentication(request);
        filterChain.doFilter(request, response);
    }

    private void setAuthentication(HttpServletRequest request) {
        try {
            String jwt = extractJwtFromRequest(request);

            if (!(StringUtils.hasText(jwt) && jwtProvider.validateJWT(jwt))) return;

            String email = jwtProvider.getEmailFromJWT(jwt);
            UserDetails userDetail = userDetailsService.loadUserByUsername(email);

            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    userDetail, null, userDetail.getAuthorities());

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (Exception e) {
            log.error("JwtAuthenticationFilter - setAuthentication {}", e);
        }
    }

    private String extractJwtFromRequest(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_KEY);

        return StringUtils.hasText(bearerToken) && bearerToken.startsWith(TOKEN_PREFIX) ?
                     bearerToken.replaceFirst(TOKEN_PREFIX, "") : null;
    }

}
