package org.example.domain.repository;

import org.example.domain.entity.ProductEspecification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductEspecificationRepository extends JpaRepository<ProductEspecification, Integer> {
}
