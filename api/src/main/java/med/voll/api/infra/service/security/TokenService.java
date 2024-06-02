package med.voll.api.infra.service.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTDecodeException;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import med.voll.api.domain.model.user.User;
import med.voll.api.infra.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.Date;


@Service
public class TokenService {

    @Autowired
    private UserRepository userRepository;

    private String secret;

    private void setSecret(String token) {
        try {
            DecodedJWT decodedJWT = JWT.decode(token);
            String login = decodedJWT.getSubject();
            this.secret = userRepository.findByLogin(login).getPassword();

        } catch (JWTDecodeException e) {
            throw new RuntimeException("Erro ao decodificar o token JWT: " + e.getMessage());
        }
    }

    /*gera o token a partir dos dados do usuario*/
    public String generateToken(User user) {
        try {
            this.secret = user.getPassword();
            Algorithm algorithm = Algorithm.HMAC256(secret);

            Instant expireAt = this.expire();

            return JWT.create()
                    .withIssuer("API voll.med")
                    .withSubject(user.getLogin())
                    .withClaim("id", user.getId())
                    .withExpiresAt(Date.from(expireAt))
                    .sign(algorithm);
        } catch (Exception e) {
            throw new RuntimeException("Erro ao gerar o TOKEN: " + e.getMessage());
        }
    }

    /*  utiliza a password disponibilizada pelo getSecret e o usa para setar o algoritimo
     *   para permitir a verificação do token passado*/
    public void validateToken(String token) {
        try {
            this.setSecret(token);
            Algorithm algorithm = Algorithm.HMAC256(secret);
            JWTVerifier verifier = JWT.require(algorithm)
                    .withIssuer("API voll.med")
                    .build();

            verifier.verify(token);
        } catch (JWTVerificationException e) {
            throw new RuntimeException("TokenJWT Inválido ou expirado" + e);
        }
    }

    private Instant expire() {
        return LocalDateTime.now().plusHours(2).toInstant(ZoneOffset.of("-03:00"));
    }
}

