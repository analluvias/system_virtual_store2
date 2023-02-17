package org.example.service.impl;


import org.example.domain.entity.Login;
import org.example.domain.repository.LoginRepository;
import org.example.exception.InvalidPasswordException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

//essa classe lida com o banco de dados do login e compara se está tudo correto
// de acordo com o que entramos ao tentar acessar o sistema.
@Service
public class UsuarioServiceImpl implements UserDetailsService{

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private LoginRepository loginRepository;

    //para salvar um novo login
    @Transactional
    public Login salvar(Login login){
        return loginRepository.save(login);
    }

    public UserDetails autenticar (Login usuario){
        UserDetails user = loadUserByUsername(usuario.getUser());

        //passando primeiro a senha do banco, depois a senha digitada
        boolean senhasBatem = encoder.matches(usuario.getPassword(), user.getPassword());

        if (senhasBatem){
            return user;
        }

        throw new InvalidPasswordException("Try again, the password is invalid.");
    }

    //construindo o nosso UserDetails a partir do username
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        //recuperando o login pelo nome de usuario
        //cada user tem um login diferente
        Login login = loginRepository.findByUser(username)
                .orElseThrow(() ->
                        new UsernameNotFoundException("The user does not existis on DB."));


        //se o campo admin for true, então criamos o array com as permissões de admin e user,
        //senão, só daremos a permissão de user
        String[] roles = login.isAdmin() ?
                new String[]{"ADMIN", "USER"} : new String[] {"USER"};


        return User
                .builder()
                .username(login.getUser())
                .password(login.getPassword())//nao vamos criptografar pois no bd jáa estará criptografado
                .roles(roles)
                .build();
    }
}
