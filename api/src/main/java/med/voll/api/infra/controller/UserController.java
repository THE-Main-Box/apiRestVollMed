package med.voll.api.infra.controller;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import med.voll.api.domain.dto.user.AuthenticationDataDTO;
import med.voll.api.domain.dto.user.TokenJWTDataDTO;
import med.voll.api.domain.dto.user.UserDetailedDataDTO;
import med.voll.api.domain.model.user.User;
import med.voll.api.infra.repository.UserRepository;
import med.voll.api.infra.service.security.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserRepository repository;

    @Autowired
    private AuthenticationManager manager;

    @PostMapping("/register")
    @Transactional
    public ResponseEntity<Object> registerUser(@RequestBody @Valid AuthenticationDataDTO dataDTO, UriComponentsBuilder uriBuilder){
        String hashPassword = passwordEncoder.encode(dataDTO.password());
        User user = new User(dataDTO.login(), hashPassword);
        repository.save(user);

        URI uri = uriBuilder.path("/user/{id}").buildAndExpand(user.getId()).toUri();

        return ResponseEntity.created(uri).body(new TokenJWTDataDTO(tokenService.generateToken(user)));
    }

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody @Valid AuthenticationDataDTO dataDTO) {
        var token = new UsernamePasswordAuthenticationToken(dataDTO.login(), dataDTO.password());
        Authentication authentication = manager.authenticate(token);
        User user = (User) authentication.getPrincipal();

        String tokenToReturn = tokenService.generateToken(user); // Aqui ocorre a geração do token

        return ResponseEntity.ok(new TokenJWTDataDTO(tokenToReturn)); // Aqui você está retornando o token como string
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserDetailedDataDTO> showUser(@PathVariable Long id){
        User user = repository.getReferenceById(id);
        UserDetailedDataDTO userDTO = new UserDetailedDataDTO(user);
        return ResponseEntity.ok(userDTO);
    }

}