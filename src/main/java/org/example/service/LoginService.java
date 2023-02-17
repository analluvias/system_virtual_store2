package org.example.service;

import org.example.domain.entity.Cart;
import org.example.domain.entity.Login;
import org.example.domain.repository.LoginRepository;
import org.example.exception.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoginService {

    @Autowired
    private LoginRepository loginRepository;

    public Login findById(Integer id) {
        Optional<Login> login = loginRepository.findById(id);

        if (login.isPresent()){
            return login.get();
        }

        throw new nonExistentIdException("The login searched does not exists.");
    }

    public Login findByUser(String user){
        Optional<Login> login = loginRepository.findByUser(user);

        if (login.isPresent()){
            return login.get();
        }

        throw new nonExistentIdException("The login entered does not exists.");
    }


    public Login create(Cart cart, String user, String password, Boolean admin) {

        if (!loginRepository.existsByUser(user)){

            try {
                Login login = new Login(user, password, cart, admin);

                return loginRepository.save(login);
            }catch (Exception exception){
                throw new CartInUseException("This Cart is already in use. Choice another one.");
            }

        }

       throw new ExistentUserException("This username is already in use.");

    }

    public Login authenticate(String user, String password) {

        Optional<Login> login = loginRepository.findByUserAndPassword(user, password);

        //mudar essa lógica pra só deixar acessar o sistema se existir esse login
        if (login.isPresent()){
            return login.get();
        }

        throw new nonExistentLoginException("The login does not exists on database.");

    }

    public List<Login> findAll() {

        return loginRepository.findAll();

    }

    public void delete(Login login) {

        loginRepository.delete(login);

    }

    public Login findLoginByCart(Cart cart) {

        return loginRepository.findByCart(cart);
    }

    public void deleteById(Integer id) {
        Login login = findById(id);

        try {
            loginRepository.delete(login);
        }catch (Exception e){
            throw new IdHasDependenceException("The Login you tried to delete is part of a client.");
        }

    }


}
