package org.example.domain.repository;

import org.example.domain.entity.Cart;
import org.example.domain.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LoginRepository extends JpaRepository<Login, Integer> {
    Optional<Login> findByUserAndPassword(String user, String password);

    Boolean existsByUser(String user);

    Boolean existsByCart(Integer id);

    Login findByCart(Cart cart);
}
