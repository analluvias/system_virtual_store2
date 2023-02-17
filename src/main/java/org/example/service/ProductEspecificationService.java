package org.example.service;

import org.example.domain.entity.ProductEspecification;
import org.example.domain.repository.ProductEspecificationRepository;
import org.example.exception.IdHasDependenceException;
import org.example.exception.nonExistentIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductEspecificationService {

    @Autowired
    private ProductEspecificationRepository productEspecificationRepository;


    public ProductEspecification create (ProductEspecification productEspecification){

        return productEspecificationRepository.save(productEspecification);

    }

    public List<ProductEspecification> findAll() {

        return productEspecificationRepository.findAll();

    }

    public ProductEspecification findById(Integer id){

        Optional<ProductEspecification> productEspecification = productEspecificationRepository.findById(id);

        if(productEspecification.isPresent()){
            return productEspecification.get();
        }

        throw new nonExistentIdException("The product especification searched does not exists.");

    }

    public void delete(Integer id) {
        ProductEspecification especification = findById(id);

        try {
            productEspecificationRepository.delete(especification);
        }catch (Exception e){
            throw new IdHasDependenceException("The product Especification" +
                    " that you tried to delete is part of a purchase item.");
        }

    }
}
