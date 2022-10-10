package org.example.controller;

import org.example.domain.entity.Client;
import org.example.domain.entity.Login;
import org.example.dto.ClientDTO;
import org.example.dto.UpdateClientDTO;
import org.example.service.ClientService;
import org.example.service.LoginService;
import org.hibernate.LazyInitializationException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/clients")
public class ClientController {

    @Autowired
    private ClientService clientService;

    @Autowired
    private LoginService loginService;

    @GetMapping
    public List<Client> findAll (){

        return clientService.findAll();

    }

    @GetMapping("/{id}")
    public ResponseEntity<Client> findById (@PathVariable("id") Integer id){

        Client client = clientService.findById(id);

        return new ResponseEntity<Client>(
                client,
                HttpStatus.OK
        );

    }

    @PostMapping
    public ResponseEntity<Client> create(@RequestBody ClientDTO clientDTO){

        Integer loginId = clientDTO.getLogin();

        Login login = loginService.findById(loginId);

        Client client = clientService.create(login, clientDTO.getName(), clientDTO.getAddress());

        return new ResponseEntity<Client>(
                client,
                HttpStatus.CREATED
        );

    }

    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public ResponseEntity<Client> update(@PathVariable("id") Integer id ,@RequestBody UpdateClientDTO clientDTO){

        Client client = clientService.update(clientDTO.getName(), clientDTO.getAddress(), id);

        return new ResponseEntity<Client>(
                client,
                HttpStatus.NO_CONTENT
        );

    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable("id") Integer id){
        try {

            clientService.delete( id );

        }catch (LazyInitializationException e){

        }


    }

}
