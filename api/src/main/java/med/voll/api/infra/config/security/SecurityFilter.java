package med.voll.api.infra.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.domain.model.user.User;
import med.voll.api.infra.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    @Autowired
    private TokenService tokenService;


    /*    verifica se o path da uri contém as uris correspondentes ào login e ao cadastro e libera a requisição
     * alem de fazer uma verificação do token e permitir a execução dependendo do resultado*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        String token = getTokenJWT(request);
        if (token != null) {
            tokenService.validateToken(token);
            User user = tokenService.getSecretOwner();

            var auth = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());

            SecurityContextHolder.getContext().setAuthentication(auth);
        }

        filterChain.doFilter(request, response);

    }


    /*  valida se o token é nulo se for lança uma exceção caso contrário retira todos os espaços em branco e
     *   retira a anotação bearer*/
    private String getTokenJWT(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader != null) {
            return authorizationHeader.replace("Bearer ", "");
        } else {
            return null;
        }
    }
}
