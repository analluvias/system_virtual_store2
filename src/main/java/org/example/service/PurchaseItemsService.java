package org.example.service;

import org.example.domain.entity.Product;
import org.example.domain.entity.PurchaseItems;
import org.example.domain.repository.PurchaseItemsRepository;
import org.example.exception.nonExistentIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PurchaseItemsService {

    @Autowired
    private PurchaseItemsRepository purchaseItemsRepository;


    public List<PurchaseItems> findAll() {

        return purchaseItemsRepository.findAll();

    }

    public PurchaseItems create(Product product, Integer quantity) {

        PurchaseItems purchaseItems = new PurchaseItems(product, quantity);

        return purchaseItemsRepository.save(purchaseItems);

    }

    public PurchaseItems findById(Integer id){

        Optional<PurchaseItems> purchaseItem = purchaseItemsRepository.findById(id);

        if (purchaseItem.isPresent()){
            return purchaseItem.get();
        }

        throw new nonExistentIdException("The purchase item searched does not exists.");

    }

    public List<PurchaseItems> findAllById(List<Integer> purchases) {

        for (Integer id : purchases) {
            if (!purchaseItemsRepository.existsById(id)){
                throw new nonExistentIdException("The purchase Item of id: "+id+" does not exists.");
            }
        }

        return purchaseItemsRepository.findAllById(purchases);

    }

    public void delete(Integer id) {
        PurchaseItems purchaseItem = findById(id);

        purchaseItemsRepository.delete(purchaseItem);
    }
}
