package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.domain.entity.Cart;
import org.example.domain.entity.Login;
import org.example.dto.CreateLoginDTO;
import org.example.dto.CredentialsDTO;
import org.example.dto.TokenDTO;
import org.example.exception.InvalidPasswordException;
import org.example.security.jwt.JwtService;
import org.example.service.CartService;
import org.example.service.LoginService;
import org.example.service.impl.UsuarioServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/logins")
@RequiredArgsConstructor //faz construtor de atributo com finals
public class LoginController {

    private final UsuarioServiceImpl usuarioService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    @Autowired
    private LoginService loginService;

    @Autowired
    private CartService cartService;

    @GetMapping
    public List<Login> findById (){

        return loginService.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Login> findById (@PathVariable("id") Integer id){

        Login login = loginService.findById(id);

        return new ResponseEntity<Login>(
                login,
                HttpStatus.OK
        );

    }

    @PostMapping
    public ResponseEntity<Login> create(@RequestBody CreateLoginDTO createLoginDTO){

        Integer cartId = createLoginDTO.getCart();

        Cart cart = cartService.findById(cartId);

        Login login = loginService.create(cart, createLoginDTO.getUser(), createLoginDTO.getPassword(), createLoginDTO.getAdmin());

        return new ResponseEntity<Login>(
                login,
                HttpStatus.CREATED
        );

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {

        loginService.deleteById(id);
    }

    @PostMapping("/auth")
    public TokenDTO autenticar(@RequestBody CredentialsDTO credentials){
        try{
            Login login= Login.builder()
                    .user(credentials.getUser())
                    .password(credentials.getPassword())
                    .build();

            String token = jwtService.gerarToken(login);

            return new TokenDTO(login.getUser(), token);
        }
        catch (UsernameNotFoundException | InvalidPasswordException e){

            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, e.getMessage());

        }
    }

}
