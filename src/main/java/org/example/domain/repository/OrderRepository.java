package org.example.domain.repository;

import org.example.domain.entity.Login;
import org.example.domain.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderRepository extends JpaRepository<Order, Integer> {
    boolean existsByLogin(Login login);

    List<Order> findByLogin(Login login);
}
