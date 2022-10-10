package org.example.controller;

import org.example.domain.entity.Cart;
import org.example.domain.entity.Login;
import org.example.dto.CreateLoginDTO;
import org.example.dto.LoginDTO;
import org.example.service.CartService;
import org.example.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logins")
public class LoginController {

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

        Login login = loginService.create(cart, createLoginDTO.getUser(), createLoginDTO.getPassword());

        return new ResponseEntity<Login>(
                login,
                HttpStatus.CREATED
        );

    }

    @PostMapping("/login")
    public ResponseEntity<Login> login(@RequestBody LoginDTO loginDTO){


        Login login = loginService.authenticate(loginDTO.getUser(), loginDTO.getPassword());

        return new ResponseEntity<Login>(
                login,
                HttpStatus.OK
        );

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id) {

        loginService.deleteById(id);
    }

}
