package org.example.service;

import org.example.domain.entity.Product;
import org.example.domain.entity.ProductEspecification;
import org.example.domain.repository.ProductRepository;
import org.example.exception.IdHasDependenceException;
import org.example.exception.nonExistentIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public Product create (ProductEspecification productEspecification){

        return productRepository.save(new Product(productEspecification));

    }

    public List<Product> findAll() {

        return productRepository.findAll();

    }

    public Product findById(Integer id){

        Optional<Product> productById = productRepository.findById(id);

        if (productById.isPresent()){
            return productById.get();
        }

        throw new nonExistentIdException("The product searched does not exists.");

    }

    public void delete(Integer id) {
        Product product = findById(id);

        try {
            productRepository.delete(product);
        }catch (Exception e){
            throw new IdHasDependenceException("The product" +
                    " that you tried to delete is part of a order.");
        }

    }
}
