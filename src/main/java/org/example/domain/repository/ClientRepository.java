package org.example.domain.repository;

import org.example.domain.entity.Client;
import org.example.domain.entity.Login;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {
    Boolean existsByLogin(Login login);
}
