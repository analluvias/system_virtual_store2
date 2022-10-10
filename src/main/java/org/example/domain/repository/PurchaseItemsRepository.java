package org.example.domain.repository;

import org.example.domain.entity.PurchaseItems;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PurchaseItemsRepository extends JpaRepository<PurchaseItems, Integer> {
}
