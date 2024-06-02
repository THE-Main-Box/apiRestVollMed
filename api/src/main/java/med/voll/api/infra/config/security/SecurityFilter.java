package med.voll.api.infra.config.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import med.voll.api.infra.repository.UserRepository;
import med.voll.api.infra.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
public class SecurityFilter extends OncePerRequestFilter {

    private static final String REGISTER_PATH = "/register";
    private static final String LOGIN_PATH = "/login";

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository userRepository;

    /*    verifica se o path da uri contém as uris correspondentes ào login e ao cadastro e libera a requisição
     * alem de fazer uma verificação do token e permitir a execução dependendo do resultado*/
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String path = request.getRequestURI();
        String method = request.getMethod();

        if( path.contains(REGISTER_PATH) && method.equals("POST") || path.contains(LOGIN_PATH) && method.equals("POST") ){
            filterChain.doFilter(request, response);
        }else {
            String token = getTokenJWT(request);
            System.out.println(token);
            tokenService.validateToken(token);
            filterChain.doFilter(request, response);
        }

    }

    /*  valida se o token é nulo se for lança uma exceção caso contrário retira todos os espaços em branco e
     *   retira a anotação bearer*/
    private String getTokenJWT(HttpServletRequest request) {
        String authorizationHeader = request.getHeader("Authorization");
        if (authorizationHeader == null) {
            throw new RuntimeException("Token Inexistente ou Malformado");
        }
        return authorizationHeader.replace("Bearer ", "");
    }
}
