package az.babazade.esocial.security;

import az.babazade.esocial.exception.ExceptionConstant;
import az.babazade.esocial.exception.MyException;
import az.babazade.esocial.services.impl.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {


    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            String jwtToken = extractJwtFromRequest(request);
            if (StringUtils.hasText(jwtToken) && jwtTokenProvider.validateToken(jwtToken)) {
                String username = jwtTokenProvider.getUsernameFromJwt(jwtToken);
                UserDetails user = userDetailsService.loadUserByUsername(username);
                if (user != null) {
                    UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
                    auth.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(auth);

                } else {
                    SecurityContextHolder.getContext().setAuthentication(null);
                }

                filterChain.doFilter(request, response);
            }

        } catch (Exception e) {
            e.printStackTrace();
            return;
        }

    }

    private String extractJwtFromRequest(HttpServletRequest request) throws Exception {

        String bearer = request.getHeader("Authorization");

        String token;

        if (StringUtils.hasText(bearer) && bearer.startsWith("Bearer ")) {

            token = bearer.substring("Bearer".length() + 1);

        } else {
            token = null;
        }

        return token;
    }

}
