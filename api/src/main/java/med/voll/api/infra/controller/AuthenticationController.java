package med.voll.api.infra.controller;

import jakarta.validation.Valid;
import med.voll.api.domain.dto.user.AuthenticationDataDTO;
import med.voll.api.domain.dto.user.TokenJWTDataDTO;
import med.voll.api.domain.model.user.User;
import med.voll.api.infra.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/login")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager manager;

    @Autowired
    private TokenService tokenService;

    @PostMapping
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDataDTO dataDTO){
        var token = new UsernamePasswordAuthenticationToken(dataDTO.login(), dataDTO.password());
        Authentication authentication = manager.authenticate(token);

        String tokenToReturn = tokenService.generateToken((User) authentication.getPrincipal());

        return ResponseEntity.ok(new TokenJWTDataDTO(tokenToReturn));
    }

}
